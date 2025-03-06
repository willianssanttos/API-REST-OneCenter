package onecenter.com.br.ecommerce.produto.repository.produtos;

import onecenter.com.br.ecommerce.produto.dto.produtos.response.ProdutosResponse;
import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;

import java.util.List;

public interface IProdutosRepository {

    ProdutosEntity criar(ProdutosEntity produtos);

    ProdutosEntity buscarIdProduto(Integer IdProduto);
    List<ProdutosEntity> obterTodosProdutos();

    ProdutosEntity atualizarProduto(ProdutosEntity editar);

    void excluirProduto(Integer idProduto);

}
