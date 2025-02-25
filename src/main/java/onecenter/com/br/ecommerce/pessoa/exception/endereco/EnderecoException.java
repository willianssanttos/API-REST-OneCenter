package onecenter.com.br.ecommerce.pessoa.exception.endereco;

import onecenter.com.br.ecommerce.utils.Constantes;

public class EnderecoException extends RuntimeException {

    public EnderecoException() {
        super(Constantes.ErroCadastrarEndereco);
    }
}