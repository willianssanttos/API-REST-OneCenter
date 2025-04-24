package onecenter.com.br.ecommerce.pessoa.exception.login;

import onecenter.com.br.ecommerce.utils.Constantes;

public class TokenException extends RuntimeException{

    public TokenException(){
        super(Constantes.GToken);
    }
}
