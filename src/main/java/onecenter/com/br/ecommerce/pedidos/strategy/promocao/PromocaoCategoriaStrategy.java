package onecenter.com.br.ecommerce.pedidos.strategy.promocao;

import onecenter.com.br.ecommerce.pedidos.entity.PedidoEntity;
import onecenter.com.br.ecommerce.pedidos.strategy.DescontoStrategy;

import java.math.BigDecimal;

public class PromocaoCategoriaStrategy implements DescontoStrategy {

    @Override
    public BigDecimal aplicarDesconto(PedidoEntity pedido) {
        return pedido.getItens().stream()
                .filter(item -> item.getProdutos().getNomeCategoria().equalsIgnoreCase("PROMOCAO"))
                .map(item -> item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()))
                        .multiply(BigDecimal.valueOf(0.10))) // 10% de desconto
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
