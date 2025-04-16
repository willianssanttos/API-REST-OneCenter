package onecenter.com.br.ecommerce.pessoa.service.pessoas.juridica;

import onecenter.com.br.ecommerce.config.security.config.SecurityConfiguration;
import onecenter.com.br.ecommerce.pessoa.dto.endereco.response.EnderecoResponse;
import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoBase;
import onecenter.com.br.ecommerce.pessoa.enums.RolesEnum;
import onecenter.com.br.ecommerce.pessoa.exception.endereco.CepValidacaoExcecao;
import onecenter.com.br.ecommerce.pessoa.exception.endereco.BuscarEnderecoNotFoundException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.*;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.fisica.NumeroCelularValidacaoException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.juridico.CnpjExistenteException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.juridico.CnpjValidacaoException;
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

@Service
public class PessoaJuridicaService {

    @Autowired
    private ApiViaCepService apiViaCepService;
    @Autowired
    private IPessoaRepository iPessoaRepository;
    @Autowired
    private IEnderecoRepository iEnderecoRepository;
    @Autowired
    private SecurityConfiguration securityConfiguration;
    @Autowired
    private IPessoaJuridicaRepository iPessoaJuridicaRepository;

    private static final Logger logger = LoggerFactory.getLogger(PessoaJuridicaService.class);

    private void validarDados(PessoaJuridicaRequest pessoaJuridica){

        if(!ValidarCNPJ.isCnpjValid(pessoaJuridica.getCnpj())){
            throw new CnpjValidacaoException();
        }

        if (Boolean.TRUE.equals(iPessoaJuridicaRepository.verificarCnpjExistente(pessoaJuridica.getCnpj()))){
            throw new CnpjExistenteException();
        }

        if (Boolean.TRUE.equals(iPessoaRepository.verificarEmailExistente(pessoaJuridica.getEmail()))){
            throw new EmailExistenteException();
        }

        if(!ValidarNome.validarNome(pessoaJuridica.getNomeRazaosocial())){
            throw new NomeValidacaoException();
        }

        if (!ValidarEmail.validaEmail(pessoaJuridica.getEmail())){
            throw new EmailValidacaoException();
        }

        if (!ValidarSenha.validarSenha(pessoaJuridica.getSenha())){
            throw new SenhaValidacaoException();
        }
        if (!ValidarCEP.validarCep(pessoaJuridica.getEndereco().getCep())){
            throw new CepValidacaoExcecao();
        }

        if (!ValidarNumeroCelular.validarNumeroCelular(pessoaJuridica.getTelefone())) {
            throw new NumeroCelularValidacaoException();
        }
    }
    public PessoaJuridicaResponse cadastrarPessoaJuridica (PessoaJuridicaRequest juridica){
        logger.info(Constantes.DebugRegistroProcesso);

        validarDados(juridica);

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
                    .nome_fantasia(juridica.getNome_fantasia())
                    .build();

            EnderecoEntity endereco = EnderecoEntity.builder()
                    .idPessoa(pessoaCriada.getIdPessoa())
                    .rua(juridica.getEndereco().getRua())
                    .numero(juridica.getEndereco().getNumero())
                    .bairro(juridica.getEndereco().getBairro())
                    .localidade(juridica.getEndereco().getLocalidade())
                    .cep(juridica.getEndereco().getCep())
                    .uf(juridica.getEndereco().getUf())
                    .build();

            iEnderecoRepository.salverEndereco(endereco);

            iPessoaJuridicaRepository.criarJuridica(criarJuridica);
            logger.info(Constantes.InfoRegistrar, juridica);
            return mapearPessoaJuridica(criarJuridica);
        } catch (Exception e){
            logger.error(Constantes.ErroRegistrarNoServidor);
            throw new PessoaException();
        }
    }

    private PessoaJuridicaResponse mapearPessoaJuridica(PessoaJuridicaEntity juridica){
        try {
            return PessoaJuridicaResponse.builder()
                    .idPessoa(juridica.getIdPessoa())
                    .nomeRazaosocial(juridica.getNomeRazaosocial())
                    .cnpj(juridica.getCnpj())
                    .nome_fantasia(juridica.getNome_fantasia())
                    .email(juridica.getEmail())
                    .telefone(juridica.getTelefone())
                    .endereco(mapearEndereco(juridica.getEndereco()))
                    .build();

        } catch (Exception e){
            throw new BuscarEnderecoNotFoundException();
        }
    }

    private EnderecoResponse mapearEndereco(EnderecoBase endereco){
        if(endereco == null) return null;

        return EnderecoResponse.builder()
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .bairro(endereco.getBairro())
                .localidade(endereco.getLocalidade())
                .cep(endereco.getCep())
                .uf(endereco.getUf())
                .build();
    }


    public PessoaJuridicaResponse obterPorCnpj(String CNPJ){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            PessoaJuridicaEntity pessoaJuridica = iPessoaJuridicaRepository.buscarPorCnpj(CNPJ);
            logger.info(Constantes.InfoBuscar, CNPJ);
            return mapearPessoaJuridica(pessoaJuridica);
        } catch (Exception e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor);
            throw new ObterPessoaPorCnpjNotFoundException();
        }
    }
//    public PessoaJuridicaResponse atualizarDados(PessoaJuridicaRequest editar){
//        logger.info(Constantes.DebugEditarProcesso);
//        try {
//            Integer idPessoa = iPessoaJuridicaRepository.buscarIdPorCnpj(editar.getCnpj());
//            if (idPessoa == null){
//                throw new EditarPessoaException();
//            }
//
//            ViaCepResponse viaCep = apiViaCepService.consultarCep(editar.getEndereco().getCep());
//            editar.getEndereco().setRua(viaCep.getLogradouro());
//            editar.getEndereco().setBairro(viaCep.getBairro());
//            editar.getEndereco().setLocalidade(viaCep.getLocalidade());
//            editar.getEndereco().setUf(viaCep.getUf());
//
//            iEnderecoRepository.atualizarEndereco(idPessoa, editar.getEndereco());
//
//            return PessoaJuridicaResponse.builder()
//                    .idPessoa(idPessoa)
//                    //.nomeRole(editar.getEndereco().getRoles())
//                    .nomeRazaosocial(editar.getNomeRazaosocial())
//                    .nome_fantasia(editar.getNome_fantasia())
//                    .cnpj(editar.getCnpj())
//                    .email(editar.getEmail())
//                    .telefone(editar.getTelefone())
//                    .endereco(mapearEndereco(editar.getEndereco()))
//                    .build();
//
//        }catch (Exception e){
//            logger.error(Constantes.ErroEditarRegistroNoServidor, e);
//            throw new EditarPessoaException();
//        }
//    }
}
