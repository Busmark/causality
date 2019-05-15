package oscar.gmail.com.causality.repository.services;

import android.app.IntentService;
import android.app.RemoteInput;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import oscar.gmail.com.causality.repository.Answer;
import oscar.gmail.com.causality.repository.AnswerRepository;


public class NotificationReceiver extends IntentService {
    private final String TAG = "causalityapp";

    private static String KEY_TEXT_REPLY = "key_text_reply";

    public NotificationReceiver() {
        super("NotificationReceiver");
    }

    /**
     *
     * @param intent
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            // todo: hur illa är det att min bakgrundsservice pratar direkt med databasen?
            //AppDatabase db = AppDatabase.getDatabase(this);

            AnswerRepository answerRepository = new AnswerRepository(this.getApplication());

            // kan ett alternativ vara att jag tar reda på qId genom qText och sen ser till att två frågor inte får ha samma text?
            Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
            if (remoteInput != null) {
                // tar emot svaret
                String inputString = remoteInput.getCharSequence(
                        KEY_TEXT_REPLY).toString();
                //tar emot frågans id
                String questionId = intent.getStringExtra("questionId");

                answerRepository.insert(new Answer(questionId, inputString));

                //db.answerDao().insert(new Answer(questionId, inputString));

                int notificationId = intent.getIntExtra("notificationId", 0);

                //todo: stäng ner notification OM det gick att spara datan. Annars be om svaret igen
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                notificationManager.cancel(notificationId);
            }

            //todo: vill jag skicka ett mottagarnotis till användaren, typ "tack".
//        //OM jag vill skicka en mottagarNotification till användaren kan jag använda detta
//        NotificationNotifier nn = new NotificationNotifier(this);
//        nn.sendReplyNotification();
        } catch (Exception e) {
            Log.i(TAG, "crash boom bang in not.service");
        }
    }
}
