package onecenter.com.br.ecommerce.pessoa.repository.pessoas;

import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;

public interface IPessoaRepository {
    PessoaEntity criarPessoa(PessoaEntity pessoa);
    PessoaEntity obterLogin(String email);
    boolean verificarEmailExistente(String email);
    PessoaEntity buscarIdPessoa(Integer IdPessoa);
    PessoaEntity atualizarPessoa( PessoaEntity editar);

}
