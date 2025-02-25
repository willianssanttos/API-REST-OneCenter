package onecenter.com.br.ecommerce.pessoa.exception.pessoas;

import onecenter.com.br.ecommerce.utils.Constantes;

public class PessoaException extends RuntimeException{

    public PessoaException(){
        super(Constantes.ErroCadastrarPessoa);
    }
}