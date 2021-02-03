package com.example.threadinglearn.foregroundService;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


import com.example.threadinglearn.R;

import static com.example.threadinglearn.App.CHANNEL_ID;

public class IntentServiceExample extends IntentService {
    private static final String TAG = "IntentServiceExample";
    private PowerManager.WakeLock wakeLock;

    public IntentServiceExample() {
        super("IntentServiceExample");
        //If OS kill the service due to low memory then restart the service with previos intent
        setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate:");



        //Wakelock can be set so, that even if screen is locked then CPU is active for the service
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "IntentServiceExample:WakeLock");
        //it is always better to add wakelock time inside acquire, so that the system resource is not wasted even after work completion.
        //But for our case we know when to release wakelock so we have not added anything
        wakeLock.acquire();

        //This time we are just creating notificaiton once in onCreate becoz, we are not changing anything in notification
        //We show notificaiton only if OS is greater than oreo
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("IntentServiceExample")
                    .setContentText("Running ...")
                    .setSmallIcon(R.drawable.ic_android)
                    .build();
            startForeground(1,notification);
        }

    }

    //IntentService runs all incoming work in background thread.
    //Intent Service is basically to do the task in background and yet not let the OS kill it untill completion.
    //onHandleIntent is called whenever a new intent is fired to start the service, and it executes sequentially untill all intents are complete,
    //Post completion the service is destroyed

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: Intent started");

        String input = intent.getStringExtra("inputExtra");

        //Replicating background work
        for (int i = 0; i < 10; i++) {
            Log.d(TAG, "onHandleIntent: run - "+input+"-"+i);
            SystemClock.sleep(1000);
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        wakeLock.release();
        Log.d(TAG, "onDestroy: Wake lock release");
    }
}
