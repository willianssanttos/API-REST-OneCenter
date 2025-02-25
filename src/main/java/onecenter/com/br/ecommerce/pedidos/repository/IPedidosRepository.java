package onecenter.com.br.ecommerce.pedidos.repository;

import onecenter.com.br.ecommerce.pedidos.entity.PedidosEntity;

public interface IPedidosRepository {

    PedidosEntity criarPedido(PedidosEntity pedido);
}
