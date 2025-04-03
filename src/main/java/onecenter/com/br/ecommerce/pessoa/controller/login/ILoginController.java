package onecenter.com.br.ecommerce.pessoa.controller.login;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import onecenter.com.br.ecommerce.pessoa.dto.login.JwtTokenResponse;
import onecenter.com.br.ecommerce.pessoa.dto.login.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface ILoginController {

    @Operation(summary = "Realizar o login de usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso, copie o seu token!"),
            @ApiResponse(responseCode = "400", description = "Erro ao realizar o login, senha ou email incorretos!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao recuperar informaçoes de login!")
    })
    ResponseEntity<JwtTokenResponse> autenticacaoUsuario(@RequestBody LoginResponse login);
}
