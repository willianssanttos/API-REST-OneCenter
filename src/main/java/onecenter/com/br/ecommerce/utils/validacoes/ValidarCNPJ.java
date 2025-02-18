package onecenter.com.br.ecommerce.utils.validacoes;

public class ValidarCNPJ {

    public boolean isCnpjValid(String cnpj) {
        cnpj = cnpj.replaceAll("[^\\d]", ""); // Remove todos os caracteres não numéricos

        // Verifica se o CNPJ tem um formato inválido ou se todos os dígitos são iguais
        if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") || cnpj.equals("22222222222222") ||
                cnpj.equals("33333333333333") || cnpj.equals("44444444444444") || cnpj.equals("55555555555555") ||
                cnpj.equals("66666666666666") || cnpj.equals("77777777777777") || cnpj.equals("88888888888888") ||
                cnpj.equals("99999999999999") || cnpj.length() != 14)
            return false;

        char dig13, dig14;
        int sm, i, r, num, peso;

        try {
            // Cálculo do primeiro dígito verificador
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                num = (int) (cnpj.charAt(i) - 48);
                sm += (num * peso);
                peso++;
                if (peso == 10) peso = 2;
            }
            r = sm % 11;
            dig13 = (r == 0 || r == 1) ? '0' : (char) ((11 - r) + 48);

            // Cálculo do segundo dígito verificador
            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (int) (cnpj.charAt(i) - 48);
                sm += (num * peso);
                peso++;
                if (peso == 10) peso = 2;
            }
            r = sm % 11;
            dig14 = (r == 0 || r == 1) ? '0' : (char) ((11 - r) + 48);

            // Verifica se os dígitos calculados correspondem aos dígitos verificadores do CNPJ
            return (dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13));
        } catch (Exception e) {
            return false;
        }
    }
}
