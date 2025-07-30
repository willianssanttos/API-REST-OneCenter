package onecenter.com.br.ecommerce.pedidos.repository.mapper;

import onecenter.com.br.ecommerce.pedidos.entity.pagamento.PagamentoEntity;
import onecenter.com.br.ecommerce.pedidos.entity.pedido.PedidoEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PagamentoRowMapper implements RowMapper<PagamentoEntity> {

    @Override
    public PagamentoEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        PagamentoEntity pagamento = new PagamentoEntity();
        PedidoEntity pedido = new PedidoEntity();
        pagamento.setIdPagamento(rs.getInt("nr_id_pagamento"));
        pagamento.setFormaPagamento(rs.getString("ds_forma_pagamento"));
        pagamento.setStatusPagamento(rs.getString("ds_status_pagamento"));
        pagamento.setValorTotal(rs.getBigDecimal("ds_valor_total"));
        pagamento.setDataPagamento(Timestamp.valueOf(rs.getTimestamp("ds_data_pagamento").toLocalDateTime()));
        pagamento.setPedido(pedido);
        pagamento.setIdTransacaoExterna(rs.getString("fk_nr_id_transacao_externa"));
        return pagamento;
    }
}
