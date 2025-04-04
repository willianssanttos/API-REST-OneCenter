package onecenter.com.br.ecommerce.produto.service.produtos;

import onecenter.com.br.ecommerce.produto.entity.categoria.EnumCategoria;
import onecenter.com.br.ecommerce.produto.exception.DeletarProdutoException;
import onecenter.com.br.ecommerce.produto.exception.EditarProdutoException;
import onecenter.com.br.ecommerce.produto.exception.ObterProdutosNotFundException;
import onecenter.com.br.ecommerce.produto.exception.ProdutoException;
import onecenter.com.br.ecommerce.produto.dto.produtos.request.ProdutoRequest;
import onecenter.com.br.ecommerce.produto.dto.produtos.response.ProdutosResponse;
import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;
import onecenter.com.br.ecommerce.produto.exception.imagens.ImagensException;
import onecenter.com.br.ecommerce.produto.repository.categoria.ICategoriaRepository;
import onecenter.com.br.ecommerce.produto.repository.produtos.IProdutosRepository;
import onecenter.com.br.ecommerce.produto.service.imagem.FileStorageService;
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

           Integer categoria = iCategoriaRepository.obterCategoriaPorNome(produto.getNomeCategoria());
           String caminhoImagem = fileStorageService.salvarImagem(produto.getProduto_imagem());

           ProdutosEntity novoProduto = ProdutosEntity.builder()
               .nome(produto.getNome())
               .preco(produto.getPreco())
               .descricaoProduto(produto.getDescricaoProduto())
               .produto_imagem(caminhoImagem)
               .id_categoria(categoria)
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
                .produtoImagem(String.valueOf(produtoInserido.getProduto_imagem()))
                .descricaoProduto(produtoInserido.getDescricaoProduto())
                .nomeCategoria(EnumCategoria.valueOf(produtoInserido.getNomeCategoria()).name())
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

    public ProdutosEntity obterProdutoPorId( Integer idProduto){
        try {
            logger.info(Constantes.InfoBuscar, idProduto);
            return iProdutosRepository.buscarIdProduto(idProduto);
        } catch (Exception e){
            throw new ObterProdutosNotFundException();
        }
    }

    public List<String> buscarImagensProduto(Integer idProduto){
        try {
            return iProdutosRepository.buscarImagensProduto(idProduto);
        } catch (Exception ex){
            throw new ImagensException();
        }
    }

    public ProdutosEntity buscarProdutoComImagens(Integer idProduto) {
        ProdutosEntity produto = obterProdutoPorId(idProduto);
        List<String> imagens = buscarImagensProduto(idProduto);
        produto.setImagens(imagens);
        return produto;
    }

    public void atualizarProduto(ProdutosResponse editar){
        logger.info(Constantes.DebugEditarProcesso);
        try {
            Integer categoria = iCategoriaRepository.obterCategoriaPorNome(editar.getNomeCategoria());

            ProdutosEntity atualizarProduto = ProdutosEntity.builder()
                    .id_produto(editar.getId_produto())
                    .nome(editar.getNome())
                    .preco(editar.getPreco())
                    .descricaoProduto(editar.getDescricaoProduto())
                    .produto_imagem(editar.getProdutoImagem())
                    .id_categoria(categoria)
                    .build();
            logger.info(Constantes.InfoEditar, editar);
            iProdutosRepository.atualizarProduto(atualizarProduto);
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
