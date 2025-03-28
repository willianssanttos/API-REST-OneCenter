package onecenter.com.br.ecommerce.pessoa.service.login;

import onecenter.com.br.ecommerce.config.security.authentication.JwtTokenService;
import onecenter.com.br.ecommerce.config.security.userdetails.UserDetailsImpl;
import onecenter.com.br.ecommerce.pessoa.dto.login.JwtTokenResponse;
import onecenter.com.br.ecommerce.pessoa.dto.login.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    public JwtTokenResponse authenticateUser(LoginResponse loginUserDto) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.getEmailUsuario(), loginUserDto.getSenhaUsuario());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new JwtTokenResponse(jwtTokenService.generateJwtToken(userDetails));
    }
}
