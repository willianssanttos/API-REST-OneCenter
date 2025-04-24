package onecenter.com.br.ecommerce.pessoa.exception.login;

import onecenter.com.br.ecommerce.utils.Constantes;

public class ObterLoginNotFoundException extends RuntimeException{

    public ObterLoginNotFoundException(){
        super(Constantes.erroLoginConta);
    }
}
