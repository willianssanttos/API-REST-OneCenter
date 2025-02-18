package onecenter.com.br.ecommerce.utils.validacoes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidarDataNascimento {

    public static boolean validarDataNascimento(String dataNascimento) {
        String regexDataNascimento = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$";
        Pattern pattern = Pattern.compile(regexDataNascimento);
        Matcher matcher = pattern.matcher(dataNascimento);
        return matcher.matches();
    }
}
