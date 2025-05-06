package onecenter.com.br.ecommerce.pedidos.dto.request;

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
public class CupomRequest {

    private Integer idCupom;
    private String cupom;
    private BigDecimal valorDesconto;
    private LocalDateTime dataValidade;
}
