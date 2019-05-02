package oscar.gmail.com.causality.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.support.v4.content.ContextCompat;
import android.view.View;

import oscar.gmail.com.causality.R;

public class NotificationNotifier {

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
                "DirectReply News", "Example News Channel");
    }


    //impl tutorial https://www.techotopia.com/index.php/An_Android_Direct_Reply_Notification_Tutorial
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

    public void sendNotification(View view) {
        String replyLabel = "Enter your reply here";
        RemoteInput remoteInput =
                new RemoteInput.Builder(KEY_TEXT_REPLY)
                        .setLabel(replyLabel)
                        .build();

        Intent resultIntent = new Intent(mainActivityContext, NotificationReceiver.class);

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
                        "Reply", resultPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        Notification newMessageNotification =
                new Notification.Builder(mainActivityContext, channelID)
                        .setColor(ContextCompat.getColor(mainActivityContext,
                                R.color.colorPrimary))
                        .setSmallIcon(
                                android.R.drawable.ic_dialog_info)
                        .setContentTitle("My Notification")
                        .setContentText("This is a test message")
                        .addAction(replyAction).build();

        //todo; varför skapar vi upp en ny? KAn vi inte använda den globala?
        NotificationManager notificationManager =
                (NotificationManager) mainActivityContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, newMessageNotification);
    }

//     //OM jag vill skicka en mottagarNotification till användaren kan jag använda detta
//    public void sendReplyNotification() {
//    // Build a new notification, which informs the user that the system
//    // handled their interaction with the previous notification.
//        Notification repliedNotification = new Notification.Builder(mainActivityContext, channelID)
//                .setSmallIcon(R.drawable.ic_add_black_24dp)
//                .setContentText("message received")
//                .build();
//
//    // Issue the new notification.
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mainActivityContext);
//        notificationManager.notify(notificationId, repliedNotification);
//        notificationManager.cancel(notificationId);
//}

}
