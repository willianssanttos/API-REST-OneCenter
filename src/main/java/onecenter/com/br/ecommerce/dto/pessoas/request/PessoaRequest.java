package onecenter.com.br.ecommerce.dto.pessoas.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaRequest {

    private Integer id_pessoa;
    private String nome_razaosocial;
    private String email;
    private String senha;
    private String telefone;
}
