package onecenter.com.br.ecommerce.config.exception;

import onecenter.com.br.ecommerce.utils.Constantes;

public class ProdutoException extends RuntimeException{

    public ProdutoException(){
        super(Constantes.ErroCadastrarProduto);
    }
}
