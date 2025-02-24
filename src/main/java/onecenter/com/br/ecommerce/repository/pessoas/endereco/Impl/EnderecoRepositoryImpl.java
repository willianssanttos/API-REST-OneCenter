package onecenter.com.br.ecommerce.repository.pessoas.endereco.Impl;

import onecenter.com.br.ecommerce.config.exception.pessoas.AtualizarEnderecoException;
import onecenter.com.br.ecommerce.config.exception.pessoas.BuscarEnderecoNotFoundException;
import onecenter.com.br.ecommerce.config.exception.pessoas.EnderecoException;
import onecenter.com.br.ecommerce.dto.pessoas.request.fisica.PessoaFisicaRequest;
import onecenter.com.br.ecommerce.entity.pessoas.endereco.EnderecoEntity;
import onecenter.com.br.ecommerce.repository.mapper.EnderecoRowMapper;
import onecenter.com.br.ecommerce.repository.pessoas.endereco.IEnderecoRepository;
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
    public EnderecoEntity salverEndereco(EnderecoEntity endereco) {
        logger.info(Constantes.DebugRegistroProcesso);
        try {
            String sql = "INSERT INTO enderecos (nm_rua, ds_numero, ds_bairro, ds_cidade, ds_uf, ds_cep, fk_nr_id_pessoa) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING nr_id_endereco";
            jdbcTemplate.queryForObject(sql, Integer.class,
                    endereco.getRua(),
                    endereco.getNumero(),
                    endereco.getBairro(),
                    endereco.getLocalidade(),
                    endereco.getUf(),
                    endereco.getCep(),
                    endereco.getId_pessoa());
            logger.info(Constantes.InfoRegistrar, endereco);
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroRegistrarNoServidor, e.getMessage());
            throw new EnderecoException();
        }
        return endereco;
    }

    @Override
    @Transactional
    public EnderecoEntity obterEnderecoPorIdPessoa(Integer idPessoa) {
        try {
            String sql = "SELECT * FROM enderecos WHERE fk_nr_id_pessoa = ?";
            return jdbcTemplate.queryForObject(sql, new EnderecoRowMapper(), idPessoa);
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroRegistrarNoServidor, e.getMessage());
            throw new BuscarEnderecoNotFoundException();
        }
    }

    @Override
    @Transactional
    public void atualizarEndereco(Integer idPessoa, PessoaFisicaRequest editar) {
        logger.info(Constantes.DebugEditarProcesso);
        try {
            String sql = "UPDATE enderecos SET nm_rua = ?, ds_numero = ?, ds_bairro = ?, ds_cidade = ?, ds_cep = ?, ds_uf = ? WHERE fk_nr_id_pessoa = ?";
            jdbcTemplate.update(sql,
                    editar.getRua(),
                    editar.getNumero(),
                    editar.getBairro(),
                    editar.getLocalidade(),
                    editar.getCep(),
                    editar.getUf(),
                    idPessoa);
            logger.info(Constantes.InfoBuscar, editar);
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroRegistrarNoServidor, e.getMessage());
            throw new AtualizarEnderecoException();
        }
    }
}
