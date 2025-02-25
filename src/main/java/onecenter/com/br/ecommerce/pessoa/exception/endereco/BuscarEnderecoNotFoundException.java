package onecenter.com.br.ecommerce.pessoa.exception.endereco;

import onecenter.com.br.ecommerce.utils.Constantes;

public class BuscarEnderecoNotFoundException extends RuntimeException{

    public BuscarEnderecoNotFoundException(){
        super(Constantes.ErroEnderecoNaoEncontrado);
    }
}
