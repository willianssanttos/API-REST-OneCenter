package onecenter.com.br.ecommerce.pedidos.repository.mapper;

import onecenter.com.br.ecommerce.pedidos.entity.PedidosEntity;
import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import onecenter.com.br.ecommerce.pessoa.repository.mapper.PessoaRowMapper;
import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;
import onecenter.com.br.ecommerce.produto.repository.mapper.ProdutosRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PedidoRowMapper implements RowMapper<PedidosEntity> {

    @Override
    public PedidosEntity mapRow(ResultSet rs, int rowNum) throws SQLException{

        PessoaEntity pessoa = new PessoaRowMapper().mapRow(rs, rowNum);
        ProdutosEntity produto = new ProdutosRowMapper().mapRow(rs, rowNum);

        return PedidosEntity.builder()
                .idPedido(rs.getInt("nr_id_pedido"))
                .quantidade(rs.getInt("ds_quantidade"))
                .dataPedido(rs.getTimestamp("dt_pedido"))
                .statusPedido(rs.getString("ds_status"))
                .idProduto(produto.getIdProduto())
                .cliente(pessoa)
                .build();
    }
}
