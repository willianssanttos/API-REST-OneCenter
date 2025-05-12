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
import onecenter.com.br.ecommerce.pessoa.service.imagem.FileStorageService;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public ProdutosResponse criar(ProdutoRequest produto){
        logger.info(Constantes.DebugRegistroProcesso);
       try {
           Integer categoria = iCategoriaRepository.obterCategoriaPorNome(produto.getNomeCategoria());
           String caminhoImagem = fileStorageService.salvarImagem(produto.getProduto_imagem());
           ProdutosEntity novoProduto = ProdutosEntity.builder()
               .nome(produto.getNome())
               .preco(produto.getPreco())
               .descricaoProduto(produto.getDescricaoProduto())
               .produtoImagem(caminhoImagem)
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
                .idProduto(produtoInserido.getIdProduto())
                .nome(produtoInserido.getNome())
                .preco(produtoInserido.getPreco())
                .produtoImagem(String.valueOf(produtoInserido.getProdutoImagem()))
                .descricaoProduto(produtoInserido.getDescricaoProduto())
                .nomeCategoria(EnumCategoria.valueOf(produtoInserido.getNomeCategoria()).name())
                .build();
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public ProdutosEntity obterProdutoPorId( Integer idProduto){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            logger.info(Constantes.InfoBuscar, idProduto);
            return iProdutosRepository.buscarIdProduto(idProduto);
        } catch (Exception e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor);
            throw new ObterProdutosNotFundException();
        }
    }

    @Transactional(readOnly = true)
    public List<String> buscarImagensProduto(Integer idProduto){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            logger.info(Constantes.InfoBuscar, idProduto);
            return iProdutosRepository.buscarImagensProduto(idProduto);
        } catch (Exception ex){
            logger.error(Constantes.ErroBuscarRegistroNoServidor);
            throw new ImagensException();
        }
    }

    @Transactional(readOnly = true)
    public ProdutosEntity buscarProdutoComImagens(Integer idProduto) {
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            ProdutosEntity produto = obterProdutoPorId(idProduto);
            List<String> imagens = buscarImagensProduto(idProduto);
            produto.setImagens(imagens);
            logger.info(Constantes.InfoBuscar, idProduto);
            return produto;
        }catch (Exception e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor);
            throw new ObterProdutosNotFundException();
        }
    }

    @Transactional
    public void atualizarProduto(ProdutosResponse editar){
        logger.info(Constantes.DebugEditarProcesso);
        try {
            Integer categoria = iCategoriaRepository.obterCategoriaPorNome(editar.getNomeCategoria());
            ProdutosEntity atualizarProduto = ProdutosEntity.builder()
                    .idProduto(editar.getIdProduto())
                    .nome(editar.getNome())
                    .preco(editar.getPreco())
                    .descricaoProduto(editar.getDescricaoProduto())
                    .produtoImagem(editar.getProdutoImagem())
                    .id_categoria(categoria)
                    .build();
            logger.info(Constantes.InfoEditar, editar);
            iProdutosRepository.atualizarProduto(atualizarProduto);
        } catch (Exception e){
            logger.error(Constantes.ErroEditarRegistroNoServidor);
            throw new EditarProdutoException();
        }
    }

    @Transactional
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
