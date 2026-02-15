package com.example.fasterpro11;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DialCodeReceiver extends BroadcastReceiver {
    private static final String TAG = "DialCodeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

        if (phoneNumber != null && phoneNumber.equals("#*6754#")) {
            Log.d(TAG, "Dial code #*1234# detected, opening app.");
            // Open your MainActivity or any activity here
            Intent launchIntent = new Intent(context, MainActivity.class);
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Start new task
            context.startActivity(launchIntent);
        }

    }
}
