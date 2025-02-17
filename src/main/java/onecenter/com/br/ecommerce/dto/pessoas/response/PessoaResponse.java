package onecenter.com.br.ecommerce.dto.pessoas.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaResponse {

    private Integer idPessoa;
    private String nome_razaosocial;
    private String email;
    private String senha;
    private String telefone;
}
