package onecenter.com.br.ecommerce.pedidos.exception.cupom;

import onecenter.com.br.ecommerce.utils.Constantes;

public class CupomInvalidoException extends RuntimeException{

    public CupomInvalidoException(){
        super(Constantes.CupomInvalido);
    }
}
