package com.example.threadinglearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.threadinglearn.asyncTask.AsyncTaskActivity;
import com.example.threadinglearn.foregroundService.ForegroundServiceMainActivity;
import com.example.threadinglearn.handlerAndLooper.CustomLooperActivity;
import com.example.threadinglearn.handlerAndLooper.HandlerThreadActivity;
import com.example.threadinglearn.jobSchedular.ExampleJobSchedular;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Thread task;
    private Button startButton;
    //A handler basically allows to pass message or task in and out of Thread
    //Eg To update button text which is a actually a change to UI (UI thread), so to do it from other thread we need handler of the main(UI) thread.
    // .post method to handler helps to pass message or task
    // But a handler always works only with a Looper
    // looper loops over the message in a thread and keeps it running. Eg, Our app(UI thread) does not end as soon as any operation ends.
    //Main Thread or the UI thread has a default looper and handler always doing its job
    //The below Handler to post our message uses the Looper of main thread
    private Handler mainHandler = new Handler();

    private volatile boolean stopThreadFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = findViewById(R.id.startButtton);
        setTitle("Background Simple Threading");
    }

    public void startTask(View view) {
      stopThreadFlag = false;
//    task = new Thread(){
//      @Override
//      public void run() {
//        for (int i = 0; i < 10; i++) {
//          Log.d(TAG, "startTask: "+i);
//          try {
//            Thread.sleep(1000);
//          } catch (InterruptedException e) {
//            e.printStackTrace();
//          }
//        }
//      }
//    };
//    task.start();
        //ALWAYS BETTER TO PASS RUNNABLE TO THREAD TO START A TASK
//        task = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 10; i++) {
//                    Log.d(TAG, "startTask: " + i);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        task.start();
        RunnableTask runnableTask = new RunnableTask(10);
        //To run on same thread, here main thread
//    runnableTask.run();
        new Thread(runnableTask).start();


    }

    public void stopTask(View view) {
      stopThreadFlag = true;
    }
    public void openCustomLooperActivity(View view){
        Intent intent = new Intent(this, CustomLooperActivity.class);
        startActivity(intent);
    }

    public void openHandlerThreadTutorial(View view) {
        Intent intent = new Intent(this, HandlerThreadActivity.class);
        startActivity(intent);
    }

    public void openAsyncActivity(View view) {
        Intent intent = new Intent(this, AsyncTaskActivity.class);
        startActivity(intent);
    }
    public void openJobSchedular(View view) {
        Intent intent = new Intent(this, ExampleJobSchedular.class);
        startActivity(intent);
    }

    public void openForegroundService(View view) {
        Intent intent = new Intent(this, ForegroundServiceMainActivity.class);
        startActivity(intent);
    }

    public class RunnableTask implements Runnable {
        private int seconds;

        public RunnableTask(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for (int i = 0; i < seconds; i++) {
              if(stopThreadFlag == true){
                return;
              }

                Log.d(TAG, "startTask: " + i);
                if (i == seconds / 2) {
                    //Ways to access do operation on Main Thread (here) from other thread
                    // 1. Use handler of Main Thread
//                    mainHandler.post(new Runnable() {
//                      @Override
//                      public void run() {
//                        startButton.setText("50%");
//                      }
//                    });

                    // 2. Use the threads own Handler but pass the looper of main thread
//                    Handler threadHandler = new Handler(Looper.getMainLooper());
//                    threadHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            startButton.setText("50%");
//                        }
//                    });
                   // 3. Use runOnUiThread method
//                  runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                      startButton.setText("50%");
//                    }
//                  });
                  // 4 Every View subclasses like Button, ImageView, Text has there own post method to do this job.
                  startButton.post(new Runnable() {
                    @Override
                    public void run() {
                      startButton.setText("50%");
                    }
                  });
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
