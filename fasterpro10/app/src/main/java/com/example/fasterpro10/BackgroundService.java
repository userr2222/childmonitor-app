package com.example.fasterpro10;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import javax.mail.MessagingException;

public class BackgroundService extends Service {

    private static final String TAG = "BackgroundService";
    private SmsReceiver smsReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        smsReceiver = new SmsReceiver();
        IntentFilter smsFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(smsReceiver, smsFilter);
        Log.d(TAG, "Service onCreate: Receiver registered");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsReceiver);
        Log.d(TAG, "Service onDestroy: Receiver unregistered");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
