package onecenter.com.br.ecommerce.entity.produtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaEntity {

    private Integer id_categoria;
    private String nome;
}
