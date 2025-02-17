package onecenter.com.br.ecommerce.config.exception;

import onecenter.com.br.ecommerce.utils.Constantes;

public class CategoriaException extends RuntimeException{

    public CategoriaException(){
        super(Constantes.ErroCadastrarCategoria);
    }
}
