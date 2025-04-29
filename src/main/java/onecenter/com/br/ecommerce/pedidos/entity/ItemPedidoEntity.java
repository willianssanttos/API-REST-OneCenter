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
public class ItemPedidoEntity {

    private Integer idItemPedido;
    private PedidoEntity pedido;
    private ProdutosEntity produtos;
    private Integer quantidade;
    private Double precoUnitario;
    @Override
    public String toString() {
        return "ItemPedidoEntity{" +
                " ID =" + idItemPedido +
                ", Pedido=" + getPedido() +
                ", Produtos=" + getProdutos() +
                ", Quantidade=" + quantidade +
                ", Preco Unitario=" + getProdutos().getPreco() +
                '}';
    }

}
