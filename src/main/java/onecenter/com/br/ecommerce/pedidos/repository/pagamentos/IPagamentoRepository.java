package onecenter.com.br.ecommerce.pedidos.repository.pagamentos;

import onecenter.com.br.ecommerce.pedidos.entity.pagamento.HistoricoPagamentoNaoAssociadoPedidoEntity;
import onecenter.com.br.ecommerce.pedidos.entity.pagamento.PagamentoEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

public interface IPagamentoRepository {

    void salvarPagamento(String formaPagamento, String status, BigDecimal valor, OffsetDateTime dataPagamento, Integer idPedido, String idTrasacaoExterna);

    boolean existePagamentoPorId(String idPagamento);

    List<PagamentoEntity> buscarPagamentoRealizado(Integer IdPedido);

    void atualizarStatusEstorno(Integer idPagamento, String status);

    HistoricoPagamentoNaoAssociadoPedidoEntity historicoPagamentoNaoAssociadoPedido (HistoricoPagamentoNaoAssociadoPedidoEntity historico);

}
