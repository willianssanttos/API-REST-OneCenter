package onecenter.com.br.ecommerce.pedidos.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import onecenter.com.br.ecommerce.pedidos.dto.request.PedidoRequest;
import onecenter.com.br.ecommerce.pedidos.dto.response.PedidoResponse;
import onecenter.com.br.ecommerce.pedidos.service.PedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/pedido")
public class PedidosController implements IPedidosController{

    @Autowired
    private PedidosService pedidosService;

    @PostMapping("/")
    @PreAuthorize("hasRole('CLIENTE')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<PedidoResponse> pedidoCriado(@RequestBody @Valid PedidoRequest pedido){
        return new ResponseEntity<>(pedidosService.criarPedidos(pedido), HttpStatus.CREATED);
    }

    @GetMapping("/localizar")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('CLIENTE')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<List<PedidoResponse>> localizarPedido(){
        List<PedidoResponse> response = pedidosService.obterTodosPedidos();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
