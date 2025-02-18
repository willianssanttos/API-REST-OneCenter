package onecenter.com.br.ecommerce.utils.validacoes;

public class ValidarSenha {

    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%&])[A-Za-z\\d@#$%&]{8,}$";

    public static boolean validarSenha(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }
}
