package onecenter.com.br.ecommerce.produto.repository.categoria.Impl;

import onecenter.com.br.ecommerce.produto.exception.categoria.CategoriaException;
import onecenter.com.br.ecommerce.produto.exception.categoria.CategoriaNotFoundException;
import onecenter.com.br.ecommerce.produto.entity.categoria.CategoriaEntity;
import onecenter.com.br.ecommerce.produto.repository.mapper.CategoriaRowMapper;
import onecenter.com.br.ecommerce.produto.repository.categoria.ICategoriaRepository;
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
            String sql = "SELECT inserir_categoria(?)";
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
    @Transactional(readOnly = true)
    public List<CategoriaEntity> obterTodasCategoria(){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT * FROM listar_categorias()";
            logger.info(Constantes.InfoBuscar);
            return jdbcTemplate.query(sql, new CategoriaRowMapper());
        } catch (DataAccessException e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new CategoriaNotFoundException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Integer obterCategoriaPorNome(String nomeCategoria) {
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT * FROM categorias WHERE nm_categoria = ? ";
            logger.info(Constantes.InfoBuscar, nomeCategoria);
            CategoriaEntity categoria = jdbcTemplate.queryForObject(sql, new Object[]{nomeCategoria}, new CategoriaRowMapper());
            return categoria.getId_categoria();
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new CategoriaNotFoundException();
        }
    }
}
