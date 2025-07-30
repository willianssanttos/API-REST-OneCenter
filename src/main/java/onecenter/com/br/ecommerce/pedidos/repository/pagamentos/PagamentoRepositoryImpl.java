package onecenter.com.br.ecommerce.pedidos.repository.pagamentos;

import onecenter.com.br.ecommerce.pedidos.entity.pagamento.PagamentoEntity;
import onecenter.com.br.ecommerce.pedidos.exception.pagamento.AtualizarStatusPagamentoException;
import onecenter.com.br.ecommerce.pedidos.exception.pagamento.PagamentoException;
import onecenter.com.br.ecommerce.pedidos.exception.pagamento.PagamentoExistenteExceptionNotFound;
import onecenter.com.br.ecommerce.pedidos.repository.mapper.PagamentoRowMapper;
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
import java.util.List;

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
            logger.error(Constantes.ErroAoLocalizarPagamentoExistente, e.getMessage());
            throw new PagamentoExistenteExceptionNotFound();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagamentoEntity> buscarPagamentoRealizado(Integer IdPedido) {
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT * FROM buscar_pagamento_por_pedido(?)";
            List<PagamentoEntity> pagamento = jdbcTemplate.query(sql, new Object[]{IdPedido}, new PagamentoRowMapper());
            logger.info(Constantes.InfoBuscar,  IdPedido);
            return pagamento;

        } catch (DataAccessException e){
            logger.error(Constantes.ErroAoLocalizarPagamentoExistente, e.getMessage());
            throw new PagamentoExistenteExceptionNotFound();
        }
    }

    @Override
    @Transactional
    public void atualizarStatusEstorno(Integer idPagamento, String status) {
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "CALL atualizar_status_estorno(?, ?)";
            jdbcTemplate.update(sql, idPagamento, status);
            logger.info(Constantes.InfoBuscar,  idPagamento);
        } catch (Exception e){
            logger.error(Constantes.ErroAtualizarStatusEstorno, e.getMessage());
            throw new AtualizarStatusPagamentoException();
        }
    }
}
