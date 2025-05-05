package onecenter.com.br.ecommerce.pedidos.strategy.desconto;

import onecenter.com.br.ecommerce.pedidos.entity.ItemPedidoEntity;
import onecenter.com.br.ecommerce.pedidos.entity.PedidoEntity;
import onecenter.com.br.ecommerce.pedidos.strategy.DescontoStrategy;

import java.math.BigDecimal;

public class DescontoProgressivoStrategy implements DescontoStrategy {

    @Override
    public BigDecimal aplicarDesconto(PedidoEntity pedido) {
        int quantidadeTotal = pedido.getItens().stream()
                .mapToInt(ItemPedidoEntity::getQuantidade)
                .sum();

        if (quantidadeTotal >= 5) {
            BigDecimal totalPedido = pedido.getItens().stream()
                    .map(item -> item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return totalPedido.multiply(BigDecimal.valueOf(0.10)); // 10% de desconto
        }

        return BigDecimal.ZERO;
    }
}
