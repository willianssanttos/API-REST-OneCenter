package onecenter.com.br.ecommerce.pedidos.service.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import onecenter.com.br.ecommerce.pedidos.entity.ItemPedidoEntity;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailPagamentoService {

    @Autowired
    private JavaMailSender emailSender;

    private final Configuration freemarkerConfig;

    public EmailPagamentoService(@Qualifier("freemarkerConfigPersonalizado") Configuration freemarkerConfig){
        this.freemarkerConfig = freemarkerConfig;
    }

    private static final Logger logger = LoggerFactory.getLogger(EmailPagamentoService.class);

    public void enviarAtualizacaoPagamento(String destinatario, String assunto,
                            Integer pedido, String statusPedido, BigDecimal valorTotal,
                            String nomeRazaosocial, List<ItemPedidoEntity> itensPedidos){

        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            Map<String, Object> model = new HashMap<>();
            model.put("pedido", pedido);
            model.put("statusPedido", statusPedido);
            model.put("valorTotal", valorTotal);
            model.put("nomeRazaosocial", nomeRazaosocial);
            model.put("itensPedidos", itensPedidos);

            String templateNome = switch (statusPedido.toUpperCase()) {
                case "APROVADO" -> "email/email-pagamento-aprovado.ftl";
                case "REJEITADO" -> "email/email-pagamento-rejeitado.ftl";
                case "PROCESSANDO" -> "email/email-pagamento-processando.ftl";
                default -> "email/email-pedido-gerado.ftl";
            };

            Template template = freemarkerConfig.getTemplate(templateNome);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            helper.setTo(destinatario);
            helper.setSubject(assunto);
            helper.setText(html, true);

            emailSender.send(mimeMessage);

        } catch (MessagingException | IOException | TemplateException e) {
            logger.error(Constantes.ERRO_ENVIAR_EMAIL, e);
        }
    }
}
