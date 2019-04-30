package oscar.gmail.com.causality.question;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import oscar.gmail.com.causality.AppDatabase;

// Note that in order to unit test the QuestionRepository, you have to remove the Application
// dependency. This adds complexity and much more code, and this sample is not about testing.
// See the BasicSample in the android-architecture-components repository at
// https://github.com/googlesamples
public class QuestionRepository {
    private final String TAG = "app";

    private QuestionDao mQuestionDao;
    private LiveData<List<Question>> mAllQuestions;


    QuestionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mQuestionDao = db.questionDao();
        mAllQuestions = mQuestionDao.getAlphabetizedQuestions();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Question>> getAllQuestions() {
        return mAllQuestions;
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public void insert(Question question) {
        new insertAsyncTask(mQuestionDao).execute(question);
    }



    private static class insertAsyncTask extends AsyncTask<Question, Void, Void> {
        private final String TAG = "app";

        private QuestionDao mAsyncQuestionDao;

        insertAsyncTask(QuestionDao dao) {
            mAsyncQuestionDao = dao;
        }

        @Override
        protected Void doInBackground(final Question... params) {
            mAsyncQuestionDao.insert(params[0]);
            return null;
        }
    }
}
