package onecenter.com.br.ecommerce.pessoa.exception.pessoas.juridico;

import onecenter.com.br.ecommerce.utils.Constantes;

public class CnpjExistenteException extends RuntimeException{

    public CnpjExistenteException(){
        super(Constantes.CnpjJaCadastrado);
    }
}
