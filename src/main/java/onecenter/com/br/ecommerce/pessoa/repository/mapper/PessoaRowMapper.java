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

        // Criar a Role corretamente
        RolesEntity role = new RolesEntity();
        role.setNomeRole(RolesEnum.valueOf(rs.getString("nm_roles").toUpperCase()));
        pessoa.getNomeRole().add(role);  // Adicionando a role na lista

        // Mapear os campos corretamente
        pessoa.setId_pessoa(rs.getInt("nr_id_pessoa"));
        pessoa.setNome_razaosocial(rs.getString("nm_nome_razaosocial"));
        pessoa.setEmail(rs.getString("ds_email"));
        pessoa.setSenha(rs.getString("ds_senha"));
        pessoa.setTelefone(rs.getString("ds_telefone"));


        return pessoa;
    }
}
