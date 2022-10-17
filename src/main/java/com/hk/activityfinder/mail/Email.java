package com.hk.activityfinder.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {

    private static final Logger logger = LoggerFactory.getLogger(Email.class);

    private static final Properties properties = System.getProperties();
    private static final Session session = Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("activityjava@gmail.com", "bozlurwwejdbwkpk");
        }
    });

    private static final MimeMessage message = new MimeMessage(session);

    static {
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", usingGmail() ? "smtp.gmail.com" : "smtp-mail.outlook.com");
        properties.put("mail.smtp.port", "587");
    }

    static {
        try {
            message.setFrom(new InternetAddress("activityjava@gmail.com"));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void mail(String topic, String content) {
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

    private static boolean usingGmail() {
        return true;
    }

}
