package onecenter.com.br.ecommerce.repository.produtos.categoria;

import onecenter.com.br.ecommerce.entity.produtos.categoria.CategoriaEntity;

import java.util.List;

public interface ICategoriaRepository {

    CategoriaEntity criarCategoria(CategoriaEntity categoria);

    List<CategoriaEntity> obterTodasCategoria();

    CategoriaEntity obterCategoriaPorId(Integer idCategoria);
}
