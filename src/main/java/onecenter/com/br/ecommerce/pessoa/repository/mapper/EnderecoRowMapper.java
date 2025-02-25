package onecenter.com.br.ecommerce.pessoa.repository.mapper;

import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnderecoRowMapper implements RowMapper<EnderecoEntity> {

    @Override
    public EnderecoEntity mapRow(ResultSet rs, int rowNum) throws SQLException{
        EnderecoEntity endereco = new EnderecoEntity();
        endereco.setIdEndereco(rs.getInt(1));
        endereco.setRua(rs.getString(2));
        endereco.setNumero(rs.getString(3));
        endereco.setBairro(rs.getString(4));
        endereco.setLocalidade(rs.getString(5));
        endereco.setUf(rs.getString(6));
        endereco.setCep(rs.getString(7));
        return endereco;
    }
}
