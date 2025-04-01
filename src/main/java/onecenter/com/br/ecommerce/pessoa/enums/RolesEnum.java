package onecenter.com.br.ecommerce.pessoa.enums;

import onecenter.com.br.ecommerce.config.security.exception.RolesException;
import onecenter.com.br.ecommerce.utils.Constantes;

public enum RolesEnum {

    CLIENTE("CLIENTE"),
    MODERADOR("MODERADOR"),
    ADMINISTRADOR("ADMINISTRADOR");

    private String rolesEnum;
    RolesEnum(String rolesEnum) {
        this. rolesEnum = rolesEnum;
    }

    public String getRolesEnum() {
        return rolesEnum;
    }

    public static RolesEnum fromString(String input) {
        for (RolesEnum role : RolesEnum.values()) {
            if (role.getRolesEnum().equalsIgnoreCase(input.trim())) {
                return role;
            }
        }
        throw new RolesException(Constantes.RolesInvalido);
    }
}
