package onecenter.com.br.ecommerce.service.produtos.categoria;

import onecenter.com.br.ecommerce.config.exception.CategoriaException;
import onecenter.com.br.ecommerce.config.exception.CategoriaNotFoundException;
import onecenter.com.br.ecommerce.dto.produtos.categoria.request.CategoriaRequest;
import onecenter.com.br.ecommerce.dto.produtos.categoria.response.CategoriaResponse;
import onecenter.com.br.ecommerce.entity.produtos.categoria.CategoriaEntity;
import onecenter.com.br.ecommerce.repository.produtos.categoria.ICategoriaRepository;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private ICategoriaRepository iCategoriaRepository;
    private static final Logger logger = LoggerFactory.getLogger(CategoriaService.class);

    public CategoriaResponse criar(CategoriaRequest categoria){
        logger.info(Constantes.DebugRegistroProcesso);
        try {

            CategoriaEntity novaCategoria = CategoriaEntity.builder()
                .nomeCategoria(categoria.getNome())
                .build();
            CategoriaEntity categoriaCriada = iCategoriaRepository.criarCategoria(novaCategoria);
            logger.info(Constantes.InfoRegistrar, categoria);
            return mapearCategoria(categoriaCriada);

        } catch (Exception e){
            logger.error(Constantes.ErroRegistrarNoServidor);
            throw new CategoriaException();
        }
    }

    private CategoriaResponse mapearCategoria(CategoriaEntity categoriaCriada){
        return CategoriaResponse.builder()
                .nome(categoriaCriada.getNomeCategoria())
                .build();
    }

    public List<CategoriaEntity> obterCategoria(){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            return iCategoriaRepository.obterTodasCategoria();
        } catch (Exception e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor);
            throw new CategoriaNotFoundException();
        }
    }
}
