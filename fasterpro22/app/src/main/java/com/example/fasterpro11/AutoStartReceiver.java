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
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, workIntent, PendingIntent.FLAG_UPDATE_CURRENT);

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
