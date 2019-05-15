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
import android.util.Log;
import androidx.core.content.ContextCompat;

import oscar.gmail.com.causality.R;

import java.util.Random;

public class NotificationNotifier {
    private final String TAG = "causalityapp";

    private Context mainActivityContext;
    private NotificationManager notificationManager;
    private String channelID = "oscar.gmail.com.causality";
    private static int notificationId = 102;
    private static String KEY_TEXT_REPLY = "key_text_reply";


    public NotificationNotifier(Context context) {
        this.mainActivityContext = context;
        notificationManager =
                (NotificationManager)
                        mainActivityContext.getSystemService(Context.NOTIFICATION_SERVICE);

        createNotificationChannel(channelID,
                "Causality alarm", "Causality Channel");
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
     *
     * @param questionText
     * @param questionId
     */
    public void sendNotification(String questionText, String questionId) {
        String replyLabel = "Enter your answer here";
        RemoteInput remoteInput =
                new RemoteInput.Builder(KEY_TEXT_REPLY)
                        .setLabel(replyLabel)
                        .build();

        Intent resultIntent = new Intent(mainActivityContext, NotificationReceiver.class);
        resultIntent.putExtra("questionText", questionText);
        resultIntent.putExtra("questionId", questionId);

        PendingIntent resultPendingIntent =
                PendingIntent.getService(
                        mainActivityContext,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        final Icon icon =
                Icon.createWithResource(mainActivityContext,
                        R.drawable.ic_add_black_24dp);

        Notification.Action replyAction =
                new Notification.Action.Builder(
                        icon,
                        "Answer", resultPendingIntent)
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
                        .setAutoCancel(true)
                        .setOngoing(false)
                        .addAction(replyAction).build();

        Random notification_id = new Random();
        int temp = notification_id.nextInt(10000);
        Log.i(TAG, "Notification notifier, random id = " + temp);

        NotificationManager notificationManager =
                (NotificationManager) mainActivityContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(temp, newMessageNotification);
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
