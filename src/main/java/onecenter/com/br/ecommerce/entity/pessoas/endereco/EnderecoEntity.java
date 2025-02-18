package onecenter.com.br.ecommerce.entity.pessoas.endereco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.entity.pessoas.PessoaEntity;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

public class EnderecoEntity  extends PessoaEntity {

    private Integer idEndereco;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String cep;
    private String uf;

    @Override
    public String toString() {
        return "EnderecoEntity{" +
                "idEndereco=" + idEndereco +
                ", Locradouro='" + rua + '\'' +
                ", numero='" + numero + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", cep='" + cep + '\'' +
                ", uf='" + uf + '\'' +
                '}';
    }
}
