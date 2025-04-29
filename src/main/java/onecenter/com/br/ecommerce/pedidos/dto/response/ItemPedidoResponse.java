package onecenter.com.br.ecommerce.pedidos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoResponse {

    private Integer quantidade;
    private Double precoUnitario;
}
