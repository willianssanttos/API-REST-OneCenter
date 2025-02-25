package onecenter.com.br.ecommerce.produto.exception;

import onecenter.com.br.ecommerce.utils.Constantes;

public class EditarProdutoException extends RuntimeException{

    public EditarProdutoException(){
        super(Constantes.ErroEditarProduto);
    }
}
