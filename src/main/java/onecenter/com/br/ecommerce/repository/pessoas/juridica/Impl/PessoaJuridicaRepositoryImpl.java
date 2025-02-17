package onecenter.com.br.ecommerce.repository.pessoas.juridica.Impl;

import onecenter.com.br.ecommerce.config.exception.PessoaException;
import onecenter.com.br.ecommerce.entity.pessoas.juridica.PessoaJuridicaEntity;
import onecenter.com.br.ecommerce.repository.pessoas.juridica.IPessoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PessoaJuridicaRepositoryImpl implements IPessoaJuridicaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public PessoaJuridicaEntity criarJuridica(PessoaJuridicaEntity juridica){
        try {
            String sql = "INSERT INTO pessoas_juridicas (nm_nome_fantasia, ds_cnpj, fk_nr_id_pessoa) VALUES (?,?,?) RETURNING nr_id_pessoa_juridica";

            Integer idJuridico = jdbcTemplate.queryForObject(sql, Integer.class,
                    juridica.getNome_fantasia(),
                    juridica.getCnpj(),
                    juridica.getId_pessoa()
            );
            juridica.setId_pessoa_juridica(idJuridico);
            return juridica;
        } catch (DataAccessException e) {
            throw new PessoaException();
        }
    }
}
