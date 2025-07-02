package onecenter.com.br.ecommerce.pedidos.repository.pagamentos;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public interface IPagamentoRepository {

    void salvarPagamento(String formaPagamento, String status, BigDecimal valor, OffsetDateTime dataPagamento, Integer idPedido, String idTrasacaoExterna);

    boolean existePagamentoPorId(String idPagamento);

}
