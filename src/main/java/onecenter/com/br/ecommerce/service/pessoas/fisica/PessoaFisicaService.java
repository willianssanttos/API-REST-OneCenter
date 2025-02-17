package onecenter.com.br.ecommerce.service.pessoas.fisica;

import onecenter.com.br.ecommerce.config.exception.pessoas.EditarPessoaException;
import onecenter.com.br.ecommerce.config.exception.pessoas.ObterPessoaPorCpfNotFoundException;
import onecenter.com.br.ecommerce.config.exception.pessoas.ObterTodasPessoas;
import onecenter.com.br.ecommerce.config.exception.pessoas.PessoaException;
import onecenter.com.br.ecommerce.dto.pessoas.request.fisica.PessoaFisicaRequest;
import onecenter.com.br.ecommerce.dto.pessoas.response.fisica.PessoaFisicaResponse;
import onecenter.com.br.ecommerce.entity.pessoas.PessoaEntity;
import onecenter.com.br.ecommerce.entity.pessoas.fisica.PessoaFisicaEntity;
import onecenter.com.br.ecommerce.repository.pessoas.IPessoaRepository;
import onecenter.com.br.ecommerce.repository.pessoas.fisica.IPessoaFisicaRepository;
import onecenter.com.br.ecommerce.repository.pessoas.juridica.IPessoaJuridicaRepository;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaFisicaService {

    @Autowired
    private IPessoaRepository iPessoaRepository;

    @Autowired
    private IPessoaFisicaRepository iPessoaFisicaRepository;

    @Autowired
    private IPessoaJuridicaRepository iPessoaJuridicaRepository;

    private static final Logger logger = LoggerFactory.getLogger(PessoaFisicaService.class);

    public PessoaFisicaResponse cadastrarPessoaFisica (PessoaFisicaRequest fisica){
        logger.info(Constantes.DebugRegistroProcesso);
        try {
            PessoaEntity pessoa = PessoaEntity.builder()
                    .nome_razaosocial(fisica.getNome_razaosocial())
                    .email(fisica.getEmail())
                    .senha(fisica.getSenha())
                    .telefone(fisica.getTelefone())
                    .build();

            PessoaEntity pessoaCriada = iPessoaRepository.criarPessoa(pessoa);

            PessoaFisicaEntity criarFisica = PessoaFisicaEntity.builder()
                    .id_pessoa(pessoaCriada.getId_pessoa())
                    .cpf(fisica.getCpf())
                    .data_nascimento(fisica.getData_nascimento())
                    .build();

            iPessoaFisicaRepository.criarFisica(criarFisica);
            logger.info(Constantes.InfoRegistrar, fisica);
            return mapearPessoaFisica(criarFisica);
        } catch (Exception e){
            logger.error(Constantes.ErroRegistrarNoServidor);
            throw new PessoaException();
        }
    }

    private PessoaFisicaResponse mapearPessoaFisica(PessoaFisicaEntity pessoaCriada){
        return PessoaFisicaResponse.builder()
                .idPessoa(pessoaCriada.getId_pessoa())
                .nome_razaosocial(pessoaCriada.getNome_razaosocial())
                .email(pessoaCriada.getEmail())
                .senha(pessoaCriada.getSenha())
                .telefone(pessoaCriada.getTelefone())
                .cpf(pessoaCriada.getCpf())
                .data_nascimento(pessoaCriada.getData_nascimento())
                .build();
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
             List<PessoaFisicaEntity> todasPessos = iPessoaFisicaRepository.obterTodasPessos();
            logger.info(Constantes.InfoBuscar, todasPessos);
            return todasPessos.stream().map(this::mapearPessoaFisica).collect(Collectors.toList());
        } catch (Exception e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor);
            throw new ObterTodasPessoas();
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
