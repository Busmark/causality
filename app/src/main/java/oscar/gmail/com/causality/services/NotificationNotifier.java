package oscar.gmail.com.causality.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;

import oscar.gmail.com.causality.R;
import oscar.gmail.com.causality.ui.MainActivity;

public class NotificationNotifier {


    private final String TAG = "qn";
    int checked = -1;
    String buttonText;

    //impl tutorial https://www.techotopia.com/index.php/An_Android_Direct_Reply_Notification_Tutorial
    Context mainActivityContext;
    NotificationManager notificationManager;
    private String channelID = "oscar.gmail.com.causality";
    private static int notificationId = 101;
    private static String KEY_TEXT_REPLY = "key_text_reply";


    //impl tutorial https://www.techotopia.com/index.php/An_Android_Direct_Reply_Notification_Tutorial
    public NotificationNotifier(Context context) {
        this.mainActivityContext = context;
        notificationManager =
                (NotificationManager)
                        mainActivityContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }


    //impl tutorial https://www.techotopia.com/index.php/An_Android_Direct_Reply_Notification_Tutorial
    protected void createTutorialNotificationChannel(String id, String name, String description) {

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

    //impl tutorial https://www.techotopia.com/index.php/An_Android_Direct_Reply_Notification_Tutorial
    public void sendTutorialNotification(View view) {
        createTutorialNotificationChannel(channelID,
                "DirectReply News", "Example News Channel");

        String replyLabel = "Enter your reply here";
        RemoteInput remoteInput =
                new RemoteInput.Builder(KEY_TEXT_REPLY)
                        .setLabel(replyLabel)
                        .build();

//        Intent resultIntent = new Intent(mainActivityContext, MainActivity.class);
        Intent resultIntent = new Intent(mainActivityContext, NotificationReceiver.class);

        /*
                PendingIntent readPendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                conversation.getConversationId(),
                getMessageReadIntent(conversation.getConversationId()),
                PendingIntent.FLAG_UPDATE_CURRENT);

         */


        PendingIntent resultPendingIntent =
                PendingIntent.getService(
                        mainActivityContext,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
//        final Icon icon =
//                Icon.createWithResource(MainActivity.this,
//                        android.R.drawable.ic_dialog_info);
        Notification.Action replyAction =
                new Notification.Action.Builder(
                        null,
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
                (NotificationManager)
                        mainActivityContext.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId,
                newMessageNotification);
    }




    public static void createNotification(Context context) {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(context, MainActivity.class);


        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "666")
                .setSmallIcon(R.drawable.ic_add_black_24dp)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());

    }
    //todo: Registrera det channelId jag använder i new NotificationCompat.Builder(this, "666")
    public static void createTutorialNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("666", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public AlertDialog getAlertDialogWithMultipleList(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //todo: hämta godkända värden från Question så jag bara visar det som är valbart.

        final String[] itemsToChooseFrom = {"A", "B", "C"};

        builder.setTitle("Välj och klicka OK")
                .setSingleChoiceItems(itemsToChooseFrom, checked, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checked = which;
                        buttonText = itemsToChooseFrom[checked];
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //todo; spara ner resultatet (Answer) till databasen!

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }

    public String getButtonText() {
        return buttonText;
    }
}
