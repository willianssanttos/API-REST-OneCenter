package onecenter.com.br.ecommerce.entity.produtos.categoria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaEntity {

    private Integer id_categoria;
    private String nomeCategoria;
}
