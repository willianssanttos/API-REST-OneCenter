package onecenter.com.br.ecommerce.pessoa.dto.pessoas.response.juridica;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.response.PessoaResponse;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

public class PessoaJuridicaResponse extends PessoaResponse {

    private String nomeFantasia;
    private String cnpj;
}
