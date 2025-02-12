package onecenter.com.br.ecommerce.dto.pessoas.request.fisica;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.dto.pessoas.request.PessoaRequest;

import java.time.LocalDate;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

public class PessoaFisicaRequest extends PessoaRequest {

    private Integer id_pessoa_fisica;
    private Character cpf;
    private LocalDate data_nascimento;
}
