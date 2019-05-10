package oscar.gmail.com.causality.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    private final String TAG = "causalityapp";
    //Create Notification
    NotificationNotifier notifier;

    @Override
    public void onReceive(Context context, Intent intent) {

//        Log.i(TAG, "AlarmReceiver: Receiving alarm");
        String questionText = intent.getStringExtra("text");
        String questionId = intent.getStringExtra("id");

            //todo: ska detta göras i annan tråd eller rent av från AnswerViewModel??
            //todo: skicka med frågans text.
            notifier = new NotificationNotifier(context);
            notifier.sendNotification(questionText, questionId);
    }
}
