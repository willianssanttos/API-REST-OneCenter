package onecenter.com.br.ecommerce.utils.validacoes;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidarNumeroCelular {

    // Método para formatar o número de celular
    public static String formatarNumeroCelular(String numeroCelular) {
        // Remove todos os caracteres não numéricos
        String numeroApenasDigitos = numeroCelular.replaceAll("\\D", "");

        // Verifica se o número tem 10 ou 11 dígitos
        if (numeroApenasDigitos.length() == 10 || numeroApenasDigitos.length() == 11) {
            return MessageFormat.format("({0}) {1}-{2}",
                    numeroApenasDigitos.substring(0, 2),
                    numeroApenasDigitos.substring(2, 7),
                    numeroApenasDigitos.substring(7));
        }

        return numeroCelular;
    }

    // Método para validar número de celular
    public static boolean validarNumeroCelular(String numeroCelular) {
        // Formato aceito: (XX) XXXXX-XXXX ou (XX) XXXX-XXXX ou XXXXXXXXXXX
        String regexNumeroCelular = "(\\(\\d{2}\\)\\d{4,5}-\\d{4})|\\d{11}|\\d{10}";

        Pattern pattern = Pattern.compile(regexNumeroCelular);
        Matcher matcher = pattern.matcher(numeroCelular);

        return matcher.matches();
    }
    
}
