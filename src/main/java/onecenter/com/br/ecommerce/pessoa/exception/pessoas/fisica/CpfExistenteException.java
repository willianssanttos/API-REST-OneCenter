package onecenter.com.br.ecommerce.pessoa.exception.pessoas.fisica;

import onecenter.com.br.ecommerce.utils.Constantes;

public class CpfExistenteException extends RuntimeException{

    public CpfExistenteException(){
        super(Constantes.CPFJaCadastrado);
    }
}
