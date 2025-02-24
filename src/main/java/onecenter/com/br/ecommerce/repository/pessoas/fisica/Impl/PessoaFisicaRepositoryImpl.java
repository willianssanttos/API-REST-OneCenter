package onecenter.com.br.ecommerce.repository.pessoas.fisica.Impl;

import onecenter.com.br.ecommerce.config.exception.pessoas.*;
import onecenter.com.br.ecommerce.dto.pessoas.request.fisica.PessoaFisicaRequest;
import onecenter.com.br.ecommerce.entity.pessoas.fisica.PessoaFisicaEntity;
import onecenter.com.br.ecommerce.repository.mapper.PessoaFisicaRowMapper;
import onecenter.com.br.ecommerce.repository.pessoas.fisica.IPessoaFisicaRepository;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public static final Logger logger = LoggerFactory.getLogger(PessoaFisicaRepositoryImpl.class);

    @Override
    @Transactional
    public PessoaFisicaEntity criarFisica(PessoaFisicaEntity fisica) {
        logger.info(Constantes.DebugRegistroProcesso);

        try {
            String sql = "INSERT INTO pessoas_fisicas (ds_cpf, ds_data_nascimento, fk_nr_id_pessoa) VALUES (?,?,?) RETURNING nr_id_pessoa_fisica";
             jdbcTemplate.queryForObject(sql, Integer.class,
                    fisica.getCpf(),
                    fisica.getData_nascimento(),
                    fisica.getId_pessoa()
            );
            logger.info(Constantes.InfoRegistrar, fisica);
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroRegistrarNoServidor, e.getMessage());
            throw new PessoaException();
        }
        return fisica;
    }

    @Override
    @Transactional
    public boolean verificarCpfExistente(String cpf){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT * FROM pessoas_fisicas WHERE ds_cpf = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cpf);
            return count > 0;

        } catch (DataAccessException e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public Integer buscarIdPorCpf(String cpf){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT p.nr_id_pessoa FROM pessoas p " +
                    "INNER JOIN pessoas_fisicas pf ON p.nr_id_pessoa = pf.fk_nr_id_pessoa " +
                    "WHERE pf.ds_cpf = ?";
            return jdbcTemplate.queryForObject(sql, Integer.class, cpf);
        } catch (DataAccessException e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new ObterPessoaPorCpfNotFoundException();
        }
    }

    @Override
    @Transactional
    public PessoaFisicaEntity buscarPorCpf(String CPF) {
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = """
            SELECT 
                p.nr_id_pessoa,
                p.nm_nome_razaosocial AS nomeRazaoSocial,
                p.ds_email AS email,
                p.ds_senha AS senha,
                p.ds_telefone AS telefone,
                pf.ds_cpf AS cpf,
                pf.ds_data_nascimento AS dataNascimento,
                e.nm_rua AS rua,
                e.ds_numero AS numero,
                e.ds_bairro AS bairro,
                e.ds_cidade AS cidade,
                e.ds_cep AS cep,
                e.ds_uf AS uf
            FROM pessoas p
            LEFT JOIN pessoas_fisicas pf ON p.nr_id_pessoa = pf.fk_nr_id_pessoa AS idPessoa
            LEFT JOIN enderecos e ON p.nr_id_pessoa = e.fk_nr_id_pessoa
            WHERE pf.ds_cpf = ?
        """;

            logger.info(Constantes.InfoBuscar, CPF);
            return jdbcTemplate.queryForObject(sql, new Object[] { CPF }, new PessoaFisicaRowMapper());
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new ObterPessoaPorCpfNotFoundException();
        }
    }


    @Override
    @Transactional
    public List<PessoaFisicaEntity> obterTodasPessos() {
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = """
                SELECT 
                    p.nr_id_pessoa,
                    p.nm_nome_razaosocial,
                    p.ds_email,
                    p.ds_senha,
                    p.ds_telefone,
                    pf.ds_cpf,
                    pf.ds_data_nascimento,
                    e.nm_rua,
                    e.ds_numero,
                    e.ds_bairro,
                    e.ds_cidade,
                    e.ds_cep,
                    e.ds_uf
                FROM pessoas p
                LEFT JOIN pessoas_fisicas pf ON p.nr_id_pessoa = pf.fk_nr_id_pessoa
                LEFT JOIN enderecos e ON p.nr_id_pessoa = e.fk_nr_id_pessoa
                """;

            logger.info(Constantes.InfoBuscar);
            return jdbcTemplate.query(sql, new PessoaFisicaRowMapper());
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e);
            throw new ObterTodasPessoasException();
        }
    }

    @Override
    @Transactional
     public void atualizarPessoaFisica(Integer idPessoa, PessoaFisicaRequest editar) {
        logger.info(Constantes.DebugEditarProcesso);
        try {
            String sql = "UPDATE pessoas_fisicas SET ds_cpf = ?, ds_data_nascimento = ? WHERE fk_nr_id_pessoa = ?";
            jdbcTemplate.update(sql, editar.getCpf(), editar.getData_nascimento(), idPessoa);
            logger.info(Constantes.InfoEditar, editar);
        } catch (DataAccessException e){
            logger.error(Constantes.ErroEditarRegistroNoServidor, e.getMessage());
            throw new EditarPessoaException();
        }
    }
}
