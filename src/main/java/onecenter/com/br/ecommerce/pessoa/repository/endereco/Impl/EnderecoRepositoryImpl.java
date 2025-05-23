package onecenter.com.br.ecommerce.pessoa.repository.endereco.Impl;

import onecenter.com.br.ecommerce.pessoa.dto.endereco.request.EnderecoRequest;
import onecenter.com.br.ecommerce.pessoa.exception.endereco.AtualizarEnderecoException;
import onecenter.com.br.ecommerce.pessoa.exception.endereco.BuscarEnderecoNotFoundException;
import onecenter.com.br.ecommerce.pessoa.exception.endereco.EnderecoException;
import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoEntity;
import onecenter.com.br.ecommerce.pessoa.repository.endereco.IEnderecoRepository;
import onecenter.com.br.ecommerce.pessoa.repository.mapper.EnderecoRowMapper;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EnderecoRepositoryImpl implements IEnderecoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final Logger logger = LoggerFactory.getLogger(EnderecoRepositoryImpl.class);

    @Override
    @Transactional
    public EnderecoEntity salvarEndereco(EnderecoEntity endereco) {
        logger.info(Constantes.DebugRegistroProcesso);
        try {
            String sql = "SELECT inserir_endereco(?, ?, ?, ?, ?, ?, ?)";
            Integer idEndereco = jdbcTemplate.queryForObject(sql, Integer.class,
                    endereco.getRua(),
                    endereco.getNumero(),
                    endereco.getBairro(),
                    endereco.getLocalidade(),
                    endereco.getUf(),
                    endereco.getCep(),
                    endereco.getIdPessoa());
            endereco.setIdEndereco(idEndereco);
            logger.info(Constantes.InfoRegistrar, endereco);
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroRegistrarNoServidor, e.getMessage());
            throw new EnderecoException();
        }
        return endereco;
    }

    @Override
    @Transactional(readOnly = true)
    public EnderecoEntity obterEnderecoPorIdPessoa(Integer idPessoa) {
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT * FROM buscar_endereco_por_pessoa(?)";
            return jdbcTemplate.queryForObject(sql, new EnderecoRowMapper(), idPessoa);
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new BuscarEnderecoNotFoundException();
        }
    }

    @Override
    @Transactional
    public void atualizarEndereco(Integer idPessoa, EnderecoRequest editar) {
        logger.info(Constantes.DebugEditarProcesso);
        try {
            String sql = "CALL atualizar_endereco_por_pessoa(?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    editar.getRua(),
                    editar.getNumero(),
                    editar.getBairro(),
                    editar.getLocalidade(),
                    editar.getCep(),
                    editar.getUf(),
                    idPessoa
            );
            logger.info(Constantes.InfoEditar, editar);
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroEditarRegistroNoServidor, e.getMessage());
            throw new AtualizarEnderecoException();
        }
    }
}
