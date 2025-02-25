package onecenter.com.br.ecommerce.pessoa.exception.pessoas;

import onecenter.com.br.ecommerce.utils.Constantes;

public class EmailValidacaoException extends RuntimeException{

    public EmailValidacaoException(){
        super(Constantes.cadastroEmail);
    }
}
