package onecenter.com.br.ecommerce.produto.controller.imagens;

import onecenter.com.br.ecommerce.produto.dto.imagens.request.ImagensProdutosRequest;
import onecenter.com.br.ecommerce.produto.dto.imagens.response.ImagensProdutosResponse;
import onecenter.com.br.ecommerce.produto.service.imagem.ImagemProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/v1/imagem" )
public class ImagensProdutosController {

    @Autowired
    private ImagemProdutoService imagemProdutoService;

    @PostMapping(value = "/registrar", consumes = {"multipart/form-data"})
    public ResponseEntity<ImagensProdutosResponse> resgistrarImagem(
            @RequestParam("id_produtos") Integer id_produtos,
            @RequestParam("produto_imagem") MultipartFile  imagens){

        ImagensProdutosRequest imagem = new ImagensProdutosRequest();
        imagem.setId_produto(id_produtos);
        imagem.setCaminho(imagens);

        return new ResponseEntity<>(imagemProdutoService.registrarImagens(imagem), HttpStatus.CREATED);
    }
}
