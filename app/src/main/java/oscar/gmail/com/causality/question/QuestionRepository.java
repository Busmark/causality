package oscar.gmail.com.causality.question;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;


public class QuestionRepository {

    private QuestionDao mQuestionDao;
    private LiveData<List<Question>> mAllQuestions;

    // Note that in order to unit test the QuestionRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    QuestionRepository(Application application) {
        QuestionDatabase db = QuestionDatabase.getDatabase(application);
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
    void insert(Question question) {
        new QuestionRepository.insertAsyncTask(mQuestionDao).execute(question);
    }

    private static class insertAsyncTask extends AsyncTask<Question, Void, Void> {

        private QuestionDao mAsyncTaskDao;

        insertAsyncTask(QuestionDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Question... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
