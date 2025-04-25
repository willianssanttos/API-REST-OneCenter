package onecenter.com.br.ecommerce.produto.dto.produtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.produto.dto.categoria.request.CategoriaRequest;
import org.springframework.web.multipart.MultipartFile;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoRequest extends CategoriaRequest {

    private Integer idProduto;
    private String nome;
    private Double preco;
    private String descricaoProduto;
    private MultipartFile produto_imagem;
}
