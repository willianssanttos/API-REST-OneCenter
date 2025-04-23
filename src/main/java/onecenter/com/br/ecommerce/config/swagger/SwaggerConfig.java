package onecenter.com.br.ecommerce.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API E-commerce OneCenter")
                        .version("1.0")
                        .description("Documentação da API do sistema de e-commerce OneCenter"))
                .schemaRequirement("jwt_auth", creaSecurityScheme());
    }

    private SecurityScheme creaSecurityScheme() {
        return new SecurityScheme()
                .name("jwt_auth")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }
}
