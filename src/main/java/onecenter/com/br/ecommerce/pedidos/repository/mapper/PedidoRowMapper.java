package onecenter.com.br.ecommerce.pedidos.repository.mapper;

import onecenter.com.br.ecommerce.pedidos.entity.PedidosEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PedidoRowMapper implements RowMapper<PedidosEntity> {

    @Override
    public PedidosEntity mapRow(ResultSet rs, int rowNum) throws SQLException{
        PedidosEntity pedidos = new PedidosEntity();
        pedidos.setIdPedido(rs.getInt(1));
        pedidos.setId_produto(rs.getInt(2));
        pedidos.setQuantidade(rs.getInt(3));
        pedidos.setIdPessoa(rs.getInt(4));
        return pedidos;
    }
}
