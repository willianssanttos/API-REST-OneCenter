package onecenter.com.br.ecommerce.pagamento;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentRefundClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import onecenter.com.br.ecommerce.pedidos.dto.request.HistoricoPagamentoNaoAssociadoPedidoRequest;
import onecenter.com.br.ecommerce.pedidos.repository.IPedidosRepository;
import onecenter.com.br.ecommerce.pedidos.repository.pagamentos.IPagamentoRepository;
import onecenter.com.br.ecommerce.pedidos.service.email.EmailPagamentoService;
import onecenter.com.br.ecommerce.pedidos.service.pagamento.HistoricoPagamentoNaoAssociadoPedidoService;
import onecenter.com.br.ecommerce.pedidos.service.pagamento.PagamentoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

    @InjectMocks
    private PagamentoService pagamentoService;

    @Mock
    private IPedidosRepository iPedidosRepository;

    @Mock
    private IPagamentoRepository iPagamentoRepository;

    @Mock
    private HistoricoPagamentoNaoAssociadoPedidoService historicoPagamentoNaoAssociadoPedidoService;

    @Mock
    private EmailPagamentoService emailPagamentoService;

    // Mocks para os clientes do Mercado Pago que serão usados no mock estático
    @Mock
    private PaymentClient paymentClientMock;

    @Mock
    private PaymentRefundClient refundClientMock;

    @Captor
    private ArgumentCaptor<HistoricoPagamentoNaoAssociadoPedidoRequest> historicoRequestCaptor;

    // Controladores para os mocks estáticos
    private MockedStatic<PaymentClient> mockedPaymentClient;
    private MockedStatic<PaymentRefundClient> mockedRefundClient;

    private Map<String, Object> webhookPayload;
    private Payment mockPayment;
    private final Long PAGAMENTO_ID = 987654321L;
    private final Integer PEDIDO_ID = 12345;

    @BeforeEach
    void setUp() throws MPException, MPApiException {
        // 1. Iniciar os mocks estáticos.
        // A partir daqui, qualquer 'new PaymentClient()' retornará nosso 'paymentClientMock'
        mockedPaymentClient = mockStatic(PaymentClient.class);
        mockedPaymentClient.when(() -> new PaymentClient()).thenReturn(paymentClientMock);

        // O mesmo para o PaymentRefundClient
        mockedRefundClient = mockStatic(PaymentRefundClient.class);
        mockedRefundClient.when(() -> new PaymentRefundClient()).thenReturn(refundClientMock);

        // 2. Configurar o payload do webhook
        webhookPayload = new HashMap<>();
        webhookPayload.put("type", "payment");
        Map<String, Object> data = new HashMap<>();
        data.put("id", PAGAMENTO_ID.toString());
        webhookPayload.put("data", data);

        // 3. Configurar o mock do objeto de Pagamento do Mercado Pago
        mockPayment = mock(Payment.class);
        when(mockPayment.getId()).thenReturn(PAGAMENTO_ID);
        when(mockPayment.getStatus()).thenReturn("approved");
        when(mockPayment.getExternalReference()).thenReturn(PEDIDO_ID.toString());
        when(mockPayment.getPaymentMethodId()).thenReturn("pix");
        when(mockPayment.getTransactionAmount()).thenReturn(new BigDecimal("150.75"));
        when(mockPayment.getDateApproved()).thenReturn(OffsetDateTime.now());
        when(mockPayment.getDateCreated()).thenReturn(OffsetDateTime.now());

        // 4. Configurar o comportamento do nosso cliente mockado
        when(paymentClientMock.get(PAGAMENTO_ID)).thenReturn(mockPayment);
    }

    @AfterEach
    void tearDown() {
        // 5. É CRUCIAL fechar os mocks estáticos após cada teste para não afetar outros testes.
        mockedPaymentClient.close();
        mockedRefundClient.close();
    }

    @Test
    @DisplayName("Deve estornar e salvar histórico quando pedido não for encontrado para pagamento aprovado")
    void processarWebhook_QuandoPedidoNaoEncontrado_DeveEstornarESalvarHistorico() throws Exception {
        // --- ARRANGE ---

        // Cenário principal: O repositório de pedidos retorna null
        when(iPedidosRepository.buscarPedidosPorId(PEDIDO_ID)).thenReturn(null);

        // O pagamento ainda não existe no nosso banco
        when(iPagamentoRepository.existePagamentoPorId(PAGAMENTO_ID.toString())).thenReturn(false);

        // --- ACT ---
        pagamentoService.processarWebhook(webhookPayload);

        // --- ASSERT ---

        // Verifica se a busca pelo pedido foi tentada
        verify(iPedidosRepository, times(1)).buscarPedidosPorId(PEDIDO_ID);

        // Verifica se a chamada de estorno foi feita no nosso refundClientMock
        verify(refundClientMock, times(1)).refund(PAGAMENTO_ID);

        // Verifica se o método para salvar o histórico foi chamado
        verify(historicoPagamentoNaoAssociadoPedidoService, times(1)).salvarPagamentoNaoAssociado(historicoRequestCaptor.capture());

        // Valida os dados capturados
        HistoricoPagamentoNaoAssociadoPedidoRequest capturedRequest = historicoRequestCaptor.getValue();
        assertEquals(PAGAMENTO_ID.toString(), capturedRequest.getIdTransacaoExterna());
        assertEquals("ESTORNADO_AUTOMATICO", capturedRequest.getStatusPagamento());
        assertEquals("Pedido não encontrado", capturedRequest.getMotivo());
        assertEquals(new BigDecimal("150.75"), capturedRequest.getValorTotal());

        // Garante que nenhuma operação indevida foi realizada
        verify(iPedidosRepository, never()).atualizarStatusPagamento(anyInt(), anyString());
        verify(iPagamentoRepository, never()).salvarPagamento(any(), any(), any(), any(), any(), any());
        verify(emailPagamentoService, never()).enviarAtualizacaoPagamento(any(), any(), any(), any(), any(), any(), any(), any(), any());
    }
}