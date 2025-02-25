package onecenter.com.br.ecommerce.pessoa.repository.mapper;

import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PessoaRowMapper implements RowMapper<PessoaEntity> {

    @Override
    public PessoaEntity mapRow(ResultSet rs, int rowNum) throws SQLException{
        PessoaEntity pessoa = new PessoaEntity();
        pessoa.setId_pessoa(rs.getInt(1));
        pessoa.setNome_razaosocial(rs.getString(2));
        pessoa.setEmail(rs.getString(3));
        pessoa.setSenha(rs.getString(4));
        pessoa.setTelefone(rs.getString(5));
        return pessoa;
    }
}
