package onecenter.com.br.ecommerce.config.webconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.imagem")
public class ImagemProperties {

    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl){
        this.baseUrl = baseUrl;
    }
}
