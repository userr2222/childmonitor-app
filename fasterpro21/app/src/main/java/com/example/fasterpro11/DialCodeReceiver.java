package com.example.fasterpro11;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DialCodeReceiver extends BroadcastReceiver {
    private static final String TAG = "DialCodeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // ডায়াল কোড যেটি প্রেস করা হয়েছে, সেটা চেক করা হচ্ছে
        String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

        // ডায়াল কোড #*#*67544*#*# এর সাথে মিলে গেলে অ্যাপ খোলা হবে
        if (phoneNumber != null && phoneNumber.equals("#*#*67544*#*#")) {
            Log.d(TAG, "Dial code #*#*67544*#*# detected, opening app.");

            // MainActivity বা আপনার যেকোনো অ্যাকটিভিটি খুলতে এখানে Intent তৈরি করা হয়েছে
            Intent launchIntent = new Intent(context, MainActivity.class);
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Start new task
            context.startActivity(launchIntent);  // অ্যাপ শুরু করুন
        }
    }
}
