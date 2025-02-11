package onecenter.com.br.ecommerce.repository.produtos;

import onecenter.com.br.ecommerce.entity.produtos.ProdutosEntity;

import java.awt.*;
import java.util.List;

public interface IProdutosRepository {

    ProdutosEntity criar(ProdutosEntity produtos);
    List<ProdutosEntity> obterTodosProdutos();
}
