package onecenter.com.br.ecommerce.dto.pessoas.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaResponse {

    private String nome_razaosocial;
    private String email;
    private String senha;
    private String telefone;
}
