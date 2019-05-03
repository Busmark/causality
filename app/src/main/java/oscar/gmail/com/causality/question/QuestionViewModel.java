package oscar.gmail.com.causality.question;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

// Using LiveData and caching what getAlphabetizedQuestionss returns has several benefits:
// - We can put an observer on the data (instead of polling for changes) and only update the
//   the UI when the data actually changes.
// - Repository is completely separated from the UI through the ViewModel.
public class QuestionViewModel extends AndroidViewModel  {

    private final String TAG = "causalityapp";

    private QuestionRepository mRepository;
    private LiveData<List<Question>> mAllQuestions;

    public QuestionViewModel(Application application) {
        super(application);
        mRepository = new QuestionRepository(application);
        mAllQuestions = mRepository.getAllQuestions();
    }

    public LiveData<List<Question>> getAllQuestions() {
        return mAllQuestions;
    }

    public String insert(Question question) {
        mRepository.insert(question);
        return question.getId();
    }

}

