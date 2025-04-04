package onecenter.com.br.ecommerce.produto.controller.categoria;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import onecenter.com.br.ecommerce.produto.dto.categoria.request.CategoriaRequest;
import onecenter.com.br.ecommerce.produto.dto.categoria.response.CategoriaResponse;
import onecenter.com.br.ecommerce.produto.entity.categoria.CategoriaEntity;
import onecenter.com.br.ecommerce.produto.service.categoria.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/categoria")
public class CategoriaController implements ICategoriaController{

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping("/criar-categoria")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<CategoriaResponse> criarCategoria(@RequestBody CategoriaRequest categoria){
        return new ResponseEntity<>(categoriaService.criar(categoria), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoriaEntity>> obterTodosProdutos(){
        List<CategoriaEntity> response = categoriaService.obterCategoria();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
