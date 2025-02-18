package onecenter.com.br.ecommerce.config.exception.pessoas;

import onecenter.com.br.ecommerce.utils.Constantes;

public class CpfExistenteException extends RuntimeException{

    public CpfExistenteException(){
        super(Constantes.CPFJaCadastrado);
    }
}
