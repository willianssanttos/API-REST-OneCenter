package onecenter.com.br.ecommerce.repository.produtos.Impl;

import onecenter.com.br.ecommerce.config.exception.ObterProdutosNotFundException;
import onecenter.com.br.ecommerce.config.exception.ProdutoException;
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
            String sql = "INSERT INTO produtos (nome, preco, imagem_produto, id_categoria) VALUES (?,?,?,?) RETURNING id_produto";

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

    public List<ProdutosEntity> obterTodosProdutos(){
        try {
            String sql = "SELECT * FROM produtos";
            return jdbcTemplate.query(sql, new ProdutosRowMapper());
        } catch (DataAccessException e){
            throw new ObterProdutosNotFundException();
        }
    }


}
