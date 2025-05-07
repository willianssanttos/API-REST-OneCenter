package onecenter.com.br.ecommerce.pedidos.exception.cupom;

import onecenter.com.br.ecommerce.utils.Constantes;

public class CupomException extends RuntimeException{

    public CupomException(){
        super(Constantes.ErroCadastrarCupom);
    }
}
