package onecenter.com.br.ecommerce.produto.exception.imagens;

import onecenter.com.br.ecommerce.utils.Constantes;

public class ImagensException extends RuntimeException{

    public ImagensException(){
        super(Constantes.ErroCadastraImagem);
    }
}
