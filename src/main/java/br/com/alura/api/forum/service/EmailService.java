package br.com.alura.api.forum.service;

import br.com.alura.api.forum.service.interfaces.IEmailService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService implements IEmailService {

    @Value("${api.forum.email.access.username}")
    private String username;

    @Value("${api.forum.email.access.password}")
    private String password;

    @Value("${api.forum.application.email}")
    private String applicationEmail;

    @Override
    public void sendActivationCode(String email, String code) {
        try {
            var props = new Properties();
            props.put("mail.smtp.auth", true);
            props.put("mail.smtp.starttls.enableAccount", true);
            props.put("mail.smtp.host", "smtp.mailtrap.io");
            props.put("mail.smtp.port", "2525");
            props.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

            var session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });


            var message = new MimeMessage(session);
            message.setFrom(new InternetAddress(applicationEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Forum code confirmation");

            var msg = "Please, don't share this code with anyone! This code will be expired in " + 5 + " minutes! <br/><br/>Your account activation code is: " + code;

            var mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

            var multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
