package onecenter.com.br.ecommerce.pessoa.exception.login;

import onecenter.com.br.ecommerce.utils.Constantes;

public class AcessoNegadoException extends RuntimeException{
    public AcessoNegadoException(){
        super(Constantes.AcessoNegadoException);
    }
}
