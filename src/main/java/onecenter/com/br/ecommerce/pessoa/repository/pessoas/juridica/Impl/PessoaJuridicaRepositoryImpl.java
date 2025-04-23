package onecenter.com.br.ecommerce.pessoa.repository.pessoas.juridica.Impl;

import onecenter.com.br.ecommerce.pessoa.exception.pessoas.EditarPessoaException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.PessoaException;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.juridico.ObterPessoaPorCnpjNotFoundException;
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
            String sql = "SELECT inserir_pessoa_juridica(?, ?, ?)";

            Integer idJuridico = jdbcTemplate.queryForObject(sql, Integer.class,
                    juridica.getNomeFantasia(),
                    juridica.getCnpj(),
                    juridica.getIdPessoa()
            );
            juridica.setIdPessoaJuridica(idJuridico);
            logger.info(Constantes.InfoRegistrar, juridica);
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroRegistrarNoServidor, e.getMessage());
            throw new PessoaException();
        }
        return juridica;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verificarCnpjExistente(String cnpj) {
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT verificar_cnpj_existe(?)";
            Boolean existeCNPJ = jdbcTemplate.queryForObject(sql, Boolean.class, cnpj);
            return Boolean.TRUE.equals(existeCNPJ);
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Integer buscarIdPorCnpj(String cnpj){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT buscar_id_pessoa_por_cnpj(?)";
            return jdbcTemplate.queryForObject(sql, Integer.class, cnpj);
        } catch (DataAccessException e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new ObterPessoaPorCnpjNotFoundException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PessoaJuridicaEntity buscarPorCnpj(String cnpj){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT * FROM buscar_pessoa_por_cnpj(?)";
            logger.info(Constantes.InfoBuscar, cnpj);
            return jdbcTemplate.queryForObject(sql, new Object[] { cnpj }, new PessoasJuridicaRowMapper());
        } catch (DataAccessException e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e);
            throw new ObterPessoaPorCnpjNotFoundException();
        }
    }

    @Override
    @Transactional
    public PessoaJuridicaEntity atualizarPessoaJuridica(PessoaJuridicaEntity editar) {
        logger.info(Constantes.DebugEditarProcesso);
        try {
            String sql = "SELECT atualizar_pessoa_juridica(?, ?, ?)";
            jdbcTemplate.queryForObject(sql, new Object[]{
                    editar.getCnpj(), editar.getNomeFantasia(), editar.getIdPessoa()
            }, Integer.class);
            logger.info(Constantes.InfoEditar, editar);
        } catch (DataAccessException e){
            logger.error(Constantes.ErroEditarRegistroNoServidor, e.getMessage());
            throw new EditarPessoaException();
        }
        return editar;
    }
}
