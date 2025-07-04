package onecenter.com.br.ecommerce.pedidos.repository.itemPedido;

import onecenter.com.br.ecommerce.pedidos.entity.pedido.ItemPedidoEntity;

public interface IItemsPedidoRepository {

    ItemPedidoEntity salvarItemPedido(ItemPedidoEntity itemPedido);

    ItemPedidoEntity obterItemPedidoPorIdP(Integer idItemPedido);
}
