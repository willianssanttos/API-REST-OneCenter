package onecenter.com.br.ecommerce.pessoa.repository.pessoas.fisica;

import onecenter.com.br.ecommerce.pessoa.entity.fisica.PessoaFisicaEntity;

public interface IPessoaFisicaRepository {

    PessoaFisicaEntity criarFisica(PessoaFisicaEntity fisica);

    boolean verificarCpfExistente(String cpf);

    Integer buscarIdPorCpf(String cpf);

    PessoaFisicaEntity buscarPorCpf(String cpf);

    PessoaFisicaEntity atualizarPessoaFisica(PessoaFisicaEntity editar);
}
