package onecenter.com.br.ecommerce.config.exception.pessoas;

import onecenter.com.br.ecommerce.utils.Constantes;

public class DataNascimentoException extends RuntimeException{

    public DataNascimentoException(){
        super(Constantes.DataNascimentoInvalida);
    }
}
