package onecenter.com.br.ecommerce.pedidos.service.pedido;

import onecenter.com.br.ecommerce.pedidos.dto.mapper.PedidoDtoMapper;
import onecenter.com.br.ecommerce.pedidos.dto.request.ItemPedidoRequest;
import onecenter.com.br.ecommerce.pedidos.entity.ItemPedidoEntity;
import onecenter.com.br.ecommerce.pedidos.exception.pedido.ErroAoLocalizarPedidoNotFoundException;
import onecenter.com.br.ecommerce.pedidos.exception.cupom.CupomInvalidoException;
import onecenter.com.br.ecommerce.pedidos.repository.itemPedido.IItemsPedidoRepository;
import onecenter.com.br.ecommerce.pedidos.service.cupom.CupomService;
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
import onecenter.com.br.ecommerce.pedidos.entity.PedidoEntity;

import onecenter.com.br.ecommerce.pedidos.exception.PedidosException;
import onecenter.com.br.ecommerce.pedidos.repository.IPedidosRepository;
import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoEntity;
import onecenter.com.br.ecommerce.pessoa.repository.endereco.IEnderecoRepository;
import onecenter.com.br.ecommerce.pessoa.repository.pessoas.IPessoaRepository;
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
    private IItemsPedidoRepository iItemsPedidoRepository;

    public static final Logger logger = LoggerFactory.getLogger(PedidosService.class);

    @Transactional
    public PedidoResponse criarPedidos(PedidoRequest pedido, Integer token) {
        logger.info(Constantes.DebugRegistroProcesso);

        try {
            PessoaEntity pessoa = iPessoaRepository.buscarIdPessoa(token);

            PedidoEntity inserirPedido = PedidoEntity.builder()
                    .cliente(pessoa)
                    .dataPedido(Timestamp.valueOf(LocalDateTime.now()))
                    .statusPedido("AGUARDANDO_PAGAMENTO")
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
                try {
                   BigDecimal valorDesconto = cupomService.validarEAplicarCupom(
                           pedido.getCupomDesconto(),
                           token
                   );
                   descontos.add(new DescontoPorCupomStrategy(valorDesconto));
                } catch (Exception e){
                    throw new CupomInvalidoException();
                }
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
            logger.error(Constantes.ErroRegistrarNoServidor, e);
            throw new PedidosException();
        }
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> obterTodosPedidos(){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            List<PedidoEntity> pedidos = iPedidosRepository.localizarPedido();
            return pedidos.stream().map(pedidoDtoMapper::mapear).collect(Collectors.toList());
        } catch (Exception e){
            logger.error(Constantes.ErroRegistrarNoServidor, e);
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
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e);
            throw new ErroAoLocalizarPedidoNotFoundException();
        }
    }
}
