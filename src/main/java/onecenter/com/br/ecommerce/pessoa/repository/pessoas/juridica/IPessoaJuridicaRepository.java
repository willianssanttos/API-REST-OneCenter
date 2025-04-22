package onecenter.com.br.ecommerce.pessoa.repository.pessoas.juridica;

import onecenter.com.br.ecommerce.pessoa.entity.juridica.PessoaJuridicaEntity;

public interface IPessoaJuridicaRepository {

    PessoaJuridicaEntity criarJuridica(PessoaJuridicaEntity juridica);

    boolean verificarCnpjExistente(String CNPJ);

    Integer buscarIdPorCnpj(String CNPJ);

    PessoaJuridicaEntity buscarPorCnpj(String CNPJ);

    PessoaJuridicaEntity atualizarPessoaJuridica(PessoaJuridicaEntity editar);
}
