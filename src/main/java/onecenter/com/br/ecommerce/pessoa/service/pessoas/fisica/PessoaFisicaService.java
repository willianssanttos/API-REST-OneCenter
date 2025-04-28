package onecenter.com.br.ecommerce.pessoa.service.pessoas.fisica;

import onecenter.com.br.ecommerce.pessoa.dto.mapper.EnderecoDtoMapper;
import onecenter.com.br.ecommerce.config.security.config.SecurityConfiguration;
import onecenter.com.br.ecommerce.pessoa.dto.mapper.PessoaDtoMapper;
import onecenter.com.br.ecommerce.pessoa.enums.RolesEnum;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.request.fisica.PessoaFisicaRequest;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.response.fisica.PessoaFisicaResponse;
import onecenter.com.br.ecommerce.pessoa.dto.endereco.ApiViaCep.ViaCepResponse;
import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoEntity;
import onecenter.com.br.ecommerce.pessoa.entity.fisica.PessoaFisicaEntity;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.*;
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
import org.springframework.transaction.annotation.Transactional;

@Service
public class PessoaFisicaService {

    @Autowired
    private PessoaDtoMapper pessoaDtoMapper;
    @Autowired
    private ApiViaCepService apiViaCepService;
    @Autowired
    private IPessoaRepository iPessoaRepository;
    @Autowired
    private EnderecoDtoMapper enderecoDtoMapper;
    @Autowired
    private IEnderecoRepository iEnderecoRepository;
    @Autowired
    private SecurityConfiguration securityConfiguration;
    @Autowired
    private IPessoaFisicaRepository iPessoaFisicaRepository;

    private static final Logger logger = LoggerFactory.getLogger(PessoaFisicaService.class);

    @Transactional
    public PessoaFisicaResponse cadastrarPessoaFisica (PessoaFisicaRequest fisica){
        logger.info(Constantes.DebugRegistroProcesso);

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

            EnderecoEntity enderecoCriado = iEnderecoRepository.salvarEndereco(endereco);

            logger.info(Constantes.InfoRegistrar, fisica);
            return pessoaDtoMapper.mapearPessoaFisica(fisicaCriada, pessoaCriada, enderecoCriado);
        } catch (Exception e){
            logger.error(Constantes.ErroRegistrarNoServidor);
            throw new PessoaException();
        }
    }

    @Transactional(readOnly = true)
    public PessoaFisicaResponse buscarPorCpf(String cpf){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
             PessoaFisicaEntity buscarCPF = iPessoaFisicaRepository.buscarPorCpf(cpf);

             PessoaEntity pessoa = PessoaEntity.builder()
                     .idPessoa(buscarCPF.getIdPessoa())
                     .nomeRazaosocial(buscarCPF.getNomeRazaosocial())
                     .email(buscarCPF.getEmail())
                     .telefone(buscarCPF.getTelefone())
                     .build();

            EnderecoEntity endereco = buscarCPF.getEndereco();

            logger.info(Constantes.InfoBuscar, cpf);
            return pessoaDtoMapper.mapearPessoaFisica(buscarCPF, pessoa, endereco);
        } catch (Exception e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e);
            throw new ObterPessoaPorCpfNotFoundException();
        }
    }

    @Transactional
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
                    .endereco(enderecoDtoMapper.mapear(editar.getEndereco()))
                    .build();

        }catch (Exception e){
            logger.error(Constantes.ErroEditarRegistroNoServidor, e);
            throw new EditarPessoaException();
        }
    }
}
