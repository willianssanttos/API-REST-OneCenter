package onecenter.com.br.ecommerce.repository.produtos.categoria.Impl;

import onecenter.com.br.ecommerce.config.exception.CategoriaException;
import onecenter.com.br.ecommerce.config.exception.CategoriaNotFoundException;
import onecenter.com.br.ecommerce.entity.produtos.categoria.CategoriaEntity;
import onecenter.com.br.ecommerce.repository.mapper.CategoriaRowMapper;
import onecenter.com.br.ecommerce.repository.produtos.categoria.ICategoriaRepository;
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

    @Override
    @Transactional
    public CategoriaEntity criarCategoria( CategoriaEntity categoria){
        try {
            String sql = "INSERT INTO categorias (nm_nome) VALUES (?) RETURNING nr_id_categoria";

            Integer idCategoria = jdbcTemplate.queryForObject(sql, Integer.class,
                    categoria.getNomeCategoria()
            );
            categoria.setId_categoria(idCategoria);
        } catch (DataAccessException e){
            throw new CategoriaException();
        }
        return categoria;
    }

    @Override
    @Transactional
    public List<CategoriaEntity> obterTodasCategoria(){
        try {
            String sql = "SELECT * FROM categorias";
            return jdbcTemplate.query(sql, new CategoriaRowMapper());
        } catch (DataAccessException e){
            throw new CategoriaNotFoundException();
        }
    }

    @Override
    @Transactional
    public CategoriaEntity categoriaId(Integer idCategoria){
        try {
            String sql = "SELECT * FROM categorias WHERE nr_id_categoria = ?";
            return jdbcTemplate.queryForObject(sql, new Object[] { idCategoria }, new  CategoriaRowMapper());
        } catch (DataAccessException e){
            throw new CategoriaNotFoundException();
        }
    }


}
