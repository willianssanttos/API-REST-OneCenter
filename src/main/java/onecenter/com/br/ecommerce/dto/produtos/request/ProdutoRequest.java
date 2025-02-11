package onecenter.com.br.ecommerce.dto.produtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoRequest {

    private Integer id_produto;
    private String nome;
    private Float preco;
    private MultipartFile produto_imagem;
    private Integer id_categoria;
}
