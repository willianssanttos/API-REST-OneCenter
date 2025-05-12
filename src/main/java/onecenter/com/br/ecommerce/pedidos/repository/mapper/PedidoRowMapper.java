package onecenter.com.br.ecommerce.pedidos.repository.mapper;

import onecenter.com.br.ecommerce.pedidos.entity.ItemPedidoEntity;
import onecenter.com.br.ecommerce.pedidos.entity.PedidoEntity;
import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import onecenter.com.br.ecommerce.pessoa.repository.mapper.PessoaRowMapper;
import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;
import onecenter.com.br.ecommerce.produto.repository.mapper.ProdutosRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoRowMapper implements RowMapper<PedidoEntity> {

    @Override
    public PedidoEntity mapRow(ResultSet rs, int rowNum) throws SQLException{

        int idPedido = rs.getInt("nr_id_pedido");

        PessoaEntity pessoa = new PessoaRowMapper().mapRow(rs, rowNum);
        ItemPedidoEntity itemPedido = new ItemPedidoRowMapper().mapRow(rs, rowNum);

        PedidoEntity pedido = PedidoEntity.builder()
                .idPedido(idPedido)
                .dataPedido(rs.getTimestamp("dt_pedido"))
                .statusPedido(rs.getString("ds_status"))
                .descontoAplicado(rs.getBigDecimal("ds_desconto_aplicado"))
                .valorTotal(rs.getBigDecimal("ds_valor_total"))
                .cliente(pessoa)
                .itens(new ArrayList<>(List.of(itemPedido)))
                .build();
        return pedido;
    }
}
