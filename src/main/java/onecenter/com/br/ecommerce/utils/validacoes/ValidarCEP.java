package onecenter.com.br.ecommerce.utils.validacoes;

public class ValidarCEP {

    public boolean validarCep(String cep) {
        String regex = "^[0-9]{5}-[0-9]{3}$";
        return cep.matches(regex);
    }
}
