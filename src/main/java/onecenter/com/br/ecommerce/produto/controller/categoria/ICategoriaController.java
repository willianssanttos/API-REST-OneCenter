package onecenter.com.br.ecommerce.produto.controller.categoria;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import onecenter.com.br.ecommerce.produto.dto.categoria.request.CategoriaRequest;
import onecenter.com.br.ecommerce.produto.dto.categoria.response.CategoriaResponse;
import onecenter.com.br.ecommerce.produto.entity.categoria.CategoriaEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ICategoriaController {

    @Operation(summary = "Cadastro de categoria de produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao realizar o cadastro!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao realizar cadastro!")
    })
    ResponseEntity<CategoriaResponse> criarCategoria(@RequestBody CategoriaRequest categoria);

    @Operation(summary = "Listagem de categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao listar categoria!")
    })
    ResponseEntity<List<CategoriaEntity>> obterTodosProdutos();
}
