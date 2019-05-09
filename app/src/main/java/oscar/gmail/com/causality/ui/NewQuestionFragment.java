package oscar.gmail.com.causality.ui;


import android.os.Bundle;
import androidx.fragment.app.Fragment;

import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import oscar.gmail.com.causality.R;

import static android.app.Activity.RESULT_CANCELED;

public class NewQuestionFragment extends Fragment implements View.OnClickListener {
    private final String TAG = "causalityapp";

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


    public static NewQuestionFragment newInstance() {
        return new NewQuestionFragment();
    }

    //save button
    @Override
    public void onClick(View view) {
        {
            if (TextUtils.isEmpty(createQuestionEditText.getText())) {
                getActivity().setResult(RESULT_CANCELED);
            } else {
                String newQuestionText = createQuestionEditText.getText().toString();
                String notification_hour = ((Spinner) rootView.findViewById(R.id.hour_spinner)).getSelectedItem().toString();
                String notification_mins = ((Spinner) rootView.findViewById(R.id.minute_spinner)).getSelectedItem().toString();
                String notification_time = notification_hour + notification_mins;
                String notification_reps = ((Spinner) rootView.findViewById(R.id.reps_spinner)).getSelectedItem().toString();


//            questionViewModel.saveQuestion(getActivity().getApplicationContext(), newQuestionText, notification_hour, notification_mins, notification_time, notification_reps);



            }
        }
        //todo: tightCoupling? Ska jag ändra till att anropa ett interface med samma metoder i som MainActivity har?
        ((MainActivity) getActivity()).setFragmentDisplayed(false);

        //todo: om objektet är sparat till db´n kan fragmentet stängas ner
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }



}
