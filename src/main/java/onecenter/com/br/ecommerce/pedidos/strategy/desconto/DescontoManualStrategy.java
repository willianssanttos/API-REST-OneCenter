package onecenter.com.br.ecommerce.pedidos.strategy.desconto;

import onecenter.com.br.ecommerce.pedidos.entity.pedido.PedidoEntity;
import onecenter.com.br.ecommerce.pedidos.strategy.DescontoStrategy;

import java.math.BigDecimal;

public class DescontoManualStrategy implements DescontoStrategy {

    private final BigDecimal percentual;

    public DescontoManualStrategy(BigDecimal percentual) {
        this.percentual = percentual;
    }

    @Override
    public BigDecimal aplicarDesconto(PedidoEntity pedido) {
        BigDecimal total = pedido.getItens().stream()
                .map(item -> item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return total.multiply(percentual);
    }
}
