package onecenter.com.br.ecommerce.entity.pessoas.juridica;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.entity.pessoas.PessoaEntity;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaJuridicaEntity extends PessoaEntity {

    private Integer id_pessoa_juridica;
    private String nome_fantasia;
    private String cnpj;

}
