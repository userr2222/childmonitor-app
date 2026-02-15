package com.example.fasterpro11;

import android.util.Log;

public class CountEmail {
    private static int emailCount = 0; // দৈনিক ইমেলের সংখ্যা ট্র্যাক করার জন্য
    private static final int DAILY_LIMIT = 499; // দৈনিক সীমা
    private static final String TAG = "CountEmail"; // লগ ট্যাগ

    // ইমেল পাঠানোর অনুমতি আছে কিনা তা চেক করে
    public static boolean canSendEmail() {
        return emailCount < DAILY_LIMIT; // যদি পাঠানো সংখ্যা সীমার মধ্যে থাকে
    }

    // ইমেল কাউন্ট বৃদ্ধি করে এবং লগে সংখ্যা দেখায়
    public static void incrementEmailCount() {
        if (emailCount < DAILY_LIMIT) { // সীমার মধ্যে থাকলে
            emailCount++; // কাউন্টার বৃদ্ধি
            Log.d(TAG, "Email sent successfully. Total sent emails today: " + emailCount); // সফল ইমেল পাঠানোর লগ মেসেজ
        } else {
            Log.d(TAG, "Email limit reached for today. No email sent."); // সীমা পৌঁছালে লগ মেসেজ
        }
    }

    // পাঠানো ইমেলের সংখ্যা ফেরত দেয়
    public static int getEmailCount() {
        return emailCount; // মোট পাঠানো ইমেলের সংখ্যা
    }
}
