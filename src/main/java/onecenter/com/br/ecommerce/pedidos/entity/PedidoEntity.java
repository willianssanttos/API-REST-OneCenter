package onecenter.com.br.ecommerce.pedidos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"cliente", "itens"})
public class PedidoEntity{

    private Integer idPedido;
    private Timestamp dataPedido;
    private String statusPedido;
    private BigDecimal descontoAplicado;
    private PessoaEntity cliente;
    private List<ItemPedidoEntity> itens;
    private BigDecimal valorTotal;
    private String cupomDesconto;

    @Override
    public String toString() {
        return "PedidosEntity{" +
                "ID=" + idPedido +
                ", Data do Pedido =" + dataPedido +
                ", Status do Pedido =" + statusPedido +
                ", Desconto Aplicado =" + descontoAplicado +
                ", Valor Tatal =" + valorTotal +
                ", Cupom de Desconto =" + cupomDesconto +
                '}';
    }
}
