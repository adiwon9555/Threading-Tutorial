package com.example.threadinglearn.handlerAndLooper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//Custom Handler tp handler messages using message code
public class ExampleHandler extends Handler {
    private static final String TAG = "ExampleHandler";
    public static final int TASKA_MSG = 1;
    public static final int TASKB_MSG = 2;
    public static final int DO_WORK = 3;

    public ExampleHandler() {
        super();
    }

    public ExampleHandler(@NonNull Looper looper) {
        super(looper);
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        switch (msg.what) {
            case TASKA_MSG:
                Log.d(TAG, "handleMessage: Task A executed");
                return;
            case TASKB_MSG:
                Log.d(TAG, "handleMessage: Task B executed");
                return;
            case DO_WORK:
                Log.d(TAG, "handleMessage: do work executed"+msg.arg1 + " " + msg.obj);
                return;
        }
    }
}
