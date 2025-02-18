package onecenter.com.br.ecommerce.config.exception.pessoas;

import onecenter.com.br.ecommerce.utils.Constantes;

public class SenhaValidacaoException extends RuntimeException{

    public SenhaValidacaoException(){
        super(Constantes.cadastroSenha);
    }
}
