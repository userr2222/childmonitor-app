package com.example.fasterpro11;

import android.os.AsyncTask; // AsyncTask ব্যবহার
import android.util.Log; // লগ ব্যবহারের জন্য

import java.io.File; // ফাইল ব্যবহারের জন্য
import java.util.Properties; // প্রপার্টি সেটিংস
import javax.mail.*; // মেইল ব্যবহারের জন্য
import javax.mail.internet.*; // ইন্টারনেট মেইল
import javax.activation.*; // অ্যাকটিভেশন

public class JavaMailAPI_VideoRecord_Sender {
    private static final String TAG = "JavaMailAPI"; // লগ ট্যাগ
    private String email; // প্রাপক ইমেইল
    private String subject; // ইমেইল সাবজেক্ট
    private String message; // ইমেইল মেসেজ
    private String filePath; // ফাইলের পাথ

    // কনস্ট্রাক্টর
    public JavaMailAPI_VideoRecord_Sender(String email, String subject, String message, String filePath) {
        this.email = email; // প্রাপক ইমেইল সেট করা
        this.subject = subject; // সাবজেক্ট সেট করা
        this.message = message; // মেসেজ সেট করা
        this.filePath = filePath; // ফাইল পাথ সেট করা
    }

    // মেইল পাঠানোর মেথড
    public void sendMail() {
        new SendMailTask().execute(); // AsyncTask শুরু
    }

    private class SendMailTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            // ইমেল পাঠানোর অনুমতি চেক করুন
            if (!CountEmail.canSendEmail()) {
                Log.d(TAG, "Email limit reached for today. No email sent."); // সীমা পৌঁছালে লগ
                return null; // মেইল পাঠানো হবে না
            }

            // SMTP সেটিংস
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP সার্ভার
            props.put("mail.smtp.port", "587"); // SMTP পোর্ট
            props.put("mail.smtp.auth", "true"); // অথেনটিকেশন
            props.put("mail.smtp.starttls.enable", "true"); // TLS সক্রিয় করা

            final String username = "abontiangum99@gmail.com"; // আপনার Gmail ঠিকানা
            final String password = "egqnjvccoqtgwaxo"; // আপনার Gmail পাসওয়ার্ড

            // সেশন তৈরি করা
            Session session = null;
            try {
                session = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password); // অথেনটিকেশন
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Error occurred while creating session: " + e.getMessage(), e); // সেশন তৈরির ত্রুটি
                e.printStackTrace();
                return null; // সেশন তৈরি ব্যর্থ হলে প্রক্রিয়া থামান
            }

            try {
                // মেইল মেসেজ তৈরি করা
                MimeMessage mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom(new InternetAddress(username)); // প্রেরক সেট করা
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email)); // প্রাপক সেট করা
                mimeMessage.setSubject(subject); // সাবজেক্ট সেট করা

                // মেসেজ এবং অ্যাটাচমেন্ট যুক্ত করা
                Multipart multipart = new MimeMultipart();
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(message); // মেসেজ সেট করা
                multipart.addBodyPart(messageBodyPart); // মেসেজ যুক্ত করা

                // ফাইল অ্যাটাচমেন্ট যুক্ত করা (যদি থাকে)
                if (filePath != null && !filePath.isEmpty()) {
                    try {
                        MimeBodyPart attachmentPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(filePath); // ফাইল সোর্স
                        attachmentPart.setDataHandler(new DataHandler(source)); // ডেটা হ্যান্ডলার
                        attachmentPart.setFileName(new File(filePath).getName()); // ফাইলের নাম
                        multipart.addBodyPart(attachmentPart); // অ্যাটাচমেন্ট যুক্ত করা
                    } catch (Exception e) {
                        Log.e(TAG, "Error occurred while attaching the file: " + e.getMessage(), e); // ফাইল অ্যাটাচমেন্ট ত্রুটি
                        e.printStackTrace();
                        return null; // ফাইল অ্যাটাচ করতে সমস্যা হলে প্রক্রিয়া থামান
                    }
                }

                mimeMessage.setContent(multipart); // মেইল কনটেন্ট সেট করা

                try {
                    // মেইল পাঠানো
                    Transport.send(mimeMessage);
                    CountEmail.incrementEmailCount(); // ইমেল সফলভাবে পাঠানোর পর কাউন্টার বাড়ান
                    Log.d(TAG, "VideoRecord Email sent successfully with attachment."); // সফল লগ
                } catch (MessagingException e) {
                    Log.e(TAG, "Error occurred while sending email: " + e.getMessage(), e); // মেইল পাঠানোর ত্রুটি
                    e.printStackTrace();
                    return null; // মেইল পাঠানো ব্যর্থ হলে প্রক্রিয়া থামান
                }

            } catch (Exception e) {
                Log.e(TAG, "Error occurred during mail creation or sending: " + e.getMessage(), e); // মেইল তৈরি বা পাঠানোর ত্রুটি
                e.printStackTrace();
            }
            return null; // সম্পন্ন
        }
    }

    // স্ট্যাটিক মেথড যা অন্য জায়গা থেকে ব্যবহার করা যাবে
    public static void sendMailWithAttachment(String email, String subject, String message, String filePath) {
        JavaMailAPI_VideoRecord_Sender javaMailAPI = new JavaMailAPI_VideoRecord_Sender(email, subject, message, filePath);
        javaMailAPI.sendMail(); // মেইল পাঠানোর মেথড কল করা
    }
}
