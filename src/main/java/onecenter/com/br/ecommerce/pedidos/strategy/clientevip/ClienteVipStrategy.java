package onecenter.com.br.ecommerce.pedidos.strategy.clientevip;

import onecenter.com.br.ecommerce.pedidos.entity.pedido.PedidoEntity;
import onecenter.com.br.ecommerce.pedidos.strategy.DescontoStrategy;

import java.math.BigDecimal;

public class ClienteVipStrategy implements DescontoStrategy {

    @Override
    public BigDecimal aplicarDesconto(PedidoEntity pedido) {
        if (pedido.getCliente().isVip()) {
            return calcularTotalPedido(pedido).multiply(BigDecimal.valueOf(0.15)); // 15% de desconto
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal calcularTotalPedido(PedidoEntity pedido) {
        return pedido.getItens().stream()
                .map(item -> item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
