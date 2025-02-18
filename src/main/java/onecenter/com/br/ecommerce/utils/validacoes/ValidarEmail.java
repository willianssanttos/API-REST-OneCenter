package onecenter.com.br.ecommerce.utils.validacoes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidarEmail {

    public static boolean validaEmail(String email) {
        // Define a expressão regular para validar o formato do e-mail
        String regex = "^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compila a expressão regular
        Pattern pattern = Pattern.compile(regex);

        // Cria um objeto Matcher para o e-mail fornecido
        Matcher matcher = pattern.matcher(email);

        // Verifica se o e-mail corresponde ao padrão definido
        return matcher.matches();
    }
}
