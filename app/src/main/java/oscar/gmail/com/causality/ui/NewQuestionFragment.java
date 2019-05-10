package oscar.gmail.com.causality.ui;


import android.os.Bundle;
import androidx.fragment.app.Fragment;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import oscar.gmail.com.causality.R;
import oscar.gmail.com.causality.question.Question;
import oscar.gmail.com.causality.question.QuestionViewModel;

import static android.app.Activity.RESULT_CANCELED;

public class NewQuestionFragment extends Fragment implements View.OnClickListener {
    private final String TAG = "causalityapp";

    private View rootView;
    private EditText createQuestionEditText;
    private Button save_button;

    private QuestionViewModel questionViewModel;

    public NewQuestionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_new_questions, container, false);

        save_button = rootView.findViewById(R.id.button_save);
        save_button.setOnClickListener(this);
        createQuestionEditText = rootView.findViewById(R.id.new_question_text);

        questionViewModel = ((MainActivity) getActivity()).getModel();
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
                String notification_time = ((Spinner) rootView.findViewById(R.id.hour_spinner)).getSelectedItem().toString()
                                        + ((Spinner) rootView.findViewById(R.id.minute_spinner)).getSelectedItem().toString();
                String notification_reps = ((Spinner) rootView.findViewById(R.id.reps_spinner)).getSelectedItem().toString();

            questionViewModel.saveQuestion(getActivity().getApplicationContext(), newQuestionText, notification_time, notification_reps);
            }
        }
        //todo: tightCoupling? Ska jag ändra till att anropa ett interface med samma metoder i som MainActivity har?
        ((MainActivity) getActivity()).clearFragmentMembers();

        //todo: om objektet är sparat till db´n kan fragmentet stängas ner
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}
