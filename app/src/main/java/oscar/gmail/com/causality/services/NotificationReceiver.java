package oscar.gmail.com.causality.services;

import android.app.IntentService;
import android.app.RemoteInput;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;


public class NotificationReceiver extends IntentService {

    private final String TAG = "causalityapp";
    private static String KEY_TEXT_REPLY = "key_text_reply";

    public NotificationReceiver() {
        super("NotificationReceiver");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "NotReceiver: Got an intent");
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            String inputString = remoteInput.getCharSequence(
                    KEY_TEXT_REPLY).toString();
            Log.i(TAG, "NotReceiver: Text in  = " + inputString);
            //todo: när modellering är klar, spara då ner ett Answer härifrån.
            // Frågan är bara hur jag ska få tag i rätt Question som hör till remoteInput...
            // Ska jag skicka med UUID för Question? Då blir det väl rätt i databasens fkQuestion?
        }

        //todo: stäng ner notification OM det gick att spara datan. Annars be om svaret igen
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.cancel(102);





//        //OM jag vill skicka en mottagarNotification till användaren kan jag använda detta
//        NotificationNotifier nn = new NotificationNotifier(this);
//        nn.sendReplyNotification();
    }
}
