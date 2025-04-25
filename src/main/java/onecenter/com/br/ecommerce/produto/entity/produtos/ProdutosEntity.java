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

    private Integer idProduto;
    private String nome;
    private Double preco;
    private String descricaoProduto;
    private String produtoImagem;
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
                "ID= " + idProduto +
                ", Nome= '" + nome + '\'' +
                ", Preço= " + preco +
                ", Imagem= '" + produtoImagem + '\'' +
                ", Descrição= '" + descricaoProduto + '\''+
                '}';
    }
}
