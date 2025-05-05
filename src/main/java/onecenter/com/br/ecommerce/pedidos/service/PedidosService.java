package onecenter.com.br.ecommerce.pedidos.service;

import onecenter.com.br.ecommerce.pedidos.dto.mapper.PedidoDtoMapper;
import onecenter.com.br.ecommerce.pedidos.dto.request.ItemPedidoRequest;
import onecenter.com.br.ecommerce.pedidos.entity.ItemPedidoEntity;
import onecenter.com.br.ecommerce.pedidos.repository.itemPedido.IItemsPedidoRepository;
import onecenter.com.br.ecommerce.pedidos.strategy.*;
import onecenter.com.br.ecommerce.pedidos.strategy.calculadora.CalculadoraDeDesconto;
import onecenter.com.br.ecommerce.pedidos.strategy.clientevip.ClienteVipStrategy;
import onecenter.com.br.ecommerce.pedidos.strategy.desconto.DescontoProgressivoStrategy;
import onecenter.com.br.ecommerce.pedidos.strategy.promocao.PromocaoCategoriaStrategy;
import onecenter.com.br.ecommerce.pessoa.dto.mapper.EnderecoDtoMapper;
import onecenter.com.br.ecommerce.pedidos.dto.request.PedidoRequest;
import onecenter.com.br.ecommerce.pedidos.dto.response.PedidoResponse;
import onecenter.com.br.ecommerce.pedidos.entity.PedidoEntity;
import onecenter.com.br.ecommerce.pedidos.exception.ErroAoLocalizarPedidoNotFoundException;
import onecenter.com.br.ecommerce.pedidos.exception.PedidosException;
import onecenter.com.br.ecommerce.pedidos.repository.IPedidosRepository;
import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
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
    public PedidoResponse criarPedidos(PedidoRequest pedido){
        logger.info(Constantes.DebugRegistroProcesso);

        try {

            PessoaEntity pessoa = iPessoaRepository.buscarIdPessoa(pedido.getCliente().getIdPessoa());

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

            List<DescontoStrategy> descontos = List.of(
                    new ClienteVipStrategy(),
                    new PromocaoCategoriaStrategy(),
                    new DescontoProgressivoStrategy()
            );

            CalculadoraDeDesconto calculadora = new CalculadoraDeDesconto(descontos);
            BigDecimal descontoTotal = calculadora.calcularDesconto(inserirPedido);
            inserirPedido.setDescontoAplicado(descontoTotal);

            PedidoEntity pedidoCriado = iPedidosRepository.criarPedido(inserirPedido);

            for (ItemPedidoEntity item : itens){
                item.setPedido(pedidoCriado);
                iItemsPedidoRepository.salvarItemPedido(item);
            }

            return pedidoDtoMapper.mapear(pedidoCriado);
        }
            catch (Exception e){
            logger.error(Constantes.ErroRegistrarNoServidor);
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
            logger.error(Constantes.ErroRegistrarNoServidor);
            throw new ErroAoLocalizarPedidoNotFoundException();
        }
    }
}
