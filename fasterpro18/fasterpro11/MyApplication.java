package com.example.fasterpro11;

import android.app.Application;
import android.media.VolumeShaper;

import androidx.work.WorkManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        // Start your background work when the app starts
        BackgroundSmsNotificationWorker.startWork(this);
    }
}
