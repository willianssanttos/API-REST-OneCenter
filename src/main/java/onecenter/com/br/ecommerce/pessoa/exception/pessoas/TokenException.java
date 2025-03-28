package onecenter.com.br.ecommerce.pessoa.exception.pessoas;

import onecenter.com.br.ecommerce.utils.Constantes;

public class TokenException extends RuntimeException{

    public TokenException(){
        super(Constantes.GToken);
    }
}
