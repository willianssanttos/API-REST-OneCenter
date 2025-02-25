package onecenter.com.br.ecommerce.pessoa.repository.mapper;

import onecenter.com.br.ecommerce.pessoa.entity.juridica.PessoaJuridicaEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PessoasJuridicaRowMapper implements RowMapper<PessoaJuridicaEntity> {

    @Override
    public PessoaJuridicaEntity mapRow(ResultSet rs, int rowNum) throws SQLException{
        PessoaJuridicaEntity pessoa = new PessoaJuridicaEntity();
        pessoa.setId_pessoa(rs.getInt(1));
        pessoa.setNome_razaosocial(rs.getString(2));
        pessoa.setEmail(rs.getString(3));
        pessoa.setSenha(rs.getString(4));
        pessoa.setTelefone(rs.getString(5));
        pessoa.setCnpj(rs.getString(6));
        pessoa.setNome_fantasia(rs.getString(7));
        return pessoa;
    }
}
