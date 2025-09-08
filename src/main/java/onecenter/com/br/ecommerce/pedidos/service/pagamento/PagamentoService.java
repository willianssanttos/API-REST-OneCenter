package onecenter.com.br.ecommerce.pedidos.service.pagamento;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentRefundClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import onecenter.com.br.ecommerce.config.mercadopago.MercadoPagoConfiguracao;
import onecenter.com.br.ecommerce.config.webconfig.ImagemProperties;
import onecenter.com.br.ecommerce.pedidos.dto.request.HistoricoPagamentoNaoAssociadoPedidoRequest;
import onecenter.com.br.ecommerce.pedidos.entity.pagamento.PagamentoEntity;
import onecenter.com.br.ecommerce.pedidos.entity.pedido.ItemPedidoEntity;
import onecenter.com.br.ecommerce.pedidos.entity.pedido.PedidoEntity;
import onecenter.com.br.ecommerce.pedidos.exception.pagamento.AtualizarStatusPagamentoException;
import onecenter.com.br.ecommerce.pedidos.exception.pagamento.CheckoutPagamentoException;
import onecenter.com.br.ecommerce.pedidos.repository.IPedidosRepository;
import onecenter.com.br.ecommerce.pedidos.repository.pagamentos.IPagamentoRepository;
import onecenter.com.br.ecommerce.pedidos.service.email.EmailPagamentoService;
import onecenter.com.br.ecommerce.pessoa.exception.login.AcessoNegadoException;
import onecenter.com.br.ecommerce.pedidos.entity.Enums.StatusPagamento;
import onecenter.com.br.ecommerce.pedidos.entity.Enums.StatusPedido;
import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;
import onecenter.com.br.ecommerce.produto.repository.produtos.IProdutosRepository;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class PagamentoService {

    @Autowired
    private ImagemProperties imagemProperties;
    @Autowired
    private IPedidosRepository iPedidosRepository;
    @Autowired
    private IProdutosRepository iProdutosRepository;
    @Autowired
    private IPagamentoRepository iPagamentoRepository;
    @Autowired
    private EmailPagamentoService emailPagamentoService;
    @Autowired
    private MercadoPagoConfiguracao mercadoPagoConfiguracao;
    @Autowired
    private HistoricoPagamentoNaoAssociadoPedidoService historicoPagamentoNaoAssociadoPedidoService;

    public static final Logger logger = LoggerFactory.getLogger(PagamentoService.class);

    @Transactional
    public String criarPreferenciaPagamento(Integer token, Integer idPedido) {
        logger.info(Constantes.DebugRegistroProcesso);

        try {
            MercadoPagoConfig.setAccessToken(mercadoPagoConfiguracao.getAccessToken());

            String baseUrl = imagemProperties.getBaseUrl();

            PedidoEntity pedido = iPedidosRepository.buscarPedidosPorId(idPedido);

            if(!pedido.getCliente().getIdPessoa().equals(token)){
                logger.warn(Constantes.AcessoNegadoConteudoException, token);
                throw new AcessoNegadoException();
            }

            List<PreferenceItemRequest> items = new ArrayList<>();
            for (ItemPedidoEntity item : pedido.getItens()) {
                ProdutosEntity produto = item.getProdutos();

                PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                        .id(produto.getIdProduto().toString())
                        .title(produto.getNome())
                        .description(produto.getDescricaoProduto())
                        .pictureUrl(baseUrl + "/uploads/" + produto.getProdutoImagem())
                        .categoryId(produto.getNomeCategoria())
                        .quantity(item.getQuantidade())
                        .currencyId("BRL")
                        .unitPrice(item.getPrecoUnitario())
                        .build();

                items.add(itemRequest);
            }

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .notificationUrl(baseUrl + "/webhook/mercadopago")
                    .externalReference(pedido.getIdPedido().toString())
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            return preference.getSandboxInitPoint();
        } catch (Exception e){
            logger.error(Constantes.ErroAoGerarCheckoutDePagamento, e.getMessage());
            throw new CheckoutPagamentoException();
        }
    }

    @Transactional
    public void processarWebhook(Map<String, Object> payload) {
        logger.info(Constantes.NotificacaoRecebida, payload);

        try {
            String topic = (String) payload.get("type");
            if (!"payment".equalsIgnoreCase(topic)) {
                logger.warn(Constantes.WebhookIgnorado, topic);
                return;
            }

            Map<String, Object> data = (Map<String, Object>) payload.get("data");
            if (data == null || !data.containsKey("id")) {
                logger.warn(Constantes.WebhookTipoPagamento);
                return;
            }

            Long pagamentoId = Long.parseLong(data.get("id").toString());
            PaymentClient client = new PaymentClient();
            Payment pagamento = client.get(pagamentoId);

            if (pagamento == null) return;
            String externalRef = pagamento.getExternalReference();
            if (externalRef == null) return;

            Integer pedidoId = Integer.parseInt(externalRef);
            Optional<PedidoEntity> pedidoOpt = Optional.ofNullable(iPedidosRepository.buscarPedidosPorId(pedidoId));

            if (pedidoOpt.isPresent()) {
                PedidoEntity pedido = pedidoOpt.get();
                String status = pagamento.getStatus();

                switch (status) {
                    case "approved" -> pedido.setStatusPedido(StatusPagamento.APPROVED.name());
                    case "rejected" -> pedido.setStatusPedido(StatusPagamento.REJECTED.name());
                    case "in_process" -> pedido.setStatusPedido(StatusPagamento.IN_PROCESS.name());
                    case "refunded", "cancelled" -> {
                        pedido.setStatusPedido(StatusPedido.CANCELADO.name());
                        List<PagamentoEntity> pagamentos = iPagamentoRepository.buscarPagamentoRealizado(pedido.getIdPedido());
                        pagamentos.stream()
                                .filter(p -> p.getIdTransacaoExterna().equals(String.valueOf(pagamentoId)))
                                .findFirst()
                                .ifPresent(p -> iPagamentoRepository.atualizarStatusEstorno(p.getIdPagamento(), StatusPagamento.REFUNDED.name()));
                    }
                    default -> pedido.setStatusPedido(StatusPedido.PROCESSANDO.name());
                }

                iPedidosRepository.atualizarStatusPagamento(pedido.getIdPedido(), pedido.getStatusPedido());

                if("approved".equalsIgnoreCase(status)){
                    boolean pagamentoJaExiste = iPagamentoRepository.existePagamentoPorId(pagamento.getId().toString());
                    if(!pagamentoJaExiste){
                        iPagamentoRepository.salvarPagamento(
                                pagamento.getPaymentMethodId(),
                                status,
                                pagamento.getTransactionAmount(),
                                pagamento.getDateApproved(),
                                pedido.getIdPedido(),
                                pagamento.getId().toString()
                        );

                        emailPagamentoService.enviarAtualizacaoPagamento(
                                pedido.getCliente().getEmail(),
                                "Atualização do seu pedido",
                                pedido.getIdPedido(),
                                pedido.getStatusPedido(),
                                pedido.getDescontoAplicado(),
                                pedido.getValorTotal(),
                                pedido.getCliente().getNomeRazaosocial(),
                                pedido.getCupomDesconto(),
                                pedido.getItens()
                        );
                    } else {
                        logger.warn("Pedido não encontrado para o ID: {}", pedidoId);

                        if("approved".equalsIgnoreCase(pagamento.getStatus())){
                            try {
                                PaymentRefundClient refundClient = new PaymentRefundClient();
                                refundClient.refund(pagamento.getId());

                                historicoPagamentoNaoAssociadoPedidoService.salvarPagamentoNaoAssociado(
                                        HistoricoPagamentoNaoAssociadoPedidoRequest.builder()
                                                .idTransacaoExterna(pagamento.getId().toString())
                                                .formaPagamento(pagamento.getPaymentMethodId())
                                                .statusPagamento("ESTORNADO_AUTOMATICO")
                                                .valorTotal(pagamento.getTransactionAmount())
                                                .dataAprovacao(pagamento.getDateApproved().toLocalDateTime())
                                                .motivo("Pedido não encontrado")
                                                .dataRegistro(pagamento.getDateCreated().toLocalDateTime())
                                                .build()
                                );
                                logger.info("Estorno automático registrado para transação: {}", pagamento.getId());
                            } catch (Exception e){
                                logger.info("Erro ao estornar pagamento órfão: {}", e.getMessage());
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            logger.error(Constantes.ErroNotificacaoRecebida, e.getMessage());
            throw new AtualizarStatusPagamentoException();
        }
    }
}


