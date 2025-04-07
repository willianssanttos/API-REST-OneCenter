package onecenter.com.br.ecommerce.produto.repository.produtos.Impl;

import onecenter.com.br.ecommerce.pessoa.exception.pessoas.ErroLocalizarPessoaNotFoundException;
import onecenter.com.br.ecommerce.produto.exception.DeletarProdutoException;
import onecenter.com.br.ecommerce.produto.exception.EditarProdutoException;
import onecenter.com.br.ecommerce.produto.exception.ObterProdutosNotFundException;
import onecenter.com.br.ecommerce.produto.exception.ProdutoException;
import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;
import onecenter.com.br.ecommerce.produto.repository.mapper.ProdutosRowMapper;
import onecenter.com.br.ecommerce.produto.repository.produtos.IProdutosRepository;
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
            String sql = "SELECT inserir_produto(?, ?, ?, ?, ?)";
            Integer idProduto = jdbcTemplate.queryForObject(sql, Integer.class,
                    produtos.getNome(),
                    produtos.getPreco(),
                    produtos.getDescricaoProduto(),
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
    public ProdutosEntity buscarIdProduto(Integer IdProduto){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT * FROM buscar_produto_id(?)";
            return jdbcTemplate.queryForObject(sql,  new ProdutosRowMapper(), IdProduto);
        } catch (DataAccessException e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new ErroLocalizarPessoaNotFoundException();
        }
    }

    @Override
    @Transactional
    public List<String> buscarImagensProduto(Integer idProduto) {
        String baseUrl = "http://localhost:8080";
        String sql = "SELECT ds_caminho FROM buscar_imagens_produto(?)";
        return jdbcTemplate.query(sql, (rs, rowNum) -> baseUrl + rs.getString("ds_caminho"), idProduto);
    }

    @Override
    @Transactional
    public List<ProdutosEntity> obterTodosProdutos(){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT * FROM obter_todos_produtos()";
            List<ProdutosEntity> produtos = jdbcTemplate.query(sql, new ProdutosRowMapper());
            logger.info(Constantes.InfoBuscar, produtos);
            return produtos;
        } catch (DataAccessException e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new ObterProdutosNotFundException();
        }
    }

    @Override
    @Transactional
    public void atualizarProduto(ProdutosEntity editar) {
        logger.info(Constantes.DebugEditarProcesso);
        try {
            String sql = "SELECT atualizar_produto(?, ?, ?, ?, ?, ?)";
            jdbcTemplate.query(sql, new Object[] {
                    editar.getId_produto(),
                    editar.getNome(),
                    editar.getPreco(),
                    editar.getDescricaoProduto(),
                    editar.getProduto_imagem(),
                    editar.getId_categoria()}, rs -> {}
            );
            logger.info(Constantes.InfoEditar, editar);
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroEditarRegistroNoServidor, e.getMessage());
            throw new EditarProdutoException();
        }
    }


    @Override
    @Transactional
    public void excluirProduto(Integer idProduto){
        logger.info(Constantes.DebugDeletarProcesso);
        try {
            String sql = "CALL deletar_produto(?)";
            jdbcTemplate.update(sql, idProduto);
            logger.info(Constantes.InfoDeletar, idProduto);
        } catch (DataAccessException e){
            logger.error(Constantes.ErroDeletarRegistroNoServidor, e.getMessage());
            throw new DeletarProdutoException();
        }
    }
}
