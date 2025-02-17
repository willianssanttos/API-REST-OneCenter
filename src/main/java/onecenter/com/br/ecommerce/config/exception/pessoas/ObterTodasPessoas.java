package onecenter.com.br.ecommerce.config.exception.pessoas;

import onecenter.com.br.ecommerce.utils.Constantes;

public class ObterTodasPessoas extends RuntimeException{

    public ObterTodasPessoas(){
        super(Constantes.ErroListaCliente);
    }
}
