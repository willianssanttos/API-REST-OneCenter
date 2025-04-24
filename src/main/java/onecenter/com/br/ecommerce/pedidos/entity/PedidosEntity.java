package onecenter.com.br.ecommerce.pedidos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class  PedidosEntity extends ProdutosEntity{

    private Integer IdPedido;
    private Integer IdPessoa;
    private Integer quantidade;

    @Override
    public String toString() {
        return "PedidosEntity{" +
                "ID=" + IdPedido +
                ", cliente=" + IdPessoa +
                ", quantidade=" + quantidade +
                '}';
    }
}
