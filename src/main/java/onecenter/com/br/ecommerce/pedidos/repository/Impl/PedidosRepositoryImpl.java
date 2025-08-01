package onecenter.com.br.ecommerce.pedidos.repository.Impl;

import onecenter.com.br.ecommerce.pedidos.dto.response.PedidoAgrupado;
import onecenter.com.br.ecommerce.pedidos.entity.pedido.PedidoEntity;
import onecenter.com.br.ecommerce.pedidos.exception.pedido.PedidosException;
import onecenter.com.br.ecommerce.pedidos.exception.pagamento.AtualizarStatusPagamentoException;
import onecenter.com.br.ecommerce.pedidos.exception.pedido.ErroAoLocalizarPedidoNotFoundException;
import onecenter.com.br.ecommerce.pedidos.repository.IPedidosRepository;
import onecenter.com.br.ecommerce.pedidos.repository.mapper.PedidoRowMapper;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PedidosRepositoryImpl implements IPedidosRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final Logger logger = LoggerFactory.getLogger(PedidosRepositoryImpl.class);

    @Override
    @Transactional
    public PedidoEntity criarPedido(PedidoEntity pedido){
        logger.info(Constantes.DebugRegistroProcesso);

        try {
            String sql = "SELECT criar_pedido(?, ?, ?, ?, ?)";
            Integer idPedido = jdbcTemplate.queryForObject(sql, new Object[]{
                    pedido.getDataPedido(),
                    pedido.getStatusPedido(),
                    pedido.getDescontoAplicado(),
                    pedido.getValorTotal(),
                    pedido.getCliente().getIdPessoa()
                    }, Integer.class);
            pedido.setIdPedido(idPedido);
            logger.info(Constantes.InfoRegistrar, pedido);
        } catch (DataAccessException e){
            logger.error(Constantes.ErroRegistrarNoServidor, e.getMessage());
            throw new PedidosException();
        }
        return pedido;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoEntity> localizarPedido() {
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT * FROM obter_todos_pedidos_completo()";
            List<PedidoEntity> pedido = jdbcTemplate.query(sql, new PedidoRowMapper());
            return PedidoAgrupado.agruparPedidos(pedido);
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new ErroAoLocalizarPedidoNotFoundException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoEntity>buscarPedidosPorIdPessoa(Integer idPessoa) {
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT * FROM obter_pedidos_por_id_pessoa(?)";
            List<PedidoEntity> pedido = jdbcTemplate.query(sql, new PedidoRowMapper(), idPessoa);
            return PedidoAgrupado.agruparPedidos(pedido);
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new ErroAoLocalizarPedidoNotFoundException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoEntity buscarPedidosPorId(Integer idPedido) {
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT * FROM obter_pedido_por_id(?)";
            List<PedidoEntity> pedido = jdbcTemplate.query(sql, new PedidoRowMapper(), idPedido);
            return PedidoAgrupado.agruparPedidos(pedido).get(0);
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new ErroAoLocalizarPedidoNotFoundException();
        }
    }

    @Override
    @Transactional
    public void atualizarStatusPagamento(Integer idPedido, String novoStatus) {
        logger.info(Constantes.InfoEditar, idPedido);
        try {
            String sql = "CALL atualizar_status_pagamento_pedido(?, ?)";
            jdbcTemplate.update(sql, idPedido, novoStatus);
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroEditarRegistroNoServidor, e);
            throw new AtualizarStatusPagamentoException();
        }
    }
}
