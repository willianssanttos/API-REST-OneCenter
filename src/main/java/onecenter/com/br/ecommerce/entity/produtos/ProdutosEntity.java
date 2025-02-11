package onecenter.com.br.ecommerce.entity.produtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutosEntity {

    private Integer id_produto;
    private String nome;
    private Float preco;
    private String produto_imagem;
    private Integer id_categoria;
}
