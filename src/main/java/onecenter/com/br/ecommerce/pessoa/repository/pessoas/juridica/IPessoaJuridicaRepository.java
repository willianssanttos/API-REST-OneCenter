package onecenter.com.br.ecommerce.pessoa.repository.pessoas.juridica;

import onecenter.com.br.ecommerce.pessoa.dto.pessoas.request.juridico.PessoaJuridicaRequest;
import onecenter.com.br.ecommerce.pessoa.entity.juridica.PessoaJuridicaEntity;

public interface IPessoaJuridicaRepository {

    PessoaJuridicaEntity criarJuridica(PessoaJuridicaEntity juridica);

    boolean verificarCnpjExistente(String CNPJ);

    Integer buscarIdPorCnpj(String CNPJ);

    PessoaJuridicaEntity buscarPorCnpj(String CNPJ);

    void atualizarPessoaJuridica(Integer idPessoa, PessoaJuridicaRequest editar);
}
