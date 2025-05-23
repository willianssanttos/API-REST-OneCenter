package onecenter.com.br.ecommerce.produto.dto.produtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.produto.dto.categoria.response.CategoriaResponse;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutosResponse extends CategoriaResponse {

    private Integer idProduto;
    private String nome;
    private String descricaoProduto;
    private String nomeCategoria;
    private Double preco;
    private String produtoImagem;

}
