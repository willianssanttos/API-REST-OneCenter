package onecenter.com.br.ecommerce.pessoa.dto.mapper;

import onecenter.com.br.ecommerce.pessoa.dto.endereco.response.EnderecoResponse;
import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoBase;
import org.springframework.stereotype.Component;

@Component
public class EnderecoDtoMapper {
    public EnderecoResponse mapear(EnderecoBase endereco) {
        if (endereco == null) {
            return null;
        }

        return EnderecoResponse.builder()
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .bairro(endereco.getBairro())
                .localidade(endereco.getLocalidade())
                .cep(endereco.getCep())
                .uf(endereco.getUf())
                .build();
    }
}