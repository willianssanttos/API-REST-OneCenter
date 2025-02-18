package onecenter.com.br.ecommerce.service.produtos;

import onecenter.com.br.ecommerce.config.exception.produtos.DeletarProdutoException;
import onecenter.com.br.ecommerce.config.exception.produtos.EditarProdutoException;
import onecenter.com.br.ecommerce.config.exception.produtos.ObterProdutosNotFundException;
import onecenter.com.br.ecommerce.config.exception.produtos.ProdutoException;
import onecenter.com.br.ecommerce.dto.produtos.request.ProdutoRequest;
import onecenter.com.br.ecommerce.dto.produtos.response.ProdutosResponse;
import onecenter.com.br.ecommerce.entity.produtos.categoria.CategoriaEntity;
import onecenter.com.br.ecommerce.entity.produtos.ProdutosEntity;
import onecenter.com.br.ecommerce.repository.produtos.categoria.ICategoriaRepository;
import onecenter.com.br.ecommerce.repository.produtos.IProdutosRepository;
import onecenter.com.br.ecommerce.service.produtos.imagem.FileStorageService;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ProdutosService.class);

    public ProdutosResponse criar(ProdutoRequest produto){
        logger.info(Constantes.DebugRegistroProcesso);
       try {

           CategoriaEntity categoria = iCategoriaRepository.obterCategoriaPorId(produto.getId_categoria());
           String caminhoImagem = fileStorageService.salvarImagem(produto.getProduto_imagem());

           ProdutosEntity novoProduto = ProdutosEntity.builder()
               .nome(produto.getNome())
               .preco(produto.getPreco())
               .produto_imagem(caminhoImagem)
               .id_categoria(categoria.getId_categoria())
               .build();
           ProdutosEntity produtoInserido = iProdutosRepository.criar(novoProduto);
           logger.info(Constantes.InfoRegistrar, produto);
           return mapearProduto(produtoInserido);

       } catch (Exception e){
           logger.error(Constantes.ErroRegistrarNoServidor);
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
        logger.info(Constantes.DebugBuscarProcesso);
        try {

            List<ProdutosEntity> produtos = iProdutosRepository.obterTodosProdutos();
            logger.info(Constantes.InfoBuscar);
            return produtos.stream().map(this::mapearProduto).collect(Collectors.toList());

        } catch (Exception e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor);
            throw new ObterProdutosNotFundException();
        }
    }

    public ProdutosResponse atualizarProduto(ProdutosResponse editar){
        logger.info(Constantes.DebugEditarProcesso);
        try {
            logger.info(Constantes.InfoEditar, editar);
            return iProdutosRepository.atualizarProduto(editar);
        } catch (Exception e){
            logger.error(Constantes.ErroEditarRegistroNoServidor);
            throw new EditarProdutoException();
        }
    }

    public void excluirProduto(Integer idProduto){
        logger.info(Constantes.DebugDeletarProcesso);
        try {
            logger.info(Constantes.InfoDeletar, idProduto);
            iProdutosRepository.excluirProduto(idProduto);
        } catch (Exception e){
            logger.error(Constantes.ErroDeletarRegistroNoServidor);
            throw new DeletarProdutoException();
        }
    }

}
