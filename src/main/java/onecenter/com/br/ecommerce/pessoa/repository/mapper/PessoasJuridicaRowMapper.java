package onecenter.com.br.ecommerce.pessoa.repository.mapper;

import onecenter.com.br.ecommerce.pessoa.entity.RolesEntity;
import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoEntity;
import onecenter.com.br.ecommerce.pessoa.entity.juridica.PessoaJuridicaEntity;
import onecenter.com.br.ecommerce.pessoa.enums.RolesEnum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PessoasJuridicaRowMapper implements RowMapper<PessoaJuridicaEntity> {

    @Override
    public PessoaJuridicaEntity mapRow(ResultSet rs, int rowNum) throws SQLException{
        PessoaJuridicaEntity pessoa = new PessoaJuridicaEntity();
        pessoa.setIdPessoa(rs.getInt(1));

        RolesEntity role = new RolesEntity();
        role.setNomeRole(RolesEnum.valueOf(rs.getString(2).toUpperCase()));
        pessoa.getRoles().add(role);
        pessoa.setNomeRazaosocial(rs.getString(3));
        pessoa.setEmail(rs.getString(4));
        pessoa.setTelefone(rs.getString(5));
        pessoa.setCnpj(rs.getString(6));
        pessoa.setNome_fantasia(rs.getString(7));

        EnderecoEntity endereco = new EnderecoEntity();
        endereco.setRua(rs.getString(8));
        endereco.setNumero(rs.getString(9));
        endereco.setBairro(rs.getString(10));
        endereco.setLocalidade(rs.getString(11));
        endereco.setUf(rs.getString(12));
        endereco.setCep(rs.getString(13));
        pessoa.setEndereco(endereco);
        return pessoa;
    }
}
