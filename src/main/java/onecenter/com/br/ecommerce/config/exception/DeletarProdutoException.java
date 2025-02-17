package onecenter.com.br.ecommerce.config.exception;

import onecenter.com.br.ecommerce.utils.Constantes;

public class DeletarProdutoException extends RuntimeException{

    public DeletarProdutoException(){
        super(Constantes.ErroDeletarProduto);
    }
}
