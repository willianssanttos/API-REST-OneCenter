package onecenter.com.br.ecommerce.pedidos.repository;

import onecenter.com.br.ecommerce.pedidos.dto.response.PedidoResponse;
import onecenter.com.br.ecommerce.pedidos.entity.PedidosEntity;

import java.util.List;

public interface IPedidosRepository {

    PedidosEntity criarPedido(PedidosEntity pedido);
    List<PedidosEntity> localizarPedido();

}
