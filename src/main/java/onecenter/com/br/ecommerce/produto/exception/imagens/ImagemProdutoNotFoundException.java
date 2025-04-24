package onecenter.com.br.ecommerce.produto.exception.imagens;

import onecenter.com.br.ecommerce.utils.Constantes;

public class ImagemProdutoNotFoundException extends RuntimeException{

    public ImagemProdutoNotFoundException(){
        super(Constantes.ErroBuscarImagemProduto);
    }
}

