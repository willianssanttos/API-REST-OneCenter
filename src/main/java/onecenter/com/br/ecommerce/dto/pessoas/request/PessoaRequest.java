package onecenter.com.br.ecommerce.dto.pessoas.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaRequest {

    private Integer id_pessoa;
    private String nome_razaosocial;
    private String email;
    private String senha;
    private String telefone;
}
