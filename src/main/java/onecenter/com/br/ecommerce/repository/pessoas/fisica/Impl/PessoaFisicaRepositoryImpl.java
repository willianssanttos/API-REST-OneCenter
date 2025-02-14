package onecenter.com.br.ecommerce.repository.pessoas.fisica.Impl;

import onecenter.com.br.ecommerce.config.exception.ObterPessoaPorCpfNotFoundException;
import onecenter.com.br.ecommerce.config.exception.ObterProdutosNotFundException;
import onecenter.com.br.ecommerce.config.exception.PessoaException;
import onecenter.com.br.ecommerce.entity.pessoas.fisica.PessoaFisicaEntity;
import onecenter.com.br.ecommerce.repository.mapper.PessoaFisicaRowMapper;
import onecenter.com.br.ecommerce.repository.pessoas.fisica.IPessoaFisicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PessoaFisicaRepositoryImpl implements IPessoaFisicaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public PessoaFisicaEntity criarFisica(PessoaFisicaEntity fisica) {

        try {
            String sql = "INSERT INTO pessoas_fisicas (ds_cpf, ds_data_nascimento, fk_nr_id_pessoa) VALUES (?,?,?) RETURNING nr_id_pessoa_fisica";

             jdbcTemplate.queryForObject(sql, Integer.class,
                    fisica.getCpf(),
                    fisica.getData_nascimento(),
                    fisica.getId_pessoa()
            );

            return fisica;
        } catch (DataAccessException e) {
            throw new PessoaException();
        }
    }

    @Override
    @Transactional
    public PessoaFisicaEntity buscarPorCpf(String CPF){
        try {
            String sql = "SELECT p.nr_id_pessoa, p.nm_nome_razaosocial, p.ds_email, p.ds_senha, p.ds_telefone,\n" +
                    "           pf.ds_cpf, pf.ds_data_nascimento\n" +
                    "    FROM pessoas_fisicas pf\n" +
                    "    INNER JOIN pessoas p ON pf.fk_nr_id_pessoa = p.nr_id_pessoa\n" +
                    "    WHERE pf.ds_cpf = ?";
            return jdbcTemplate.queryForObject(sql, new Object[] { CPF }, new PessoaFisicaRowMapper());

        } catch (DataAccessException e){
            throw new ObterPessoaPorCpfNotFoundException();
        }
    }

    @Override
    @Transactional
    public List<PessoaFisicaEntity> obterTodasPessos() {
        try {
            String sql = "SELECT \n" +
                    "    p.nr_id_pessoa,\n" +
                    "    p.nm_nome_razaosocial,\n" +
                    "    p.ds_email,\n" +
                    "    p.ds_senha,\n" +
                    "    p.ds_telefone,\n" +
                    "    pf.ds_cpf,\n" +
                    "    pf.ds_data_nascimento\n" +
                    "FROM pessoas p\n" +
                    "LEFT JOIN pessoas_fisicas pf ON p.nr_id_pessoa = pf.fk_nr_id_pessoa;\n";
            return jdbcTemplate.query(sql, new PessoaFisicaRowMapper());
        } catch (DataAccessException e) {
            throw new ObterProdutosNotFundException();
        }
    }
}
