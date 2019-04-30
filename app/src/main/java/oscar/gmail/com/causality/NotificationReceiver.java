package oscar.gmail.com.causality;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class NotificationReceiver extends IntentService {

    private final String TAG = "app";

    public NotificationReceiver() {
        super("NotificationReceiver");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "Handling intent!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Handling intent!");

        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent,flags,startId);
    }

    /*
    Ta emot intentet
    Ta ur datan
    Spara ner datan till db´n
    När bekräftelse kommer stäng ner servicen
     */
}//https://developer.android.com/guide/components/services.html#ExtendingIntentService
