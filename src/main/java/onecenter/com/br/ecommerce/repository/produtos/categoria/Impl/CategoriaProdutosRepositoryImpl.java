package onecenter.com.br.ecommerce.repository.produtos.categoria.Impl;

import onecenter.com.br.ecommerce.config.exception.CategoriaException;
import onecenter.com.br.ecommerce.config.exception.CategoriaNotFoundException;
import onecenter.com.br.ecommerce.entity.produtos.categoria.CategoriaEntity;
import onecenter.com.br.ecommerce.repository.mapper.CategoriaRowMapper;
import onecenter.com.br.ecommerce.repository.produtos.categoria.ICategoriaRepository;
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
public class CategoriaProdutosRepositoryImpl implements ICategoriaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final Logger logger = LoggerFactory.getLogger(CategoriaProdutosRepositoryImpl.class);

    @Override
    @Transactional
    public CategoriaEntity criarCategoria( CategoriaEntity categoria){
        logger.info(Constantes.DebugRegistroProcesso);
        try {
            String sql = "INSERT INTO categorias (nm_nome) VALUES (?) RETURNING nr_id_categoria";
            Integer idCategoria = jdbcTemplate.queryForObject(sql, Integer.class,
                    categoria.getNomeCategoria()
            );
            categoria.setId_categoria(idCategoria);
            logger.info(Constantes.InfoRegistrar, categoria);
        } catch (DataAccessException e){
            logger.error(Constantes.ErroRegistrarNoServidor, e.getMessage());
            throw new CategoriaException();
        }
        return categoria;
    }

    @Override
    @Transactional
    public List<CategoriaEntity> obterTodasCategoria(){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT * FROM categorias";
            logger.info(Constantes.InfoBuscar);
            return jdbcTemplate.query(sql, new CategoriaRowMapper());
        } catch (DataAccessException e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new CategoriaNotFoundException();
        }
    }

    @Override
    @Transactional
    public CategoriaEntity obterCategoriaPorId(Integer idCategoria){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT * FROM categorias WHERE nr_id_categoria = ?";
            logger.info(Constantes.InfoBuscar, idCategoria);
            return jdbcTemplate.queryForObject(sql, new Object[] { idCategoria }, new  CategoriaRowMapper());
        } catch (DataAccessException e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new CategoriaNotFoundException();
        }
    }
}
