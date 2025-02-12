package onecenter.com.br.ecommerce.entity.produtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import onecenter.com.br.ecommerce.entity.produtos.categoria.CategoriaEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutosEntity {

    private Integer id_produto;
    private String nome;
    private Double preco;
    private String produto_imagem;
    private CategoriaEntity id_categoria;
}
