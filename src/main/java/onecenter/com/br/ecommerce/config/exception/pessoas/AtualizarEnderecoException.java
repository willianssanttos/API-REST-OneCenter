package onecenter.com.br.ecommerce.config.exception.pessoas;

import onecenter.com.br.ecommerce.utils.Constantes;

public class AtualizarEnderecoException extends RuntimeException{

    public AtualizarEnderecoException(){
        super(Constantes.ErroEditarEndereco);
    }
}
