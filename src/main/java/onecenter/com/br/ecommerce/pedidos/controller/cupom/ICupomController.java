package onecenter.com.br.ecommerce.pedidos.controller.cupom;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import onecenter.com.br.ecommerce.pedidos.dto.request.CupomRequest;
import onecenter.com.br.ecommerce.pedidos.dto.response.CupomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface ICupomController {

    @Operation(summary = "Cadastrar Cupom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro do cupom realizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao realizar o cadastro!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao realizar cadastro!")
    })
    ResponseEntity<CupomResponse> cadastrarCupom(@RequestBody CupomRequest cupom);
}
