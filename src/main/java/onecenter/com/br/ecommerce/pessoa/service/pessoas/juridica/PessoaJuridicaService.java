package onecenter.com.br.ecommerce.pessoa.service.pessoas.juridica;

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

        if(!ValidarNome.validarNome(pessoaJuridica.getNome_razaosocial())){
            throw new NomeValidacaoException();
        }

        if (!ValidarEmail.validaEmail(pessoaJuridica.getEmail())){
            throw new EmailValidacaoException();
        }

        if (!ValidarSenha.validarSenha(pessoaJuridica.getSenha())){
            throw new SenhaValidacaoException();
        }
        if (!ValidarCEP.validarCep(pessoaJuridica.getCep())){
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

            ViaCepResponse viaCep = apiViaCepService.consultarCep(juridica.getCep());
            juridica.setRua(viaCep.getLogradouro());
            juridica.setBairro(viaCep.getBairro());
            juridica.setLocalidade(viaCep.getLocalidade());
            juridica.setUf(viaCep.getUf());

            PessoaEntity pessoa = PessoaEntity.builder()
                    .role(String.valueOf(RolesEnum.ROLE_CLIENTE))
                    .nome_razaosocial(juridica.getNome_razaosocial())
                    .email(juridica.getEmail())
                    .senha(juridica.getSenha())
                    .telefone(ValidarNumeroCelular.formatarNumeroCelular(juridica.getTelefone()))
                    .build();

            PessoaEntity pessoaCriada = iPessoaRepository.criarPessoa(pessoa);

            PessoaJuridicaEntity criarJuridica = PessoaJuridicaEntity.builder()
                    .id_pessoa(pessoaCriada.getId_pessoa())
                    .cnpj(juridica.getCnpj())
                    .nome_fantasia(juridica.getNome_fantasia())
                    .build();

            EnderecoEntity endereco = EnderecoEntity.builder()
                    .id_pessoa(pessoaCriada.getId_pessoa())
                    .rua(juridica.getRua())
                    .numero(juridica.getNumero())
                    .bairro(juridica.getBairro())
                    .localidade(juridica.getLocalidade())
                    .cep(juridica.getCep())
                    .uf(juridica.getUf())
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
            PessoaEntity pessoa = iPessoaRepository.buscarIdPessoa(juridica.getId_pessoa());
            EnderecoEntity endereco = iEnderecoRepository.obterEnderecoPorIdPessoa(juridica.getId_pessoa());

            return PessoaJuridicaResponse.builder()
                    .idPessoa(juridica.getId_pessoa())
                    .role(pessoa.getRole())
                    .nome_razaosocial(pessoa.getNome_razaosocial())
                    .cnpj(juridica.getCnpj())
                    .nome_fantasia(juridica.getNome_fantasia())
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
            throw new BuscarEnderecoNotFoundException();
        }
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
    public PessoaJuridicaResponse atualizarDados(PessoaJuridicaRequest editar){
        logger.info(Constantes.DebugEditarProcesso);
        try {
            Integer idPessoa = iPessoaJuridicaRepository.buscarIdPorCnpj(editar.getCnpj());
            if (idPessoa == null){
                throw new EditarPessoaException();
            }

            iPessoaRepository.atualizarPessoa(idPessoa, editar);
            iPessoaJuridicaRepository.atualizarPessoaJuridica(idPessoa, editar);

            ViaCepResponse viaCep = apiViaCepService.consultarCep(editar.getCep());
            editar.setRua(viaCep.getLogradouro());
            editar.setBairro(viaCep.getBairro());
            editar.setLocalidade(viaCep.getLocalidade());
            editar.setUf(viaCep.getUf());

            iEnderecoRepository.atualizarEndereco(idPessoa, editar);

            return PessoaJuridicaResponse.builder()
                    .idPessoa(idPessoa)
                    .role(editar.getRole())
                    .nome_razaosocial(editar.getNome_razaosocial())
                    .nome_fantasia(editar.getNome_fantasia())
                    .cnpj(editar.getCnpj())
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
