package onecenter.com.br.ecommerce.pessoa.repository.mapper;

import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoEntity;
import onecenter.com.br.ecommerce.pessoa.entity.juridica.PessoaJuridicaEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PessoasJuridicaRowMapper implements RowMapper<PessoaJuridicaEntity> {

    @Override
    public PessoaJuridicaEntity mapRow(ResultSet rs, int rowNum) throws SQLException{

        PessoaEntity pessoa = new PessoaRowMapper().mapRow(rs, rowNum);
        EnderecoEntity endereco = new EnderecoRowMapper().mapRow(rs, rowNum);

        return PessoaJuridicaEntity.builder()
                .idPessoa(pessoa.getIdPessoa())
                .roles(pessoa.getRoles())
                .nomeRazaosocial(pessoa.getNomeRazaosocial())
                .email(pessoa.getEmail())
                .senha(pessoa.getSenha())
                .telefone(pessoa.getTelefone())
                .endereco(endereco)
                .cnpj(rs.getString(13))
                .nomeFantasia(rs.getString(14))
                .build();
    }
}
