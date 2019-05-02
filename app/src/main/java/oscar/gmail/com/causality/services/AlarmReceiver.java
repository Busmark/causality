package oscar.gmail.com.causality.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;


public class AlarmReceiver extends IntentService {
    private final String TAG = "causalityapp";

    public AlarmReceiver() {
        super("AlarmReceiver");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "AlarmReceiver: Receiving alarm");
        Log.i(TAG, "Data in AlarmIntent = " + intent.getStringExtra("deliverytime"));



//        NotificationManager notificationManager = (NotificationManager) context
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
    }
}

