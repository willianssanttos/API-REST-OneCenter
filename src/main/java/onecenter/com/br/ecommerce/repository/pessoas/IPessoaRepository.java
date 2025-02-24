package onecenter.com.br.ecommerce.repository.pessoas;

import onecenter.com.br.ecommerce.dto.pessoas.request.fisica.PessoaFisicaRequest;
import onecenter.com.br.ecommerce.entity.pessoas.PessoaEntity;

public interface IPessoaRepository {
    PessoaEntity criarPessoa(PessoaEntity pessoa);
    boolean verificarEmailExistente(String email);
    void atualizarPessoa(Integer idPessoa, PessoaFisicaRequest editar);

}
