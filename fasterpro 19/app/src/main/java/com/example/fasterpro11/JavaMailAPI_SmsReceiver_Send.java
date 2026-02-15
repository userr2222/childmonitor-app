package com.example.fasterpro11;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import android.util.Log;

public class JavaMailAPI_SmsReceiver_Send {
    private static final String TAG = "JavaMailAPI_SmsReceiver_Send";

    public static void sendMail(String recipientEmail, String subject, String messageBody) {
        // দৈনিক সীমা চেক করুন
        if (!CountEmail.canSendEmail()) {
            Log.d(TAG, "Email limit reached for today. No email sent.");
            return; // ইমেল পাঠানো হবে না
        }

        Thread thread = new Thread(() -> {
            final String username = "abontiangum99@gmail.com"; // আপনার ইমেইল
            final String password = "egqnjvccoqtgwaxo"; // আপনার ইমেইল পাসওয়ার্ড

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            Log.d(TAG, "Authenticating with username: " + username);
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {
                Log.d(TAG, "Creating email message...");
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                message.setSubject(subject);
                message.setText(messageBody);

                Log.d(TAG, "Email message created successfully. Attempting to send...");
                Transport.send(message);

                CountEmail.incrementEmailCount(); // ইমেল সফলভাবে পাঠানোর পর কাউন্টার বৃদ্ধি
                Log.d(TAG, "Email sent successfully to: " + recipientEmail);
            } catch (MessagingException e) {
                Log.e(TAG, "Failed to send email: " + e.getMessage(), e);
                e.printStackTrace();
            }
        });
        thread.start();
    }
}
