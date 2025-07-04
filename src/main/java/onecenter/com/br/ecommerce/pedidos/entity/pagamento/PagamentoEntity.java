package onecenter.com.br.ecommerce.pedidos.entity.pagamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import onecenter.com.br.ecommerce.pedidos.entity.pedido.PedidoEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoEntity {

    private Integer idPagamento;
    private String formaPagamento;
    private String statusPagamento;
    private BigDecimal valorTotal;
    private Timestamp dataPagamento;
    private PedidoEntity pedido;
    private String idTransacaoExterna;
}
