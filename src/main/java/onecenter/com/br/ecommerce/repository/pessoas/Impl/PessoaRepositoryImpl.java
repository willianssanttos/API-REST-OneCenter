package onecenter.com.br.ecommerce.repository.pessoas.Impl;

import onecenter.com.br.ecommerce.config.exception.pessoas.EditarPessoaException;
import onecenter.com.br.ecommerce.config.exception.pessoas.ObterPessoaPorCpfNotFoundException;
import onecenter.com.br.ecommerce.config.exception.pessoas.PessoaException;
import onecenter.com.br.ecommerce.dto.pessoas.response.PessoaResponse;
import onecenter.com.br.ecommerce.dto.pessoas.response.fisica.PessoaFisicaResponse;
import onecenter.com.br.ecommerce.entity.pessoas.PessoaEntity;
import onecenter.com.br.ecommerce.entity.pessoas.fisica.PessoaFisicaEntity;
import onecenter.com.br.ecommerce.repository.mapper.PessoaFisicaRowMapper;
import onecenter.com.br.ecommerce.repository.pessoas.IPessoaRepository;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PessoaRepositoryImpl implements IPessoaRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final Logger logger = LoggerFactory.getLogger(PessoaRepositoryImpl.class);

    @Override
    @Transactional
    public PessoaEntity criarPessoa(PessoaEntity pessoa){
        logger.info(Constantes.DebugRegistroProcesso);
        try{
            String sql = "INSERT INTO pessoas (nm_nome_razaosocial, ds_email, ds_senha, ds_telefone) VALUES (?,?,?,?) RETURNING nr_id_pessoa";
            Integer idPessoa = jdbcTemplate.queryForObject(sql, Integer.class,
                    pessoa.getNome_razaosocial(),
                    pessoa.getEmail(),
                    pessoa.getSenha(),
                    pessoa.getTelefone()
            );
            pessoa.setId_pessoa(idPessoa);
            logger.info(Constantes.InfoRegistrar, pessoa);
        } catch (DataAccessException e){
            logger.error(Constantes.ErroRegistrarNoServidor, e.getMessage());
            throw new PessoaException();
        }
        return pessoa;
    }

    @Override
    @Transactional
    public PessoaEntity obterPessoaPorId(Integer idPessoa){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT nr_id_pessoa, nm_nome_razaosocial, ds_email, ds_senha, ds_telefone,\n" +
                    "           ds_cpf, ds_data_nascimento\n" +
                    "    FROM pessoas WHERE nr_id_pessoa = ?";
            logger.info(Constantes.InfoBuscar, idPessoa);
            return jdbcTemplate.queryForObject(sql, new Object[] { idPessoa }, new PessoaFisicaRowMapper());
        } catch (DataAccessException e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new ObterPessoaPorCpfNotFoundException();
        }
    }

}
