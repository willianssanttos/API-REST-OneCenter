package onecenter.com.br.ecommerce.config.mercadopago;

import com.mercadopago.MercadoPagoConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MercadoPagoConfiguracao {

    @PostConstruct
    public void init(){
        MercadoPagoConfig.setAccessToken("TEST-1640815376925393-012907-1c8105c8165ad989e4ec9bf7b9c7c9ef-211994072");
    }
}
