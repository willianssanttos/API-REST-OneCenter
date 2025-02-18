package onecenter.com.br.ecommerce.config.exception.pessoas;

import onecenter.com.br.ecommerce.utils.Constantes;

public class NomeValidacaoException extends RuntimeException{

    public NomeValidacaoException(){
        super(Constantes.cadastroNome);
    }
}
