package oscar.gmail.com.causality.answer;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class AnswerViewModel extends AndroidViewModel {

    private final String TAG = "causalityapp";

    private AnswerRepository mRepository;
    private LiveData<List<Answer>> mAllAnswers;

    public AnswerViewModel(Application application) {
        super(application);
        mRepository = new AnswerRepository(application);
        mAllAnswers = mRepository.getAllAnswers();
    }

    public LiveData<List<Answer>> getAllAnswers() {
        return mAllAnswers;
    }


    public void insert(Answer answer) {
        mRepository.insert(answer);
    }
}
