package onecenter.com.br.ecommerce.pedidos.service;

import onecenter.com.br.ecommerce.pedidos.dto.request.PedidoRequest;
import onecenter.com.br.ecommerce.pedidos.dto.response.PedidoResponse;
import onecenter.com.br.ecommerce.pedidos.entity.PedidosEntity;
import onecenter.com.br.ecommerce.pedidos.exception.PedidosException;
import onecenter.com.br.ecommerce.pedidos.repository.IPedidosRepository;
import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import onecenter.com.br.ecommerce.pessoa.repository.pessoas.IPessoaRepository;
import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;
import onecenter.com.br.ecommerce.produto.repository.produtos.IProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidosService {

    @Autowired
    private IPessoaRepository iPessoaRepository;
    @Autowired
    private IPedidosRepository iPedidosRepository;
    @Autowired
    private IProdutosRepository iProdutosRepository;

    public PedidoResponse criarPedidos(PedidoRequest pedido){

        try {
            PessoaEntity idPessoa = iPessoaRepository.buscarIdPessoa(pedido.getId_Pessoa());

            ProdutosEntity idProduto = iProdutosRepository.buscarIdProduto(pedido.getId_produto());

            PedidosEntity inserirPedido = PedidosEntity.builder()
                    .id_produto(idProduto.getId_produto())
                    .IdPessoa(idPessoa.getId_pessoa())
                    .quantidade(pedido.getQuantidade())
                    .build();

            PedidosEntity pedidoCriado = iPedidosRepository.criarPedido(inserirPedido);
            return mapearPedido(pedidoCriado);
        } catch (Exception e){
            throw new PedidosException();
        }
    }

    private PedidoResponse mapearPedido(PedidosEntity pedido){

        PessoaEntity idPessoa = iPessoaRepository.buscarIdPessoa(pedido.getIdPessoa());
        ProdutosEntity idProduto = iProdutosRepository.buscarIdProduto(pedido.getId_produto());

        return PedidoResponse.builder()
                .IdPedido(pedido.getIdPedido())
                .IdPessoa(idPessoa.getId_pessoa())
                .IdProduto(idProduto.getId_produto())
                .quantidade(pedido.getQuantidade())
                .build();
    }
}
