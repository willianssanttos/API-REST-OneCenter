package onecenter.com.br.ecommerce.repository.pessoas.fisica.Impl;

import onecenter.com.br.ecommerce.config.exception.PessoaException;
import onecenter.com.br.ecommerce.entity.pessoas.fisica.PessoaFisicaEntity;
import onecenter.com.br.ecommerce.repository.pessoas.fisica.IPessoaFisicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PessoaFisicaRepositoryImpl implements IPessoaFisicaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public PessoaFisicaEntity criarFisica(PessoaFisicaEntity fisica) {

        try {
            String sql = "INSERT INTO pessoas_fisicas (ds_cpf, ds_data_nascimento, fk_nr_id_pessoa) VALUES (?,?,?) RETURNING nr_id_pessoa_fisica";

            Integer idFisica = jdbcTemplate.queryForObject(sql, Integer.class,
                    fisica.getCpf(),
                    fisica.getData_nascimento(),
                    fisica.getId_pessoa()
            );
            fisica.setId_pessoa_fisica(idFisica);
            return fisica;
        } catch (DataAccessException e) {
            throw new PessoaException();
        }
    }
}
