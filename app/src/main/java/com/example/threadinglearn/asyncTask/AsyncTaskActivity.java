package com.example.threadinglearn.asyncTask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.threadinglearn.R;

import java.lang.ref.WeakReference;

public class AsyncTaskActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Button asyncTaskButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        progressBar = findViewById(R.id.progress_bar);
        asyncTaskButton = findViewById(R.id.asyncTaskButton);

    }
    public void startAsyncTask(View v) {
        ExampleAsyncTask task = new ExampleAsyncTask(this);
        task.execute(10);
    }
    private static class ExampleAsyncTask extends AsyncTask<Integer, Integer, String> {
        private WeakReference<AsyncTaskActivity> activityWeakReference;
        ExampleAsyncTask(AsyncTaskActivity activity) {
            activityWeakReference = new WeakReference<AsyncTaskActivity>(activity);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AsyncTaskActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(Integer... integers) {
            for (int i = 0; i < integers[0]; i++) {
                publishProgress((i * 100) / integers[0]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Finished!";
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            AsyncTaskActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.progressBar.setProgress(values[0]);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            AsyncTaskActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            activity.progressBar.setProgress(0);
            activity.asyncTaskButton.setText("Completed");
            activity.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
