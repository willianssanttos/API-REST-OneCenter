package onecenter.com.br.ecommerce.pedidos.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import onecenter.com.br.ecommerce.produto.dto.produtos.request.ProdutoRequest;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoRequest {

    private Integer idItemPedido;
    private PedidoRequest pedido;
    private ProdutoRequest produtos;
    private Integer quantidade;
    private Double precoUnitario;
}
