package onecenter.com.br.ecommerce.pedidos.controller;

import onecenter.com.br.ecommerce.pedidos.dto.request.PedidoRequest;
import onecenter.com.br.ecommerce.pedidos.dto.response.PedidoResponse;
import onecenter.com.br.ecommerce.pedidos.service.PedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/pedido")
public class PedidosController {

    @Autowired
    private PedidosService pedidosService;

    @PostMapping("/")
    public ResponseEntity<PedidoResponse> pedidoCriado(@RequestBody PedidoRequest pedido){
        return new ResponseEntity<>(pedidosService.criarPedidos(pedido), HttpStatus.CREATED);
    }
}
