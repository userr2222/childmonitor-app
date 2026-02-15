package com.example.fasterpro11;

import android.graphics.Bitmap;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

public class JavaMailAPISendNotification {

    public static void sendMail(String recipientEmail, String subject, String messageBody, Bitmap image) throws MessagingException {
        Thread thread = new Thread(() -> {
            final String username = "abontiangum99@gmail.com"; // Replace with your Gmail address
            final String password = "egqnjvccoqtgwaxo"; // Replace with your Gmail password

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

                // Create multipart message
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setText(messageBody);

                MimeMultipart multipart = new MimeMultipart();
                multipart.addBodyPart(textPart);

                // Add image attachment
                if (image != null) {
                    MimeBodyPart imagePart = new MimeBodyPart();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    ByteArrayDataSource dataSource = new ByteArrayDataSource(byteArray, "image/png");

                    imagePart.setDataHandler(new DataHandler(dataSource));
                    imagePart.setFileName("image.png");

                    multipart.addBodyPart(imagePart);
                }

                message.setContent(multipart);

                Transport.send(message);
                System.out.println("JavaMailAPISendNotification Email sent successfully ");
            } catch (MessagingException e) {
                System.err.println("JavaMailAPISendNotification Failed to send email : " + e.getMessage());
                throw new RuntimeException(e); // Rethrow the exception to handle it properly
            }
        });
        thread.start();
    }
}
