package oscar.gmail.com.causality.effect;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import oscar.gmail.com.causality.AppDatabase;

// Note that in order to unit test the EffectRepository, you have to remove the Application
// dependency. This adds complexity and much more code, and this sample is not about testing.
// See the BasicSample in the android-architecture-components repository at
// https://github.com/googlesamples
public class EffectRepository {
    private final String TAG = "app";

    private EffectDao mEffectDao;
    private LiveData<List<Effect>> mAllEffects;


    EffectRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mEffectDao = db.effectDao();
        mAllEffects = mEffectDao.getAlphabetizedEffects();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Effect>> getAllWords() {
        return mAllEffects;
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public void insert(Effect effect) {
        new insertAsyncTask(mEffectDao).execute(effect);
    }



    private static class insertAsyncTask extends AsyncTask<Effect, Void, Void> {
        private final String TAG = "app";

        private EffectDao mAsyncEffectDao;

        insertAsyncTask(EffectDao dao) {
            mAsyncEffectDao = dao;
        }

        @Override
        protected Void doInBackground(final Effect... params) {

            Effect[] temp = (Effect[]) params;
            Log.i(TAG, "insertAsyncTask : params[0] = " + temp[0].getText());


            mAsyncEffectDao.insert(params[0]);
            return null;
        }
    }
}
