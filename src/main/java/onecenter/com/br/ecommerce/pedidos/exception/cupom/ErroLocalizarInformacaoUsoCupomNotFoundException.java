package onecenter.com.br.ecommerce.pedidos.exception.cupom;

import onecenter.com.br.ecommerce.utils.Constantes;

public class ErroLocalizarInformacaoUsoCupomNotFoundException extends RuntimeException{

    public ErroLocalizarInformacaoUsoCupomNotFoundException(){
        super(Constantes.ErroLocalizarInformacaoCupom);
    }
}
