package onecenter.com.br.ecommerce.config.exception.pessoas;

import onecenter.com.br.ecommerce.utils.Constantes;

public class BuscarEnderecoNotFoundException extends RuntimeException{

    public BuscarEnderecoNotFoundException(){
        super(Constantes.ErroEnderecoNaoEncontrado);
    }
}
