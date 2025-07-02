package onecenter.com.br.ecommerce.config.mercadopago;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mercadopago")
public class MercadoPagoConfiguracao {

    @Value("${mercadopago.access-token}")
    private String accessToken;

    public String getAccessToken(){
        return accessToken;
    }

}
