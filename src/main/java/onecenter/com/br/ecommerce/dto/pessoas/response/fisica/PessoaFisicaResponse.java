package onecenter.com.br.ecommerce.dto.pessoas.response.fisica;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.dto.pessoas.response.PessoaResponse;

import java.time.LocalDate;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

public class PessoaFisicaResponse extends PessoaResponse{

    private Integer idPessoaFisica;
    private String cpf;
    private LocalDate data_nascimento;
}
