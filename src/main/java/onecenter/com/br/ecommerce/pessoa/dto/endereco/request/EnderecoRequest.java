package onecenter.com.br.ecommerce.pessoa.dto.endereco.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoRequest {

    private Integer idEndereco;
    private String rua;
    private String numero;
    private String bairro;
    private String localidade;
    private String cep;
    private String uf;
}
