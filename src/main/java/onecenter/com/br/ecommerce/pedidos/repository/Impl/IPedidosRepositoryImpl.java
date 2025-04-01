package onecenter.com.br.ecommerce.pedidos.repository.Impl;

import onecenter.com.br.ecommerce.pedidos.entity.PedidosEntity;
import onecenter.com.br.ecommerce.pedidos.exception.ErroAoLocalizarPedidoNotFoundException;
import onecenter.com.br.ecommerce.pedidos.exception.PedidosException;
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
public class IPedidosRepositoryImpl implements IPedidosRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final Logger logger = LoggerFactory.getLogger(IPedidosRepositoryImpl.class);

    @Override
    @Transactional
    public PedidosEntity criarPedido(PedidosEntity pedido){
        logger.info(Constantes.DebugRegistroProcesso);

        try {
            String sql = "INSERT INTO pedidos (fk_nr_id_produto, ds_quantidade, fk_nr_id_pessoa) VALUES (?,?,?) RETURNING nr_id_pedido";
            jdbcTemplate.queryForObject(sql, Integer.class, pedido.getId_produto(), pedido.getQuantidade(), pedido.getIdPessoa());
            logger.info(Constantes.InfoRegistrar, pedido);
        } catch (DataAccessException e){
            logger.error(Constantes.ErroRegistrarNoServidor, e.getMessage());
            throw new PedidosException();
        }
        return pedido;
    }

    @Override
    @Transactional
    public List<PedidosEntity> localizarPedido() {
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT * FROM pedidos";
            return jdbcTemplate.query(sql, new PedidoRowMapper());
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new ErroAoLocalizarPedidoNotFoundException();
        }
    }


}
