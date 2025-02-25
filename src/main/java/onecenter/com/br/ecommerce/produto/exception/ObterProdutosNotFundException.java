package onecenter.com.br.ecommerce.produto.exception;

import onecenter.com.br.ecommerce.utils.Constantes;

public class ObterProdutosNotFundException extends RuntimeException{

    public ObterProdutosNotFundException(){
        super(Constantes.ErroBuscarProduto);
    }
}
