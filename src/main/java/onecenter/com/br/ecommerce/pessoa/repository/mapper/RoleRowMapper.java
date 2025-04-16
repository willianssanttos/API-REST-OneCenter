package onecenter.com.br.ecommerce.pessoa.repository.mapper;

import onecenter.com.br.ecommerce.pessoa.entity.RolesEntity;
import onecenter.com.br.ecommerce.pessoa.enums.RolesEnum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<RolesEntity> {

    @Override
    public RolesEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return RolesEntity.builder()
                .nomeRole(RolesEnum.fromString(rs.getString("nm_roles")))
                .build();
    }
}
