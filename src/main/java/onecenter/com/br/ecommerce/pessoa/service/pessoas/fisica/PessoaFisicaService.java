package onecenter.com.br.ecommerce.pessoa.service.pessoas.fisica;

import onecenter.com.br.ecommerce.config.security.config.SecurityConfiguration;
import onecenter.com.br.ecommerce.pessoa.dto.endereco.response.EnderecoResponse;
import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoBase;
import onecenter.com.br.ecommerce.pessoa.enums.RolesEnum;
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

        if(!ValidarNome.validarNome(pessoaFisica.getNomeRazaosocial())){
            throw new NomeValidacaoException();
        }

        if (!ValidarEmail.validaEmail(pessoaFisica.getEmail())){
            throw new EmailValidacaoException();
        }

        if (!ValidarSenha.validarSenha(pessoaFisica.getSenha())){
            throw new SenhaValidacaoException();
        }
        if (!ValidarCEP.validarCep(pessoaFisica.getEndereco().getCep())){
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

            ViaCepResponse viaCep = apiViaCepService.consultarCep(fisica.getEndereco().getCep());
            fisica.getEndereco().setRua(viaCep.getLogradouro());
            fisica.getEndereco().setBairro(viaCep.getBairro());
            fisica.getEndereco().setLocalidade(viaCep.getLocalidade());
            fisica.getEndereco().setUf(viaCep.getUf());


            PessoaEntity pessoa = PessoaEntity.builder()
                    .rolePrincipal(String.valueOf(RolesEnum.CLIENTE))
                    .nomeRazaosocial(fisica.getNomeRazaosocial())
                    .email(fisica.getEmail())
                    .senha(securityConfiguration.passwordEncoder().encode(fisica.getSenha()))
                    .telefone(ValidarNumeroCelular.formatarNumeroCelular(fisica.getTelefone()))
                    .build();

            PessoaEntity pessoaCriada = iPessoaRepository.criarPessoa(pessoa);

            PessoaFisicaEntity criarFisica = PessoaFisicaEntity.builder()
                    .idPessoa(pessoaCriada.getIdPessoa())
                    .cpf(fisica.getCpf())
                    .dataNascimento(fisica.getDataNascimento())
                    .build();

            PessoaFisicaEntity fisicaCriada = iPessoaFisicaRepository.criarFisica(criarFisica);

            EnderecoEntity endereco = EnderecoEntity.builder()
                    .idPessoa(pessoaCriada.getIdPessoa())
                    .rua(fisica.getEndereco().getRua())
                    .numero(fisica.getEndereco().getNumero())
                    .bairro(fisica.getEndereco().getBairro())
                    .localidade(fisica.getEndereco().getLocalidade())
                    .cep(fisica.getEndereco().getCep())
                    .uf(fisica.getEndereco().getUf())
                    .build();

            EnderecoEntity enderecoCriado = iEnderecoRepository.salverEndereco(endereco);

            logger.info(Constantes.InfoRegistrar, fisica);
            return mapearPessoaFisica(fisicaCriada, pessoaCriada, enderecoCriado);
        } catch (Exception e){
            logger.error(Constantes.ErroRegistrarNoServidor);
            throw new PessoaException();
        }
    }

    private PessoaFisicaResponse mapearPessoaFisica(PessoaFisicaEntity fisicaCriada, PessoaEntity pessoaCriada, EnderecoEntity enderecoCriado ){
        try {
            return PessoaFisicaResponse.builder()
                    .idPessoa(pessoaCriada.getIdPessoa())
                    .rolePrincipal(pessoaCriada.getRolePrincipal())
                    .nomeRazaosocial(pessoaCriada.getNomeRazaosocial())
                    .cpf(fisicaCriada.getCpf())
                    .dataNascimento(Timestamp.valueOf(String.valueOf(fisicaCriada.getDataNascimento())))
                    .email(pessoaCriada.getEmail())
                    .telefone(pessoaCriada.getTelefone())
                    .endereco(mapearEndereco(enderecoCriado))
                    .build();

        } catch (Exception e){
            throw new ObterTodasPessoasNotFoundException();
        }
    }

    private EnderecoResponse mapearEndereco(EnderecoBase endereco) {
        if (endereco == null) {
            logger.warn("EnderecoBase recebido é null no método mapearEndereco.");
            return null;
        }

        return EnderecoResponse.builder()
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .bairro(endereco.getBairro())
                .localidade(endereco.getLocalidade())
                .cep(endereco.getCep())
                .uf(endereco.getUf())
                .build();
    }


    public PessoaFisicaResponse obterPorCpf(String CPF){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
             iPessoaFisicaRepository.buscarPorCpf(CPF);
            logger.info(Constantes.InfoBuscar, CPF);
            return null;
        } catch (Exception e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e);
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

            ViaCepResponse viaCep = apiViaCepService.consultarCep(editar.getEndereco().getCep());
            editar.getEndereco().setRua(viaCep.getLogradouro());
            editar.getEndereco().setBairro(viaCep.getBairro());
            editar.getEndereco().setLocalidade(viaCep.getLocalidade());
            editar.getEndereco().setUf(viaCep.getUf());

            PessoaEntity pessoa = PessoaEntity.builder()
                    .idPessoa(idPessoa)
                    .nomeRazaosocial(editar.getNomeRazaosocial())
                    .email(editar.getEmail())
                    .senha(securityConfiguration.passwordEncoder().encode(editar.getSenha()))
                    .telefone(ValidarNumeroCelular.formatarNumeroCelular(editar.getTelefone()))
                    .build();

            PessoaEntity pessoaAtualizada = iPessoaRepository.atualizarPessoa(pessoa);

            PessoaFisicaEntity criarFisica = PessoaFisicaEntity.builder()
                    .idPessoa(pessoaAtualizada.getIdPessoa())
                    .cpf(editar.getCpf())
                    .dataNascimento(editar.getDataNascimento())
                    .build();

            iPessoaFisicaRepository.atualizarPessoaFisica(criarFisica);

            iEnderecoRepository.atualizarEndereco(idPessoa, editar.getEndereco());

            logger.info(Constantes.InfoEditar, editar);
            return PessoaFisicaResponse.builder()
                    .idPessoa(idPessoa)
                    .nomeRazaosocial(editar.getNomeRazaosocial())
                    .cpf(editar.getCpf())
                    .email(editar.getEmail())
                    .dataNascimento(editar.getDataNascimento())
                    .telefone(editar.getTelefone())
                    .endereco(mapearEndereco(editar.getEndereco()))
                    .build();

        }catch (Exception e){
            logger.error(Constantes.ErroEditarRegistroNoServidor, e);
            throw new EditarPessoaException();
        }
    }
}
