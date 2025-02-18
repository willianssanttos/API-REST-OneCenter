package onecenter.com.br.ecommerce.config.exception.pessoas;

import onecenter.com.br.ecommerce.utils.Constantes;

public class CpfValidacaoException extends RuntimeException{
    public CpfValidacaoException(){
        super(Constantes.CpfInvalido);
    }
}
