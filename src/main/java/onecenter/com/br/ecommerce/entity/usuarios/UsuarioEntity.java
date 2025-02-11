package onecenter.com.br.ecommerce.entity.usuarios;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEntity {

    private Integer id_usuario;
    private String email;
    private String senha;
}
