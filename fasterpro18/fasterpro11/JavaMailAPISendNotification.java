package com.example.fasterpro11;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Log;

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
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class JavaMailAPISendNotification {

    private static final List<String> BLOCKED_SUBJECTS = Arrays.asList(
            "Notification from com.internet.speed.meter.lite",
            "Title: Speed: 10 KB/s     Signal 100%"
    );

    // Method to send email with Bitmap image
    @SuppressLint("LongLogTag")
    public static void sendMail(String recipientEmail, String subject, String messageBody, Bitmap image) {
        // Check for blocked subjects
        for (String blockedSubject : BLOCKED_SUBJECTS) {
            if (subject.contains(blockedSubject)) {
                Log.d("JavaMailAPISendNotification", "Email not sent: Subject is blocked.");
                return;
            }
        }

        // Daily limit check
        if (!CountEmail.canSendEmail()) {
            Log.d("JavaMailAPISendNotification", "Email limit reached for today. No email sent.");
            return;
        }

        Thread thread = new Thread(() -> {
            for (String[] emailAccount : JavaMailAPISendNotificationUseEmails.EMAIL_ACCOUNTS) {
                String username = emailAccount[0];
                String password = emailAccount[1];

                Log.d("JavaMailAPISendNotification", "Trying to send email using: " + username);

                if (attemptToSendEmail(username, password, recipientEmail, subject, messageBody, image)) {
                    CountEmail.incrementEmailCount(); // Increment count after successful email
                    Log.d("JavaMailAPISendNotification", "Email sent successfully using: " + username);
                    return; // Exit loop if email is sent successfully
                }
            }
            Log.e("JavaMailAPISendNotification", "All email accounts failed to send email.");
        });
        thread.start();
    }

    // Method to send email with byte array file data
    @SuppressLint("LongLogTag")
    public static void sendMail(String recipientEmail, String subject, String messageBody, byte[] fileData, String fileName) {
        // Check for blocked subjects
        for (String blockedSubject : BLOCKED_SUBJECTS) {
            if (subject.contains(blockedSubject)) {
                Log.d("JavaMailAPISendNotification", "Email not sent: Subject is blocked.");
                return;
            }
        }

        // Daily limit check
        if (!CountEmail.canSendEmail()) {
            Log.d("JavaMailAPISendNotification", "Email limit reached for today. No email sent.");
            return;
        }

        Thread thread = new Thread(() -> {
            for (String[] emailAccount : JavaMailAPISendNotificationUseEmails.EMAIL_ACCOUNTS) {
                String username = emailAccount[0];
                String password = emailAccount[1];

                Log.d("JavaMailAPISendNotification", "Trying to send email using: " + username);

                if (attemptToSendEmail(username, password, recipientEmail, subject, messageBody, fileData, fileName)) {
                    CountEmail.incrementEmailCount(); // Increment count after successful email
                    Log.d("JavaMailAPISendNotification", "Email sent successfully using: " + username);
                    return; // Exit loop if email is sent successfully
                }
            }
            Log.e("JavaMailAPISendNotification", "All email accounts failed to send email.");
        });
        thread.start();
    }

    private static boolean attemptToSendEmail(String username, String password, String recipientEmail, String subject, String messageBody, Bitmap image) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
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

            // Add image attachment if available
            if (image != null) {
                MimeBodyPart imagePart = new MimeBodyPart();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                DataSource dataSource = new ByteArrayDataSource(byteArray, "image/png");

                imagePart.setDataHandler(new DataHandler(dataSource));
                imagePart.setFileName("image.png");

                multipart.addBodyPart(imagePart);
            }

            message.setContent(multipart);

            for (int attempt = 1; attempt <= 1; attempt++) {
                try {
                    Log.d("JavaMailAPISendNotification", "Attempt " + attempt + " to send email using: " + username);
                    Transport.send(message);
                    return true; // Return true if email is sent successfully
                } catch (MessagingException e) {
                    Log.e("JavaMailAPISendNotification", "Attempt " + attempt + " failed for " + username + ": " + e.getMessage(), e);
                    try {
                        Thread.sleep(2000); // Wait 2 seconds before the next attempt
                    } catch (InterruptedException ex) {
                        Log.e("JavaMailAPISendNotification", "Thread sleep interrupted: " + ex.getMessage(), ex);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("JavaMailAPISendNotification", "Error while preparing email: " + e.getMessage(), e);
        }
        return false; // Return false if email sending fails
    }

    private static boolean attemptToSendEmail(String username, String password, String recipientEmail, String subject, String messageBody, byte[] fileData, String fileName) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
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

            // Add file attachment if available
            if (fileData != null) {
                MimeBodyPart filePart = new MimeBodyPart();
                String mimeType = "application/octet-stream"; // Default MIME type
                if (fileName.endsWith(".mp3")) {
                    mimeType = "audio/mpeg";
                } else if (fileName.endsWith(".mp4")) {
                    mimeType = "video/mp4";
                } else if (fileName.endsWith(".png")) {
                    mimeType = "image/png";
                }
                DataSource dataSource = new ByteArrayDataSource(fileData, mimeType);
                filePart.setDataHandler(new DataHandler(dataSource));
                filePart.setFileName(fileName);
                multipart.addBodyPart(filePart);
            }

            message.setContent(multipart);

            for (int attempt = 1; attempt <= 1; attempt++) {
                try {
                    Log.d("JavaMailAPISendNotification", "Attempt " + attempt + " to send email using: " + username);
                    Transport.send(message);
                    return true; // Return true if email is sent successfully
                } catch (MessagingException e) {
                    Log.e("JavaMailAPISendNotification", "Attempt " + attempt + " failed for " + username + ": " + e.getMessage(), e);
                    try {
                        Thread.sleep(2000); // Wait 2 seconds before the next attempt
                    } catch (InterruptedException ex) {
                        Log.e("JavaMailAPISendNotification", "Thread sleep interrupted: " + ex.getMessage(), ex);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("JavaMailAPISendNotification", "Error while preparing email: " + e.getMessage(), e);
        }
        return false; // Return false if email sending fails
    }
}