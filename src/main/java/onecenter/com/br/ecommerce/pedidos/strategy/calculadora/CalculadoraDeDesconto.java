package onecenter.com.br.ecommerce.pedidos.strategy.calculadora;

import onecenter.com.br.ecommerce.pedidos.entity.PedidoEntity;
import onecenter.com.br.ecommerce.pedidos.strategy.DescontoStrategy;

import java.math.BigDecimal;
import java.util.List;

public class CalculadoraDeDesconto {

    private final List<DescontoStrategy> descontos;

    public CalculadoraDeDesconto(List<DescontoStrategy> descontos){
        this.descontos = descontos;
    }

    public BigDecimal calcularDesconto(PedidoEntity pedido){
        return descontos.stream()
                .map(descontos -> descontos.aplicarDesconto(pedido))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
