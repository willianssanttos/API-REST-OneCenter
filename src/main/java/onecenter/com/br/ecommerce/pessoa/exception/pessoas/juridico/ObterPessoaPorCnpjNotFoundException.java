package onecenter.com.br.ecommerce.pessoa.exception.pessoas.juridico;

import onecenter.com.br.ecommerce.utils.Constantes;

public class ObterPessoaPorCnpjNotFoundException extends RuntimeException{

    public ObterPessoaPorCnpjNotFoundException(){
        super(Constantes.ErroBuscarCnpjPessoa);
    }
}
