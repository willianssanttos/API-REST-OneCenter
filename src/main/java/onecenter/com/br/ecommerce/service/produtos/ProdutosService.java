package onecenter.com.br.ecommerce.service.produtos;

import onecenter.com.br.ecommerce.config.exception.ObterProdutosNotFundException;
import onecenter.com.br.ecommerce.config.exception.ProdutoException;
import onecenter.com.br.ecommerce.dto.produtos.request.ProdutoRequest;
import onecenter.com.br.ecommerce.dto.produtos.response.ProdutosResponse;
import onecenter.com.br.ecommerce.entity.produtos.ProdutosEntity;
import onecenter.com.br.ecommerce.repository.produtos.IProdutosRepository;
import onecenter.com.br.ecommerce.service.imagem.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutosService {

    @Autowired
    private IProdutosRepository iProdutosRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public ProdutosResponse criar(ProdutoRequest produto){

       try {
           String caminhoImagem = fileStorageService.salvarImagem(produto.getProduto_imagem());

           ProdutosEntity novoProduto = ProdutosEntity.builder()
               .nome(produto.getNome())
               .preco(produto.getPreco())
               .produto_imagem(caminhoImagem)
               .id_categoria(produto.getId_categoria())
               .build();

           ProdutosEntity produtoInserido = iProdutosRepository.criar(novoProduto);
           return mapearProduto(produtoInserido);

       } catch (Exception e){
           throw new ProdutoException();
       }
    }

    private ProdutosResponse mapearProduto(ProdutosEntity produtoInserido){
        return ProdutosResponse.builder()
                .nome(produtoInserido.getNome())
                .preco(produtoInserido.getPreco())
                .produto_imagem(String.valueOf(produtoInserido.getProduto_imagem()))
                .id_categoria(produtoInserido.getId_categoria())
                .build();
    }

    public List<ProdutosEntity> obterTodosProdutos(){
        try {
            return iProdutosRepository.obterTodosProdutos();
        } catch (Exception e){
            throw new ObterProdutosNotFundException();
        }
    }
}
