package onecenter.com.br.ecommerce.config.security.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    private static final String ROTAS_JSON_PATH = "src/main/resources/rotas.json";

    public static final String[] PERMISSAO_SEM_AUTORIZACAO = {
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-ui/index.html/",
            "/webjars/**",
            "/uploads/**",
            "/v1/produtos/{id}"
    };
    public static Map<String, List<String>> rotasMap = new HashMap<>();

    static {
        carregarRotas();
    }

    private static void carregarRotas(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Map<String, Object>> rotas = objectMapper.readValue(Paths.get(ROTAS_JSON_PATH).toFile(), new TypeReference<>() {
            });
            for (Map<String, Object> rota : rotas) {
                String path = (String) rota.get("path");
                List<String> roles = (List<String>) rota.get("roles");
                rotasMap.put(path, roles);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar o arquivo de rotas: " + e.getMessage());
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> {
                authorize.requestMatchers(PERMISSAO_SEM_AUTORIZACAO).permitAll();

                for (Map.Entry<String, List<String>> entry : rotasMap.entrySet()){
                    String path = entry.getKey();
                    List<String> roles = entry.getValue();
                    if (roles.isEmpty()) {
                        authorize.requestMatchers(path).permitAll();
                    } else {
                        authorize.requestMatchers(HttpMethod.GET, path).hasAnyAuthority(roles.toArray(new String[0]));
                        authorize.requestMatchers(HttpMethod.POST, path).hasAnyAuthority(roles.toArray(new String[0]));
                        authorize.requestMatchers(HttpMethod.DELETE, path).hasAnyAuthority(roles.toArray(new String[0]));
                    }
                }
                authorize.anyRequest().authenticated();
            })
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

