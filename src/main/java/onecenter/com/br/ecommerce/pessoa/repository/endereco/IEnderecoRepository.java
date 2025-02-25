package onecenter.com.br.ecommerce.pessoa.repository.endereco;

import onecenter.com.br.ecommerce.pessoa.dto.pessoas.request.PessoaRequest;
import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoEntity;

public interface IEnderecoRepository {

    EnderecoEntity salverEndereco(EnderecoEntity endereco);

    EnderecoEntity obterEnderecoPorIdPessoa(Integer idPessoa);

    void atualizarEndereco(Integer idPessoa, PessoaRequest editar);
}
