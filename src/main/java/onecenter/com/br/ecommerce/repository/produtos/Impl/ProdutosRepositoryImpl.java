package onecenter.com.br.ecommerce.repository.produtos.Impl;

import onecenter.com.br.ecommerce.config.exception.DeletarProdutoException;
import onecenter.com.br.ecommerce.config.exception.ObterProdutosNotFundException;
import onecenter.com.br.ecommerce.config.exception.ProdutoException;
import onecenter.com.br.ecommerce.dto.produtos.request.ProdutoRequest;
import onecenter.com.br.ecommerce.dto.produtos.response.ProdutosResponse;
import onecenter.com.br.ecommerce.entity.produtos.ProdutosEntity;
import onecenter.com.br.ecommerce.repository.mapper.ProdutosRowMapper;
import onecenter.com.br.ecommerce.repository.produtos.IProdutosRepository;
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

    @Override
    @Transactional
    public ProdutosEntity criar(ProdutosEntity produtos) {
        try {
            String sql = "INSERT INTO produtos (nm_nome, ds_preco, ds_imagem_produto, fk_nr_id_categoria) VALUES (?,?,?,?) RETURNING nr_id_produto";

            Integer idProduto = jdbcTemplate.queryForObject(sql, Integer.class,
                    produtos.getNome(),
                    produtos.getPreco(),
                    produtos.getProduto_imagem(),
                    produtos.getId_categoria()
            );
            produtos.setId_produto(idProduto);
            return produtos;

        } catch (DataAccessException e) {
            throw new ProdutoException();
        }
    }

    @Override
    @Transactional
    public List<ProdutosEntity> obterTodosProdutos(){
        try {
            String sql = "SELECT * FROM produtos";
            return jdbcTemplate.query(sql, new ProdutosRowMapper());
        } catch (DataAccessException e){
            throw new ObterProdutosNotFundException();
        }
    }

    @Override
    @Transactional
    public ProdutosResponse atualizarProduto(ProdutosResponse editar){
        try {
            String sql = "UPDATE produtos SET nm_nome = ?, ds_preco = ?, ds_imagem_produto = ?, fk_nr_id_categoria = ? WHERE nr_id_produto = ?";
            jdbcTemplate.update(sql,editar.getNome(), editar.getPreco(), editar.getProduto_imagem(), editar.getId_categoria(), editar.getId_produto());
            return editar;
        } catch (DataAccessException e){
            throw new ProdutoException();
        }
    }

    @Override
    @Transactional
    public void excluirProduto(Integer idProduto){
        try {
            String sql = "DELETE FROM produtos WHERE nr_id_produto = ?";
            jdbcTemplate.update(sql, idProduto);
        } catch (DataAccessException e){
            throw new DeletarProdutoException();
        }
    }

}
