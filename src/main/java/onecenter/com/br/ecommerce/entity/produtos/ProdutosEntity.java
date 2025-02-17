package onecenter.com.br.ecommerce.entity.produtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.entity.produtos.categoria.CategoriaEntity;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutosEntity extends CategoriaEntity{

    private Integer id_produto;
    private String nome;
    private Double preco;
    private String produto_imagem;

    @Override
    public String toString() {
        return "ProdutosEntity{" +
                "ID= " + id_produto +
                ", Nome= '" + nome + '\'' +
                ", Preço= " + preco +
                ", Imagem= '" + produto_imagem + '\'' +
                '}';
    }


}
