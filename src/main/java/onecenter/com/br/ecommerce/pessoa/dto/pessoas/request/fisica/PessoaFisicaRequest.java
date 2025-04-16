package onecenter.com.br.ecommerce.pessoa.dto.pessoas.request.fisica;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.request.PessoaRequest;

import java.sql.Timestamp;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

public class PessoaFisicaRequest extends PessoaRequest {


    private String cpf;
    private Timestamp dataNascimento;

}
