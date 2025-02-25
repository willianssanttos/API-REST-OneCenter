package onecenter.com.br.ecommerce.produto.dto.produtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutosResponse {

    private Integer id_produto;
    private String nome;
    private Double preco;
    private String produto_imagem;
    private Integer id_categoria;

}
