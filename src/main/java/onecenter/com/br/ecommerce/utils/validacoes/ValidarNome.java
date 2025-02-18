package onecenter.com.br.ecommerce.utils.validacoes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidarNome {

    public static boolean validarNome(String nome) {
        String padrao = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]{2,100}$";

        Pattern pattern = Pattern.compile(padrao);
        Matcher matcher = pattern.matcher(nome);

        return matcher.matches();
    }
}
