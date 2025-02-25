package onecenter.com.br.ecommerce.produto.exception.categoria;

import onecenter.com.br.ecommerce.utils.Constantes;

public class CategoriaNotFoundException extends RuntimeException {

    public CategoriaNotFoundException(){
        super(Constantes.ErroBuscarCategoria);
    }
}

