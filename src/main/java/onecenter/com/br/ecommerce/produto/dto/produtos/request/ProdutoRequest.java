package onecenter.com.br.ecommerce.produto.dto.produtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoRequest {

    private Integer id_produto;
    private String nome;
    private Double preco;
    private MultipartFile produto_imagem;
    private Integer id_categoria;
}
