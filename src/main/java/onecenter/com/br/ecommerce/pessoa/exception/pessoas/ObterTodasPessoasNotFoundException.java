package onecenter.com.br.ecommerce.pessoa.exception.pessoas;

import onecenter.com.br.ecommerce.utils.Constantes;

public class ObterTodasPessoasNotFoundException extends RuntimeException{

    public ObterTodasPessoasNotFoundException(){
        super(Constantes.ErroListaCliente);
    }
}
