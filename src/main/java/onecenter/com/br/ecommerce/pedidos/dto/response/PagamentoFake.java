package onecenter.com.br.ecommerce.pedidos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoFake {

    private Long id;
    private String externalReference;
    private String paymentMethodId;
    private String status;
    private BigDecimal transactionAmount;
    private OffsetDateTime dateApproved;
    private OffsetDateTime dateCreated;
}
