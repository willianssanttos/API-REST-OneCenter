package onecenter.com.br.ecommerce.controller.produtos.categoria;

import onecenter.com.br.ecommerce.dto.produtos.categoria.request.CategoriaRequest;
import onecenter.com.br.ecommerce.dto.produtos.categoria.response.CategoriaResponse;
import onecenter.com.br.ecommerce.entity.produtos.categoria.CategoriaEntity;
import onecenter.com.br.ecommerce.service.produtos.categoria.CategoriaService;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    private static final Logger logger = LoggerFactory.getLogger(CategoriaController.class);

    @PostMapping("/criar-categoria")
    public ResponseEntity<CategoriaResponse> criarCategoria(@RequestBody CategoriaRequest categoria){
        logger.info(Constantes.InfoRegistrar, categoria);
        return new ResponseEntity<>(categoriaService.criar(categoria), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoriaEntity>> obterTodosProdutos(){
        List<CategoriaEntity> response = categoriaService.obterCategoria();
        logger.info(Constantes.InfoBuscar, response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
