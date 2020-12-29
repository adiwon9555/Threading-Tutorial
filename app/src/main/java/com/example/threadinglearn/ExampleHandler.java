package com.example.threadinglearn;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

//Custom Handler tp handler messages using message code
public class ExampleHandler extends Handler {
    private static final String TAG = "ExampleHandler";
    public static final int TASKA_MSG = 1;
    public static final int TASKB_MSG = 2;

    @Override
    public void handleMessage(@NonNull Message msg) {
        switch (msg.what) {
            case TASKA_MSG:
                Log.d(TAG, "handleMessage: Task A executed");
                return;
            case TASKB_MSG:
                Log.d(TAG, "handleMessage: Task B executed");
                return;
        }
    }
}
