package onecenter.com.br.ecommerce.config.exception.handler;

import onecenter.com.br.ecommerce.pedidos.exception.cupom.CupomDuplicadoException;
import onecenter.com.br.ecommerce.pedidos.exception.cupom.CupomException;
import onecenter.com.br.ecommerce.pedidos.exception.cupom.CupomInvalidoException;
import onecenter.com.br.ecommerce.pedidos.exception.pagamento.AtualizarStatusPagamentoException;
import onecenter.com.br.ecommerce.pedidos.exception.pagamento.CheckoutPagamentoException;
import onecenter.com.br.ecommerce.pedidos.exception.pagamento.PagamentoException;
import onecenter.com.br.ecommerce.pedidos.exception.pagamento.PagamentoExistenteExceptionNotFound;
import onecenter.com.br.ecommerce.pedidos.exception.pedido.CancelarPedidoException;
import onecenter.com.br.ecommerce.pedidos.exception.pedido.ErroAoLocalizarPedidoNotFoundException;
import onecenter.com.br.ecommerce.pedidos.exception.pedido.ItemPedidoException;
import onecenter.com.br.ecommerce.pessoa.exception.endereco.EnderecoException;
import onecenter.com.br.ecommerce.config.exception.entity.ApiError;
import onecenter.com.br.ecommerce.pessoa.exception.login.AcessoNegadoException;
import onecenter.com.br.ecommerce.pessoa.exception.login.ObterLoginNotFoundException;
import onecenter.com.br.ecommerce.pessoa.exception.login.SenhaValidacaoException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.*;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.fisica.CpfExistenteException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.fisica.CpfValidacaoException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.NumeroCelularValidacaoException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.fisica.ObterPessoaPorCpfNotFoundException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.juridico.CnpjExistenteException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.juridico.CnpjValidacaoException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.juridico.ObterPessoaPorCnpjNotFoundException;
import onecenter.com.br.ecommerce.produto.exception.*;
import onecenter.com.br.ecommerce.produto.exception.categoria.CategoriaException;
import onecenter.com.br.ecommerce.produto.exception.categoria.CategoriaNotFoundException;
import onecenter.com.br.ecommerce.produto.exception.imagens.ErroLocalizarImagemNotFoundException;
import onecenter.com.br.ecommerce.produto.exception.imagens.ImagemProdutoNotFoundException;
import onecenter.com.br.ecommerce.produto.exception.imagens.ImagensException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({
            Exception.class,
            CupomException.class,
            ImagensException.class,
            ProdutoException.class,
            EnderecoException.class,
            CategoriaException.class,
            PagamentoException.class,
            ItemPedidoException.class,
            EditarPessoaException.class,
            EditarProdutoException.class,
            DeletarProdutoException.class,
            CancelarPedidoException.class,
            AtualizarStatusPagamentoException.class,
            ObterTodasPessoasNotFoundException.class
    })
    public ResponseEntity<ApiError> genericException(Exception ex){
        ApiError apiError = ApiError
                .builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({

            ObterLoginNotFoundException.class,
            CategoriaNotFoundException.class,
            ObterProdutosNotFundException.class,
            ImagemProdutoNotFoundException.class,
            ObterPessoaPorCpfNotFoundException.class,
            ObterPessoaPorCnpjNotFoundException.class,
            PagamentoExistenteExceptionNotFound.class,
            ErroLocalizarImagemNotFoundException.class,
            ErroLocalizarPessoaNotFoundException.class,
            ErroLocalizarProdutoNotFoundException.class,
            ErroAoLocalizarPedidoNotFoundException.class,
    })
    public ResponseEntity<ApiError> notFoundException(RuntimeException ex) {
        ApiError apiError = ApiError
                .builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND.name())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            CpfValidacaoException.class,
            CnpjValidacaoException.class,
            NomeValidacaoException.class,
            EmailValidacaoException.class,
            SenhaValidacaoException.class,
            CheckoutPagamentoException.class,
            NumeroCelularValidacaoException.class
    })
    public ResponseEntity<ApiError> badRequetException(RuntimeException ex) {
        ApiError apiError = ApiError
                .builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            CpfExistenteException.class,
            CupomInvalidoException.class,
            CnpjExistenteException.class,
            EmailExistenteException.class,
            CupomDuplicadoException.class,
    })
    public ResponseEntity<ApiError> unavailableException(RuntimeException ex) {
        ApiError apiError = ApiError
                .builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.CONFLICT.value())
                .status(HttpStatus.CONFLICT.name())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            AcessoNegadoException.class,
    })
    public ResponseEntity<ApiError> unauthorizedAccess(RuntimeException ex) {
        ApiError apiError = ApiError
                .builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.FORBIDDEN.value())
                .status(HttpStatus.FORBIDDEN.name())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ApiError> argumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errorList = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        ApiError apiError = ApiError
                .builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .errors(errorList)
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

}
