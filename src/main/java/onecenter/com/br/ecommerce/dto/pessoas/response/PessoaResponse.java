package onecenter.com.br.ecommerce.dto.pessoas.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.dto.produtos.endereco.response.EnderecoResponse;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaResponse extends EnderecoResponse {

    private Integer idPessoa;
    private String nome_razaosocial;
    private String email;
    private String telefone;
}
