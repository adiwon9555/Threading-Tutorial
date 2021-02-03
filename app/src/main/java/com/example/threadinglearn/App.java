package com.example.threadinglearn;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

//Since our foreground service will run in notification, so create notication channel in main app
public class App extends Application {
    public static final String CHANNEL_ID = "exampleServiceChannel";
    @Override
    public void onCreate() {
        super.onCreate();

        createNoticationChannel();
    }

    private void createNoticationChannel() {
        //We create notificaiton channel only on oreo or higher
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channelService = new NotificationChannel(
                    CHANNEL_ID,
                    "Example Service Channel",
                    //Requires low or above importance
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channelService);
        }
    }

}
