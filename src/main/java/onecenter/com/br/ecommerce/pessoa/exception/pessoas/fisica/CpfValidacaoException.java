package onecenter.com.br.ecommerce.pessoa.exception.pessoas.fisica;

import onecenter.com.br.ecommerce.utils.Constantes;

public class CpfValidacaoException extends RuntimeException{
    public CpfValidacaoException(){
        super(Constantes.CpfInvalido);
    }
}
