package onecenter.com.br.ecommerce.repository.pessoas.fisica;

import onecenter.com.br.ecommerce.dto.pessoas.request.fisica.PessoaFisicaRequest;
import onecenter.com.br.ecommerce.entity.pessoas.fisica.PessoaFisicaEntity;

import java.util.List;

public interface IPessoaFisicaRepository {

    PessoaFisicaEntity criarFisica(PessoaFisicaEntity fisica);

    boolean verificarCpfExistente(String cpf);

    Integer buscarIdPorCpf(String cpf);

    PessoaFisicaEntity buscarPorCpf(String CPF);

    List<PessoaFisicaEntity> obterTodasPessos();

    void atualizarPessoaFisica(Integer idPessoa, PessoaFisicaRequest editar);
}
