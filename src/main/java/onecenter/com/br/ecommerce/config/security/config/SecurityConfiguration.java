package onecenter.com.br.ecommerce.config.security.config;

import onecenter.com.br.ecommerce.config.security.authentication.UserAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    public static final String[] ENDPOINTS_COM_AUTENTICACAO_NAO_OBRIGATORIA = {
            "/v1/login/usuario",
            "/v1/pessoa/fisica/",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-ui/index.html/"
    };

    public static final String[] ENDPOINTS_COM_AUTENTICACAO_NECESSARIA_PARA_LISTAR = {
            "/v1/conta",
            "/v1/conta/obter-contas/{idUsuario}",
            "/v1/transacao",
            "v1/transacao/extrato-transacao/{idUsuario}"
    };

    private static final String[] ENDPOINTS_COM_AUTENTICACAO_NECESSARIO_PARA_CRIAR = {
            "/v1/conta",
            "/v1/conta/criar-conta",
            "/v1/conta/atualizar-conta/{idConta}",
            "/v1/transacao",
            "/v1/transacao/criar-transacao",
    };

    private static final String[] ENDPOINTS_COM_AUTENTICACAO_NECESSARIO_PARA_DELETAR = {
            "/v1/conta",
            "/v1/conta/deletar-conta/{idConta}"
    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(ENDPOINTS_COM_AUTENTICACAO_NAO_OBRIGATORIA).permitAll()
                        .requestMatchers(HttpMethod.GET, ENDPOINTS_COM_AUTENTICACAO_NECESSARIA_PARA_LISTAR).hasAuthority("ROLE_CLIENTE")
                        .requestMatchers(HttpMethod.POST, ENDPOINTS_COM_AUTENTICACAO_NECESSARIO_PARA_CRIAR).hasAuthority("ROLE_CLIENTE")
                        .requestMatchers(HttpMethod.DELETE, ENDPOINTS_COM_AUTENTICACAO_NECESSARIO_PARA_DELETAR).hasAuthority("ROLE_CLIENTE")
                        .anyRequest().authenticated()
                )

                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
