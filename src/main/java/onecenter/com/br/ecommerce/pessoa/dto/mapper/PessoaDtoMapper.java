package onecenter.com.br.ecommerce.pessoa.dto.mapper;

import onecenter.com.br.ecommerce.pessoa.dto.pessoas.response.fisica.PessoaFisicaResponse;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.response.juridica.PessoaJuridicaResponse;
import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoEntity;
import onecenter.com.br.ecommerce.pessoa.entity.fisica.PessoaFisicaEntity;
import onecenter.com.br.ecommerce.pessoa.entity.juridica.PessoaJuridicaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class PessoaDtoMapper {

    @Autowired
    private EnderecoDtoMapper enderecoDtoMapper;

    public PessoaFisicaResponse mapearPessoaFisica(PessoaFisicaEntity fisicaCriada, PessoaEntity pessoaCriada, EnderecoEntity enderecoCriado){
        return PessoaFisicaResponse.builder()
                .idPessoa(pessoaCriada.getIdPessoa())
                .nomeRazaosocial(pessoaCriada.getNomeRazaosocial())
                .cpf(fisicaCriada.getCpf())
                .dataNascimento(Timestamp.valueOf(String.valueOf(fisicaCriada.getDataNascimento())))
                .email(pessoaCriada.getEmail())
                .telefone(pessoaCriada.getTelefone())
                .endereco(enderecoDtoMapper.mapear(enderecoCriado))
                .build();
    }

    public PessoaJuridicaResponse mapearPessoaJuridica(PessoaJuridicaEntity juridicaCriada, PessoaEntity pessoaCriada, EnderecoEntity enderecoCriado){
        return PessoaJuridicaResponse.builder()
                .idPessoa(pessoaCriada.getIdPessoa())
                .nomeRazaosocial(pessoaCriada.getNomeRazaosocial())
                .cnpj(juridicaCriada.getCnpj())
                .nomeFantasia(juridicaCriada.getNomeFantasia())
                .email(pessoaCriada.getEmail())
                .telefone(pessoaCriada.getTelefone())
                .endereco(enderecoDtoMapper.mapear(enderecoCriado))
                .build();
    }
}
