package onecenter.com.br.ecommerce.utils.validacoes;

import onecenter.com.br.ecommerce.pessoa.dto.pessoas.request.PessoaRequest;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.request.fisica.PessoaFisicaRequest;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.request.juridico.PessoaJuridicaRequest;
import onecenter.com.br.ecommerce.pessoa.exception.endereco.CepValidacaoExcecao;
import onecenter.com.br.ecommerce.pessoa.exception.login.SenhaValidacaoException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.EmailExistenteException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.EmailValidacaoException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.NomeValidacaoException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.NumeroCelularValidacaoException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.fisica.CpfExistenteException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.fisica.CpfValidacaoException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.juridico.CnpjExistenteException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.juridico.CnpjValidacaoException;
import onecenter.com.br.ecommerce.pessoa.repository.endereco.IEnderecoRepository;
import onecenter.com.br.ecommerce.pessoa.repository.pessoas.IPessoaRepository;
import onecenter.com.br.ecommerce.pessoa.repository.pessoas.fisica.IPessoaFisicaRepository;
import onecenter.com.br.ecommerce.pessoa.repository.pessoas.juridica.IPessoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarDadosPessoa {

    @Autowired
    private IPessoaRepository iPessoaRepository;
    @Autowired
    private IEnderecoRepository iEnderecoRepository;
    @Autowired
    private IPessoaFisicaRepository iPessoaFisicaRepository;
    @Autowired
    private IPessoaJuridicaRepository iPessoaJuridicaRepository;

    public void validar(PessoaRequest pessoa){

       if (pessoa instanceof PessoaFisicaRequest){
           validarDadosFisica((PessoaFisicaRequest) pessoa);
       }

        if (pessoa instanceof PessoaJuridicaRequest){
            validarDadosJuridico((PessoaJuridicaRequest) pessoa);
        }

        if (Boolean.TRUE.equals(iPessoaRepository.verificarEmailExistente(pessoa.getEmail()))){
            throw new EmailExistenteException();
        }

        if(!ValidarNome.validarNome(pessoa.getNomeRazaosocial())){
            throw new NomeValidacaoException();
        }

        if (!ValidarEmail.validaEmail(pessoa.getEmail())){
            throw new EmailValidacaoException();
        }

        if (!ValidarSenha.validarSenha(pessoa.getSenha())){
            throw new SenhaValidacaoException();
        }
        if (!ValidarCEP.validarCep(pessoa.getEndereco().getCep())){
            throw new CepValidacaoExcecao();
        }

        if (!ValidarNumeroCelular.validarNumeroCelular(pessoa.getTelefone())) {
            throw new NumeroCelularValidacaoException();
        }
    }

    private void validarDadosFisica(PessoaFisicaRequest pessoaFisica){

        if(!ValidarCPF.cpfValidado(pessoaFisica.getCpf())){
            throw new CpfValidacaoException();
        }

        if (Boolean.TRUE.equals(iPessoaFisicaRepository.verificarCpfExistente(pessoaFisica.getCpf()))){
            throw new CpfExistenteException();
        }
    }

    private void validarDadosJuridico(PessoaJuridicaRequest pessoaJuridica){

        if(!ValidarCNPJ.isCnpjValid(pessoaJuridica.getCnpj())){
            throw new CnpjValidacaoException();
        }

        if (Boolean.TRUE.equals(iPessoaJuridicaRepository.verificarCnpjExistente(pessoaJuridica.getCnpj()))){
            throw new CnpjExistenteException();
        }
    }
}
