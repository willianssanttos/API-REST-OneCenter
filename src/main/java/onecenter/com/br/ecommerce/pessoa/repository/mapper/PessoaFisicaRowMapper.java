package onecenter.com.br.ecommerce.pessoa.repository.mapper;

import onecenter.com.br.ecommerce.pessoa.entity.RolesEntity;
import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoEntity;
import onecenter.com.br.ecommerce.pessoa.entity.fisica.PessoaFisicaEntity;
import onecenter.com.br.ecommerce.pessoa.enums.RolesEnum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PessoaFisicaRowMapper implements RowMapper<PessoaFisicaEntity> {

    @Override
    public PessoaFisicaEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        PessoaFisicaEntity pessoa = new PessoaFisicaEntity();
        RolesEntity role = new RolesEntity();
        pessoa.setId_pessoa(rs.getInt(1));
        role.setNomeRole(RolesEnum.valueOf(rs.getString(2).toUpperCase()));
        pessoa.getNomeRole().add(role);
        pessoa.setNome_razaosocial(rs.getString(3));
        pessoa.setEmail(rs.getString(4));
        pessoa.setSenha(rs.getString(5));
        pessoa.setTelefone(rs.getString(6));
        pessoa.setCpf(rs.getString(7));
        pessoa.setData_nascimento(rs.getTimestamp(8));
        pessoa.setEndereco( new EnderecoEntity());
        EnderecoEntity endereco = new EnderecoEntity();
        endereco.setIdEndereco(rs.getInt(1));
        endereco.setRua(rs.getString(2));
        endereco.setNumero(rs.getString(3));
        endereco.setBairro(rs.getString(4));
        endereco.setLocalidade(rs.getString(5));
        endereco.setUf(rs.getString(6));
        endereco.setCep(rs.getString(7));

        return pessoa;
    }

}
