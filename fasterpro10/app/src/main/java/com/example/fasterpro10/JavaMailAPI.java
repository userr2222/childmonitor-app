package com.example.fasterpro10;

import android.util.Log;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailAPI {

    private static final String TAG = "JavaMailAPI";

    public static void sendMail(String recipient, String subject, String messageBody) throws MessagingException {
        final String username = "babulahmed000015@gmail.com";
        final String password = "ncozjamyddqjiaba";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(messageBody);

            Transport.send(message);
            Log.d(TAG, "Email sent successfully to " + recipient);
        } catch (MessagingException e) {
            Log.e(TAG, "Failed to send email", e);
            throw e;
        }
    }
}
