package com.example.fasterpro11;

import android.app.Notification;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.Telephony;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimNumberSetFromSmsOrNotification {
    private Context mContext;
    private static final String TAG = "SimNumberSetFromSmsOrNotification";

    // Method to extract text from a notification
    public String getNotificationText(StatusBarNotification sbn) {
        String Notification_MessageBody = "";

        try {
            Bundle extras = sbn.getNotification().extras;
            CharSequence text = extras.getCharSequence(Notification.EXTRA_TEXT);
            if (text != null) {
                Notification_MessageBody = text.toString();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in getting notification text: ", e);
        }

        return Notification_MessageBody;
    }

    // Method to find a keyword in SMS or notification message body
    public String findKeywordSmsOrNotificationSimTypeNumberSet(String messageBody, StatusBarNotification sbn) {
        String[] MatchKeywordsSimTypeNumberSet = {
                "bonus", "Emergency balance", "Emergency", "GB450TK"
        };

        // Get notification text from StatusBarNotification
        String notificationmessageBody = getNotificationText(sbn);

        // Search for keywords in the message body and notification message body
        for (String keyword : MatchKeywordsSimTypeNumberSet) {
            if (messageBody.equals(keyword) ||
                    messageBody.contains(keyword) ||
                    notificationmessageBody.equals(keyword) ||
                    notificationmessageBody.contains(keyword)) {
                return keyword;
            }
        }
        return null;
    }

    // Method to match a number pattern in recent incoming SMS
    public String SetNumberPatternMatchInRecentIncomingMessages() {
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
                        Log.d(TAG, "Pattern matched 4 to 6 digit numbers in Incoming Messages: " + matchedNumber);
                        return matchedNumber;
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Pattern Error querying incoming messages: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    // Method to match number patterns in call logs
    private boolean isPatternMatchInCallLogs(Context context) {
        Pattern pattern = Pattern.compile("\\b\\d{4,6}\\b");
        Uri callLogUri = CallLog.Calls.CONTENT_URI;
        String[] projection = { CallLog.Calls.NUMBER };
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(callLogUri, projection, null, null, CallLog.Calls.DATE + " DESC LIMIT 1");
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

    // Method to check if alert keyword is found in SMS or notification
    public String isfindAlertKeywordSmsOrNotificationSimTypeNumberSet(String messageBody, StatusBarNotification sbn) {
        String MatchKeywordsSimTypeNumberSet = findKeywordSmsOrNotificationSimTypeNumberSet(messageBody, sbn);
        if (MatchKeywordsSimTypeNumberSet != null) {
            Log.d(TAG, "Message Body Match Keywords Sim Type Number Set: " + MatchKeywordsSimTypeNumberSet);
            return MatchKeywordsSimTypeNumberSet;
        }
        return null;
    }
}
