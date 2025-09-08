package onecenter.com.br.ecommerce.pedidos.entity.pagamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoPagamentoNaoAssociadoPedidoEntity {

    private Integer idPagamento;
    private String idTransacaoExterna;
    private String formaPagamento;
    private String statusPagamento;
    private BigDecimal valorTotal;
    private LocalDateTime dataAprovacao;
    private String motivo;
    private LocalDateTime dataRegistro = LocalDateTime.now();

}
