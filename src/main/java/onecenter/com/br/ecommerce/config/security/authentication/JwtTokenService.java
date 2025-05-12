package onecenter.com.br.ecommerce.config.security.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import onecenter.com.br.ecommerce.config.security.userdetails.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtTokenService {

    @Value("${spring.application.projeto.jwtSecret}")
    private String jwtSecret;

    private static final String EMISSOR = "OneCenter";

    public String generateJwtToken(UserDetailsImpl userDetails) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

            return JWT.create()
                    .withIssuer(EMISSOR)
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(Instant.now().plus(4, ChronoUnit.HOURS))
                    .withSubject(userDetails.getUsername()) // email
                    .withClaim("idPessoa", userDetails.getLogin().getIdPessoa())
                    .withClaim("vip", userDetails.getLogin().isVip())
                    .withClaim("rolePrincipal", userDetails.getLogin().getRolePrincipal())
                    .withClaim("roles", userDetails.getLogin().getRoles().stream()
                            .map(role -> role.getNomeRole().name()).toList())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Erro ao gerar token", exception);
        }
    }

    public DecodedJWT decodeToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.require(algorithm)
                    .withIssuer(EMISSOR)
                    .build()
                    .verify(token);
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token inválido ou expirado");
        }
    }

}
