package onecenter.com.br.ecommerce.config.exception.pessoas;

import onecenter.com.br.ecommerce.utils.Constantes;

public class CepValidacaoExcecao extends RuntimeException{

    public CepValidacaoExcecao(){
        super(Constantes.CepInvalido);
    }
}
