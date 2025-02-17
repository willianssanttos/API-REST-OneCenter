//package onecenter.com.br.ecommerce.config.exception.handler;
//
//import onecenter.com.br.ecommerce.config.exception.entity.ApiError;
//import onecenter.com.br.ecommerce.config.exception.pessoas.EditarPessoaException;
//import onecenter.com.br.ecommerce.config.exception.pessoas.ObterPessoaPorCpfNotFoundException;
//import onecenter.com.br.ecommerce.config.exception.pessoas.ObterTodasPessoas;
//import onecenter.com.br.ecommerce.config.exception.produtos.*;
//import onecenter.com.br.ecommerce.config.exception.produtos.categoria.CategoriaException;
//import onecenter.com.br.ecommerce.config.exception.produtos.categoria.CategoriaNotFoundException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestControllerAdvice
//public class RestExceptionHandler {
//
//    @ExceptionHandler({
//            Exception.class,
//            ProdutoException.class,
//            ProdutoException.class,
//            ObterTodasPessoas.class,
//            CategoriaException.class,
//            EditarPessoaException.class,
//            EditarProdutoException.class,
//            DeletarProdutoException.class,
//
//    })
//    public ResponseEntity<ApiError> genericException(Exception ex) {
//        ApiError apiError = ApiError
//                .builder()
//                .timestamp(LocalDateTime.now())
//                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
//                .errors(List.of(ex.getMessage()))
//                .build();
//        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//    @ExceptionHandler({
//
//            CategoriaNotFoundException.class,
//            ImagemProdutoNotFoundException.class,
//            ObterPessoaPorCpfNotFoundException.class,
//            ObterProdutosNotFundException.class,
//
//    })
//    public ResponseEntity<ApiError> notFoundException(RuntimeException ex) {
//        ApiError apiError = ApiError
//                .builder()
//                .timestamp(LocalDateTime.now())
//                .code(HttpStatus.NOT_FOUND.value())
//                .status(HttpStatus.NOT_FOUND.name())
//                .errors(List.of(ex.getMessage()))
//                .build();
//        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler({
//
//
//    })
//    public ResponseEntity<ApiError> badRequetException(RuntimeException ex) {
//        ApiError apiError = ApiError
//                .builder()
//                .timestamp(LocalDateTime.now())
//                .code(HttpStatus.BAD_REQUEST.value())
//                .status(HttpStatus.BAD_REQUEST.name())
//                .errors(List.of(ex.getMessage()))
//                .build();
//        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler({
//
//    })
//    public ResponseEntity<ApiError> unavailableException(RuntimeException ex) {
//        ApiError apiError = ApiError
//                .builder()
//                .timestamp(LocalDateTime.now())
//                .code(HttpStatus.CONFLICT.value())
//                .status(HttpStatus.CONFLICT.name())
//                .errors(List.of(ex.getMessage()))
//                .build();
//        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiError> argumentNotValidException(MethodArgumentNotValidException ex) {
//        List<String> errorList = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(error -> error.getField() + ": " + error.getDefaultMessage())
//                .collect(Collectors.toList());
//        ApiError apiError = ApiError
//                .builder()
//                .timestamp(LocalDateTime.now())
//                .code(HttpStatus.BAD_REQUEST.value())
//                .status(HttpStatus.BAD_REQUEST.name())
//                .errors(errorList)
//                .build();
//        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
//    }
//
//}
