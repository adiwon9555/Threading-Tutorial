package com.example.threadinglearn.jobSchedular;


import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class DemoJob extends JobService {
    private static final String TAG = "DemoJob";
    private boolean jobCancelled = false;

    //Called when the OS schedules this job
    @Override
    public boolean onStartJob(final JobParameters params) {
        //Runs on UI thread hence can hang UI if long process running, hence we should use thread for background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                dobackhroundJob(params);
            }
        }).start();
        return true;
    }

    private void dobackhroundJob(final JobParameters params) {
        Log.d(TAG, "dobackhroundJob: background job started");
        for (int i = 0; i < 10; i++) {
            if(jobCancelled){
                return;
            }
            Log.d(TAG, "dobackhroundJob: running"+i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        Log.d(TAG, "dobackhroundJob: ended");
        //signal OS to stop the service
        jobFinished(params,false);
    }

    //Gets called when the job is interrupted.
    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob: job interrupted before completion");
        //we manually also end the background job
        jobCancelled = true;
        return false;
    }
}
