package oscar.gmail.com.causality.repository.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;

import java.util.Random;

import androidx.core.content.ContextCompat;
import oscar.gmail.com.causality.R;

public class NotificationNotifier {
    private final String TAG = "causalityapp";

    private Context mainActivityContext;
    private NotificationManager notificationManager;
    private String channelID = "oscar.gmail.com.causality";
    private static String KEY_TEXT_REPLY = "key_text_reply";


    public NotificationNotifier(Context context, String questionText, String questionId, String alarmDate) {
        this.mainActivityContext = context;
        notificationManager =
                (NotificationManager)
                        mainActivityContext.getSystemService(Context.NOTIFICATION_SERVICE);

        createNotificationChannel(channelID,
                "Causality alarm", "Causality Channel");

        sendNotification(questionText, questionId, alarmDate);
    }

    /**
     *
     * @param id
     * @param name
     * @param description
     */
    protected void createNotificationChannel(String id, String name, String description) {

        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationChannel channel =
                new NotificationChannel(id, name, importance);

        channel.setDescription(description);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{100, 200, 300, 400,
                500, 400, 300, 200, 400});

        notificationManager.createNotificationChannel(channel);
    }

    /**
     * Creates Notification when AlarmReceiver detects an alarm broadcast.
     * The variable randomNumberForNotificationId is attatched to the Intent so the
     * receiver can shut down the corresponding notification.
     * @param questionText  The notification question to device user.
     * @param questionId    The questions identifier value. Needed for saving the answer.
     */
    public void sendNotification(String questionText, String questionId, String alarmDate) {
        String replyLabel = "Enter your answer here";

        Intent intent = new Intent(mainActivityContext, NotificationReceiver.class);
        Random notification_id = new Random();
        int randomNumberForNotificationId = notification_id.nextInt(10000);

        intent.putExtra("questionText", questionText);
        intent.putExtra("questionId", questionId);
        intent.putExtra("alarmDate", alarmDate);
        intent.putExtra("notificationId", randomNumberForNotificationId);

        PendingIntent resultPendingIntent =
                PendingIntent.getService(
                        mainActivityContext,
                        randomNumberForNotificationId,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        final Icon icon =
                Icon.createWithResource(mainActivityContext,
                        R.drawable.ic_add_black_24dp);
        RemoteInput remoteInput =
                new RemoteInput.Builder(KEY_TEXT_REPLY)
                        .setLabel(replyLabel)
                        .build();

        Notification.Action replyAction =
                new Notification.Action.Builder(icon, "Answer", resultPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        Notification newMessageNotification =
                new Notification.Builder(mainActivityContext, channelID)
                        .setColor(ContextCompat.getColor(mainActivityContext,
                                R.color.colorPrimary))
                        .setSmallIcon(
                                android.R.drawable.ic_dialog_info)
                        .setContentTitle("This is your daily question:")
                        .setContentText(questionText)
//                        .setAutoCancel(true)
//                        .setOngoing(false)
                        .addAction(replyAction).build();



        NotificationManager notificationManager =
                (NotificationManager) mainActivityContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(randomNumberForNotificationId, newMessageNotification);
    }

//     //OM jag vill skicka en mottagarNotification till användaren kan jag använda detta
//    public void sendReplyNotification() {
//    // Build a new notification, which informs the user that the system
//    // handled their interaction with the previous notification.
//        Notification repliedNotification = new Notification.Builder(mainActivityContext, channelID)
//                .setSmallIcon(R.drawable.ic_add_black_24dp)
//                .setContentText("message received")
//                .build();


}
