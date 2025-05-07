package onecenter.com.br.ecommerce.pedidos.exception.cupom;

import onecenter.com.br.ecommerce.utils.Constantes;

public class CupomUtilizadoPorClienteException extends RuntimeException {

    public CupomUtilizadoPorClienteException(){
        super(Constantes.CupomJaUilizado);
    }
}
