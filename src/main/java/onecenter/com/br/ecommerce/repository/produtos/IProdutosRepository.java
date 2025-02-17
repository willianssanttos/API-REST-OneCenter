package onecenter.com.br.ecommerce.repository.produtos;

import onecenter.com.br.ecommerce.dto.produtos.request.ProdutoRequest;
import onecenter.com.br.ecommerce.dto.produtos.response.ProdutosResponse;
import onecenter.com.br.ecommerce.entity.produtos.ProdutosEntity;
import org.springframework.http.RequestEntity;

import java.util.List;

public interface IProdutosRepository {

    ProdutosEntity criar(ProdutosEntity produtos);
    List<ProdutosEntity> obterTodosProdutos();

    void atualizarProduto(ProdutosResponse editar);

    void excluirProduto(Integer idProduto);

}
