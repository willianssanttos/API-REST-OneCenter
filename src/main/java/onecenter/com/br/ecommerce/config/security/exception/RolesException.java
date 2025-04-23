package onecenter.com.br.ecommerce.config.security.exception;

import onecenter.com.br.ecommerce.utils.Constantes;

public class RolesException extends RuntimeException{

    public RolesException(String rolesInvalido){
        super(Constantes.RolesInvalido);
    }
}
