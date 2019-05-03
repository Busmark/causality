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

        Log.i(TAG, "AlarmReceiver: Receiving alarm");
        String questionText = intent.getStringExtra("text");
        String questionId = intent.getStringExtra("id");

            //todo: ska detta göras i annan tråd eller rent av från AnswerViewModel??
            //todo: skicka med frågans text.
            notifier = new NotificationNotifier(context);
            notifier.sendNotification(questionText, questionId);




        Log.i(TAG, "notifier work done");
    }
}

//    NotificationManager notificationManager = (NotificationManager) context
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Intent notificationIntent = new Intent(context, NotificationReceiver.class);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 101,
//                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//
//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
//                context).setSmallIcon(R.drawable.ic_add_black_24dp)
//                .setContentTitle("Alarm Fired")
//                .setContentText("Events to be Performed").setSound(alarmSound)
//                .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
//                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
//
//        if (intent.getAction().equals("MY_NOTIFICATION_MESSAGE")) {
//            notificationManager.notify(102, mNotifyBuilder.build());
//        }
