package onecenter.com.br.ecommerce.repository.pessoas.endereco.Impl;

import onecenter.com.br.ecommerce.config.exception.pessoas.BuscarEnderecoNotFoundException;
import onecenter.com.br.ecommerce.config.exception.pessoas.EnderecoException;
import onecenter.com.br.ecommerce.config.exception.pessoas.PessoaException;
import onecenter.com.br.ecommerce.entity.pessoas.endereco.EnderecoEntity;
import onecenter.com.br.ecommerce.repository.pessoas.endereco.IEnderecoRepository;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.Optional;

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
                    endereco.getCidade(),
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
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(EnderecoEntity.class), idPessoa);
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroRegistrarNoServidor, e.getMessage());
            throw new BuscarEnderecoNotFoundException();
        }
    }

}
