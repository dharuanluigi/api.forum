package br.com.alura.api.forum.service;

import br.com.alura.api.forum.service.interfaces.IEmailService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.util.Properties;

public class EmailService implements IEmailService {
    @Override
    public void SendActivationCode(String email, String code) {
        try {
            var props = new Properties();
            props.put("mail.smtp.auth", true);
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.mailtrap.io");
            props.put("mail.smtp.port", "2525");
            props.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

            var session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("e258538622ef95", "388cdd70b2fbd0");
                }
            });


            var message = new MimeMessage(session);
            message.setFrom(new InternetAddress("1d6e840eba-246989+1@inbox.mailtrap.io"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("dharuanluigi@gmail.com"));
            message.setSubject("Forum code confirmation");

            var msg = "Your activation code is: ad2112";

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
