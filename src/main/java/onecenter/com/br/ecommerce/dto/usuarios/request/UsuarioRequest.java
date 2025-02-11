package onecenter.com.br.ecommerce.dto.usuarios.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {

    private Integer id_usuario;
    private String email;
    private String senha;
}
