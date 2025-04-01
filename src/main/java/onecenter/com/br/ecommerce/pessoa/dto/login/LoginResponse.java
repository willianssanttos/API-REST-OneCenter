package onecenter.com.br.ecommerce.pessoa.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String emailUsuario;
    private String senhaUsuario;
}
