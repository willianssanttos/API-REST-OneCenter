package onecenter.com.br.ecommerce.pessoa.repository.pessoas.fisica;

import onecenter.com.br.ecommerce.pessoa.dto.pessoas.request.fisica.PessoaFisicaRequest;
import onecenter.com.br.ecommerce.pessoa.entity.fisica.PessoaFisicaEntity;

public interface IPessoaFisicaRepository {

    PessoaFisicaEntity criarFisica(PessoaFisicaEntity fisica);

    boolean verificarCpfExistente(String CPF);

    PessoaFisicaEntity buscarIdPessoa(Integer IdPessoa);

    Integer buscarIdPorCpf(String CPF);

    PessoaFisicaEntity buscarPorCpf(String CPF);

    PessoaFisicaEntity atualizarPessoaFisica(PessoaFisicaEntity editar);
}
