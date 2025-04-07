package onecenter.com.br.ecommerce.produto.repository.imagens.Impl;

import onecenter.com.br.ecommerce.produto.entity.imagens.ImagensProdutosEntity;
import onecenter.com.br.ecommerce.produto.exception.imagens.ImagensException;
import onecenter.com.br.ecommerce.produto.repository.imagens.IImagensProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ImagensProdutosRepositoryImpl implements IImagensProdutosRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public ImagensProdutosEntity cadastrarImgans(ImagensProdutosEntity imagens){
        try {
            String sql = "SELECT inserir_imagem_produto(?,?)";
            Integer idImagem = jdbcTemplate.queryForObject(sql, Integer.class, imagens.getId_produto(), imagens.getCaminho());
            imagens.setId_imagem(idImagem);
        } catch (DataAccessException ex){
            throw new ImagensException();
        }
        return imagens;
    }

}
