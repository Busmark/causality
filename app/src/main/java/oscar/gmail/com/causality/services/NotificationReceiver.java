package oscar.gmail.com.causality.services;

import android.app.IntentService;
import android.app.RemoteInput;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import android.util.Log;

import oscar.gmail.com.causality.AppDatabase;
import oscar.gmail.com.causality.answer.Answer;


public class NotificationReceiver extends IntentService {

    private final String TAG = "causalityapp";
    private static String KEY_TEXT_REPLY = "key_text_reply";

    public NotificationReceiver() {
        super("NotificationReceiver");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.i(TAG, "arrived at service_onHandleIntent");

        try {
            // todo: hur illa är det att min bakgrundsservice pratar direkt med databasen?
//        AnswerRepository repo = new AnswerRepository(getApplication());
            AppDatabase db = AppDatabase.getDatabase(this);

            //todo: hur illa är det att jag exponerar questionId´t?
            // kan ett alternativ vara att jag tar reda på qId genom qText och sen ser till att två frågor inte får ha samma text?
            Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
            if (remoteInput != null) {
                // tar emot svaret
                String inputString = remoteInput.getCharSequence(
                        KEY_TEXT_REPLY).toString();
                //tar emot frågans id
                String questionId = intent.getStringExtra("questionId");

                db.answerDao().insert(new Answer(questionId, inputString));
            }

            //todo: stäng ner notification OM det gick att spara datan. Annars be om svaret igen
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.cancel(102);


            //todo: vill jag skicka ett mottagarnotis till användaren, typ "tack".


//        //OM jag vill skicka en mottagarNotification till användaren kan jag använda detta
//        NotificationNotifier nn = new NotificationNotifier(this);
//        nn.sendReplyNotification();
        } catch (Exception e) {
            Log.i(TAG, "crash boom bang in not.service");
        }
    }
}
