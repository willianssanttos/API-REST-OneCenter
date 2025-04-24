package onecenter.com.br.ecommerce.produto.exception.imagens;

import onecenter.com.br.ecommerce.utils.Constantes;

public class ErroLocalizarImagemNotFoundException extends RuntimeException{

    public ErroLocalizarImagemNotFoundException(){
        super(Constantes.ErroLocalizarImagems);
    }
}
