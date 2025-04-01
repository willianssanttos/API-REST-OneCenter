package onecenter.com.br.ecommerce.config.security.authentication;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import onecenter.com.br.ecommerce.config.exception.entity.ApiError;
import onecenter.com.br.ecommerce.config.security.config.SecurityConfiguration;
import onecenter.com.br.ecommerce.config.security.userdetails.UserDetailsImpl;
import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import onecenter.com.br.ecommerce.pessoa.exception.pessoas.TokenException;
import onecenter.com.br.ecommerce.pessoa.repository.pessoas.IPessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private IPessoaRepository iPessoaRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if (!checkPathExistence(request)) {
//            response.sendError(HttpStatus.NOT_FOUND.value());
//            return;
//        }

        String requestPath = request.getRequestURI();

        if (isPublicRoute(requestPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = recoveryToken(request);

            if (token != null) {
                String subject = jwtTokenService.getSubjectFromToken(token);
                PessoaEntity login = iPessoaRepository.obterLogin(subject);
                UserDetailsImpl userDetails = new UserDetailsImpl(login);

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new TokenException();
            }

            filterChain.doFilter(request, response);
        } catch (TokenException ex) {
            buidErrorResponse(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.name(), ex, response);
        } catch (JWTCreationException ex) {
            buidErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex, response);
        } catch (JWTVerificationException ex) {
            buidErrorResponse(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(), ex, response);
        }
    }

//    private boolean checkPathExistence(HttpServletRequest request) {
//        String servletPath = request.getServletPath();
//        if (servletPath.contains("/swagger") || servletPath.contains("/v3/api-docs/**")) {
//            return true;
//        }
//        return SecurityConfiguration.rotasMap.containsKey(servletPath);
//    }
    private boolean isPublicRoute(String requestPath) {
        return SecurityConfiguration.rotasMap.containsKey(requestPath) &&
                SecurityConfiguration.rotasMap.get(requestPath).isEmpty();
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    private void buidErrorResponse(Integer codeError, String statusError, Exception ex, HttpServletResponse response) throws IOException {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .code(codeError)
                .status(statusError)
                .errors(List.of(ex.getMessage()))
                .build();
        response.setStatus(codeError);
        response.setContentType("application/json");
        response.getWriter().write(convertObjToJson(apiError));
    }

    private String convertObjToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper.writeValueAsString(object);
    }
}
