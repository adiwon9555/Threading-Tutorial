package com.example.threadinglearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CustomLooperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_looper);
        setTitle("Custom Looper Handler");
    }
}
