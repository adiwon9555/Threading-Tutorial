package com.example.threadinglearn.foregroundService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.threadinglearn.R;

public class ForegroundServiceMainActivity extends AppCompatActivity {
    private EditText editTextForService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground_service_main);

        editTextForService = findViewById(R.id.editTextForService);

    }


    public void startForegroundService(View view) {
        String input = editTextForService.getText().toString();

        //Making the notification text dynamic with the text we set here
        Intent service = new Intent(this, ExampleService.class);
        service.putExtra("inputExtra",input);

        //We can use this if starting from a foreground activity, but can throw illegal state exception if triggered from background service
//        startService(serviceIntent);
        //So to avoid that we need to use startForegroundService, but for this the service triggered needs to call startForeground within 5 secs or the service will be destroyed.
//        startForegroundService(serviceIntent);
        //This gives error for requiring API 26, so to handle it internally:-
        ContextCompat.startForegroundService(this,service);



    }

    public void stopForegroundService(View view) {
        Intent service = new Intent(this, ExampleService.class);
        stopService(service);

    }

    public void startIntentService(View view) {
        String input = editTextForService.getText().toString();
        //To start IntentService
        Intent intentService = new Intent(this, IntentServiceExample.class);
        intentService.putExtra("inputExtra",input);
        ContextCompat.startForegroundService(this,intentService);
    }

    public void stopIntentService(View view) {
        //To stop IntentService
        Intent intentService = new Intent(this, IntentServiceExample.class);
        stopService(intentService);
    }

    public void enqueworkInJobIntentService(View view) {
        String input = editTextForService.getText().toString();

        Intent jobIntentService = new Intent(this, JobIntentServiceExample.class);
        jobIntentService.putExtra("inputExtra",input);
        //Setting constraint similar to Jobschedular as when to start is not possible here, system decides itself asap.
        JobIntentServiceExample.enqueueWork(this,jobIntentService);
    }
}