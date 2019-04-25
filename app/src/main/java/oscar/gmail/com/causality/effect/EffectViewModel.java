package oscar.gmail.com.causality.effect;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

// Using LiveData and caching what getAlphabetizedEffects returns has several benefits:
// - We can put an observer on the data (instead of polling for changes) and only update the
//   the UI when the data actually changes.
// - Repository is completely separated from the UI through the ViewModel.
public class EffectViewModel extends AndroidViewModel  {

    private final String TAG = "app";

    private EffectRepository mRepository;
    private LiveData<List<Effect>> mAllEffects;

    public EffectViewModel(Application application) {
        super(application);
        mRepository = new EffectRepository(application);
        mAllEffects = mRepository.getAllWords();
    }

    public LiveData<List<Effect>> getAllEffects() {
        return mAllEffects;
    }

    public void insert(Effect effect) {
        mRepository.insert(effect);
    }


}

