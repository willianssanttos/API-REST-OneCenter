package onecenter.com.br.ecommerce.config.exception.pessoas;

import onecenter.com.br.ecommerce.utils.Constantes;

public class EnderecoException extends RuntimeException {

    public EnderecoException() {
        super(Constantes.ErroCadastrarEndereco);
    }
}