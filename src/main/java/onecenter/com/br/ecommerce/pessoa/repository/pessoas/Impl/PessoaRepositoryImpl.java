package onecenter.com.br.ecommerce.pessoa.repository.pessoas.Impl;

import onecenter.com.br.ecommerce.pessoa.exception.pessoas.ErroLocalizarPessoaNotFoundException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.PessoaException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.fisica.ObterPessoaPorCpfNotFoundException;
import onecenter.com.br.ecommerce.pessoa.repository.mapper.PessoaRowMapper;
import onecenter.com.br.ecommerce.produto.exception.EditarProdutoException;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.request.PessoaRequest;
import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import onecenter.com.br.ecommerce.pessoa.repository.pessoas.IPessoaRepository;
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
    public boolean verificarEmailExistente(String email) {
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT COUNT(*) FROM pessoas WHERE ds_email = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
            return count != null && count > 0;
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public PessoaEntity buscarIdPessoa(Integer IdPessoa){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT * FROM pessoas WHERE nr_id_pessoa = ?";
            return jdbcTemplate.queryForObject(sql, new PessoaRowMapper(), IdPessoa);
        } catch (DataAccessException e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new ErroLocalizarPessoaNotFoundException();
        }
    }

    @Override
    @Transactional
    public void atualizarPessoa(Integer idPessoa, PessoaRequest editar) {
        logger.info(Constantes.DebugEditarProcesso);
        try {
            String sql = "UPDATE pessoas SET nm_nome_razaosocial = ?, ds_email = ?, ds_senha = ?, ds_telefone = ? WHERE nr_id_pessoa = ?";
            jdbcTemplate.update(sql,
                    editar.getNome_razaosocial(),
                    editar.getEmail(),
                    editar.getSenha(),
                    editar.getTelefone(),
                    idPessoa);
            logger.info(Constantes.InfoEditar, editar);
        } catch (DataAccessException e){
            logger.error(Constantes.ErroEditarRegistroNoServidor, e.getMessage());
            throw new EditarProdutoException();
        }
    }
}
