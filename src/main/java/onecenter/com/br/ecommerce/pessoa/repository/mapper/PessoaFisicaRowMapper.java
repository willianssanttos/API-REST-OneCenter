package onecenter.com.br.ecommerce.pessoa.repository.mapper;

import onecenter.com.br.ecommerce.pessoa.entity.fisica.PessoaFisicaEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PessoaFisicaRowMapper implements RowMapper<PessoaFisicaEntity> {

    @Override
    public PessoaFisicaEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        PessoaFisicaEntity pessoa = new PessoaFisicaEntity();
        pessoa.setId_pessoa(rs.getInt(1));
        pessoa.setNome_razaosocial(rs.getString(2));
        pessoa.setEmail(rs.getString(3));
        pessoa.setSenha(rs.getString(4));
        pessoa.setTelefone(rs.getString(5));
        pessoa.setCpf(rs.getString(6));
        pessoa.setData_nascimento(rs.getTimestamp(7));
        return pessoa;
    }

}
