package com.example.threadinglearn;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

public class ExampleLooperThread extends Thread {
    private static final String TAG = "LooperThread";
    public Looper exampleLooper;
    public Handler handler;
    @Override
    public void run() {
        Log.d(TAG, "run: ExampleLooperThread started");
        //This prepare method creates a looper and message queue for the thread which is necessary for creating a handler
        //Looper and message queue is only one per Thread
        //Handler can be multiple
        Looper.prepare();
        exampleLooper = Looper.myLooper();
        //Only after looper.prepare(), we can create  a handler

//        handler = new Handler();
        //Handler creation has to be between Looper.prepare() and Looper.loop()

        //To handle the message in custom Handler we can create own handler
        handler = new ExampleHandler();

        //This method is same as that of infinite loop to keep the thread alive and running.
        Looper.loop();

//        for (int i = 0; i < 5; i++) {
//            Log.d(TAG, "run: "+i);
//            SystemClock.sleep(1000);
//        }
        Log.d(TAG, "End of run()");
    }
}
