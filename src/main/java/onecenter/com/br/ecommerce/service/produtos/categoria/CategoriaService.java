package onecenter.com.br.ecommerce.service.produtos.categoria;

import onecenter.com.br.ecommerce.config.exception.CategoriaException;
import onecenter.com.br.ecommerce.config.exception.CategoriaNotFoundException;
import onecenter.com.br.ecommerce.dto.produtos.categoria.request.CategoriaRequest;
import onecenter.com.br.ecommerce.dto.produtos.categoria.response.CategoriaResponse;
import onecenter.com.br.ecommerce.entity.produtos.categoria.CategoriaEntity;
import onecenter.com.br.ecommerce.repository.produtos.categoria.ICategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private ICategoriaRepository iCategoriaRepository;

    public CategoriaResponse criar(CategoriaRequest categoria){
        try {
            CategoriaEntity novaCategoria = CategoriaEntity.builder()
                .nomeCategoria(categoria.getNome())
                .build();

            CategoriaEntity categoriaCriada = iCategoriaRepository.criarCategoria(novaCategoria);
            return mapearCategoria(categoriaCriada);
        } catch (Exception e){
            throw new CategoriaException();
        }
    }

    private CategoriaResponse mapearCategoria(CategoriaEntity categoriaCriada){
        return CategoriaResponse.builder()
                .nome(categoriaCriada.getNomeCategoria())
                .build();
    }

    public List<CategoriaEntity> obterCategoria(){
        try {
            return iCategoriaRepository.obterTodasCategoria();
        } catch (Exception e){
            throw new CategoriaNotFoundException();
        }
    }
}
