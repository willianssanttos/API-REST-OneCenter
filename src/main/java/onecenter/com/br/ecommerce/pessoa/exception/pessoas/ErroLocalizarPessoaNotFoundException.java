package onecenter.com.br.ecommerce.pessoa.exception.pessoas;

import onecenter.com.br.ecommerce.utils.Constantes;

public class ErroLocalizarPessoaNotFoundException extends RuntimeException{

    public ErroLocalizarPessoaNotFoundException(){
        super(Constantes.ErroPessoaNãoEncontrada);
    }
}
