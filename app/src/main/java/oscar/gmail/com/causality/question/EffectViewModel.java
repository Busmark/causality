package oscar.gmail.com.causality.question;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;


public class EffectViewModel extends AndroidViewModel  {

    private EffectRepository mRepository;
    // Using LiveData and caching what getAlphabetizedQuestions returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private LiveData<List<Effect>> mAllQuestions;

    public EffectViewModel(Application application) {
        super(application);
        mRepository = new EffectRepository(application);
        mAllQuestions = mRepository.getAllQuestions();
    }

    public LiveData<List<Effect>> getAllQuestions() {
        return mAllQuestions;
    }

    public void insert(Effect effect) {
        mRepository.insert(effect);
    }

}
