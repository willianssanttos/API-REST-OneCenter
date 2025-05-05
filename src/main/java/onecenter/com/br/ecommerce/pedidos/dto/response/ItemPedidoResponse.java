package onecenter.com.br.ecommerce.pedidos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoResponse {

    private Integer idProduto;
    private String nome;
    private String descricaoProduto;
    private String nomeCategoria;
    private String produtoImagem;
    private int quantidade;
    private BigDecimal precoUnitario;
}
