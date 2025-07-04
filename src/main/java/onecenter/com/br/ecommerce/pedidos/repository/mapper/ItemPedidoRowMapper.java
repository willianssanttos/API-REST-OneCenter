package onecenter.com.br.ecommerce.pedidos.repository.mapper;

import onecenter.com.br.ecommerce.pedidos.entity.pedido.ItemPedidoEntity;

import onecenter.com.br.ecommerce.pedidos.entity.pedido.PedidoEntity;
import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemPedidoRowMapper implements RowMapper<ItemPedidoEntity> {

    @Override
    public ItemPedidoEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        return ItemPedidoEntity.builder()
                .idItemPedido(rs.getInt("nr_id_itens_pedido"))
                .pedido(PedidoEntity.builder().idPedido(rs.getInt("fk_nr_id_pedido")).build())
                .produtos(ProdutosEntity.builder().idProduto(rs.getInt("fk_nr_id_produto")).build())
                .quantidade(rs.getInt("ds_quantidade"))
                .precoUnitario(rs.getBigDecimal("ds_preco_unitario"))
                .build();
    }
}
