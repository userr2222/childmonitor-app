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
