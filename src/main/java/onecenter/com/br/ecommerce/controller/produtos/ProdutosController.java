package onecenter.com.br.ecommerce.controller.produtos;

import onecenter.com.br.ecommerce.dto.produtos.request.ProdutoRequest;
import onecenter.com.br.ecommerce.dto.produtos.response.ProdutosResponse;
import onecenter.com.br.ecommerce.service.produtos.ProdutosService;
import onecenter.com.br.ecommerce.service.produtos.imagem.FileStorageService;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/produtos")
public class ProdutosController {

    @Autowired
    private ProdutosService produtosService;

    @Autowired
    private FileStorageService fileStorageService;

    private static final Logger logger = LoggerFactory.getLogger(ProdutosController.class);

    @PostMapping(value = "/criar", consumes = {"multipart/form-data"})
    public ResponseEntity<ProdutosResponse> criarProduto(
            @RequestParam("nome") String nome,
            @RequestParam("preco") Double preco,
            @RequestParam("id_categoria") Integer idCategoria,
            @RequestParam("produto_imagem") MultipartFile  imagem) {

        ProdutoRequest produto = new ProdutoRequest();
        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setId_categoria(idCategoria);
        produto.setProduto_imagem(imagem);

        logger.info(Constantes.InfoRegistrar, produto);
        return new ResponseEntity<>(produtosService.criar(produto), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProdutosResponse>> obterTodosProdutos(){
        List<ProdutosResponse> response = produtosService.obterTodosProdutos();
        logger.info(Constantes.InfoBuscar, response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(value = "/atualizar-produto/{idProduto}", consumes = {"multipart/form-data"})
    public ResponseEntity<ProdutosResponse> atualizarProduto(
            @PathVariable Integer idProduto,
            @RequestParam("nome") String nome,
            @RequestParam("preco") Double preco,
            @RequestParam("id_categoria") Integer idCategoria,
            @RequestParam("produto_imagem") MultipartFile  imagem) throws IOException {

        String caminhoImagem = fileStorageService.salvarImagem(imagem);

        ProdutosResponse produto = new ProdutosResponse();
        produto.setId_produto(idProduto);
        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setId_categoria(idCategoria);
        produto.setProduto_imagem(caminhoImagem);

        ProdutosResponse response = produtosService.atualizarProduto(produto);
        logger.info(Constantes.InfoEditar, response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/deletar-produto/{idProduto}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Integer idProduto){
        produtosService.excluirProduto(idProduto);
        logger.info(Constantes.InfoDeletar, idProduto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
