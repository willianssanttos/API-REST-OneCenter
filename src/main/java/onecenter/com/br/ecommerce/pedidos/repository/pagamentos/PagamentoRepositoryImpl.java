package onecenter.com.br.ecommerce.pedidos.repository.pagamentos;

import onecenter.com.br.ecommerce.pedidos.exception.pagamento.PagamentoException;
import onecenter.com.br.ecommerce.pedidos.exception.pagamento.PagamentoExistenteExceptionNotFound;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.OffsetDateTime;

@Repository
public class PagamentoRepositoryImpl implements IPagamentoRepository{

    public static final Logger logger = LoggerFactory.getLogger(PagamentoRepositoryImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void salvarPagamento(String formaPagamento, String status, BigDecimal valor, OffsetDateTime dataPagamento, Integer idPedido, String idTrasacaoExterna){
        logger.info(Constantes.DebugRegistroProcesso);
        try {
            String sql = "CALL salvar_pagamento(?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    formaPagamento,
                    status,
                    valor,
                    Timestamp.from(dataPagamento.toInstant()),
                    idPedido,
                    idTrasacaoExterna
            );
        } catch (DataAccessException e){
            logger.error(Constantes.ErroAoSalvarPagamento, e.getMessage());
            throw new PagamentoException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePagamentoPorId(String idPagamento) {
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT existe_pagamento(?)";
            Boolean existe = jdbcTemplate.queryForObject(sql, Boolean.class, idPagamento);
            logger.info(Constantes.InfoBuscar, idPagamento);
            return Boolean.TRUE.equals(existe);

        } catch (DataAccessException e){
            logger.error(Constantes.ErroAoLocalizarPagamentoExistente);
            throw new PagamentoExistenteExceptionNotFound();
        }
    }
}
