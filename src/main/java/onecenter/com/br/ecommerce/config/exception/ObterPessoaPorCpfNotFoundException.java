package onecenter.com.br.ecommerce.config.exception;

import onecenter.com.br.ecommerce.utils.Constantes;

public class ObterPessoaPorCpfNotFoundException extends RuntimeException{

    public ObterPessoaPorCpfNotFoundException(){
        super(Constantes.ErroBuscarCpfPessoa);
    }
}
