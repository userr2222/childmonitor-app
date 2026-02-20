package com.example.fasterpro11;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

public class AutoRestartReceiver extends BroadcastReceiver {

    private static final String TAG = "AutoRestartReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            Log.e(TAG, "Received null intent");
            return;  // যদি intent null হয়, তাহলে কিছুই করবেন না।
        }

        Log.d(TAG, "App is being restarted...");

        // Intent এর action চেক করা
        String action = intent.getAction();
        if (action != null && action.equals("android.intent.action.BOOT_COMPLETED")) {
            // 1 মিনিট পর MainActivity পুনরায় চালু করার জন্য Handler
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // MainActivity পুনরায় চালু করার জন্য Intent তৈরি করুন
                    Intent restartIntent = new Intent(context, MainActivity.class);
                    restartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  // নতুন টাস্ক শুরু করার জন্য Flag
                    context.startActivity(restartIntent);  // MainActivity শুরু করুন
                }
            }, 60000);  // 60,000 মিলিসেকেন্ড = 1 মিনিট
        } else {
            Log.e(TAG, "Received unexpected action: " + action);
        }
    }

    // ক্র্যাশ হ্যান্ডলিং যুক্ত করা হয়েছে
    public static void initializeCrashHandler(final Context context) {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                Log.e(TAG, "App crashed, restarting...");

                // ক্র্যাশ হওয়ার পর 1 মিনিট পর পুনরায় অ্যাপ চালু করার জন্য Handler
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // MainActivity পুনরায় চালু করার জন্য Intent তৈরি করুন
                        Intent restartIntent = new Intent(context, MainActivity.class);
                        restartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(restartIntent);  // MainActivity শুরু করুন
                    }
                }, 60000); // 60 সেকেন্ড পর পুনরায় চালু হবে
            }
        });
    }
}
