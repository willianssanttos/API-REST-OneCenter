package onecenter.com.br.ecommerce.controller.produtos;

import onecenter.com.br.ecommerce.dto.produtos.request.ProdutoRequest;
import onecenter.com.br.ecommerce.dto.produtos.response.ProdutosResponse;
import onecenter.com.br.ecommerce.entity.produtos.ProdutosEntity;
import onecenter.com.br.ecommerce.service.produtos.ProdutosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/produtos")
public class ProdutosController {

    @Autowired
    private ProdutosService produtosService;

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

        return new ResponseEntity<>(produtosService.criar(produto), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProdutosResponse>> obterTodosProdutos(){
        List<ProdutosResponse> response = produtosService.obterTodosProdutos();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(value = "/atualizar-produto/{idProduto}", consumes = {"multipart/form-data"})
    public ResponseEntity<String> atualizarProduto(
            @PathVariable Integer idProduto,
            @RequestParam("nome") String nome,
            @RequestParam("preco") Double preco,
            @RequestParam("id_categoria") Integer idCategoria,
            @RequestParam("produto_imagem") MultipartFile  imagem) {

        ProdutosResponse produto = new ProdutosResponse();
        produto.setId_produto(idProduto);
        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setId_categoria(idCategoria);
        produto.setProduto_imagem(String.valueOf(imagem));

        produtosService.atualizarProduto(produto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
