package onecenter.com.br.ecommerce.pessoa.exception.pessoas.fisica;

import onecenter.com.br.ecommerce.utils.Constantes;

public class DataNascimentoException extends RuntimeException{

    public DataNascimentoException(){
        super(Constantes.DataNascimentoInvalida);
    }
}
