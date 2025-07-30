package onecenter.com.br.ecommerce.pedidos.controller.pedido;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import onecenter.com.br.ecommerce.config.security.userdetails.UserDetailsImpl;
import onecenter.com.br.ecommerce.pedidos.dto.request.PedidoRequest;
import onecenter.com.br.ecommerce.pedidos.dto.response.PedidoResponse;
import onecenter.com.br.ecommerce.pedidos.service.pedido.PedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/pedido")
public class PedidosController implements IPedidosController {

    @Autowired
    private PedidosService pedidosService;

    @PostMapping("/")
    @PreAuthorize("hasRole('CLIENTE')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<PedidoResponse> pedidoCriado(@AuthenticationPrincipal UserDetailsImpl user,
                                                       @RequestBody @Valid PedidoRequest pedido) {
        Integer token = user.getLogin().getIdPessoa();
        return new ResponseEntity<>(pedidosService.criarPedidos(pedido, token), HttpStatus.CREATED);
    }

    @GetMapping("/localizar-pedido-cliente")
    @PreAuthorize("hasRole('CLIENTE')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<List<PedidoResponse>> localizarPedidoCliente(@AuthenticationPrincipal UserDetailsImpl user){
        Integer token = user.getLogin().getIdPessoa();
        List<PedidoResponse> response = pedidosService.obterPedidosDoCliente(token);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/localizar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<List<PedidoResponse>> localizarPedido(@AuthenticationPrincipal UserDetailsImpl user){
        List<PedidoResponse> response = pedidosService.obterTodosPedidos();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/cancelar/{idPedido}")
    @PreAuthorize("hasRole('CLIENTE')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Void> cancelarPedido(@AuthenticationPrincipal UserDetailsImpl user, @PathVariable Integer idPedido) {
        Integer token = user.getLogin().getIdPessoa();
        pedidosService.cancelarPedido(idPedido, token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
