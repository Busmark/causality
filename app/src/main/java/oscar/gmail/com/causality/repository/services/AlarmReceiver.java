package oscar.gmail.com.causality.repository.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    private final String TAG = "causalityapp";

    private NotificationNotifier notifier;

    /**
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(TAG, "AlarmReceiver: Receiving alarm");
        String questionText = intent.getStringExtra("text");
        String questionId = intent.getStringExtra("id");

        notifier = new NotificationNotifier(context);
        notifier.sendNotification(questionText, questionId);
    }
}
