package onecenter.com.br.ecommerce.pessoa.dto.endereco.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoResponse {

    private String rua;
    private String numero;
    private String bairro;
    private String localidade;
    private String cep;
    private String uf;
}
