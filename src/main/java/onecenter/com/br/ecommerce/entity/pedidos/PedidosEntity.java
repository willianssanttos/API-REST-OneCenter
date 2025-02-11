package onecenter.com.br.ecommerce.entity.pedidos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import onecenter.com.br.ecommerce.entity.produtos.ProdutosEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidosEntity {

    private Integer id_pedido;
    private ProdutosEntity produtosEntity;
    private Integer quantidade;
}
