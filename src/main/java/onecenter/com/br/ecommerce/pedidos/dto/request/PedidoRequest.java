package onecenter.com.br.ecommerce.pedidos.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import onecenter.com.br.ecommerce.produto.dto.produtos.request.ProdutoRequest;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequest extends ProdutoRequest {

    private Integer quantidade;
    private PessoaEntity cliente;
}
