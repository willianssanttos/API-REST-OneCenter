package onecenter.com.br.ecommerce.produto.controller.imagens;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import onecenter.com.br.ecommerce.produto.dto.imagens.request.ImagensProdutosRequest;
import onecenter.com.br.ecommerce.produto.dto.imagens.response.ImagensProdutosResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface IImagensProdutosController {

    @Operation(summary = "Cadastrar imagens dos produtos pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao realizar o cadastro!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao realizar cadastro!")
    })
    default ResponseEntity<ImagensProdutosResponse> resgistrarImagem(
            @RequestParam("id_produtos") Integer id_produtos,
            @RequestParam("produto_imagem") MultipartFile imagens) {

        ImagensProdutosRequest imagem = new ImagensProdutosRequest();
        imagem.setId_produto(id_produtos);
        imagem.setCaminho(imagens);
        return null;
    }
}
