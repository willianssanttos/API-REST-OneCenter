package onecenter.com.br.ecommerce.pedidos.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequest {

    private Integer id_Pessoa;
    private Integer id_produto;
    private Integer quantidade;
}
