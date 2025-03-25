package onecenter.com.br.ecommerce.produto.dto.imagens.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.produto.dto.produtos.request.ProdutoRequest;
import org.springframework.web.multipart.MultipartFile;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ImagensProdutosRequest extends ProdutoRequest {

    private Integer id_imagem;
    private MultipartFile caminho;
}
