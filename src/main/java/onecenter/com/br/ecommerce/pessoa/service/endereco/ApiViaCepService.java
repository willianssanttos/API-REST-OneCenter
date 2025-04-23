package onecenter.com.br.ecommerce.pessoa.service.endereco;

import com.google.gson.Gson;
import onecenter.com.br.ecommerce.pessoa.exception.endereco.BuscarEnderecoNotFoundException;
import onecenter.com.br.ecommerce.pessoa.dto.endereco.ApiViaCep.ViaCepResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Service
public class ApiViaCepService {

    @Transactional(readOnly = true)
    public ViaCepResponse consultarCep(String cep) {
        try {
            // Cria a URL para a consulta do CEP
            URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
            URLConnection connection = url.openConnection();

            // Abre a conexão e lê os dados do CEP
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            // Lê a resposta JSON linha por linha
            StringBuilder jsonCep = new StringBuilder();
            String linha;
            while ((linha = br.readLine()) != null) {
                jsonCep.append(linha);
            }

            // Converte a resposta JSON para um objeto CepResponse usando Gson
            return new Gson().fromJson(jsonCep.toString(), ViaCepResponse.class);
        } catch (Exception e) {
            // Lança uma exceção personalizada se ocorrer um erro
            throw new BuscarEnderecoNotFoundException();
        }
    }
}
