package onecenter.com.br.ecommerce.repository.pessoas.endereco;

import onecenter.com.br.ecommerce.dto.pessoas.request.fisica.PessoaFisicaRequest;
import onecenter.com.br.ecommerce.entity.pessoas.endereco.EnderecoEntity;

import java.util.Optional;

public interface IEnderecoRepository {

    EnderecoEntity salverEndereco(EnderecoEntity endereco);

    EnderecoEntity obterEnderecoPorIdPessoa(Integer idPessoa);

    void atualizarEndereco(Integer idPessoa, PessoaFisicaRequest editar);
}
