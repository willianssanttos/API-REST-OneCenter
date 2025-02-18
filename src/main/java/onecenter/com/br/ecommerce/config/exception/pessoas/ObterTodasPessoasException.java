package onecenter.com.br.ecommerce.config.exception.pessoas;

import onecenter.com.br.ecommerce.utils.Constantes;

public class ObterTodasPessoasException extends RuntimeException{

    public ObterTodasPessoasException(){
        super(Constantes.ErroListaCliente);
    }
}
