package onecenter.com.br.ecommerce.pessoa.repository.mapper;

import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import onecenter.com.br.ecommerce.pessoa.entity.RolesEntity;
import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

public class PessoaRowMapper implements RowMapper<PessoaEntity> {

    @Override
    public PessoaEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        RolesEntity role = new RoleRowMapper().mapRow(rs, rowNum);

        return PessoaEntity.builder()
                .idPessoa(rs.getInt(1))
                .roles(Collections.singletonList(role))
                .nomeRazaosocial(rs.getString(3))
                .email(rs.getString(4))
                .senha(rs.getString(5))
                .telefone(rs.getString(6))
                .build();
    }
}
