package oscar.gmail.com.causality.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import java.util.List;

import oscar.gmail.com.causality.AppDatabase;

public class QuestionRepository {
    private final String TAG = "causalityapp";

    private QuestionDao mQuestionDao;
    private MutableLiveData<Integer> insertResult = new MutableLiveData<>();
    private LiveData<List<Question>> questionList;

    public QuestionRepository(Application application) {
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
        insertAsync(question);
    }

    public String getLatestSavedQuestionId(String text) {
        return mQuestionDao.getQuestionId(text);
    }

    /**
     *
     * @param question
     */
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
}
