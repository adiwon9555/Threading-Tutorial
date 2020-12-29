package com.example.threadinglearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

/*What we are trying to achieve is to create a background thread that
is running continuosly (using looper) and can take tasks via handler in its message queue
Handler and looper is created in ExampleLooperThread which is a thread that is running in background
 */
public class CustomLooperActivity extends AppCompatActivity {
    private static final String TAG = "CustomLooperActivity";
    private ExampleLooperThread exampleLooperThread = new ExampleLooperThread();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_looper);
        setTitle("Custom Looper Handler");
    }

    public void startThread(View view) {
        exampleLooperThread.start();
    }

    public void stopThread(View view) {
        //Quits immediately after current task is completed
        exampleLooperThread.exampleLooper.quit();
        //Quits only after all tasks registered in message queue is completed
//        exampleLooperThread.exampleLooper.quitSafely();

    }

    public void taskA(View view) {
        //We are now adding task to the ExampleLooperThread running in background
        //As we know, to pass task and message we need a handler and post function
        //So, we are no branching a thread from ExampleLooperThread to do this task
        //Branching using another thread becoz -> we dont want ExampleLooperThread to hang waiting for this task to end
        //this task is registered in message queue of looper and is executed sequentially or as described in post method

//        exampleLooperThread.handler.post(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 5; i++) {
//                    Log.d(TAG, "Task A is running "+i);
//                    SystemClock.sleep(1000);
//                }
//            }
//        });


//        We can also create a handler over here only and pass looper details
        Log.d(TAG, "taskA: "+exampleLooperThread.exampleLooper);
        Handler myHandler = new Handler(exampleLooperThread.exampleLooper);
        myHandler.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    Log.d(TAG, "Task A is running "+i);
                    SystemClock.sleep(1000);
                }
            }
        });
    }

    public void taskB(View view) {
        //We are sending Message instead of Runnable in this
        Message msg = Message.obtain();
        //with what we make the custom handler understand what to do
        msg.what = ExampleHandler.TASKB_MSG;
        exampleLooperThread.handler.sendMessage(msg);
    }

    public void openHandlerThreadTutorial(View view) {
        Intent intent = new Intent(this,HandlerThreadActivity.class);
        startActivity(intent);
    }
}
