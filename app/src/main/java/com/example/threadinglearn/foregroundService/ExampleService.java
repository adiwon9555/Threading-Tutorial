package com.example.threadinglearn.foregroundService;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.threadinglearn.R;

import static com.example.threadinglearn.App.CHANNEL_ID;

public class ExampleService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //onStartCommand is called everytime we fire intent to to start service, this will not recreate another service but updates same with new intent
        Intent notificationIntent = new Intent(this,ForegroundServiceMainActivity.class);
        //Creating Pending intent so that on clicking notificaiton we come back to app
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,notificationIntent,0);
        //The intent object in parameter recieves the intent object that has started the service



        //For heavy operation use background Thread or IntentService
        String input = intent.getStringExtra("inputExtra");



        //Using the channel to build the notification
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText(input)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line.. asda s fasfdfad."))
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .build();
        //First param is identifier

        //If we dont start the as startForeground service then the OS will remove the service after a minute.
        startForeground(1,notification);

        //To stop service from within
//        stopSelf();
        //Which will call onDestroy

        //meaning if killed then will not start automatically.
        return START_NOT_STICKY;

    }



    @Nullable
    @Override
    //This method is needed only for Bound Services which are services that can communicate back and forth by binding to it.
    //But our service is a started service hence we can directly return null in the method below.
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
