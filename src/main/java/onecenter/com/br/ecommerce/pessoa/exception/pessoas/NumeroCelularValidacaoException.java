package onecenter.com.br.ecommerce.pessoa.exception.pessoas;

import onecenter.com.br.ecommerce.utils.Constantes;

public class NumeroCelularValidacaoException extends RuntimeException{

    public NumeroCelularValidacaoException(){
        super(Constantes.cadastroCelular);
    }
}
