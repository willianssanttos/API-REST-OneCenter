package onecenter.com.br.ecommerce.pedidos.exception.cupom;

import onecenter.com.br.ecommerce.utils.Constantes;

public class ErroLocalizarCupomNotFoundException extends RuntimeException{
    public ErroLocalizarCupomNotFoundException(){
        super(Constantes.CupomNaoEncontrado);
    }
}
