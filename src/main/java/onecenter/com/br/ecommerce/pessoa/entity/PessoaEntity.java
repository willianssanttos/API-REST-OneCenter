package onecenter.com.br.ecommerce.pessoa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaEntity {

    private Integer idPessoa;
    private String rolePrincipal;
    private String nomeRazaosocial;
    private String email;
    private String senha;
    private String telefone;
    private EnderecoEntity endereco;
    private List<RolesEntity> roles = new ArrayList<>();

    @Override
    public String toString() {
        return "PessoaEntity{" +
                " ID= " + idPessoa +
                ", Nome= '" + nomeRazaosocial + '\'' +
                ", Email= '" + email + '\'' +
                ", Senha= '" + senha + '\'' +
                ", Telefone= '" + telefone + '\'' +
                '}';
    }
}
