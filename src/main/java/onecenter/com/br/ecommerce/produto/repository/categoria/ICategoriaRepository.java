package onecenter.com.br.ecommerce.produto.repository.categoria;

import onecenter.com.br.ecommerce.produto.entity.categoria.CategoriaEntity;

import java.util.List;

public interface ICategoriaRepository {

    CategoriaEntity criarCategoria(CategoriaEntity categoria);

    List<CategoriaEntity> obterTodasCategoria();

    Integer obterCategoriaPorNome(String nomeCategoria);
}
