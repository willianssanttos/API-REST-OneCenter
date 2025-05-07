package onecenter.com.br.ecommerce.pedidos.exception.cupom;

import onecenter.com.br.ecommerce.utils.Constantes;

public class ErroAtualizarStatusCupomException extends RuntimeException{

    public ErroAtualizarStatusCupomException(){
        super(Constantes.ErroAtualizaStatusCupom);
    }
}
