package onecenter.com.br.ecommerce.pedidos.service.pagamento;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import onecenter.com.br.ecommerce.config.mercadopago.MercadoPagoConfiguracao;
import onecenter.com.br.ecommerce.pedidos.entity.ItemPedidoEntity;
import onecenter.com.br.ecommerce.pedidos.entity.PedidoEntity;
import onecenter.com.br.ecommerce.pedidos.exception.pagamento.AtualizarStatusPagamentoException;
import onecenter.com.br.ecommerce.pedidos.exception.pagamento.PreferenciaPagamentoException;
import onecenter.com.br.ecommerce.pedidos.repository.IPedidosRepository;
import onecenter.com.br.ecommerce.pedidos.service.email.EmailService;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private IPedidosRepository iPedidosRepository;

    public static final Logger logger = LoggerFactory.getLogger(PagamentoService.class);

    public String criarPreferenciaPagamento(Integer token, Integer idPedido) {
        logger.info(Constantes.DebugRegistroProcesso);

        MercadoPagoConfig.setAccessToken("TEST-1640815376925393-012907-1c8105c8165ad989e4ec9bf7b9c7c9ef-211994072");

        try {
             PedidoEntity pedido = iPedidosRepository.buscarPedidosPorId(idPedido);

            List<PreferenceItemRequest> items = new ArrayList<>();
            for (ItemPedidoEntity item : pedido.getItens()) {
                PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                        .id(pedido.getIdPedido().toString())
                        .title(item.getProdutos().getNome())
                        .quantity(item.getQuantidade())
                        .unitPrice(item.getPrecoUnitario())
                        .build();
                items.add(itemRequest);
            }

            PreferencePayerRequest pagamento = PreferencePayerRequest.builder()
                    .email(pedido.getCliente().getEmail())
                    .build();

            PreferenceRequest preference = PreferenceRequest.builder()
                    .items(items)
                    .payer(pagamento)
                    .backUrls(PreferenceBackUrlsRequest.builder()
                            .success("http://localhost:8080/webhook/mercadopago")
                            .failure("http://localhost:8080/webhook/mercadopago")
                            .pending("http://localhost:8080/webhook/mercadopago")
                            .build())
                    .autoReturn("approved")
                    .build();

            PreferenceClient cliente = new PreferenceClient();
            Preference preferencia = cliente.create(preference);

            logger.info(Constantes.InfoRegistrar, preferencia);
            return preferencia.getInitPoint();

        } catch (Exception e) {
            logger.error(Constantes.ErroRegistrarNoServidor, e);
            throw new PreferenciaPagamentoException();
        }
    }

    public void processarWebhook(Map<String, Object> playload) {
        logger.info(Constantes.DebugRegistroProcesso);
        try{
            String id = String.valueOf(playload.get("data")).split("=")[1].replace("}","");
            PaymentClient client = new PaymentClient();
            Payment pagamento = client.get(Long.parseLong(id));

            if (pagamento == null) return;

            String status = pagamento.getStatus();
            Long pedidoId = pagamento.getOrder().getId();

            Optional<PedidoEntity> pedidoOpt = Optional.ofNullable(iPedidosRepository.buscarPedidosPorId(pedidoId.intValue()));

            if (pedidoOpt.isPresent()) {
                PedidoEntity pedido = pedidoOpt.get();

                switch (status) {
                    case "approved":
                        pedido.setStatusPedido("APROVADO");
                        break;
                    case "rejected":
                        pedido.setStatusPedido("REJEITADO");
                        break;
                    case "in_process":
                        pedido.setStatusPedido("PROCESSANDO");
                        break;
                    default:
                        pedido.setStatusPedido("PENDENTE");
                }
                logger.info(Constantes.InfoRegistrar, pedido);
                iPedidosRepository.atualizarStatusPagamento(pedido.getIdPedido(), pedido.getStatusPedido());

                emailService.enviarEmail(
                        pedido.getCliente().getEmail(),
                        "Atualização do seu pedido",
                        pedido.getIdPedido(),
                        pedido.getStatusPedido(),
                        pedido.getDescontoAplicado(),
                        pedido.getValorTotal(),
                        pedido.getCliente().getNomeRazaosocial(),
                        pedido.getCupomDesconto(),
                        pedido.getCliente().getTelefone(),
                        pedido.getCliente().getEndereco(),
                        pedido.getItens()
                );
            }
        } catch (Exception e){
            logger.error(Constantes.ErroRegistrarNoServidor, e);
            throw new AtualizarStatusPagamentoException();
        }
    }

}


