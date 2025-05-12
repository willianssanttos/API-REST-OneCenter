package onecenter.com.br.ecommerce.config.security.authentication;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import onecenter.com.br.ecommerce.config.security.config.SecurityConfiguration;
import onecenter.com.br.ecommerce.config.security.userdetails.UserDetailsImpl;
import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import onecenter.com.br.ecommerce.pessoa.exception.login.TokenException;
import onecenter.com.br.ecommerce.pessoa.repository.pessoas.IPessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private IPessoaRepository iPessoaRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractToken(request);
        String path = request.getRequestURI();

        if (isPublicRoute(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (token != null) {
                DecodedJWT decodedJWT = jwtTokenService.decodeToken(token);
                String email = decodedJWT.getSubject();

                PessoaEntity usuario = iPessoaRepository.obterLogin(email);
                UserDetailsImpl userDetails = new UserDetailsImpl(usuario);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                throw new TokenException();
            }

            filterChain.doFilter(request, response);

        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"erro\": \"" + ex.getMessage() + "\"}");
        }
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.replace("Bearer ", "");
        }
        return null;
    }


    private boolean isPublicRoute(String requestPath) {
        return SecurityConfiguration.rotasMap.containsKey(requestPath) &&
                SecurityConfiguration.rotasMap.get(requestPath).isEmpty() ||
                isSwaggerRoute(requestPath);
    }

    private boolean isSwaggerRoute(String requestPath) {
        return requestPath.startsWith("/swagger-ui") ||
                requestPath.startsWith("/swagger-resources") ||
                requestPath.startsWith("/v3/api-docs") ||
                requestPath.startsWith("/uploads/");
    }
}
