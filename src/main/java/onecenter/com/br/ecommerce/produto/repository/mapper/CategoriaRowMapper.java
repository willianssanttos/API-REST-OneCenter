package onecenter.com.br.ecommerce.produto.repository.mapper;

import onecenter.com.br.ecommerce.produto.entity.categoria.CategoriaEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoriaRowMapper implements RowMapper<CategoriaEntity> {

    public CategoriaEntity mapRow(ResultSet rs, int rowNum) throws SQLException{
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setId_categoria(rs.getInt(1));
        categoria.setNomeCategoria(rs.getString(2));
        return categoria;
    }
}
