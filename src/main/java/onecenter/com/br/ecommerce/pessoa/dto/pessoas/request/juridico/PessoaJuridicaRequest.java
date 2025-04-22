package onecenter.com.br.ecommerce.pessoa.dto.pessoas.request.juridico;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.request.PessoaRequest;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaJuridicaRequest extends PessoaRequest {

    private String nomeFantasia;
    private String cnpj;
}
