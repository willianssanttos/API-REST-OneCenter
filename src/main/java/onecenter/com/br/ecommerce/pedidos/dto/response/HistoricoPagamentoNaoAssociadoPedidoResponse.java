package onecenter.com.br.ecommerce.pedidos.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class HistoricoPagamentoNaoAssociadoPedidoResponse {

    private Long idPagamento;
    private String idTransacaoExterna;
    private String formaPagamento;
    private String statusPagamento;
    private BigDecimal valorTotal;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataAprovacao;
    private String motivo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataRegistro;
}
