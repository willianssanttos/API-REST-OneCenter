package onecenter.com.br.ecommerce.dto.pessoas.request.juridico;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.dto.pessoas.request.PessoaRequest;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaJuridicaRequest extends PessoaRequest {

    private Integer id_pessoa_juridica;
    private String nome_fantasia;
    private String cnpj;
}
