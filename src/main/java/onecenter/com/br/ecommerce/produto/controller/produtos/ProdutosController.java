package onecenter.com.br.ecommerce.produto.controller.produtos;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import onecenter.com.br.ecommerce.produto.dto.produtos.request.ProdutoRequest;
import onecenter.com.br.ecommerce.produto.dto.produtos.response.ProdutosResponse;
import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;
import onecenter.com.br.ecommerce.produto.service.produtos.ProdutosService;
import onecenter.com.br.ecommerce.pessoa.service.imagem.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/produtos")
public class ProdutosController implements IProdutoController{

    @Autowired
    private ProdutosService produtosService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping(value = "/criar", consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<ProdutosResponse> criarProduto(
            @RequestParam("nome") String nome,
            @RequestParam("preco") Double preco,
            @RequestParam("categoria") String nomeCategaria,
            @RequestParam("descricaoProduto") String descricaoProduto,
            @RequestParam("produto_imagem") MultipartFile  imagem){

        ProdutoRequest produto = new ProdutoRequest();
        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setNomeCategoria(nomeCategaria);
        produto.setProduto_imagem(imagem);
        produto.setDescricaoProduto(descricaoProduto);

        return new ResponseEntity<>(produtosService.criar(produto), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProdutosResponse>> obterTodosProdutos(){
        List<ProdutosResponse> response = produtosService.obterTodosProdutos();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProdutosEntity> obterProdutoPorId(@PathVariable Integer id) {
        ProdutosEntity response = produtosService.buscarProdutoComImagens(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PutMapping(value = "/atualizar-produto/{idProduto}", consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity atualizarProduto(
            @PathVariable Integer idProduto,
            @RequestParam("nome") String nome,
            @RequestParam("preco") Double preco,
            @RequestParam("descricaoProduto") String descricaoProduto,
            @RequestParam("categoria") String nomeCategaria,
            @RequestParam("produto_imagem") MultipartFile  imagem) throws IOException {

        String caminhoImagem = fileStorageService.salvarImagem(imagem);

        ProdutosResponse produto = new ProdutosResponse();
        produto.setIdProduto(idProduto);
        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setDescricaoProduto(descricaoProduto);
        produto.setNomeCategoria(nomeCategaria);
        produto.setProdutoImagem(caminhoImagem);

        produtosService.atualizarProduto(produto);
        return ResponseEntity.status(HttpStatus.OK).body(produto);
    }

    @DeleteMapping("/deletar-produto/{idProduto}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Void> deletarProduto(@PathVariable Integer idProduto){
        produtosService.excluirProduto(idProduto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
