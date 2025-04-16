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
public class PessoaRequest {

    private Integer IdPessoa;
    private String rolePrincipal;
    private String nomeRazaosocial;
    private String email;
    private String senha;
    private String telefone;
    private EnderecoRequest endereco;

}
