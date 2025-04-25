package onecenter.com.br.ecommerce.pessoa.repository.mapper;

import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnderecoRowMapper implements RowMapper<EnderecoEntity> {

    @Override
    public EnderecoEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return EnderecoEntity.builder()
                .rua(rs.getString("nm_rua"))
                .numero(rs.getString("ds_numero"))
                .bairro(rs.getString("ds_bairro"))
                .localidade(rs.getString("ds_cidade"))
                .cep(rs.getString("ds_uf"))
                .uf(rs.getString("ds_cep"))
                .build();
    }
}
