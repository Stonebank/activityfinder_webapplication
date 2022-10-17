package com.hk.activityfinder.service;

import com.hk.activityfinder.interfaces.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class Email implements MailService {

    private final Logger logger = LoggerFactory.getLogger(Email.class);

    private final Properties properties = System.getProperties();
    private final Session session = Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("activityjava@gmail.com", "bozlurwwejdbwkpk");
        }
    });

    private final MimeMessage message = new MimeMessage(session);

    public Email() {

    }

    @Override
    public void sendMail(String topic, String content) {
        setProperties();
        setFrom();
        new Thread(() -> {
            try {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress("hassan_99@live.dk"));
                message.setSubject(topic);
                message.setContent(content, "text/html");
                Transport.send(message);
            } catch (MessagingException e) {
                logger.error("ERROR! Mail was not sent.", e);
            }
        }).start();
    }

    private void setProperties() {
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
    }

    private void setFrom() {
        try {
            message.setFrom(new InternetAddress("activityjava@gmail.com"));
        } catch (MessagingException e) {
            logger.error("Error: Could not set from", e);
        }
    }

}
