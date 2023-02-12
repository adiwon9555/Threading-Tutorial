package com.example.threadinglearn.foregroundService;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

//On Android 26 and higher it acts as a JobSchedular with predefine configuration to start whenever device is a state to execute servcice.
//But JobSchedular is more flexible and configurable.

//On Android 25 and lower this is a normal IntentService that does Background Task without needing a foreground service(Notification), as there is no such restriction for Background task.
//In IntentService we need to do background job in foreground service(Notificaiton), which ensures the job completion, irrespetive of device state,
//but not a good idea, since notification is popped up but with job intentservice when system resource is overloaded it is deferred and starts again in later time, also if all is fine then starts asap.

//Wake lock is internally handled
public class JobIntentServiceExample extends JobIntentService {
    private static final String TAG = "JobIntentServiceExample";


    //We are making this so that from the starting activity we can call only this
    static void enqueueWork(Context context, Intent work){
        //To start the service we need to call this static method
        enqueueWork(context,JobIntentServiceExample.class,123,work);
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
    }

    //Same as onHandleIntent
    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        String input = intent.getStringExtra("inputExtra");
        Log.d(TAG, "onHandleWork: started");

        for (int i = 0; i < 10; i++) {
            //Stopping background work is stopped
            if(isStopped()){
                return;
            }
            Log.d(TAG, "onHandleIntent: run - "+input+"-"+i);

            SystemClock.sleep(1000);
        }

    }

    @Override
    public boolean onStopCurrentWork() {

        //What we return here is whether we want the job to start again if interrupted, default is true
        return super.onStopCurrentWork();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
