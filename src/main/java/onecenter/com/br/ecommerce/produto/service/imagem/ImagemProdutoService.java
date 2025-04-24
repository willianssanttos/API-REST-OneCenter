package onecenter.com.br.ecommerce.produto.service.imagem;

import onecenter.com.br.ecommerce.produto.dto.imagens.request.ImagensProdutosRequest;
import onecenter.com.br.ecommerce.produto.dto.imagens.response.ImagensProdutosResponse;
import onecenter.com.br.ecommerce.produto.entity.imagens.ImagensProdutosEntity;
import onecenter.com.br.ecommerce.produto.exception.imagens.ImagensException;
import onecenter.com.br.ecommerce.produto.repository.imagens.IImagensProdutosRepository;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImagemProdutoService {

    @Autowired
    IImagensProdutosRepository iImagensProdutosRepository;

    @Autowired
    private FileStorageService fileStorageService;

    private static final Logger logger = LoggerFactory.getLogger(ImagemProdutoService.class);

    @Transactional
    public ImagensProdutosResponse registrarImagens(ImagensProdutosRequest imagens){
        logger.info(Constantes.DebugRegistroProcesso);
        try {
            String caminhoImagem = fileStorageService.salvarImagem(imagens.getCaminho());

            ImagensProdutosEntity registrar = ImagensProdutosEntity.builder()
                    .id_produto(imagens.getId_produto())
                    .caminho(caminhoImagem)
                    .build();

            ImagensProdutosEntity registrarImagen = iImagensProdutosRepository.cadastrarImgans(registrar);
            logger.info(Constantes.InfoRegistrar, imagens);
            return mapearImagem(registrarImagen);
        } catch (Exception ex){
            logger.error(Constantes.ErroRegistrarNoServidor);
            throw new ImagensException();
        }
    }

    private ImagensProdutosResponse mapearImagem(ImagensProdutosEntity imagens){
        return ImagensProdutosResponse.builder()
                .id_imagem(imagens.getId_imagem())
                .id_produto(imagens.getId_produto())
                .caminho(imagens.getCaminho())
                .build();
    }
}
