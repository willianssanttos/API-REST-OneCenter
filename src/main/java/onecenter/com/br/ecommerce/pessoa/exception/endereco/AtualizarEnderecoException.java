package onecenter.com.br.ecommerce.pessoa.exception.endereco;

import onecenter.com.br.ecommerce.utils.Constantes;

public class AtualizarEnderecoException extends RuntimeException{

    public AtualizarEnderecoException(){
        super(Constantes.ErroEditarEndereco);
    }
}
