package onecenter.com.br.ecommerce.repository.pessoas.fisica;

import onecenter.com.br.ecommerce.entity.pessoas.fisica.PessoaFisicaEntity;

import java.util.List;

public interface IPessoaFisicaRepository {

    PessoaFisicaEntity criarFisica(PessoaFisicaEntity fisica);

    PessoaFisicaEntity buscarPorCpf(String CPF);

    List<PessoaFisicaEntity> obterTodasPessos();
}
