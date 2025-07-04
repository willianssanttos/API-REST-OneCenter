package onecenter.com.br.ecommerce.pedidos.repository.mapper;

import onecenter.com.br.ecommerce.pedidos.entity.pedido.CupomEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CupomRowMapper implements RowMapper<CupomEntity> {

    @Override
    public CupomEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        return CupomEntity.builder()
                .idCupom(rs.getInt(1))
                .codigo(rs.getString(2))
                .valorDesconto(rs.getBigDecimal(3))
                .dataValidade(rs.getTimestamp(4).toLocalDateTime())
                .cupomUsado(rs.getBoolean(5))
                .build();
    }
}
