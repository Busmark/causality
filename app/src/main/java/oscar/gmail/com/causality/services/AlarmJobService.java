package oscar.gmail.com.causality.services;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlarmJobService extends JobService {
    private final String TAG = "causalityapp";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        doBackgroundJob(params);
        return true;
    }

    private void doBackgroundJob(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int counter = params.getJobId();
                String all = Integer.toString(counter);
                String hour = all.substring(0,2);
                String mins = all.substring(2,4);

                int h = Integer.parseInt(hour);
                int m = Integer.parseInt(mins);

                // Calendar
                Calendar calendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") int minutes = Integer.parseInt(new SimpleDateFormat("mm").format(calendar.getTime()));
                @SuppressLint("SimpleDateFormat") int hours = Integer.parseInt(new SimpleDateFormat("HH").format(calendar.getTime()));
                calendar.set(Calendar.HOUR_OF_DAY, h);
                calendar.set(Calendar.MINUTE, m);


                // intent
                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                intent.putExtra("intent_id", counter);
                intent.setAction("MY_NOTIFICATION_MESSAGE");

                // pendingIntent
                PendingIntent pendingIntent =
                        PendingIntent.getBroadcast(getApplicationContext(), counter, intent, PendingIntent.FLAG_IMMUTABLE);

                // AlarmManager
                AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                //todo: RTC_WAKEUP is for dev.purpose. Change to RTC at prod.
//                am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

//                am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),SystemClock.elapsedRealtime() + 5 * 1000, pendingIntent);

                Log.i(TAG, "New thread: Job finished");
                jobFinished(params, false);
            }
        }).start();
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "job was cancelled");
        jobCancelled = true;
        return true;
    }
}
