package onecenter.com.br.ecommerce.repository.pessoas.fisica;

import onecenter.com.br.ecommerce.dto.pessoas.response.fisica.PessoaFisicaResponse;
import onecenter.com.br.ecommerce.entity.pessoas.fisica.PessoaFisicaEntity;

import java.util.List;

public interface IPessoaFisicaRepository {

    PessoaFisicaEntity criarFisica(PessoaFisicaEntity fisica);

    boolean verificarCpfExistente(String cpf);

    PessoaFisicaEntity buscarPorCpf(String CPF);

    List<PessoaFisicaEntity> obterTodasPessos();

    PessoaFisicaResponse atualizarDados(PessoaFisicaResponse editar);
}
