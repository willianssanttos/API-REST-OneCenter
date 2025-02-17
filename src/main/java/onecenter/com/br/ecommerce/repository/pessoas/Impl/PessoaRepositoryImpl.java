package onecenter.com.br.ecommerce.repository.pessoas.Impl;

import onecenter.com.br.ecommerce.config.exception.PessoaException;
import onecenter.com.br.ecommerce.entity.pessoas.PessoaEntity;
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
}
