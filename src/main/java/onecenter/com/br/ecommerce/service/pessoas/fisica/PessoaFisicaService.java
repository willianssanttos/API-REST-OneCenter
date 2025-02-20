package onecenter.com.br.ecommerce.service.pessoas.fisica;

import onecenter.com.br.ecommerce.config.exception.pessoas.*;
import onecenter.com.br.ecommerce.dto.pessoas.request.fisica.PessoaFisicaRequest;
import onecenter.com.br.ecommerce.dto.pessoas.response.fisica.PessoaFisicaResponse;
import onecenter.com.br.ecommerce.dto.produtos.endereco.ApiViaCep.ViaCepResponse;
import onecenter.com.br.ecommerce.entity.pessoas.PessoaEntity;
import onecenter.com.br.ecommerce.entity.pessoas.endereco.EnderecoEntity;
import onecenter.com.br.ecommerce.entity.pessoas.fisica.PessoaFisicaEntity;
import onecenter.com.br.ecommerce.repository.pessoas.IPessoaRepository;
import onecenter.com.br.ecommerce.repository.pessoas.endereco.IEnderecoRepository;
import onecenter.com.br.ecommerce.repository.pessoas.fisica.IPessoaFisicaRepository;
import onecenter.com.br.ecommerce.repository.pessoas.juridica.IPessoaJuridicaRepository;
import onecenter.com.br.ecommerce.service.pessoas.endereco.ApiViaCepService;
import onecenter.com.br.ecommerce.utils.Constantes;
import onecenter.com.br.ecommerce.utils.validacoes.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaFisicaService {

    @Autowired
    private ApiViaCepService apiViaCepService;

    @Autowired
    private IPessoaRepository iPessoaRepository;

    @Autowired
    private IEnderecoRepository iEnderecoRepository;

    @Autowired
    private IPessoaFisicaRepository iPessoaFisicaRepository;

    @Autowired
    private IPessoaJuridicaRepository iPessoaJuridicaRepository;

    private static final Logger logger = LoggerFactory.getLogger(PessoaFisicaService.class);

    private void validarDados(PessoaFisicaRequest pessoaFisica){

        if(!ValidarCPF.cpfValidado(pessoaFisica.getCpf())){
            throw new CpfValidacaoException();
        }

        Boolean cpfExistente = iPessoaFisicaRepository.verificarCpfExistente(pessoaFisica.getCpf());
        if (cpfExistente != null && cpfExistente){
            throw new CpfExistenteException();
        }

        Boolean emailExistente = iPessoaRepository.verificarEmailExistente(pessoaFisica.getEmail());
        if (emailExistente != null && emailExistente){
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

//        if(!ValidarDataNascimento.validarDataNascimento(String.valueOf(pessoaFisica.getData_nascimento()))){
//            throw new DataNascimentoException();
//        }

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
                    fisica.setCidade(viaCep.getLocalidade());
                    fisica.setUf(viaCep.getUf());

            PessoaEntity pessoa = PessoaEntity.builder()
                    .nome_razaosocial(fisica.getNome_razaosocial())
                    .email(fisica.getEmail())
                    .senha(fisica.getSenha())
                    .telefone(ValidarNumeroCelular.formatarNumeroCelular(fisica.getTelefone()))
                    .build();

            PessoaEntity pessoaCriada = iPessoaRepository.criarPessoa(pessoa);

            EnderecoEntity endereco = EnderecoEntity.builder()
                    .id_pessoa(pessoaCriada.getId_pessoa())
                    .rua(fisica.getRua())
                    .numero(fisica.getNumero())
                    .bairro(fisica.getBairro())
                    .localidade(fisica.getCidade())
                    .cep(fisica.getCep())
                    .uf(fisica.getUf())
                    .build();
            EnderecoEntity enderecoSalvo = iEnderecoRepository.salverEndereco(endereco);


            PessoaFisicaEntity criarFisica = PessoaFisicaEntity.builder()
                    .id_pessoa(pessoaCriada.getId_pessoa())
                    .cpf(fisica.getCpf())
                    .data_nascimento(Timestamp.valueOf(fisica.getData_nascimento().atStartOfDay()))
                    .build();

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
            EnderecoEntity endereco = iEnderecoRepository.obterEnderecoPorIdPessoa(fisica.getId_pessoa());

            return PessoaFisicaResponse.builder()
                    .idPessoa(fisica.getId_pessoa())
                    .nome_razaosocial(fisica.getNome_razaosocial())
                    .cpf(fisica.getCpf())
                    .data_nascimento(Timestamp.valueOf(String.valueOf(fisica.getData_nascimento())))
                    .email(fisica.getEmail())
                    .telefone(fisica.getTelefone())
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

    public List<PessoaFisicaResponse> obterTodasPessoas(){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            List<PessoaFisicaEntity> todasPessoas = iPessoaFisicaRepository.obterTodasPessos();
            logger.info(Constantes.InfoBuscar, todasPessoas);
            return todasPessoas.stream().map(this::mapearPessoaFisica).collect(Collectors.toList());
        } catch (Exception e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e);
            throw new ObterTodasPessoasException();
        }
    }

    public PessoaFisicaResponse atualizarDados(PessoaFisicaResponse editar) {
        logger.info(Constantes.DebugEditarProcesso);
        try {
            // Busca o ID da pessoa com base no CPF
            PessoaFisicaEntity idPessoa = iPessoaFisicaRepository.buscarPorCpf(editar.getCpf());
            if (idPessoa == null) {
                throw new EditarPessoaException();
            }
            return iPessoaFisicaRepository.atualizarDados(editar);
        } catch (Exception e) {
            logger.error(Constantes.ErroEditarRegistroNoServidor, e);
            throw new EditarPessoaException();
        }
    }

}
