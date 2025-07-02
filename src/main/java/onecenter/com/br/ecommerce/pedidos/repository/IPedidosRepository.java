package onecenter.com.br.ecommerce.pedidos.repository;

import onecenter.com.br.ecommerce.pedidos.entity.PedidoEntity;

import java.util.List;

public interface IPedidosRepository {

    PedidoEntity criarPedido(PedidoEntity pedido);
    List<PedidoEntity> localizarPedido();
    List<PedidoEntity> buscarPedidosPorIdPessoa(Integer idPessoa);
    PedidoEntity buscarPedidosPorId(Integer idPedido);
    void atualizarStatusPagamento(Integer idPedido, String novoStatus);

}
