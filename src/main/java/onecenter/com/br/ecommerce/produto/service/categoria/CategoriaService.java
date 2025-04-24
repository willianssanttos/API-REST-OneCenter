package onecenter.com.br.ecommerce.produto.service.categoria;

import onecenter.com.br.ecommerce.produto.entity.categoria.EnumCategoria;
import onecenter.com.br.ecommerce.produto.exception.categoria.CategoriaException;
import onecenter.com.br.ecommerce.produto.exception.categoria.CategoriaNotFoundException;
import onecenter.com.br.ecommerce.produto.dto.categoria.request.CategoriaRequest;
import onecenter.com.br.ecommerce.produto.dto.categoria.response.CategoriaResponse;
import onecenter.com.br.ecommerce.produto.entity.categoria.CategoriaEntity;
import onecenter.com.br.ecommerce.produto.repository.categoria.ICategoriaRepository;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private ICategoriaRepository iCategoriaRepository;
    private static final Logger logger = LoggerFactory.getLogger(CategoriaService.class);

    @Transactional
    public CategoriaResponse criar(CategoriaRequest categoria){
        logger.info(Constantes.DebugRegistroProcesso);
        try {

            CategoriaEntity novaCategoria = CategoriaEntity.builder()
                .nomeCategoria(EnumCategoria.valueOf(categoria.getNomeCategoria()).name())
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
                .nomeCategoria(EnumCategoria.valueOf(categoriaCriada.getNomeCategoria()).name())
                .build();
    }

    @Transactional(readOnly = true)
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
