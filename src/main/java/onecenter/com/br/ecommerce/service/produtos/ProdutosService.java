package onecenter.com.br.ecommerce.service.produtos;

import onecenter.com.br.ecommerce.config.exception.DeletarProdutoException;
import onecenter.com.br.ecommerce.config.exception.ObterProdutosNotFundException;
import onecenter.com.br.ecommerce.config.exception.ProdutoException;
import onecenter.com.br.ecommerce.dto.produtos.request.ProdutoRequest;
import onecenter.com.br.ecommerce.dto.produtos.response.ProdutosResponse;
import onecenter.com.br.ecommerce.entity.produtos.categoria.CategoriaEntity;
import onecenter.com.br.ecommerce.entity.produtos.ProdutosEntity;
import onecenter.com.br.ecommerce.repository.produtos.categoria.ICategoriaRepository;
import onecenter.com.br.ecommerce.repository.produtos.IProdutosRepository;
import onecenter.com.br.ecommerce.service.produtos.imagem.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutosService {

    @Autowired
    private IProdutosRepository iProdutosRepository;

    @Autowired
    private ICategoriaRepository iCategoriaRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public ProdutosResponse criar(ProdutoRequest produto){

       try {

           CategoriaEntity categoria = iCategoriaRepository.categoriaId(produto.getId_categoria());
           String caminhoImagem = fileStorageService.salvarImagem(produto.getProduto_imagem());

           ProdutosEntity novoProduto = ProdutosEntity.builder()
               .nome(produto.getNome())
               .preco(produto.getPreco())
               .produto_imagem(caminhoImagem)
               .id_categoria(categoria.getId_categoria())
               .build();

           ProdutosEntity produtoInserido = iProdutosRepository.criar(novoProduto);
           return mapearProduto(produtoInserido);

       } catch (Exception e){
           throw new ProdutoException();
       }
    }

    private ProdutosResponse mapearProduto(ProdutosEntity produtoInserido){
        return ProdutosResponse.builder()
                .id_produto(produtoInserido.getId_produto())
                .nome(produtoInserido.getNome())
                .preco(produtoInserido.getPreco())
                .produto_imagem(String.valueOf(produtoInserido.getProduto_imagem()))
                .id_categoria(produtoInserido.getId_categoria())
                .build();
    }

    public List<ProdutosResponse> obterTodosProdutos(){
        try {
            List<ProdutosEntity> produtos = iProdutosRepository.obterTodosProdutos();
            return produtos.stream().map(this::mapearProduto).collect(Collectors.toList());
        } catch (Exception e){
            throw new ObterProdutosNotFundException();
        }
    }

    public void atualizarProduto(ProdutosResponse editar){
        try {

            iProdutosRepository.atualizarProduto(editar);
        } catch (Exception e){
            throw new ProdutoException();
        }
    }

    public void excluirProduto(Integer idProduto){
        try {
            iProdutosRepository.excluirProduto(idProduto);
        } catch (Exception e){
            throw new DeletarProdutoException();
        }
    }


}
