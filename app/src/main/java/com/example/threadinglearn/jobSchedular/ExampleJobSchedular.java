package com.example.threadinglearn.jobSchedular;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.threadinglearn.R;

public class ExampleJobSchedular extends AppCompatActivity {
    private static final String TAG = "ExampleJobSchedular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_job_schedular);
    }

    public void scheduleJob(View view){
        //jobinfo is needed to schedule job with criteria for which compnentName is required
        ComponentName componentName = new ComponentName(this,DemoJob.class);
        JobInfo jobInfo = new JobInfo.Builder(123, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = jobScheduler.schedule(jobInfo);
        if(resultCode == JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "scheduleJob: Job scheduled");
        }else{
            Log.d(TAG, "scheduleJob: Job scheduling Failed");
        }


    }

    public void cancelJob(View view){
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        //Cancel job manually
        jobScheduler.cancel(123);
        Log.d(TAG, "cancelJob: Job cancelled");
    }
}