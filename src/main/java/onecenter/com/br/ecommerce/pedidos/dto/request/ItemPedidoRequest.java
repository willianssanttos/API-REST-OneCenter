package onecenter.com.br.ecommerce.pedidos.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "O produto é obrigatório.")
    private ProdutoRequest produtos;
    @NotNull(message = "A quantidade é obrigatória.")
    @Min(value = 1, message = "A quantidade deve ser no mínimo 1.")
    private Integer quantidade;
    private Double precoUnitario;
}
