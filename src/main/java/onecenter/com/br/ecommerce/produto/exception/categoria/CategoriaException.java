package onecenter.com.br.ecommerce.produto.exception.categoria;

import onecenter.com.br.ecommerce.utils.Constantes;

public class CategoriaException extends RuntimeException{

    public CategoriaException(){
        super(Constantes.ErroCadastrarCategoria);
    }
}
