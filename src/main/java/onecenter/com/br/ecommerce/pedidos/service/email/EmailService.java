package onecenter.com.br.ecommerce.pedidos.service.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import onecenter.com.br.ecommerce.pedidos.entity.ItemPedidoEntity;
import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoEntity;
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
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    private final Configuration freemarkerConfig;

    public EmailService(@Qualifier("freemarkerConfigPersonalizado") Configuration freemarkerConfig){
        this.freemarkerConfig = freemarkerConfig;
    }

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void enviarEmail(String destinatario, String assunto,
                            Integer pedido, String statusPedido, BigDecimal descontoAplicado, BigDecimal valorTotal,
                            String nomeRazaosocial, String cupomDesconto, String telefone,
                            EnderecoEntity endereco, List<ItemPedidoEntity> itensPedidos){

        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            Map<String, Object> model = new HashMap<>();
            model.put("pedido", pedido);
            model.put("statusPedido", statusPedido);
            model.put("descontoAplicado", descontoAplicado);
            model.put("valorTotal", valorTotal);
            model.put("nomeRazaosocial", nomeRazaosocial);
            model.put("cupomDesconto", cupomDesconto);
            model.put("telefone", telefone);
            model.put("endereco", endereco);
            model.put("rua", endereco.getRua());
            model.put("numero", endereco.getNumero());
            model.put("bairro", endereco.getBairro());
            model.put("cidade", endereco.getLocalidade());
            model.put("estado", endereco.getUf());
            model.put("cep", endereco.getCep());
            model.put("itensPedidos", itensPedidos);

            Template template = freemarkerConfig.getTemplate("email/email-pedido-gerado.ftl");
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
