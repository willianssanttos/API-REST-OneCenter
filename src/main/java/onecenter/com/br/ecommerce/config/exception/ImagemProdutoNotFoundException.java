package onecenter.com.br.ecommerce.config.exception;

import onecenter.com.br.ecommerce.utils.Constantes;

public class ImagemProdutoNotFoundException extends RuntimeException{

    public ImagemProdutoNotFoundException(){
        super(Constantes.ErroBuscarImagemProduto);
    }
}

