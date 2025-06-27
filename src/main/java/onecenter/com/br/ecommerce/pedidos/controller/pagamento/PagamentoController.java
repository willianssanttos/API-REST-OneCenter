package onecenter.com.br.ecommerce.pedidos.controller.pagamento;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import onecenter.com.br.ecommerce.config.security.userdetails.UserDetailsImpl;
import onecenter.com.br.ecommerce.pedidos.service.pagamento.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping("/criar/{idPedido}")
    @PreAuthorize("hasRole('CLIENTE')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<String> criarPagamento(@AuthenticationPrincipal UserDetailsImpl user, @PathVariable Integer idPedido) throws MPException, MPApiException {
        Integer token = user.getLogin().getIdPessoa();
        String urlPagamento = pagamentoService.criarPreferenciaPagamento(token, idPedido);
        return new ResponseEntity<>(urlPagamento, HttpStatus.CREATED);
    }

}
