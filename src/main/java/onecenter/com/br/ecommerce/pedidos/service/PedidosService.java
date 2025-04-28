package onecenter.com.br.ecommerce.pedidos.service;

import onecenter.com.br.ecommerce.pedidos.dto.mapper.PedidoDtoMapper;
import onecenter.com.br.ecommerce.pessoa.dto.mapper.EnderecoDtoMapper;
import onecenter.com.br.ecommerce.pedidos.dto.request.PedidoRequest;
import onecenter.com.br.ecommerce.pedidos.dto.response.PedidoResponse;
import onecenter.com.br.ecommerce.pedidos.entity.PedidosEntity;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
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


    public static final Logger logger = LoggerFactory.getLogger(PedidosService.class);

    @Transactional
    public PedidoResponse criarPedidos(PedidoRequest pedido){
        logger.info(Constantes.DebugRegistroProcesso);

        try {
            ProdutosEntity produtos = iProdutosRepository.buscarIdProduto(pedido.getIdProduto());
            PessoaEntity pessoa = iPessoaRepository.buscarIdPessoa(pedido.getCliente().getIdPessoa());

            PedidosEntity inserirPedido = PedidosEntity.builder()
                    .idProduto(produtos.getIdProduto())
                    .cliente(pessoa)
                    .quantidade(pedido.getQuantidade())
                    .dataPedido(Timestamp.valueOf(LocalDateTime.now()))
                    .statusPedido("AGUARDANDO_PAGAMENTO")
                    .build();

            PedidosEntity pedidoCriado = iPedidosRepository.criarPedido(inserirPedido);
            logger.info(Constantes.InfoRegistrar, pedido);
            return pedidoDtoMapper.mapear(pedidoCriado);
        } catch (Exception e){
            logger.error(Constantes.ErroRegistrarNoServidor);
            throw new PedidosException();
        }
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> obterTodosPedidos(){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            List<PedidosEntity> pedidos = iPedidosRepository.localizarPedido();
            return pedidos.stream().map(pedidoDtoMapper::mapear).collect(Collectors.toList());
        } catch (Exception e){
            logger.error(Constantes.ErroRegistrarNoServidor);
            throw new ErroAoLocalizarPedidoNotFoundException();
        }
    }
}
