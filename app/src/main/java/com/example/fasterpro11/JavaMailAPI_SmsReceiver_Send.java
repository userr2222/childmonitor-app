package com.example.fasterpro11;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import android.annotation.SuppressLint;
import android.util.Log;

public class JavaMailAPI_SmsReceiver_Send {
    private static final String TAG = "JavaMailAPI_SmsReceiver_Send";

    // ইমেইল অ্যাকাউন্ট ভেরিয়েবলসমূহ
    private static final String  email1 = "abontiangum99@gmail.com";
    private static final String  email1password = "egqnjvccoqtgwaxo";
    private static final String  email2 = "babulahmed000015@gmail.com";
    private static final String  email2password = "ncozjamyddqjiaba";
    private static final String  email3 = "fgfgfdf99@gmail.com";
    private static final String  email3password = "egcvjvccoqtgwaxo";
    private static final String  email4 = "dgdgdfg@gmail.com";
    private static final String  email4password = "egqnjvcvvqtgwaxo";
    private static final String  email5 = "dgdgdgsdg@gmail.com";
    private static final String  email5password = "egqnjbbcoqtgwaxo";
    private static final String  email6 = "dgxdgxg@gmail.com";
    private static final String  email6password = "egqnjnncoqtgwaxo";

    // ইমেইল অ্যাকাউন্ট লিস্ট (ভেরিয়েবল ব্যবহার করে)
    private static final String[][] EMAIL_ACCOUNTS = {
            {email1, email1password},
            {email2, email2password},
            {email3, email3password},
            {email4, email4password},
            {email5, email5password},
            {email6, email6password}
    };

    @SuppressLint("LongLogTag")
    public static void sendMail(String recipientEmail, String subject, String messageBody) {
        // দৈনিক সীমা চেক করুন
        if (!CountEmail.canSendEmail()) {
            Log.d(TAG, "Email limit reached for today. No email sent.");
            return;
        }

        Thread thread = new Thread(() -> {
            for (String[] emailAccount : EMAIL_ACCOUNTS) {
                String username = emailAccount[0];
                String password = emailAccount[1];

                Log.d(TAG, "Trying to send email using: " + username);

                if (attemptToSendEmail(username, password, recipientEmail, subject, messageBody)) {
                    CountEmail.incrementEmailCount(); // ইমেল সফলভাবে পাঠানো হলে কাউন্টার বৃদ্ধি
                    Log.d(TAG, "Email sent successfully using: " + username);
                    return; // সফলভাবে ইমেইল পাঠানো হলে লুপ থেকে বেরিয়ে যান
                }
            }
            Log.e(TAG, "All email accounts failed to send email.");
        });

        try {
            thread.start();
        } catch (Exception e) {
            Log.e(TAG, "Error occurred while starting email send thread: " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private static boolean attemptToSendEmail(String username, String password, String recipientEmail, String subject, String messageBody) {
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
            message.setText(messageBody);

            for (int attempt = 1; attempt <= 1; attempt++) { // 1 term try attempts for each account
                try {
                    Log.d(TAG, "Attempt " + attempt + " to send email using: " + username);
                    Transport.send(message);
                    return true; // ইমেল সফলভাবে পাঠানো হলে true রিটার্ন করুন
                } catch (MessagingException e) {
                    Log.e(TAG, "Attempt " + attempt + " failed for " + username + ": " + e.getMessage(), e);
                    Thread.sleep(2000); // 2 সেকেন্ড অপেক্ষা করুন পরবর্তী প্রচেষ্টার আগে
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error while preparing email: " + e.getMessage(), e);
        }
        return false; // ইমেইল পাঠানো ব্যর্থ হলে false রিটার্ন করুন
    }
}