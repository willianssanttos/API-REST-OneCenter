package onecenter.com.br.ecommerce.entity.produtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImagensProdutosEntity {

    private Integer id_imagem;
    private ProdutosEntity id_produto;
    private String caminho;
}
