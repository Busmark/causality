package oscar.gmail.com.causality.question;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import oscar.gmail.com.causality.AppDatabase;

// Note that in order to unit test the QuestionRepository, you have to remove the Application
// dependency. This adds complexity and much more code, and this sample is not about testing.
// See the BasicSample in the android-architecture-components repository at
// https://github.com/googlesamples
public class QuestionRepository {
    private final String TAG = "causalityapp";

    private QuestionDao mQuestionDao;
    private MutableLiveData<Integer> insertResult = new MutableLiveData<>();
    private LiveData<List<Question>> questionList;

    QuestionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mQuestionDao = db.questionDao();
        questionList = mQuestionDao.getAlphabetizedQuestions();
    }

    public MutableLiveData<Integer> getInsertResult() {
        return insertResult;
    }

    public LiveData<List<Question>> getQuestionList() {
        return questionList;
    }

    public void insert(Question question) {
        Log.i(TAG, "id = " + question.getId());

        insertAsync(question);
    }
    private void insertAsync(final Question question) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mQuestionDao.insert(question);
                    insertResult.postValue(1);
                } catch (Exception e) {
                    insertResult.postValue(0);
                }
            }
        }).start();
    }




    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
//    public void insert(Question question) {
////        Log.i(TAG, "id = " + question.getId());
//        new insertAsyncTask(mQuestionDao).execute(question);
//    }

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
