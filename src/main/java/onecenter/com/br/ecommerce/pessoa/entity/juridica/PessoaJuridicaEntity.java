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

    private Integer idPessoaJuridica;
    private String nomeFantasia;
    private String cnpj;

    @Override
    public String toString() {
        return "PessoaJuridicaEntity{" +
                "ID= " + idPessoaJuridica +
                ", Nome Fantasia= '" + nomeFantasia + '\'' +
                '}';
    }
}
