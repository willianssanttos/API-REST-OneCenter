package onecenter.com.br.ecommerce.entity.pessoas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaEntity {

    private Integer id_pessoa;
    private String nome_razaosocial;
    private String email;
    private String senha;
    private String telefone;

    @Override
    public String toString() {
        return "PessoaEntity{" +
                "ID= " + id_pessoa +
                ", Nome= '" + nome_razaosocial + '\'' +
                ", Email= '" + email + '\'' +
                ", Senha= '" + senha + '\'' +
                ", Telefone= '" + telefone + '\'' +
                '}';
    }
}
