package oscar.gmail.com.causality.question;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import oscar.gmail.com.causality.database;


public class EffectRepository {

    private EffectDao mEffectDao;
    private LiveData<List<Effect>> mAllQuestions;

    // Note that in order to unit test the EffectRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    EffectRepository(Application application) {
        database db = database.getDatabase(application);
        mEffectDao = db.questionDao();
        mAllQuestions = mEffectDao.getAlphabetizedQuestions();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Effect>> getAllQuestions() {
        return mAllQuestions;
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    void insert(Effect effect) {
        new EffectRepository.insertAsyncTask(mEffectDao).execute(effect);
    }

    private static class insertAsyncTask extends AsyncTask<Effect, Void, Void> {

        private EffectDao mAsyncTaskDao;

        insertAsyncTask(EffectDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Effect... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
