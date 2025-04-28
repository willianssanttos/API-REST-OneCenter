package onecenter.com.br.ecommerce.produto.controller.produtos;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import onecenter.com.br.ecommerce.produto.dto.produtos.request.ProdutoRequest;
import onecenter.com.br.ecommerce.produto.dto.produtos.response.ProdutosResponse;
import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IProdutoController {

    @Operation(summary = "Cadastro de produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao realizar o cadastro!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao realizar cadastro!")
    })
    default ResponseEntity<ProdutosResponse> criarProduto(
            @RequestParam("nome") String nome,
            @RequestParam("preco") Double preco,
            @RequestParam("categoria") String nomeCategaria,
            @RequestParam("descricaoProduto") String descricaoProduto,
            @RequestParam("produto_imagem") MultipartFile imagem) {

        ProdutoRequest produto = new ProdutoRequest();
        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setNomeCategoria(nomeCategaria);
        produto.setProduto_imagem(imagem);
        produto.setDescricaoProduto(descricaoProduto);
        return null;
    }

    @Operation(summary = "Listagem de produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao listar produtos!")
    })
    ResponseEntity<List<ProdutosResponse>> obterTodosProdutos();

    @Operation(summary = "Listagem de produtos por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto recuperado com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao listar produto!")
    })
    ResponseEntity<ProdutosEntity> obterProdutoPorId(@PathVariable Integer id);

    @Operation(summary = "Operação para atualizar produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "produto atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar produto!"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao atualizar produto!")
    })
    default ResponseEntity<ProdutosResponse> atualizarProduto(
            @PathVariable Integer idProduto,
            @RequestParam("nome") String nome,
            @RequestParam("preco") Double preco,
            @RequestParam("descricaoProduto") String descricaoProduto,
            @RequestParam("categoria") String nomeCategaria,
            @RequestParam("produto_imagem") MultipartFile  imagem) throws IOException {

        ProdutosResponse produto = new ProdutosResponse();
        produto.setIdProduto(idProduto);
        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setDescricaoProduto(descricaoProduto);
        produto.setNomeCategoria(nomeCategaria);
        produto.setProdutoImagem(String.valueOf(imagem));
        return null;
    }

    @Operation(summary = "Operação para deletar produto ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao deletar produto!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao deletar produto!")
    })
    ResponseEntity<Void> deletarProduto(@PathVariable Integer idProduto);
}
