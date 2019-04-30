package oscar.gmail.com.causality.services;

import android.app.IntentService;
import android.app.RemoteInput;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

public class NotificationReceiver extends IntentService {

    private final String TAG = "app";

    //impl tutorial https://www.techotopia.com/index.php/An_Android_Direct_Reply_Notification_Tutorial
    private static String KEY_TEXT_REPLY = "key_text_reply";

    public NotificationReceiver() {
        super("NotificationReceiver");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            String inputString = remoteInput.getCharSequence(
                    KEY_TEXT_REPLY).toString();
            Log.i(TAG, "Text in receiverService = " + inputString);
        }
    }

    /*
    //impl tutorial https://www.techotopia.com/index.php/An_Android_Direct_Reply_Notification_Tutorial
    private void handleTutorialIntent() {
        Intent intent = this.getIntent();
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            String inputString = remoteInput.getCharSequence(
                    KEY_TEXT_REPLY).toString();
            Log.i(TAG, "Text in notifierActivity = " + inputString);
        }
    }


    /*
    Ta emot intentet
    Ta ur datan
    Spara ner datan till db´n
    När bekräftelse kommer stäng ner servicen
     */
}//https://developer.android.com/guide/components/services.html#ExtendingIntentService
