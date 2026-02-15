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
        Log.d(TAG, "doWork: SMS বা Notification আসলে কাজ শুরু হচ্ছে");

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
                Intent intent = new Intent(context, MainActivity.class);
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
