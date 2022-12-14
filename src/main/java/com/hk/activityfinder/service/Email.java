package com.hk.activityfinder.service;

import com.hk.activityfinder.interfaces.MailService;
import com.hk.activityfinder.utility.BackgroundThread;
import com.hk.settings.Constant;
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

    @Override
    public void sendMail(String recipient, String topic, String content) {
        if (!Constant.DEBUG) {
            BackgroundThread.EMAIL_EXECUTOR.execute(() -> {
                try {
                    setProperties();
                    setFrom();
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
                    message.setSubject(topic);
                    message.setContent(content, "text/html");
                    Transport.send(message);
                    logger.info("Email was sent with the topic " + topic);
                } catch (MessagingException e) {
                    logger.error("ERROR! Mail was not sent.", e);
                }
            });
        }
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
