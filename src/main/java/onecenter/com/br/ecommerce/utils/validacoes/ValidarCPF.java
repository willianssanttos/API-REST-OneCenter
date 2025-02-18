package onecenter.com.br.ecommerce.utils.validacoes;

import org.springframework.stereotype.Component;

@Component
public class ValidarCPF {

    public static boolean cpfValidado(String cpf){
        cpf = cpf.replaceAll("[^\\d]", ""); // Remove todos os caracteres não numéricos

        // Verifica se o CPF tem um formato inválido ou se todos os dígitos são iguais
        if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222") ||
                cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888") ||
                cpf.equals("99999999999") || cpf.length() != 11)
            return false;

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            // Cálculo do primeiro dígito verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (cpf.charAt(i) - 48);
                sm += (num * peso);
                peso--;
            }
            r = 11 - (sm % 11);
            dig10 = (r == 10 || r == 11) ? '0' : (char) (r + 48);

            // Cálculo do segundo dígito verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (cpf.charAt(i) - 48);
                sm += (num * peso);
                peso--;
            }
            r = 11 - (sm % 11);
            dig11 = (r == 10 || r == 11) ? '0' : (char) (r + 48);

            // Verifica se os dígitos calculados correspondem aos dígitos verificadores do CPF
            return (dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10));
        } catch (Exception e) {
            return false;
        }
    }
}