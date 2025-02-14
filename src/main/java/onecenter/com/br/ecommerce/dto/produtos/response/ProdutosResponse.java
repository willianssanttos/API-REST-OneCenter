package onecenter.com.br.ecommerce.dto.produtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutosResponse {

    private Integer id_produto;
    private String nome;
    private Double preco;
    private String produto_imagem;
    private Integer id_categoria;

}
