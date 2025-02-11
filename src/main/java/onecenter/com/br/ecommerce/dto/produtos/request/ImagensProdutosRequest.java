package onecenter.com.br.ecommerce.dto.produtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import onecenter.com.br.ecommerce.entity.produtos.ProdutosEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImagensProdutosRequest {

    private Integer id_imagem;
    private ProdutosEntity id_produto;
    private String caminho;
}

