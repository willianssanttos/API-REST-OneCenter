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
import java.util.Collections;
import java.util.List;

public class PedidoRowMapper implements RowMapper<PedidoEntity> {

    @Override
    public PedidoEntity mapRow(ResultSet rs, int rowNum) throws SQLException{

        PessoaEntity pessoa = new PessoaRowMapper().mapRow(rs, rowNum);
        ProdutosEntity produto = new ProdutosRowMapper().mapRow(rs, rowNum);
        ItemPedidoEntity itemPedido = new ItemPedidoRowMapper().mapRow(rs, rowNum);

        return PedidoEntity.builder()
                .idPedido(rs.getInt("nr_id_pedido"))
                .quantidade(rs.getInt("ds_quantidade"))
                .dataPedido(rs.getTimestamp("dt_pedido"))
                .statusPedido(rs.getString("ds_status"))
                .idProduto(produto.getIdProduto())
                .cliente(pessoa)
                .itens(Collections.singletonList(itemPedido))
                .build();
    }
}
