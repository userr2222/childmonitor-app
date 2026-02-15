package com.example.fasterpro11;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class AutoStartReceiver extends BroadcastReceiver {

    private static final String TAG = "AutoStartReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Auto start receiver triggered");

        // Schedule WorkManager task for 9 AM
        scheduleWorkAtSpecificTime(context, 9, 0);  // 9 AM
        // Schedule WorkManager task for 12 AM
        scheduleWorkAtSpecificTime(context, 0, 0);  // 12 AM
    }

    private void scheduleWorkAtSpecificTime(Context context, int hour, int minute) {
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

        // Set an alarm to trigger at the specific time
        Intent workIntent = new Intent(context, BackgroundSmsNotificationWorker.class); // You can specify your task here
        context.sendBroadcast(workIntent);  // Trigger your task
    }
}
