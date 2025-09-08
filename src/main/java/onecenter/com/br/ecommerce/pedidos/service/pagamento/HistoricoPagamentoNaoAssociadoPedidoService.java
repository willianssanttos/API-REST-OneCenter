package onecenter.com.br.ecommerce.pedidos.service.pagamento;

import onecenter.com.br.ecommerce.pedidos.dto.request.HistoricoPagamentoNaoAssociadoPedidoRequest;
import onecenter.com.br.ecommerce.pedidos.dto.response.HistoricoPagamentoNaoAssociadoPedidoResponse;
import onecenter.com.br.ecommerce.pedidos.entity.pagamento.HistoricoPagamentoNaoAssociadoPedidoEntity;
import onecenter.com.br.ecommerce.pedidos.repository.pagamentos.IPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HistoricoPagamentoNaoAssociadoPedidoService {

    @Autowired
    private IPagamentoRepository iPagamentoRepository;

    @Transactional
    public HistoricoPagamentoNaoAssociadoPedidoResponse salvarPagamentoNaoAssociado (HistoricoPagamentoNaoAssociadoPedidoRequest historico){

        HistoricoPagamentoNaoAssociadoPedidoEntity registro = HistoricoPagamentoNaoAssociadoPedidoEntity.builder()
                .idTransacaoExterna(historico.getIdTransacaoExterna())
                .formaPagamento(historico.getFormaPagamento())
                .statusPagamento(historico.getStatusPagamento())
                .valorTotal(historico.getValorTotal())
                .dataAprovacao(historico.getDataAprovacao())
                .motivo(historico.getMotivo())
                .dataRegistro(historico.getDataRegistro())
                .build();

        iPagamentoRepository.historicoPagamentoNaoAssociadoPedido(registro);
        return mapearHistorico(registro);
    }

    private HistoricoPagamentoNaoAssociadoPedidoResponse mapearHistorico (HistoricoPagamentoNaoAssociadoPedidoEntity historico){
        return HistoricoPagamentoNaoAssociadoPedidoResponse.builder()
                .idTransacaoExterna(historico.getIdTransacaoExterna())
                .formaPagamento(historico.getFormaPagamento())
                .statusPagamento(historico.getStatusPagamento())
                .valorTotal(historico.getValorTotal())
                .dataAprovacao(historico.getDataAprovacao())
                .motivo(historico.getMotivo())
                .dataRegistro(historico.getDataRegistro())
                .build();
    }
}
