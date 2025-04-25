package onecenter.com.br.ecommerce.produto.repository.mapper;

import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutosRowMapper implements RowMapper<ProdutosEntity> {
    public ProdutosEntity mapRow(ResultSet rs, int rowNum) throws SQLException{
        String baseUrl = "http://localhost:8080";
        ProdutosEntity produtos = new ProdutosEntity();
        produtos.setIdProduto(rs.getInt(1));
        produtos.setNome(rs.getString(2));
        produtos.setPreco(rs.getDouble(3));
        produtos.setDescricaoProduto(rs.getString(4));
        produtos.setProdutoImagem(baseUrl + rs.getString(5));
        produtos.setNomeCategoria(rs.getString(6));
        return produtos;
    }
}
