package onecenter.com.br.ecommerce.pessoa.repository.mapper;

import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnderecoRowMapper implements RowMapper<EnderecoEntity> {

    @Override
    public EnderecoEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return EnderecoEntity.builder()
                .idEndereco(rs.getInt(1))
                .rua(rs.getString(2))
                .numero(rs.getString(3))
                .bairro(rs.getString(4))
                .localidade(rs.getString(5))
                .cep(rs.getString(6))
                .uf(rs.getString(7))
                .build();
    }
}
