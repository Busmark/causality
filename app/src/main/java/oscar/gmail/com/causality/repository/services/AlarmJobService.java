package oscar.gmail.com.causality.repository.services;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.PersistableBundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

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
                PersistableBundle bundle = params.getExtras();
                String questionText =  bundle.getString("text");
                String questionId =  bundle.getString("id");
                String alarmTime = bundle.getString("alarm");      // typ 1115
                int repetitions = bundle.getInt("repetitions"); // typ 7

                int requestCodeForPendingIntent = params.getJobId();
                Log.i(TAG, "AlarmJobService, jobId() is: " + params.getJobId());
                String hour = alarmTime.substring(0,2); // 11
                String mins = alarmTime.substring(2,4); // 15
                int h = Integer.parseInt(hour);
                int m = Integer.parseInt(mins);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmm");
                Calendar calendar = Calendar.getInstance();
                Date currentDate = new Date();
                calendar.setTime(currentDate);

                calendar.set(Calendar.HOUR_OF_DAY, h);
                calendar.set(Calendar.MINUTE, m);


                Log.i(TAG, "Today is: " + dateFormat.format(currentDate));


                //första varvet ska jag inte lägga på en dag
                int dayToAdd = 0;

                for (int i = 0; i < repetitions; i++) {
                    Log.i(TAG, "dayToAdd = " + dayToAdd);
                    if (dayToAdd != 0) {
                        calendar.add(Calendar.DATE, 1);
                    }
                    else {
                        calendar.add(Calendar.DATE, 0);
                        dayToAdd++;
                    }

                    Date currentDatePlusRepetions = calendar.getTime();

                    Log.i(TAG, "Alarm is for: " + dateFormat.format(currentDatePlusRepetions));

                    // intent
                    Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                    intent.putExtra("text", questionText);
                    intent.putExtra("id", questionId);

                    intent.setAction("MY_NOTIFICATION_MESSAGE");

                    Random notification_id = new Random();
                    int temp = notification_id.nextInt(10000);
                    Log.i(TAG, "AlarmJobService, random id = " + temp);

                    // pendingIntent
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), temp, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    // AlarmManager
                    AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                    //todo: RTC_WAKEUP is for dev.purpose. Change to RTC at prod.
                    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                    Log.i(TAG, "AlarmJobService thread: Job finished");
                    jobFinished(params, false);

                    am.getNextAlarmClock();

                }
            }
        }).start();
    }

    /**
     *
     * @param params
     * @return
     */
    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "job was cancelled");
        jobCancelled = true;
        return true;
    }
}
