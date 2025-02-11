package onecenter.com.br.ecommerce.config.exception;

import onecenter.com.br.ecommerce.utils.Constantes;

public class CategoriaNotFoundException extends RuntimeException {

    public CategoriaNotFoundException(){
        super(Constantes.ErroBuscarCategoria);
    }
}

