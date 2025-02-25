package onecenter.com.br.ecommerce.pessoa.exception.endereco;

import onecenter.com.br.ecommerce.utils.Constantes;

public class CepValidacaoExcecao extends RuntimeException{

    public CepValidacaoExcecao(){
        super(Constantes.CepInvalido);
    }
}
