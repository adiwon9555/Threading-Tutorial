package com.example.threadinglearn.handlerAndLooper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import com.example.threadinglearn.R;

public class HandlerThreadActivity extends AppCompatActivity {
    private static final String TAG = "HandlerThreadActivity";
    //Instead of creating custom looper and message queue, we have this HandlerThread class which does all that when we create it.
    private HandlerThread handlerThread = new HandlerThread("HandlerThread");
    private Handler handler;
    private ExampleHandler exampleHandler;
    private ExampleTask2 runnable2 = new ExampleTask2();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());

        //Instead of Handler class we can also create a custom Handler sub class to read our messages and attach looper to it

        exampleHandler = new ExampleHandler(handlerThread.getLooper());

        //Alternatively we can also create a subclass of HandlerThread and in onLooperPrepared method we can create a custom handler

    }

    public void doWork(View view) {
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 5; i++) {
//                    Log.d(TAG, "run: Handler Thread do work"+i);
//                    SystemClock.sleep(1000);
//                }
//            }
//        });
        //We can do the above way but creating anonymous class actually keeps the implicit reference of its parent class just as a property.
        //Having a reference can create memory leaks when activity can get destroyed but and thread still runs and prohibits activity from garbage collecting.
        //So to avoid this we make use of static, not keeping any reference

        //Post is delayed by 2 secs so, 1st exampleTask2 then ExampleTask1 will run
        handler.postDelayed(new ExampleTask1(),2000);
        //Similar to this many variation of postTask is there
        handler.post(runnable2);

        Message msg = Message.obtain();
        msg.what = ExampleHandler.DO_WORK;
        msg.arg1 = 23;
        msg.obj = "Obj String";

        Message msg2 = Message.obtain(exampleHandler);
        msg2.what = ExampleHandler.DO_WORK;
        msg2.arg1 = 0;
        msg2.obj = "Sending msg in different way";
        //When in Message.obtain handler is passed as argument like above, we hav already attached message to handler,
        // hence can directly call msg.sendToTarget()
        msg2.sendToTarget();

//        exampleHandler.sendEmptyMessage(ExampleHandler.DO_WORK);
        exampleHandler.sendMessage(msg);
        //To post messages

    }

    public void removeMessages(View view) {

        //Remove all Messages and Runnable tasks (callbacks)
        //Removes all runnable and messages attached to handler but exampleHandler tasks will still run
        handler.removeCallbacksAndMessages(null);

        //For specific runnable remove pass the object reference
        handler.removeCallbacks(runnable2);

        //Alternative we can also attach tokens to runnable using variants or post method of handler and later use it to identify and removeCallback
        //same for messages using what


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quit();

    }

    public static class ExampleTask1 implements Runnable{
        private static final String TAG = "ExampleTask1";
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                    Log.d(TAG, "run: Handler Thread do work"+i);
                    SystemClock.sleep(1000);
                }
        }
    }

    public static class ExampleTask2 implements Runnable{
        private static final String TAG = "ExampleTask2";
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                Log.d(TAG, "run: Handler Thread do work"+i);
                SystemClock.sleep(1000);
            }
        }
    }
}
