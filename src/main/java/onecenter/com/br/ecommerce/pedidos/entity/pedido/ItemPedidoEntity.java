package onecenter.com.br.ecommerce.pedidos.entity.pedido;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoEntity {

    private Integer idItemPedido;
    @JsonIgnore
    @ToString.Exclude
    private PedidoEntity pedido;
    private ProdutosEntity produtos;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private String produtoImagem;
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
