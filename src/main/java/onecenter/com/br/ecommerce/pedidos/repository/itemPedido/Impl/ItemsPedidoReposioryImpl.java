package onecenter.com.br.ecommerce.pedidos.repository.itemPedido.Impl;

import onecenter.com.br.ecommerce.pedidos.entity.ItemPedidoEntity;
import onecenter.com.br.ecommerce.pedidos.exception.ItemPedidoException;
import onecenter.com.br.ecommerce.pedidos.repository.itemPedido.IItemsPedidoRepository;
import onecenter.com.br.ecommerce.pedidos.repository.mapper.ItemPedidoRowMapper;
import onecenter.com.br.ecommerce.pessoa.exception.endereco.BuscarEnderecoNotFoundException;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ItemsPedidoReposioryImpl implements IItemsPedidoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final Logger logger = LoggerFactory.getLogger(ItemsPedidoReposioryImpl.class);

    @Override
    @Transactional
    public ItemPedidoEntity salvarItemPedido(ItemPedidoEntity itemPedido){
        logger.info(Constantes.DebugRegistroProcesso);

        try {
            String sql = "SELECT inserir_item_pedido(?, ?, ?, ?)";
            Integer idItemPedido = jdbcTemplate.queryForObject(sql, new Object[]{
                    itemPedido.getPedido().getIdPedido(),
                    itemPedido.getProdutos().getIdProduto(),
                    itemPedido.getQuantidade(),
                    itemPedido.getPrecoUnitario()
            }, Integer.class);

            itemPedido.setIdItemPedido(idItemPedido);
            logger.info(Constantes.InfoRegistrar, idItemPedido);
        } catch (DataAccessException e){
            logger.error(Constantes.ErroRegistrarNoServidor, e.getMessage());
            throw new ItemPedidoException();
        }
        return itemPedido;
    }

    @Override
    @Transactional(readOnly = true)
    public ItemPedidoEntity obterItemPedidoPorIdP(Integer idItemPedido) {
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            String sql = "SELECT * FROM buscar_item_pedido_por_id(?)";
            return jdbcTemplate.queryForObject(sql, new ItemPedidoRowMapper(), idItemPedido);
        } catch (DataAccessException e) {
            logger.error(Constantes.ErroBuscarRegistroNoServidor, e.getMessage());
            throw new BuscarEnderecoNotFoundException();
        }
    }
}
