package onecenter.com.br.ecommerce.pessoa.entity.juridica;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaJuridicaEntity extends PessoaEntity {

    private Integer id_pessoa_juridica;
    private String nome_fantasia;
    private String cnpj;

    @Override
    public String toString() {
        return "PessoaJuridicaEntity{" +
                "ID= " + id_pessoa_juridica +
                ", Nome Fantasia= '" + nome_fantasia + '\'' +
                '}';
    }
}
