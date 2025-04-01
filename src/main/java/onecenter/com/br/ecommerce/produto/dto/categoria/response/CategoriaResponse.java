package onecenter.com.br.ecommerce.produto.dto.categoria.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaResponse {

    private String nomeCategoria;
}
