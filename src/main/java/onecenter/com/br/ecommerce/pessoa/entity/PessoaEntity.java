package onecenter.com.br.ecommerce.pessoa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaEntity {

    private Integer id_pessoa;
    private String role;
    private String nome_razaosocial;
    private String email;
    private String senha;
    private String telefone;
    private List<RolesEntity> nomeRole = new ArrayList<>();

    @Override
    public String toString() {
        return "PessoaEntity{" +
                "ID= " + id_pessoa +
                ", Role = '" + role + '\'' +
                ", Nome= '" + nome_razaosocial + '\'' +
                ", Email= '" + email + '\'' +
                ", Senha= '" + senha + '\'' +
                ", Telefone= '" + telefone + '\'' +
                '}';
    }
}
