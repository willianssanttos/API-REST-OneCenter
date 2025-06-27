package onecenter.com.br.ecommerce.produto.repository.mapper;

import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutosRowMapper implements RowMapper<ProdutosEntity> {

    @Autowired
    private String baseUrl;

    public ProdutosRowMapper(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public ProdutosEntity mapRow(ResultSet rs, int rowNum) throws SQLException{

        ProdutosEntity produtos = new ProdutosEntity();
        produtos.setIdProduto(rs.getInt("nr_id_produto"));
        produtos.setNome(rs.getString("nm_nome"));
        produtos.setPreco(rs.getDouble("ds_preco"));
        produtos.setDescricaoProduto(rs.getString("ds_descricao"));
        produtos.setProdutoImagem(baseUrl + rs.getString("ds_imagem_produto"));
        produtos.setNomeCategoria(rs.getString("categoria_nome"));
        return produtos;
    }
}
