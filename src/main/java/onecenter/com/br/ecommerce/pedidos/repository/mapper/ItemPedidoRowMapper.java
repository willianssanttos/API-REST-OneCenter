package onecenter.com.br.ecommerce.pedidos.repository.mapper;

import onecenter.com.br.ecommerce.pedidos.entity.ItemPedidoEntity;

import onecenter.com.br.ecommerce.pedidos.entity.PedidoEntity;
import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemPedidoRowMapper implements RowMapper<ItemPedidoEntity> {

    @Override
    public ItemPedidoEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        return ItemPedidoEntity.builder()
                .idItemPedido(rs.getInt("id_itens_pedido"))
                .pedido(PedidoEntity.builder().idPedido(rs.getInt("item_pedido")).build())
                .produtos(ProdutosEntity.builder().idProduto(rs.getInt("item_produto")).build())
                .quantidade(rs.getInt("item_quantidade"))
                .precoUnitario(rs.getBigDecimal("item_preco_unitario"))
                .build();
    }
}
