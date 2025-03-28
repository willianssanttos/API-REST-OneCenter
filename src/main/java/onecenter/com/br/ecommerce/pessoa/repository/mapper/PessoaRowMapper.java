package onecenter.com.br.ecommerce.pessoa.repository.mapper;

import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import onecenter.com.br.ecommerce.pessoa.entity.RolesEntity;
import onecenter.com.br.ecommerce.pessoa.enums.RolesEnum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PessoaRowMapper implements RowMapper<PessoaEntity> {

    @Override
    public PessoaEntity mapRow(ResultSet rs, int rowNum) throws SQLException{

        PessoaEntity pessoa = new PessoaEntity();

        RolesEntity role = new RolesEntity();
        role.setNomeRole(RolesEnum.valueOf(rs.getString(1)));
        pessoa.getNomeRole().add(role);
        pessoa.setId_pessoa(rs.getInt(2));
        pessoa.setNome_razaosocial(rs.getString(3));
        pessoa.setEmail(rs.getString(4));
        pessoa.setSenha(rs.getString(5));
        pessoa.setTelefone(rs.getString(6));


        return pessoa;
    }
}
