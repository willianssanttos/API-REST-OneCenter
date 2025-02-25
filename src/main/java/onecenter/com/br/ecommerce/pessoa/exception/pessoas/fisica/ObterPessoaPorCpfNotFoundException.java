package onecenter.com.br.ecommerce.pessoa.exception.pessoas.fisica;

import onecenter.com.br.ecommerce.utils.Constantes;

public class ObterPessoaPorCpfNotFoundException extends RuntimeException{

    public ObterPessoaPorCpfNotFoundException(){
        super(Constantes.ErroBuscarCpfPessoa);
    }
}
