package onecenter.com.br.ecommerce.produto.entity.imagens;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ImagensProdutosEntity extends ProdutosEntity{

    private Integer id_imagem;
    private String caminho;
}
