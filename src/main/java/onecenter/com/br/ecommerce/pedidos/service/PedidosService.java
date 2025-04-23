package onecenter.com.br.ecommerce.pedidos.service;

import onecenter.com.br.ecommerce.pedidos.dto.request.PedidoRequest;
import onecenter.com.br.ecommerce.pedidos.dto.response.PedidoResponse;
import onecenter.com.br.ecommerce.pedidos.entity.PedidosEntity;
import onecenter.com.br.ecommerce.pedidos.exception.ErroAoLocalizarPedidoNotFoundException;
import onecenter.com.br.ecommerce.pedidos.exception.PedidosException;
import onecenter.com.br.ecommerce.pedidos.repository.IPedidosRepository;
import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import onecenter.com.br.ecommerce.pessoa.repository.pessoas.IPessoaRepository;
import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;
import onecenter.com.br.ecommerce.produto.repository.produtos.IProdutosRepository;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidosService {

    @Autowired
    private IPessoaRepository iPessoaRepository;
    @Autowired
    private IPedidosRepository iPedidosRepository;
    @Autowired
    private IProdutosRepository iProdutosRepository;

    public static final Logger logger = LoggerFactory.getLogger(PedidosService.class);

    public PedidoResponse criarPedidos(PedidoRequest pedido){
        logger.info(Constantes.DebugRegistroProcesso);

        try {
            PessoaEntity idPessoa = iPessoaRepository.buscarIdPessoa(pedido.getId_Pessoa());

            ProdutosEntity idProduto = iProdutosRepository.buscarIdProduto(pedido.getId_produto());

            PedidosEntity inserirPedido = PedidosEntity.builder()
                    .id_produto(idProduto.getId_produto())
                    .IdPessoa(idPessoa.getIdPessoa())
                    .quantidade(pedido.getQuantidade())
                    .build();

            PedidosEntity pedidoCriado = iPedidosRepository.criarPedido(inserirPedido);
            logger.info(Constantes.InfoRegistrar, pedido);
            return mapearPedido(pedidoCriado);
        } catch (Exception e){
            logger.error(Constantes.ErroRegistrarNoServidor);
            throw new PedidosException();
        }
    }

    private PedidoResponse mapearPedido(PedidosEntity pedido){

        PessoaEntity idPessoa = iPessoaRepository.buscarIdPessoa(pedido.getIdPessoa());
        ProdutosEntity idProduto = iProdutosRepository.buscarIdProduto(pedido.getId_produto());

        return PedidoResponse.builder()
                .IdPedido(pedido.getIdPedido())
                .IdPessoa(idPessoa.getIdPessoa())
                .IdProduto(idProduto.getId_produto())
                .quantidade(pedido.getQuantidade())
                .build();
    }

    public List<PedidoResponse> obterTodosProdutos(){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            List<PedidosEntity> pedidos = iPedidosRepository.localizarPedido();
            return pedidos.stream().map(this::mapearPedido).collect(Collectors.toList());
        } catch (Exception e){
            logger.error(Constantes.ErroRegistrarNoServidor);
            throw new ErroAoLocalizarPedidoNotFoundException();
        }
    }
}
