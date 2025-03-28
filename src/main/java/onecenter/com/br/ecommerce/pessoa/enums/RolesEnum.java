package onecenter.com.br.ecommerce.pessoa.enums;

public enum RolesEnum {

    ROLE_CLIENTE("ROLE_CLIENTE"),
    ROLE_MODERADOR("ROLE_MODERADOR"),
    ROLE_ADMINISTRADOR("ROLE_ADMINISTRADOR");

    private String RolesEnum;
    RolesEnum(String rolesEnum) {
        RolesEnum = rolesEnum;
    }

    public String getRolesEnum() {
        return RolesEnum;
    }
}
