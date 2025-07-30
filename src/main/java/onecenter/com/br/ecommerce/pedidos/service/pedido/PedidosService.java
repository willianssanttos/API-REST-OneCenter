package onecenter.com.br.ecommerce.pedidos.service.pedido;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentRefundClient;
import onecenter.com.br.ecommerce.config.mercadopago.MercadoPagoConfiguracao;
import onecenter.com.br.ecommerce.pedidos.dto.mapper.PedidoDtoMapper;
import onecenter.com.br.ecommerce.pedidos.dto.request.ItemPedidoRequest;
import onecenter.com.br.ecommerce.pedidos.entity.pagamento.PagamentoEntity;
import onecenter.com.br.ecommerce.pedidos.entity.pedido.ItemPedidoEntity;
import onecenter.com.br.ecommerce.pedidos.exception.pedido.CancelarPedidoException;
import onecenter.com.br.ecommerce.pedidos.exception.pedido.ErroAoLocalizarPedidoNotFoundException;
import onecenter.com.br.ecommerce.pedidos.repository.itemPedido.IItemsPedidoRepository;
import onecenter.com.br.ecommerce.pedidos.repository.pagamentos.IPagamentoRepository;
import onecenter.com.br.ecommerce.pedidos.service.cupom.CupomService;
import onecenter.com.br.ecommerce.pedidos.service.email.EmailPagamentoService;
import onecenter.com.br.ecommerce.pedidos.service.email.EmailService;
import onecenter.com.br.ecommerce.pedidos.strategy.*;
import onecenter.com.br.ecommerce.pedidos.strategy.calculadora.CalculadoraDeDesconto;
import onecenter.com.br.ecommerce.pedidos.strategy.clientevip.ClienteVipStrategy;
import onecenter.com.br.ecommerce.pedidos.strategy.desconto.DescontoManualStrategy;
import onecenter.com.br.ecommerce.pedidos.strategy.desconto.DescontoPorCupomStrategy;
import onecenter.com.br.ecommerce.pedidos.strategy.desconto.DescontoProgressivoStrategy;
import onecenter.com.br.ecommerce.pedidos.strategy.promocao.PromocaoCategoriaStrategy;
import onecenter.com.br.ecommerce.pessoa.dto.mapper.EnderecoDtoMapper;
import onecenter.com.br.ecommerce.pedidos.dto.request.PedidoRequest;
import onecenter.com.br.ecommerce.pedidos.dto.response.PedidoResponse;
import onecenter.com.br.ecommerce.pedidos.entity.pedido.PedidoEntity;
import onecenter.com.br.ecommerce.pedidos.exception.pedido.PedidosException;
import onecenter.com.br.ecommerce.pedidos.repository.IPedidosRepository;
import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoEntity;
import onecenter.com.br.ecommerce.pessoa.exception.login.AcessoNegadoException;
import onecenter.com.br.ecommerce.pessoa.repository.endereco.IEnderecoRepository;
import onecenter.com.br.ecommerce.pessoa.repository.pessoas.IPessoaRepository;
import onecenter.com.br.ecommerce.produto.entity.Enums.StatusPagamento;
import onecenter.com.br.ecommerce.produto.entity.Enums.StatusPedido;
import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;
import onecenter.com.br.ecommerce.produto.repository.produtos.IProdutosRepository;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidosService {

    @Autowired
    private EmailService emailService;
    @Autowired
    private CupomService cupomService;
    @Autowired
    private PedidoDtoMapper pedidoDtoMapper;
    @Autowired
    private EnderecoDtoMapper enderecoDtoMapper;
    @Autowired
    private IPessoaRepository iPessoaRepository;
    @Autowired
    private IPedidosRepository iPedidosRepository;
    @Autowired
    private IProdutosRepository iProdutosRepository;
    @Autowired
    private IEnderecoRepository iEnderecoRepository;
    @Autowired
    private IPagamentoRepository iPagamentoRepository;
    @Autowired
    private EmailPagamentoService emailPagamentoService;
    @Autowired
    private IItemsPedidoRepository iItemsPedidoRepository;
    @Autowired
    private MercadoPagoConfiguracao mercadoPagoConfiguracao;

    public static final Logger logger = LoggerFactory.getLogger(PedidosService.class);

    @Transactional
    public PedidoResponse criarPedidos(PedidoRequest pedido, Integer token) {
        logger.info(Constantes.DebugRegistroProcesso);

        try {
            PessoaEntity pessoa = iPessoaRepository.buscarIdPessoa(token);

            PedidoEntity inserirPedido = PedidoEntity.builder()
                    .cliente(pessoa)
                    .dataPedido(Timestamp.valueOf(LocalDateTime.now()))
                    .statusPedido(StatusPagamento.AGUARDANDO_PAGAMENTO.name())
                    .build();

            List<ItemPedidoEntity> itens = new ArrayList<>();
            for (ItemPedidoRequest itemPedido : pedido.getItens()) {
                ProdutosEntity produtos = iProdutosRepository.buscarIdProduto(itemPedido.getProdutos().getIdProduto());
                ItemPedidoEntity item = ItemPedidoEntity.builder()
                        .pedido(inserirPedido)
                        .produtos(produtos)
                        .quantidade(itemPedido.getQuantidade())
                        .precoUnitario(BigDecimal.valueOf(produtos.getPreco()))
                        .build();
                itens.add(item);
                logger.info(Constantes.InfoRegistrar, pedido);
            }
            inserirPedido.setItens(itens);

            List<DescontoStrategy> descontos = new ArrayList<>();
            descontos.add(new ClienteVipStrategy());
            descontos.add(new PromocaoCategoriaStrategy());
            descontos.add(new DescontoProgressivoStrategy());

            if(pedido.getCupomDesconto() != null && !pedido.getCupomDesconto().isBlank()){
               BigDecimal valorDesconto = cupomService.validarEAplicarCupom(
                       pedido.getCupomDesconto(),
                       token
               );
               descontos.add(new DescontoPorCupomStrategy(valorDesconto));
            }

            if (Boolean.TRUE.equals(pedido.getAplicarDescontoManual()) &&
                pedido.getDescontoLiberado() != null &&
                pedido.getDescontoLiberado().compareTo(BigDecimal.ZERO) > 0) {
                descontos.add(new DescontoManualStrategy(pedido.getDescontoLiberado()));
            }

            CalculadoraDeDesconto calculadora = new CalculadoraDeDesconto(descontos);
            BigDecimal descontoTotal = calculadora.calcularDesconto(inserirPedido);
            inserirPedido.setDescontoAplicado(descontoTotal);

            BigDecimal totalItens = itens.stream()
                    .map(item -> item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal valorComDesconto = totalItens.subtract(descontoTotal);

            if (valorComDesconto.compareTo(BigDecimal.ZERO) < 0) {
                valorComDesconto = BigDecimal.ZERO;
            }
            inserirPedido.setValorTotal(valorComDesconto);
            PedidoEntity pedidoCriado = iPedidosRepository.criarPedido(inserirPedido);

            for (ItemPedidoEntity item : itens){
                item.setPedido(pedidoCriado);
                iItemsPedidoRepository.salvarItemPedido(item);
            }

            if(pedido.getCupomDesconto() != null && !pedido.getCupomDesconto().isBlank()){
                cupomService.marcarCupomComoUsado(pedido.getCupomDesconto());
            }

            String destinatario = pedidoCriado.getCliente().getEmail();
            String assunto = "Recebemos seu pedido";
            EnderecoEntity endereco = iEnderecoRepository.obterEnderecoPorIdPessoa(pessoa.getIdPessoa());

            emailService.enviarEmail(
                    destinatario,
                    assunto,
                    pedidoCriado.getIdPedido(),
                    pedidoCriado.getStatusPedido(),
                    pedidoCriado.getDescontoAplicado(),
                    pedidoCriado.getValorTotal(),
                    pedidoCriado.getCliente().getNomeRazaosocial(),
                    pedido.getCupomDesconto(),
                    pedidoCriado.getCliente().getTelefone(),
                    endereco,
                    pedidoCriado.getItens()
            );
            return pedidoDtoMapper.mapear(pedidoCriado);
        }
            catch (Exception e){
            logger.error(Constantes.ErroRegistrarNoServidor, e.getMessage());
            throw new PedidosException();
        }
    }
    @Transactional
    public void cancelarPedido(Integer idPedido, Integer token) {
        logger.info(Constantes.DebugRegistroProcesso);

        try {
            PedidoEntity pedido = iPedidosRepository.buscarPedidosPorId(idPedido);
            if (pedido == null) throw new ErroAoLocalizarPedidoNotFoundException();

            if(!pedido.getCliente().getIdPessoa().equals(token)){
                logger.warn(Constantes.AcessoNegadoConteudoException, token);
                throw new AcessoNegadoException();
            }

            if(StatusPedido.CANCELADO.name().equalsIgnoreCase(pedido.getStatusPedido())) return;

            pedido.setStatusPedido(StatusPedido.CANCELADO.name());
            iPedidosRepository.atualizarStatusPagamento(pedido.getIdPedido(), StatusPedido.CANCELADO.name());

            MercadoPagoConfig.setAccessToken(mercadoPagoConfiguracao.getAccessToken());

            List<PagamentoEntity> pagamentos = iPagamentoRepository.buscarPagamentoRealizado(idPedido);
            pagamentos.stream()
                    .filter(p -> "approved".equalsIgnoreCase(p.getStatusPagamento()))
                    .forEach(p -> {
                        try{
                            Long idTransacao = Long.valueOf(p.getIdTransacaoExterna());
                            PaymentRefundClient refundClient = new PaymentRefundClient();
                            refundClient.refund(idTransacao);

                            iPagamentoRepository.atualizarStatusEstorno(p.getIdPagamento(), StatusPagamento.PENDENTE_ESTORNO.name());
                            logger.info(Constantes.EstornoSolicitado, idTransacao);
                        } catch (Exception e){
                            logger.error(Constantes.ErroEstornoPagamento + p.getIdPagamento(), e.getMessage());
                        }
                    });

            emailPagamentoService.enviarAtualizacaoPagamento(
                    pedido.getCliente().getEmail(),
                    "Cancelamento do Pedido",
                    pedido.getIdPedido(),
                    "CANCELADO",
                    pedido.getDescontoAplicado(),
                    pedido.getValorTotal(),
                    pedido.getCliente().getNomeRazaosocial(),
                    pedido.getCupomDesconto(),
                    pedido.getItens()
            );
        } catch (Exception e){
            logger.error(Constantes.ErroCancelarPedido, e.getMessage());
            throw new CancelarPedidoException();
        }
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> obterTodosPedidos(){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            List<PedidoEntity> pedidos = iPedidosRepository.localizarPedido();
            return pedidos.stream().map(pedidoDtoMapper::mapear).collect(Collectors.toList());
        } catch (Exception e){
            logger.error(Constantes.ErroRegistrarNoServidor, e.getMessage());
            throw new ErroAoLocalizarPedidoNotFoundException();
        }
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> obterPedidosDoCliente(Integer idPessoa) {
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            List<PedidoEntity> pedidos = iPedidosRepository.buscarPedidosPorIdPessoa(idPessoa);
            return pedidos.stream()
                    .map(pedidoDtoMapper::mapear)
                    .collect(Collectors.toList());
        } catch (Exception e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new ErroAoLocalizarPedidoNotFoundException();
        }
    }
}
