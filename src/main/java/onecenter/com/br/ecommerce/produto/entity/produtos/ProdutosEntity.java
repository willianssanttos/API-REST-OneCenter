package onecenter.com.br.ecommerce.produto.entity.produtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.produto.entity.categoria.CategoriaEntity;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutosEntity extends CategoriaEntity{

    private Integer id_produto;
    private String nome;
    private Double preco;
    private String descricaoProduto;
    private String produto_imagem;
    private List<String> imagens;

    public List<String> getImagens() {
        return imagens;
    }

    public void setImagens(List<String> imagens) {
        this.imagens = imagens;
    }

    @Override
    public String toString() {
        return "ProdutosEntity{" +
                "ID= " + id_produto +
                ", Nome= '" + nome + '\'' +
                ", Preço= " + preco +
                ", Imagem= '" + produto_imagem + '\'' +
                ", Descrição= '" + descricaoProduto + '\''+
                '}';
    }


}
