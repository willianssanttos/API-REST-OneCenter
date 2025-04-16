package onecenter.com.br.ecommerce.pessoa.dto.pessoas.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.pessoa.dto.endereco.response.EnderecoResponse;
import onecenter.com.br.ecommerce.pessoa.entity.RolesEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaResponse {

    private Integer idPessoa;
    private String rolePrincipal;
    private String nomeRazaosocial;
    private String email;
    private String telefone;
    private EnderecoResponse endereco;
}
