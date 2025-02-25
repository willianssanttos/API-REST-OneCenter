package onecenter.com.br.ecommerce.produto.exception;

import onecenter.com.br.ecommerce.utils.Constantes;

public class ErroLocalizarProdutoNotFoundException extends RuntimeException{

    public ErroLocalizarProdutoNotFoundException(){
        super(Constantes.ErroBuscarProduto);
    }
}
