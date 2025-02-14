package onecenter.com.br.ecommerce.service.pessoas.fisica;

import onecenter.com.br.ecommerce.config.exception.ObterProdutosNotFundException;
import onecenter.com.br.ecommerce.config.exception.PessoaException;
import onecenter.com.br.ecommerce.dto.pessoas.request.fisica.PessoaFisicaRequest;
import onecenter.com.br.ecommerce.dto.pessoas.response.fisica.PessoaFisicaResponse;
import onecenter.com.br.ecommerce.entity.pessoas.PessoaEntity;
import onecenter.com.br.ecommerce.entity.pessoas.fisica.PessoaFisicaEntity;
import onecenter.com.br.ecommerce.repository.pessoas.IPessoaRepository;
import onecenter.com.br.ecommerce.repository.pessoas.fisica.IPessoaFisicaRepository;
import onecenter.com.br.ecommerce.repository.pessoas.juridica.IPessoaJuridicaRepository;
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

    public PessoaFisicaResponse cadastrarPessoaFisica (PessoaFisicaRequest fisica){

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

            PessoaFisicaEntity fisicaCriada = iPessoaFisicaRepository.criarFisica(criarFisica);

            return mapearPessoaFisica(criarFisica);
        } catch (Exception e){
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
        PessoaFisicaEntity pessoaFisica = iPessoaFisicaRepository.buscarPorCpf(CPF);
        return mapearPessoaFisica(pessoaFisica);
    }

    public List<PessoaFisicaResponse> obterTodasPessoas(){
        try {
             List<PessoaFisicaEntity> todasPessos = iPessoaFisicaRepository.obterTodasPessos();
             return todasPessos.stream().map(this::mapearPessoaFisica).collect(Collectors.toList());
        } catch (Exception e){
            throw new ObterProdutosNotFundException();
        }
    }

}
