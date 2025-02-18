package onecenter.com.br.ecommerce.repository.produtos.Impl;

import onecenter.com.br.ecommerce.config.exception.produtos.DeletarProdutoException;
import onecenter.com.br.ecommerce.config.exception.produtos.EditarProdutoException;
import onecenter.com.br.ecommerce.config.exception.produtos.ObterProdutosNotFundException;
import onecenter.com.br.ecommerce.config.exception.produtos.ProdutoException;
import onecenter.com.br.ecommerce.dto.produtos.response.ProdutosResponse;
import onecenter.com.br.ecommerce.entity.produtos.ProdutosEntity;
import onecenter.com.br.ecommerce.repository.mapper.ProdutosRowMapper;
import onecenter.com.br.ecommerce.repository.produtos.IProdutosRepository;
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
public class ProdutosRepositoryImpl implements IProdutosRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final Logger logger = LoggerFactory.getLogger(ProdutosRepositoryImpl.class);

    @Override
    @Transactional
    public ProdutosEntity criar(ProdutosEntity produtos) {
        logger.info(Constantes.DebugRegistroProcesso);
        try {
            String sql = "INSERT INTO produtos (nm_nome, ds_preco, ds_imagem_produto, fk_nr_id_categoria) VALUES (?,?,?,?) RETURNING nr_id_produto";
            Integer idProduto = jdbcTemplate.queryForObject(sql, Integer.class,
                    produtos.getNome(),
                    produtos.getPreco(),
                    produtos.getProduto_imagem(),
                    produtos.getId_categoria()
            );
            produtos.setId_produto(idProduto);
            logger.info(Constantes.InfoRegistrar, produtos);
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroRegistrarNoServidor, e.getMessage());
            throw new ProdutoException();
        }
        return produtos;
    }

    @Override
    @Transactional
    public List<ProdutosEntity> obterTodosProdutos(){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT * FROM produtos";
            logger.info(Constantes.InfoBuscar);
            return jdbcTemplate.query(sql, new ProdutosRowMapper());
        } catch (DataAccessException e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new ObterProdutosNotFundException();
        }
    }

    @Override
    @Transactional
    public ProdutosResponse atualizarProduto(ProdutosResponse editar){
        logger.info(Constantes.DebugEditarProcesso);
        try {
            String sql = "UPDATE produtos SET nm_nome = ?, ds_preco = ?, ds_imagem_produto = ?, fk_nr_id_categoria = ? WHERE nr_id_produto = ?";
            jdbcTemplate.update(sql,editar.getNome(), editar.getPreco(), editar.getProduto_imagem(), editar.getId_categoria(), editar.getId_produto());
            logger.info(Constantes.InfoEditar, editar);
        } catch (DataAccessException e){
            logger.error(Constantes.ErroEditarRegistroNoServidor, e.getMessage());
            throw new EditarProdutoException();
        }
        return editar;
    }

    @Override
    @Transactional
    public void excluirProduto(Integer idProduto){
        logger.info(Constantes.DebugDeletarProcesso);
        try {
            String sql = "DELETE FROM produtos WHERE nr_id_produto = ?";
            jdbcTemplate.update(sql, idProduto);
            logger.info(Constantes.InfoDeletar);
        } catch (DataAccessException e){
            logger.error(Constantes.ErroDeletarRegistroNoServidor, e.getMessage());
            throw new DeletarProdutoException();
        }
    }
}
