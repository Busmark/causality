package oscar.gmail.com.causality.repository.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    private final String TAG = "causalityapp";

    /**
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String questionText = intent.getStringExtra("text");
        String questionId = intent.getStringExtra("id");
        String alarmDate = intent.getStringExtra("alarmDate");

         new NotificationNotifier(context, questionText, questionId, alarmDate);
    }
}
