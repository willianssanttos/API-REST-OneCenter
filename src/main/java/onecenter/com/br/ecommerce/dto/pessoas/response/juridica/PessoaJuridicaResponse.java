package onecenter.com.br.ecommerce.dto.pessoas.response.juridica;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.dto.pessoas.response.PessoaResponse;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

public class PessoaJuridicaResponse extends PessoaResponse {

    private Integer id_pessoa_juridica;
    private String nome_fantasia;
    private String cnpj;
}
