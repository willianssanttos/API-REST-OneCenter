package onecenter.com.br.ecommerce.pessoa.repository.pessoas.juridica.Impl;

import onecenter.com.br.ecommerce.pessoa.exception.pessoas.EditarPessoaException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.PessoaException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.juridico.ObterPessoaPorCnpjNotFoundException;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.request.juridico.PessoaJuridicaRequest;
import onecenter.com.br.ecommerce.pessoa.entity.juridica.PessoaJuridicaEntity;
import onecenter.com.br.ecommerce.pessoa.repository.pessoas.juridica.IPessoaJuridicaRepository;
import onecenter.com.br.ecommerce.pessoa.repository.mapper.PessoasJuridicaRowMapper;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PessoaJuridicaRepositoryImpl implements IPessoaJuridicaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final Logger logger = LoggerFactory.getLogger(PessoaJuridicaRepositoryImpl.class);

    @Override
    @Transactional
    public PessoaJuridicaEntity criarJuridica(PessoaJuridicaEntity juridica){
        logger.info(Constantes.DebugRegistroProcesso);
        try {
            String sql = "INSERT INTO pessoas_juridicas (nm_nome_fantasia, ds_cnpj, fk_nr_id_pessoa) VALUES (?,?,?) RETURNING nr_id_pessoa_juridica";

            Integer idJuridico = jdbcTemplate.queryForObject(sql, Integer.class,
                    juridica.getNome_fantasia(),
                    juridica.getCnpj(),
                    juridica.getId_pessoa()
            );
            juridica.setId_pessoa_juridica(idJuridico);
            logger.info(Constantes.InfoRegistrar, juridica);
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroRegistrarNoServidor, e.getMessage());
            throw new PessoaException();
        }
        return juridica;
    }

    @Override
    @Transactional
    public boolean verificarCnpjExistente(String CNPJ) {
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT COUNT(*) FROM pessoas_juridicas WHERE ds_cnpj = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, CNPJ);
            return count != null && count > 0;
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public Integer buscarIdPorCnpj(String CNPJ){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT p.nr_id_pessoa FROM pessoas p " +
                    "INNER JOIN pessoas_juridicas pj ON p.nr_id_pessoa = pj.fk_nr_id_pessoa " +
                    "WHERE pj.ds_cnpj = ?";
            return jdbcTemplate.queryForObject(sql, Integer.class, CNPJ);
        } catch (DataAccessException e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new ObterPessoaPorCnpjNotFoundException();
        }
    }

    @Override
    @Transactional
    public PessoaJuridicaEntity buscarPorCnpj(String CNPJ){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = """
                SELECT
                    p.nr_id_pessoa,
                    p.nm_nome_razaosocial,
                    p.ds_email,
                    p.ds_senha,
                    p.ds_telefone,
                    pj.ds_cnpj,
                    pj.nm_nome_fantasia,
                    e.nm_rua,
                    e.ds_numero,
                    e.ds_bairro,
                    e.ds_cidade,
                    e.ds_cep,
                    e.ds_uf
                FROM pessoas p
                LEFT JOIN pessoas_juridicas pj ON p.nr_id_pessoa = pj.fk_nr_id_pessoa
                LEFT JOIN enderecos e ON p.nr_id_pessoa = e.fk_nr_id_pessoa
                WHERE pj.ds_cnpj = ?;
                """;
            logger.info(Constantes.InfoBuscar, CNPJ);
            return jdbcTemplate.queryForObject(sql, new Object[] { CNPJ }, new PessoasJuridicaRowMapper());
        } catch (DataAccessException e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e);
            throw new ObterPessoaPorCnpjNotFoundException();
        }
    }

    @Override
    @Transactional
    public void atualizarPessoaJuridica(Integer idPessoa, PessoaJuridicaRequest editar) {
        logger.info(Constantes.DebugEditarProcesso);
        try {
            String sql = "UPDATE pessoas_juridicas SET ds_cnpj = ?, nm_nome_fantasia = ? WHERE fk_nr_id_pessoa = ?";
            jdbcTemplate.update(sql, editar.getCnpj(), editar.getNome_fantasia(), idPessoa);
            logger.info(Constantes.InfoEditar, editar);
        } catch (DataAccessException e){
            logger.error(Constantes.ErroEditarRegistroNoServidor, e.getMessage());
            throw new EditarPessoaException();
        }
    }
}
