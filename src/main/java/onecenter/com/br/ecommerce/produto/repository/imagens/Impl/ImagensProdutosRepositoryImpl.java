package onecenter.com.br.ecommerce.produto.repository.imagens.Impl;

import onecenter.com.br.ecommerce.produto.entity.imagens.ImagensProdutosEntity;
import onecenter.com.br.ecommerce.produto.exception.imagens.ImagensException;
import onecenter.com.br.ecommerce.produto.repository.imagens.IImagensProdutosRepository;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ImagensProdutosRepositoryImpl implements IImagensProdutosRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final Logger logger = LoggerFactory.getLogger(ImagensProdutosRepositoryImpl.class);

    @Override
    @Transactional
    public ImagensProdutosEntity cadastrarImgans(ImagensProdutosEntity imagens){
        logger.info(Constantes.DebugRegistroProcesso);
        try {
            String sql = "SELECT inserir_imagem_produto(?,?)";
            Integer idImagem = jdbcTemplate.queryForObject(sql, Integer.class, imagens.getIdProduto(), imagens.getCaminho());
            imagens.setId_imagem(idImagem);
            logger.info(Constantes.InfoRegistrar, imagens);
        } catch (DataAccessException e){
            logger.error(Constantes.ErroRegistrarNoServidor, e.getMessage());
            throw new ImagensException();
        }
        return imagens;
    }

}
