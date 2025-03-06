package onecenter.com.br.ecommerce.produto.dto.categoria.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaRequest {

    private Integer id_categoria;
    private String nomeCategoria;
}
