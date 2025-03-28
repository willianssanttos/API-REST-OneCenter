package onecenter.com.br.ecommerce.pessoa.service.pessoas.fisica;

import onecenter.com.br.ecommerce.config.security.config.SecurityConfiguration;
import onecenter.com.br.ecommerce.pessoa.enums.RolesEnum;
import onecenter.com.br.ecommerce.pessoa.exception.endereco.BuscarEnderecoNotFoundException;
import onecenter.com.br.ecommerce.pessoa.exception.endereco.CepValidacaoExcecao;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.request.fisica.PessoaFisicaRequest;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.response.fisica.PessoaFisicaResponse;
import onecenter.com.br.ecommerce.pessoa.dto.endereco.ApiViaCep.ViaCepResponse;
import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoEntity;
import onecenter.com.br.ecommerce.pessoa.entity.fisica.PessoaFisicaEntity;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.*;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.fisica.CpfExistenteException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.fisica.CpfValidacaoException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.fisica.NumeroCelularValidacaoException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.fisica.ObterPessoaPorCpfNotFoundException;
import onecenter.com.br.ecommerce.pessoa.repository.pessoas.IPessoaRepository;
import onecenter.com.br.ecommerce.pessoa.repository.endereco.IEnderecoRepository;
import onecenter.com.br.ecommerce.pessoa.repository.pessoas.fisica.IPessoaFisicaRepository;
import onecenter.com.br.ecommerce.pessoa.service.endereco.ApiViaCepService;
import onecenter.com.br.ecommerce.utils.Constantes;
import onecenter.com.br.ecommerce.utils.validacoes.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;

@Service
public class PessoaFisicaService {

    @Autowired
    private ApiViaCepService apiViaCepService;

    @Autowired
    private IPessoaRepository iPessoaRepository;

    @Autowired
    private IEnderecoRepository iEnderecoRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Autowired
    private IPessoaFisicaRepository iPessoaFisicaRepository;

    private static final Logger logger = LoggerFactory.getLogger(PessoaFisicaService.class);

    private void validarDados(PessoaFisicaRequest pessoaFisica){

        if(!ValidarCPF.cpfValidado(pessoaFisica.getCpf())){
            throw new CpfValidacaoException();
        }

        if (Boolean.TRUE.equals(iPessoaFisicaRepository.verificarCpfExistente(pessoaFisica.getCpf()))){
            throw new CpfExistenteException();
        }

        if (Boolean.TRUE.equals(iPessoaRepository.verificarEmailExistente(pessoaFisica.getEmail()))){
            throw new EmailExistenteException();
        }

        if(!ValidarNome.validarNome(pessoaFisica.getNome_razaosocial())){
            throw new NomeValidacaoException();
        }

        if (!ValidarEmail.validaEmail(pessoaFisica.getEmail())){
            throw new EmailValidacaoException();
        }

        if (!ValidarSenha.validarSenha(pessoaFisica.getSenha())){
            throw new SenhaValidacaoException();
        }
        if (!ValidarCEP.validarCep(pessoaFisica.getCep())){
            throw new CepValidacaoExcecao();
        }

        if (!ValidarNumeroCelular.validarNumeroCelular(pessoaFisica.getTelefone())) {
            throw new NumeroCelularValidacaoException();
        }
    }

    public PessoaFisicaResponse cadastrarPessoaFisica (PessoaFisicaRequest fisica){
        logger.info(Constantes.DebugRegistroProcesso);

        validarDados(fisica);

        try {

            ViaCepResponse viaCep = apiViaCepService.consultarCep(fisica.getCep());
                    fisica.setRua(viaCep.getLogradouro());
                    fisica.setBairro(viaCep.getBairro());
                    fisica.setLocalidade(viaCep.getLocalidade());
                    fisica.setUf(viaCep.getUf());

            PessoaEntity pessoa = PessoaEntity.builder()
                    .role(String.valueOf(RolesEnum.ROLE_CLIENTE))
                    .nome_razaosocial(fisica.getNome_razaosocial())
                    .email(fisica.getEmail())
                    .senha(securityConfiguration.passwordEncoder().encode(fisica.getSenha()))
                    .telefone(ValidarNumeroCelular.formatarNumeroCelular(fisica.getTelefone()))
                    .build();

            PessoaEntity pessoaCriada = iPessoaRepository.criarPessoa(pessoa);

            PessoaFisicaEntity criarFisica = PessoaFisicaEntity.builder()
                    .id_pessoa(pessoaCriada.getId_pessoa())
                    .cpf(fisica.getCpf())
                    .data_nascimento(fisica.getData_nascimento())
                    .build();

            EnderecoEntity endereco = EnderecoEntity.builder()
                    .id_pessoa(pessoaCriada.getId_pessoa())
                    .rua(fisica.getRua())
                    .numero(fisica.getNumero())
                    .bairro(fisica.getBairro())
                    .localidade(fisica.getLocalidade())
                    .cep(fisica.getCep())
                    .uf(fisica.getUf())
                    .build();

            iEnderecoRepository.salverEndereco(endereco);

            iPessoaFisicaRepository.criarFisica(criarFisica);
            logger.info(Constantes.InfoRegistrar, fisica);
            return mapearPessoaFisica(criarFisica);
        } catch (Exception e){
            logger.error(Constantes.ErroRegistrarNoServidor);
            throw new PessoaException();
        }
    }

    private PessoaFisicaResponse mapearPessoaFisica(PessoaFisicaEntity fisica){
        try {
            PessoaEntity pessoa = iPessoaRepository.buscarIdPessoa(fisica.getIdPessoa());
            EnderecoEntity endereco = iEnderecoRepository.obterEnderecoPorIdPessoa(fisica.getId_pessoa());

            return PessoaFisicaResponse.builder()
                    .idPessoa(fisica.getId_pessoa())
                    .role(pessoa.getRole())
                    .nome_razaosocial(pessoa.getNome_razaosocial())
                    .cpf(fisica.getCpf())
                    .data_nascimento(Timestamp.valueOf(String.valueOf(fisica.getData_nascimento())))
                    .email(pessoa.getEmail())
                    .telefone(pessoa.getTelefone())
                    .rua(endereco.getRua())
                    .numero(endereco.getNumero())
                    .bairro(endereco.getBairro())
                    .localidade(endereco.getLocalidade())
                    .cep(endereco.getCep())
                    .uf(endereco.getUf())
                    .build();

        } catch (Exception e){
            throw new ObterTodasPessoasNotFoundException();
        }
    }

    public PessoaFisicaResponse obterPorCpf(String CPF){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            PessoaFisicaEntity pessoaFisica = iPessoaFisicaRepository.buscarPorCpf(CPF);
            logger.info(Constantes.InfoBuscar, CPF);
            return mapearPessoaFisica(pessoaFisica);
        } catch (Exception e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor);
            throw new ObterPessoaPorCpfNotFoundException();
        }
    }

    public PessoaFisicaResponse atualizarDados(PessoaFisicaRequest editar){
        logger.info(Constantes.DebugEditarProcesso);
        try {
            Integer idPessoa = iPessoaFisicaRepository.buscarIdPorCpf(editar.getCpf());
            if (idPessoa == null){
                throw new EditarPessoaException();
            }

            iPessoaRepository.atualizarPessoa(idPessoa, editar);
            iPessoaFisicaRepository.atualizarPessoaFisica(idPessoa, editar);

            ViaCepResponse viaCep = apiViaCepService.consultarCep(editar.getCep());
            editar.setRua(viaCep.getLogradouro());
            editar.setBairro(viaCep.getBairro());
            editar.setLocalidade(viaCep.getLocalidade());
            editar.setUf(viaCep.getUf());

            iEnderecoRepository.atualizarEndereco(idPessoa, editar);

            return PessoaFisicaResponse.builder()
                    .idPessoa(idPessoa)
                    .role(editar.getRole())
                    .nome_razaosocial(editar.getNome_razaosocial())
                    .cpf(editar.getCpf())
                    .data_nascimento(editar.getData_nascimento())
                    .email(editar.getEmail())
                    .telefone(editar.getTelefone())
                    .rua(editar.getRua())
                    .numero(editar.getNumero())
                    .bairro(editar.getBairro())
                    .localidade(editar.getLocalidade())
                    .cep(editar.getCep())
                    .uf(editar.getUf())
                    .build();

        }catch (Exception e){
            logger.error(Constantes.ErroEditarRegistroNoServidor, e);
            throw new EditarPessoaException();
        }
    }
}
