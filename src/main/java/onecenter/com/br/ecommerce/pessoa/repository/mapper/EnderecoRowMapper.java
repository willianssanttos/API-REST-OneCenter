package onecenter.com.br.ecommerce.pessoa.repository.mapper;

import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnderecoRowMapper implements RowMapper<EnderecoEntity> {

    @Override
    public EnderecoEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return EnderecoEntity.builder()
                .rua(rs.getString(7))
                .numero(rs.getString(8))
                .bairro(rs.getString(9))
                .localidade(rs.getString(10))
                .cep(rs.getString(11))
                .uf(rs.getString(12))
                .build();
    }
}
