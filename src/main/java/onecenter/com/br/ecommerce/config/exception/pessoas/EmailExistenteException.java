package onecenter.com.br.ecommerce.config.exception.pessoas;

import onecenter.com.br.ecommerce.utils.Constantes;

public class EmailExistenteException extends RuntimeException{

    public EmailExistenteException(){
        super(Constantes.EmailJaCadastrado);
    }
}
