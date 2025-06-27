package onecenter.com.br.ecommerce.pedidos.repository.pagamentos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.OffsetDateTime;

@Repository
public class PagamentoRepositoryImpl implements IPagamentoRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void salvarPagamento(String formaPagamento, String status, BigDecimal valor, OffsetDateTime dataPagamento, Integer idPedido, String idTrasacaoExterna){
        String sql = """
            INSERT INTO pagamentos (ds_forma_pagamento, ds_status_pagamento, ds_valor_total, ds_data_pagamento, fk_nr_id_pedido, fk_nr_id_transacao_externa)
            VALUES (?, ?, ?, ?, ?)
        """;
        jdbcTemplate.update(sql, formaPagamento, status, valor, Timestamp.from(dataPagamento.toInstant()), idPedido, idTrasacaoExterna);
    }
}
