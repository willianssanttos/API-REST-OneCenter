package onecenter.com.br.ecommerce.config.exception.pessoas;

import onecenter.com.br.ecommerce.utils.Constantes;

public class EditarPessoaException extends RuntimeException{

    public EditarPessoaException(){
        super(Constantes.ErroEditarPessoa);
    }
}
