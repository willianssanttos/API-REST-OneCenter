package onecenter.com.br.ecommerce.produto.repository.mapper;

import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutosRowMapper implements RowMapper<ProdutosEntity> {
    public ProdutosEntity mapRow(ResultSet rs, int rowNum) throws SQLException{
        ProdutosEntity produtos = new ProdutosEntity();
        produtos.setId_produto(rs.getInt(1));
        produtos.setNome(rs.getString(2));
        produtos.setPreco(rs.getDouble(3));
        produtos.setDescricaoProduto(rs.getString(4));
        produtos.setProduto_imagem(rs.getString(5));
        produtos.setNomeCategoria(rs.getString(6));
        //produtos.setNomeCategoria();

        return produtos;
    }
}
