package onecenter.com.br.ecommerce.dto.produtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaRequest {

    private Integer id_categoria;
    private String nome;
}
