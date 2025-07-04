package onecenter.com.br.ecommerce.pedidos.strategy;

import onecenter.com.br.ecommerce.pedidos.entity.pedido.PedidoEntity;

import java.math.BigDecimal;

public interface DescontoStrategy {
    BigDecimal aplicarDesconto(PedidoEntity pedido);
}
