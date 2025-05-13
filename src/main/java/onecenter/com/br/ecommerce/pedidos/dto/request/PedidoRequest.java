package onecenter.com.br.ecommerce.pedidos.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequest {

    @NotEmpty(message = "O pedido deve conter ao menos um item.")
    @Valid
    private List<ItemPedidoRequest> itens;
    private BigDecimal descontoLiberado;
    private Boolean aplicarDescontoManual;
    private String cupomDesconto;

}
