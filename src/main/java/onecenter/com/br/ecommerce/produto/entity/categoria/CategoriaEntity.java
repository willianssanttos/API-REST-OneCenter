package onecenter.com.br.ecommerce.produto.entity.categoria;

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

    @Override
    public String toString() {
        return "CategoriaEntity{" +
                "ID= " + id_categoria +
                ", Categoria= '" + nomeCategoria + '\'' +
                '}';
    }
}
