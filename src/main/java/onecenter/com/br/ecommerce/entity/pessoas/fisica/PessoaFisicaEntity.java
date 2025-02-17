package onecenter.com.br.ecommerce.entity.pessoas.fisica;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.entity.pessoas.PessoaEntity;
import java.time.LocalDate;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaFisicaEntity extends PessoaEntity {

    private String cpf;
    private LocalDate data_nascimento;

    @Override
    public String toString() {
        return "PessoaFisicaEntity{" +
                " Data de Nascimento= " + data_nascimento +
                '}';
    }

}
