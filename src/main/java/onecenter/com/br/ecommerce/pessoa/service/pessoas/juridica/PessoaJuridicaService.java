package onecenter.com.br.ecommerce.pessoa.service.pessoas.juridica;

import onecenter.com.br.ecommerce.pessoa.dto.mapper.EnderecoDtoMapper;
import onecenter.com.br.ecommerce.config.security.config.SecurityConfiguration;
import onecenter.com.br.ecommerce.pessoa.dto.mapper.PessoaDtoMapper;
import onecenter.com.br.ecommerce.pessoa.enums.RolesEnum;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.*;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.juridico.ObterPessoaPorCnpjNotFoundException;
import onecenter.com.br.ecommerce.pessoa.dto.endereco.ApiViaCep.ViaCepResponse;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.request.juridico.PessoaJuridicaRequest;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.response.juridica.PessoaJuridicaResponse;
import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoEntity;
import onecenter.com.br.ecommerce.pessoa.entity.juridica.PessoaJuridicaEntity;
import onecenter.com.br.ecommerce.pessoa.repository.pessoas.IPessoaRepository;
import onecenter.com.br.ecommerce.pessoa.repository.endereco.IEnderecoRepository;
import onecenter.com.br.ecommerce.pessoa.repository.pessoas.juridica.IPessoaJuridicaRepository;
import onecenter.com.br.ecommerce.pessoa.service.endereco.ApiViaCepService;
import onecenter.com.br.ecommerce.utils.Constantes;
import onecenter.com.br.ecommerce.utils.validacoes.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PessoaJuridicaService {
    @Autowired
    private PessoaDtoMapper pessoaDtoMapper;
    @Autowired
    private ApiViaCepService apiViaCepService;
    @Autowired
    private EnderecoDtoMapper enderecoDtoMapper;
    @Autowired
    private IPessoaRepository iPessoaRepository;
    @Autowired
    private IEnderecoRepository iEnderecoRepository;
    @Autowired
    private SecurityConfiguration securityConfiguration;
    @Autowired
    private IPessoaJuridicaRepository iPessoaJuridicaRepository;

    private static final Logger logger = LoggerFactory.getLogger(PessoaJuridicaService.class);

    @Transactional
    public PessoaJuridicaResponse cadastrarPessoaJuridica (PessoaJuridicaRequest juridica){
        logger.info(Constantes.DebugRegistroProcesso);

        try {

            ViaCepResponse viaCep = apiViaCepService.consultarCep(juridica.getEndereco().getCep());
            juridica.getEndereco().setRua(viaCep.getLogradouro());
            juridica.getEndereco().setBairro(viaCep.getBairro());
            juridica.getEndereco().setLocalidade(viaCep.getLocalidade());
            juridica.getEndereco().setUf(viaCep.getUf());

            PessoaEntity pessoa = PessoaEntity.builder()
                    .rolePrincipal(String.valueOf(RolesEnum.ADMINISTRADOR))
                    .nomeRazaosocial(juridica.getNomeRazaosocial())
                    .email(juridica.getEmail())
                    .senha(securityConfiguration.passwordEncoder().encode(juridica.getSenha()))
                    .telefone(ValidarNumeroCelular.formatarNumeroCelular(juridica.getTelefone()))
                    .build();

            PessoaEntity pessoaCriada = iPessoaRepository.criarPessoa(pessoa);

            PessoaJuridicaEntity criarJuridica = PessoaJuridicaEntity.builder()
                    .idPessoa(pessoaCriada.getIdPessoa())
                    .cnpj(juridica.getCnpj())
                    .nomeFantasia(juridica.getNomeFantasia())
                    .build();

            PessoaJuridicaEntity juridicaCriada = iPessoaJuridicaRepository.criarJuridica(criarJuridica);

            EnderecoEntity endereco = EnderecoEntity.builder()
                    .idPessoa(pessoaCriada.getIdPessoa())
                    .rua(juridica.getEndereco().getRua())
                    .numero(juridica.getEndereco().getNumero())
                    .bairro(juridica.getEndereco().getBairro())
                    .localidade(juridica.getEndereco().getLocalidade())
                    .cep(juridica.getEndereco().getCep())
                    .uf(juridica.getEndereco().getUf())
                    .build();

            EnderecoEntity enderecoCriado = iEnderecoRepository.salvarEndereco(endereco);

            logger.info(Constantes.InfoRegistrar, juridica);
            return pessoaDtoMapper.mapearPessoaJuridica(juridicaCriada, pessoaCriada, enderecoCriado);
        } catch (Exception e){
            logger.error(Constantes.ErroRegistrarNoServidor);
            throw new PessoaException();
        }
    }

    @Transactional(readOnly = true)
    public PessoaJuridicaResponse buscarPorCnpj(String cnpj){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            PessoaJuridicaEntity buscarCNPJ = iPessoaJuridicaRepository.buscarPorCnpj(cnpj);

            PessoaEntity pessoa = PessoaEntity.builder()
                    .idPessoa(buscarCNPJ.getIdPessoa())
                    .nomeRazaosocial(buscarCNPJ.getNomeRazaosocial())
                    .email(buscarCNPJ.getEmail())
                    .telefone(buscarCNPJ.getTelefone())
                    .build();

            EnderecoEntity endereco = buscarCNPJ.getEndereco();

            logger.info(Constantes.InfoBuscar, cnpj);
            return pessoaDtoMapper.mapearPessoaJuridica(buscarCNPJ, pessoa, endereco);
        } catch (Exception e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor);
            throw new ObterPessoaPorCnpjNotFoundException();
        }
    }

    @Transactional
    public PessoaJuridicaResponse atualizarDados(PessoaJuridicaRequest editar){
        logger.info(Constantes.DebugEditarProcesso);
        try {
            Integer idPessoa = iPessoaJuridicaRepository.buscarIdPorCnpj(editar.getCnpj());
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

            PessoaJuridicaEntity atualizarJuridico = PessoaJuridicaEntity.builder()
                    .idPessoa(pessoaAtualizada.getIdPessoa())
                    .cnpj(editar.getCnpj())
                    .nomeFantasia(editar.getNomeFantasia())
                    .build();

            iPessoaJuridicaRepository.atualizarPessoaJuridica(atualizarJuridico);

            iEnderecoRepository.atualizarEndereco(idPessoa, editar.getEndereco());

            logger.info(Constantes.InfoEditar, editar);
            return PessoaJuridicaResponse.builder()
                    .idPessoa(idPessoa)
                    .nomeRazaosocial(editar.getNomeRazaosocial())
                    .nomeFantasia(editar.getNomeFantasia())
                    .cnpj(editar.getCnpj())
                    .email(editar.getEmail())
                    .telefone(editar.getTelefone())
                    .endereco(enderecoDtoMapper.mapear(editar.getEndereco()))
                    .build();

        }   catch (Exception e) {
            logger.error(Constantes.ErroEditarRegistroNoServidor, e);
            throw new EditarPessoaException();
        }
    }
}
