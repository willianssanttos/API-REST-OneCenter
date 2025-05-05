package onecenter.com.br.ecommerce.pedidos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.produto.dto.produtos.response.ProdutosResponse;

import java.math.BigDecimal;


@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoResponse extends ProdutosResponse {

    private Integer quantidade;
    private BigDecimal precoUnitario;
}
