package onecenter.com.br.ecommerce.pessoa.dto.pessoas.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.pessoa.dto.endereco.response.EnderecoResponse;

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
