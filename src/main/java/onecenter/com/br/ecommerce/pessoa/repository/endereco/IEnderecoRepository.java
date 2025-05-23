package onecenter.com.br.ecommerce.pessoa.repository.endereco;

import onecenter.com.br.ecommerce.pessoa.dto.endereco.request.EnderecoRequest;
import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoEntity;

public interface IEnderecoRepository {

    EnderecoEntity salvarEndereco(EnderecoEntity endereco);

    EnderecoEntity obterEnderecoPorIdPessoa(Integer idPessoa);

    void atualizarEndereco(Integer idPessoa, EnderecoRequest editar);
}
