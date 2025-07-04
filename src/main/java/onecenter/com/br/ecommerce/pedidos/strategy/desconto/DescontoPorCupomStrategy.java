package onecenter.com.br.ecommerce.pedidos.strategy.desconto;

import onecenter.com.br.ecommerce.pedidos.entity.pedido.PedidoEntity;
import onecenter.com.br.ecommerce.pedidos.strategy.DescontoStrategy;

import java.math.BigDecimal;
public class DescontoPorCupomStrategy implements DescontoStrategy {

    private final BigDecimal valorDesconto;

    public DescontoPorCupomStrategy(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    @Override
    public BigDecimal aplicarDesconto(PedidoEntity pedido) {
        return valorDesconto;
    }
}
