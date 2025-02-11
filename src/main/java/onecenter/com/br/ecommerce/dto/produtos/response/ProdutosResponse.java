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

    private String nome;
    private Float preco;
    private String produto_imagem;
    private Integer id_categoria;
}
