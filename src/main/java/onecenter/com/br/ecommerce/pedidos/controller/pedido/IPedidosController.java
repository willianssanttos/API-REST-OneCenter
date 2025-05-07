package onecenter.com.br.ecommerce.pedidos.controller.pedido;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import onecenter.com.br.ecommerce.pedidos.dto.request.PedidoRequest;
import onecenter.com.br.ecommerce.pedidos.dto.response.PedidoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IPedidosController {

    @Operation(summary = "Cadastro de pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao realizar o cadastro!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao realizar cadastro!")
    })
    ResponseEntity<PedidoResponse> pedidoCriado(@RequestBody PedidoRequest pedido);

    @Operation(summary = "Listagem de pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao listar pedidos!")
    })
    ResponseEntity<List<PedidoResponse>> localizarPedido();
}
