package onecenter.com.br.ecommerce.pessoa.exception.pessoas.juridico;

import onecenter.com.br.ecommerce.utils.Constantes;

public class CnpjValidacaoException extends RuntimeException{

    public CnpjValidacaoException(){
        super(Constantes.CNPJInvalido);
    }
}
