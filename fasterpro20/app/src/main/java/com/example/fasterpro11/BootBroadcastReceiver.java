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
            // রিবুট হওয়ার পর কাজ পুনরায় শুরু করুন
            WorkRequest smsNotificationWorkRequest = new OneTimeWorkRequest.Builder(BackgroundSmsNotificationWorker.class).build();
            WorkManager.getInstance(context).enqueue(smsNotificationWorkRequest);
        }
    }
}
