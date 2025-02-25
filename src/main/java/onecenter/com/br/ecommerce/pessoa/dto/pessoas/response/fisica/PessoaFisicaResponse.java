package onecenter.com.br.ecommerce.pessoa.dto.pessoas.response.fisica;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.response.PessoaResponse;

import java.sql.Timestamp;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

public class PessoaFisicaResponse extends PessoaResponse{

    private Integer idPessoa;
    private String cpf;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Timestamp data_nascimento;

}
