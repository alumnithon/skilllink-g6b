package alumnithon.skilllink.domain.userprofile.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class SendMailService {

    private final JavaMailSender mailSender;
    private final String frontendBaseUrl;
    private final String appName;

    public SendMailService(JavaMailSender mailSender,
            @Value("${frontend.redirect.url}") String frontendBaseUrl,
            @Value("${spring.application.name}") String appName) {
        this.mailSender = mailSender;
        this.frontendBaseUrl = frontendBaseUrl;
        this.appName = appName;
    }
    public void sendVerificationEmail(String toEmail, String token) {
        String verificationUrl = frontendBaseUrl + "/register/confirm?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Confirma tu cuenta");
        message.setText(buildEmailContent(verificationUrl));
        mailSender.send(message);
    }

    private String buildEmailContent(String verificationUrl) {
        return "Gracias por registrarte en "+ appName +". Haz clic aquí para activar tu cuenta:\n\n" +
                verificationUrl + "\n\n" +
                "Este enlace expirará en 24 horas.\n\n" +
                "Si no solicitaste este registro, por favor ignora este mensaje.";
    }
}
