package com.example.fasterpro11;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaMailAPI_OutgoingSmsObserver_Sender {

    public static void sendMail(String recipientEmail, String subject, String messageBody) {
        Thread thread = new Thread(() -> {
            final String username = "abontiangum99@gmail.com";
            final String password = "egqnjvccoqtgwaxo";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                message.setSubject(subject);
                message.setText(messageBody);

                Transport.send(message);
                System.out.println("JavaMailAPI_OutgoingSmsObserver_Sender Email sent successfully ");
            } catch (MessagingException e) {
                System.err.println("JavaMailAPI_OutgoingSmsObserver_Sender Failed to send email: " + e.getMessage());
                e.printStackTrace();
            }
        });
        thread.start();
    }
}
