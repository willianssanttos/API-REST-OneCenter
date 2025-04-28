package onecenter.com.br.ecommerce.pedidos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;

import java.sql.Timestamp;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class  PedidosEntity extends ProdutosEntity{

    private Integer idPedido;
    private Integer quantidade;
    private Timestamp dataPedido;
    private String statusPedido;
    private PessoaEntity cliente;

    @Override
    public String toString() {
        return "PedidosEntity{" +
                "ID=" + idPedido +
                ", Quantidade =" + quantidade +
                ", Data do Pedido =" + dataPedido +
                ", Status do Pedido =" + statusPedido +
                ", Produto =" + getIdProduto() +
                ", Cliente =" + cliente +
                '}';
    }
}
