package onecenter.com.br.ecommerce.produto.dto.imagens.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ImagensProdutosResponse extends ProdutosEntity{

    private Integer id_imagem;
    private String caminho;

}
