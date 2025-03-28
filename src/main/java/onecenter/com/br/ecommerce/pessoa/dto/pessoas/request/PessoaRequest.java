package onecenter.com.br.ecommerce.pessoa.dto.pessoas.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.pessoa.dto.endereco.request.EnderecoRequest;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaRequest extends EnderecoRequest {

    private Integer id_pessoa;
    private String role;
    private String nome_razaosocial;
    private String email;
    private String senha;
    private String telefone;
}
