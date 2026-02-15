package com.example.fasterpro11;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaMailAPI {

    public static void sendMail(String recipientEmail, String subject, String messageBody) {
        try {
            // দৈনিক সীমা চেক করুন
            if (!CountEmail.canSendEmail()) {
                System.out.println("Email limit reached for today. No email sent."); // সীমা পৌঁছালে লগ
                return;
            }

            final String username = "abontiangum99@gmail.com"; // আপনার Gmail ঠিকানা
            final String password = "egqnjvccoqtgwaxo"; // আপনার Gmail পাসওয়ার্ড

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

            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(messageBody);

            // Send the email
            Transport.send(message);

            // Increment the email count after successful send
            CountEmail.incrementEmailCount();
            System.out.println("JavaMailAPI Email sent successfully");

        } catch (MessagingException e) {
            // Handle email-specific exceptions
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Handle general exceptions (e.g., network issues, unexpected errors)
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
