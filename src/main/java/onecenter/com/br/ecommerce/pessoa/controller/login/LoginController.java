package onecenter.com.br.ecommerce.pessoa.controller.login;

import onecenter.com.br.ecommerce.pessoa.dto.login.JwtTokenResponse;
import onecenter.com.br.ecommerce.pessoa.dto.login.LoginResponse;
import onecenter.com.br.ecommerce.produto.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/login")
public class LoginController implements ILoginController{

    @Autowired
    private LoginService loginService;

    @PostMapping("/usuario")
    public ResponseEntity<JwtTokenResponse> autenticacaoUsuario(@RequestBody LoginResponse login){
        JwtTokenResponse token = loginService.authenticateUser(login);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
