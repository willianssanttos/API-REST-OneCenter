package onecenter.com.br.ecommerce.produto.dto.categoria.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaResponse {

    private String nome;
}
