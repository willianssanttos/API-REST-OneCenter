package onecenter.com.br.ecommerce.pessoa.repository.mapper;

import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoEntity;
import onecenter.com.br.ecommerce.pessoa.entity.fisica.PessoaFisicaEntity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PessoaFisicaRowMapper implements RowMapper<PessoaFisicaEntity> {

    @Override
    public PessoaFisicaEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        PessoaEntity pessoa = new PessoaRowMapper().mapRow(rs, rowNum);
        EnderecoEntity endereco = new EnderecoRowMapper().mapRow(rs, rowNum);

        return PessoaFisicaEntity.builder()
                .idPessoa(pessoa.getIdPessoa())
                .roles(pessoa.getRoles())
                .nomeRazaosocial(pessoa.getNomeRazaosocial())
                .email(pessoa.getEmail())
                .senha(pessoa.getSenha())
                .telefone(pessoa.getTelefone())
                .endereco(endereco)
                .cpf(rs.getString(2))
                .dataNascimento(rs.getTimestamp(3))
                .build();
    }
}
