package onecenter.com.br.ecommerce.pedidos.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidosEntity {

    private Integer id_pedido;
    private ProdutosEntity produtosEntity;
    private Integer quantidade;

    @Override
    public String toString() {
        return "PedidosEntity{" +
                "id_pedido=" + id_pedido +
                ", produtosEntity=" + produtosEntity +
                ", quantidade=" + quantidade +
                '}';
    }
}
