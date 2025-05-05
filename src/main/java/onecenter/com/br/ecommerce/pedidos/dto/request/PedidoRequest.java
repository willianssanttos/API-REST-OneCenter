package onecenter.com.br.ecommerce.pedidos.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.request.PessoaRequest;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequest {


    private PessoaRequest cliente;
    private List<ItemPedidoRequest> itens;

}
