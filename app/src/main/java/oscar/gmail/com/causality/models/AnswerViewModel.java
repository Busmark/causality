package oscar.gmail.com.causality.models;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

import oscar.gmail.com.causality.repository.AnswerRepository;
import oscar.gmail.com.causality.repository.Answer;

public class AnswerViewModel extends AndroidViewModel {

    private final String TAG = "causalityapp";

    private AnswerRepository mRepository;
    private LiveData<List<Answer>> mAllAnswers;

    /**
     *
     * @param application
     */
    public AnswerViewModel(Application application) {
        super(application);
        mRepository = new AnswerRepository(application);
        mAllAnswers = mRepository.getAllAnswers();
    }

    /**
     *
     * @return
     */
    public LiveData<List<Answer>> getAllAnswers() {
        return mAllAnswers;
    }

    /**
     *
     * @param answer
     */
    public void insert(Answer answer) {
        mRepository.insert(answer);
    }

    /**
     *
     * @param pickedQuestionId
     */
    public void printAllAnswers(String pickedQuestionId) {
        mAllAnswers.getValue().forEach(answer -> {
            if (answer.getFkQuestionId().equals(pickedQuestionId)) {
                Log.i(TAG, answer.getAnswerText());
            }
        });
    }
}
