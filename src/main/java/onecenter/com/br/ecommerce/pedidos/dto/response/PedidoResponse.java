package onecenter.com.br.ecommerce.pedidos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponse {

    private Integer IdPedido;
    private Integer IdPessoa;
    private Integer IdProduto;
    private Integer quantidade;

}
