package onecenter.com.br.ecommerce.pedidos.service.pagamento;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import onecenter.com.br.ecommerce.config.mercadopago.MercadoPagoConfiguracao;
import onecenter.com.br.ecommerce.config.webconfig.ImagemProperties;
import onecenter.com.br.ecommerce.pedidos.entity.ItemPedidoEntity;
import onecenter.com.br.ecommerce.pedidos.entity.PedidoEntity;
import onecenter.com.br.ecommerce.pedidos.exception.pagamento.AtualizarStatusPagamentoException;
import onecenter.com.br.ecommerce.pedidos.repository.IPedidosRepository;
import onecenter.com.br.ecommerce.pedidos.service.email.EmailService;
import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;
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
    private ImagemProperties imagemProperties;
    @Autowired
    private IPedidosRepository iPedidosRepository;
    @Autowired
    private MercadoPagoConfiguracao mercadoPagoConfiguracao;


    public static final Logger logger = LoggerFactory.getLogger(PagamentoService.class);

    public String criarPreferenciaPagamento(Integer token, Integer idPedido) throws MPException, MPApiException {
        logger.info(Constantes.DebugRegistroProcesso);

        MercadoPagoConfig.setAccessToken(mercadoPagoConfiguracao.getAccessToken());

        String baseUrl = imagemProperties.getBaseUrl();

        PedidoEntity pedido = iPedidosRepository.buscarPedidosPorId(idPedido);

        PreferenceBackUrlsRequest backUrls =
                PreferenceBackUrlsRequest.builder()
                        .success(baseUrl + "/webhook/mercadopago")
                        .pending(baseUrl + "/webhook/mercadopago")
                        .failure(baseUrl + "/webhook/mercadopago")
                        .build();

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
                .backUrls(backUrls)
                .build();
        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);

        return preference.getSandboxInitPoint();
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


