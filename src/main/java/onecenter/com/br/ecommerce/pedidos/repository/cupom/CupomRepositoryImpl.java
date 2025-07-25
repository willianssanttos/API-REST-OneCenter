package onecenter.com.br.ecommerce.pedidos.repository.cupom;

import onecenter.com.br.ecommerce.pedidos.entity.pedido.CupomEntity;
import onecenter.com.br.ecommerce.pedidos.exception.cupom.CupomException;
import onecenter.com.br.ecommerce.pedidos.exception.cupom.ErroAtualizarStatusCupomException;
import onecenter.com.br.ecommerce.pedidos.exception.cupom.ErroLocalizarCupomNotFoundException;
import onecenter.com.br.ecommerce.pedidos.exception.cupom.ErroLocalizarInformacaoUsoCupomNotFoundException;
import onecenter.com.br.ecommerce.pedidos.repository.mapper.CupomRowMapper;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Repository
public class CupomRepositoryImpl implements ICupomRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final Logger logger = LoggerFactory.getLogger(CupomRepositoryImpl.class);

    @Override
    @Transactional
    public CupomEntity cadastrarCupom(CupomEntity cupom){
        logger.info(Constantes.DebugRegistroProcesso);
        try {
            String sql = "SELECT inserir_cupom(?, ?, ?)";
            jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(CupomEntity.class),
                    cupom.getCodigo(),
                    cupom.getValorDesconto(),
                    Timestamp.valueOf(cupom.getDataValidade())
            );
            logger.info(Constantes.InfoRegistrar, cupom);
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroRegistrarNoServidor, e.getMessage());
            throw new CupomException();
        }
        return cupom;
    }

    @Override
    @Transactional(readOnly = true)
    public CupomEntity buscarCupomPorNome(String nomeCupom){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT * FROM buscar_cupom_por_nome(?)";
            return jdbcTemplate.queryForObject(sql,  new CupomRowMapper(), nomeCupom);
        } catch (DataAccessException e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new ErroLocalizarCupomNotFoundException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean cupomJaUsadoPorCliente(Integer idCupom, Integer idCliente) {
        try {
            String sql = "SELECT * FROM contar_uso_cupom (?,?)";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idCupom, idCliente);
            return count != null && count > 0;
        } catch (DataAccessException e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new ErroLocalizarInformacaoUsoCupomNotFoundException();
        }
    }

    @Override
    @Transactional
    public void registrarUsoCupom(Integer idCupom, Integer idCliente) {
        try {
            String sql = "CALL registrar_uso_cupom (?, ?)";
            jdbcTemplate.update(sql, idCupom, idCliente);
        } catch (DataAccessException e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new ErroLocalizarInformacaoUsoCupomNotFoundException();
        }
    }

    @Override
    @Transactional
    public void marcarCupomComoUsado(String nomeCupom){
        logger.info(Constantes.DebugEditarProcesso);
        try {
            String sql = "CALL atualizar_status_cupom(?)";
            jdbcTemplate.update(sql, nomeCupom);
            logger.info(Constantes.InfoEditar, nomeCupom);
        } catch (DataAccessException e){
            logger.error(Constantes.ErroEditarRegistroNoServidor, e.getMessage());
            throw new ErroAtualizarStatusCupomException();
        }
    }
}
