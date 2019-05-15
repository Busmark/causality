package oscar.gmail.com.causality.repository;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import oscar.gmail.com.causality.AppDatabase;

public class AnswerRepository {

    private final String TAG = "causalityapp";

    private AnswerDao answerDao;
    private LiveData<List<Answer>> allAnswers;


    public AnswerRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        answerDao = db.answerDao();
        allAnswers = answerDao.getAllAnswers();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    /**
     *
     * @return
     */
    public LiveData<List<Answer>> getAllAnswers() {
        return allAnswers;
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    /**
     *
     * @param answer
     */
    public void insert(Answer answer) {
        new insertAsyncTask(answerDao).execute(answer);
    }

    private static class insertAsyncTask extends AsyncTask<Answer, Question, Void> {
        private final String TAG = "app";

        private AnswerDao ansyncAnswerDao;

        insertAsyncTask(AnswerDao dao) {
            ansyncAnswerDao = dao;
        }

        @Override
        protected Void doInBackground(final Answer... params) {
            ansyncAnswerDao.insert(params[0]);
            return null;
        }
    }

}
