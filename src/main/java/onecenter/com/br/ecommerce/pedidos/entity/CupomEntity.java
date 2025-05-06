package onecenter.com.br.ecommerce.pedidos.entity;

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
public class CupomEntity {

    private Integer idCupom;
    private String codigo;
    private BigDecimal valorDesconto;
    private LocalDateTime dataValidade;
    private Boolean cupomUsado;

    @Override
    public String toString() {
        return "CupomEntity{" +
                "ID =" + idCupom +
                ", Cupom ='" + codigo + '\'' +
                ", Valor de Desconto =" + valorDesconto +
                ", Data de Validade =" + dataValidade +
                ", Cupom foi Usado=" + cupomUsado +
                '}';
    }
}
