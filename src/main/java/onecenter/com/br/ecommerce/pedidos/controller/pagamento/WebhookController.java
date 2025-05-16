package onecenter.com.br.ecommerce.pedidos.controller.pagamento;

import onecenter.com.br.ecommerce.pedidos.service.pagamento.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping("/mercadopago")
    public ResponseEntity<Void> receberNotificacao(@RequestBody Map<String, Object> playload) {
        pagamentoService.processarWebhook(playload);
        return ResponseEntity.ok().build();
    }
}
