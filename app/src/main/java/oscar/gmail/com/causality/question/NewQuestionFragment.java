package oscar.gmail.com.causality.question;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import oscar.gmail.com.causality.R;
import oscar.gmail.com.causality.ui.MainActivity;

import static android.app.Activity.RESULT_CANCELED;

public class NewQuestionFragment extends Fragment implements View.OnClickListener {
    private final String TAG = "causalityapp";

    //ska bara användas för att spara ner en Question
    private QuestionViewModel questionViewModel;

    private View rootView;
    private EditText createQuestionEditText;
    private Button save_button;

    public NewQuestionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_new_questions, container, false);


        save_button = rootView.findViewById(R.id.button_save);
        save_button.setOnClickListener(this);
        createQuestionEditText = rootView.findViewById(R.id.new_question_text);

        return rootView;
    }

    /*
    LiveData<WeatherEntry[]> networkData = mWeatherNetworkDataSource.getCurrentWeatherForecasts();
        networkData.observeForever(newForecastsFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                // Deletes old historical data
                deleteOldData();
                Log.d(LOG_TAG, "Old weather deleted");
                // Insert our new weather data into Sunshine's database
                mWeatherDao.bulkInsert(newForecastsFromNetwork);
                Log.d(LOG_TAG, "New values inserted");
            });
        });
     */



    public static NewQuestionFragment newInstance() {
        return new NewQuestionFragment();
    }


    @Override
    public void onClick(View view) {
        {
            if (TextUtils.isEmpty(createQuestionEditText.getText())) {
                getActivity().setResult(RESULT_CANCELED);
            } else {
                String text = createQuestionEditText.getText().toString();
                String notification_hour = ((Spinner) rootView.findViewById(R.id.hour_spinner)).getSelectedItem().toString();
                String notification_mins = ((Spinner) rootView.findViewById(R.id.minute_spinner)).getSelectedItem().toString();
                String notification_time = notification_hour + notification_mins;
                String notification_reps = ((Spinner) rootView.findViewById(R.id.reps_spinner)).getSelectedItem().toString();


//           mQuestionViewModel.saveQuestionBtnClicked(this, text, notification_hour, notification_mins, notification_time, notification_reps);
//           setContentView(R.layout.activity_main);
            }
        }

        ((MainActivity) getActivity()).setFragmentDisplayed(false);

        //todo: om objektet är sparat till db´n kan fragmentet stängas ner
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}
