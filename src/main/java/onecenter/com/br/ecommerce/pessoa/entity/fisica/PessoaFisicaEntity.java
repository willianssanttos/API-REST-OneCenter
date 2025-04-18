package onecenter.com.br.ecommerce.pessoa.entity.fisica;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;

import java.sql.Timestamp;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaFisicaEntity extends PessoaEntity {

    private Integer idPessoa_Fisica;
    private Integer idPessoa;
    private String cpf;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Timestamp data_nascimento;


    @Override
    public String toString() {
        return "PessoaFisicaEntity{" +
                " Data de Nascimento= " + data_nascimento +
                '}';
    }

}
