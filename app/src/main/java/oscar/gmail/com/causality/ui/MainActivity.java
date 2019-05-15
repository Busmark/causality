package oscar.gmail.com.causality.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import oscar.gmail.com.causality.R;
import oscar.gmail.com.causality.models.AnswerViewModel;
import oscar.gmail.com.causality.models.QuestionViewModel;
import oscar.gmail.com.causality.repository.Question;

public class MainActivity extends AppCompatActivity implements QuestionListFragment.OnListFragmentInteractionListener {
    private final String TAG = "causalityapp";

    private QuestionViewModel questionViewModel;
    private AnswerViewModel answerViewModel;

    private final String NEW_QUESTION_BUTTON_TEXT = "New Question";
    private final String VIEW_ALL_QUESTIONS_BUTTON_TEXT = "View all Questions";
    private final String VIEW_ALL_ANSWERS_BUTTON_TEXT = "View all Answers";

    private final String NUMBER_OF_DISPLAYED_FRAGMENTS = "numberOfDisplayedFragments";
    private int numberOfDisplayedFragments = 0;

    private final String DISPLAYED_FRAGMENT = "displayed_fragment";
    private String displayedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        answerViewModel = ViewModelProviders.of(this).get(AnswerViewModel.class);

        if (savedInstanceState != null) {
            numberOfDisplayedFragments = savedInstanceState.getInt(NUMBER_OF_DISPLAYED_FRAGMENTS);
            displayedFragment = savedInstanceState.getString(DISPLAYED_FRAGMENT);
        }
    }

    /**
     * Saving the fragment state when configuration changes.
     * @param savedInstanceState
     */
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(NUMBER_OF_DISPLAYED_FRAGMENTS, numberOfDisplayedFragments);
        savedInstanceState.putString(DISPLAYED_FRAGMENT, displayedFragment);
        super.onSaveInstanceState(savedInstanceState);
    }

    public QuestionViewModel getQuestionViewModel() {
        return questionViewModel;
    }
    public AnswerViewModel getAnswerViewModel() { return answerViewModel; }

    /**
     * Triggers when either of the main page button is clicked. Expands the page with dialog for that page.
     * numberOfDisplayedFragments 0 = no fragment displayed, 1 = a fragment is displayed
     *
     * @param view
     */
    public void mainButtonClicked(View view) {
        Button pressedButton = (Button) view;

        switch (numberOfDisplayedFragments) {
            case 0:
                openFragment(pressedButton.getText().toString());
                break;
            case 1:
                closeFragment();
                // same buttonPressed as displayedFragment
                if (pressedButton.getText().toString().equals(displayedFragment)) {
                    clearMainActivityMembers();
                }
                else {
                    clearMainActivityMembers();
                    mainButtonClicked(view);
                }
                break;
            }
        }

    public void clearMainActivityMembers() {
        numberOfDisplayedFragments = 0;
        displayedFragment = "";
    }

    public void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    /**
     * Opens the main page corresponding fragment.
     * @param pressedButton
     */
    public void openFragment(String pressedButton) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (pressedButton) {
            case NEW_QUESTION_BUTTON_TEXT:
                NewQuestionFragment newQuestionFragment = NewQuestionFragment.newInstance();
                fragmentTransaction.add(R.id.new_question_fragment_container, newQuestionFragment).addToBackStack(null).commit();
                displayedFragment = NEW_QUESTION_BUTTON_TEXT;
                numberOfDisplayedFragments = 1;
                break;

            case VIEW_ALL_QUESTIONS_BUTTON_TEXT:
                QuestionListFragment questionListFragment = QuestionListFragment.newInstance(1);
                fragmentTransaction.add(R.id.all_questions_fragment_container, questionListFragment).addToBackStack(null).commit();
                displayedFragment = VIEW_ALL_QUESTIONS_BUTTON_TEXT;
                numberOfDisplayedFragments = 1;
                break;

                //under development
            case VIEW_ALL_ANSWERS_BUTTON_TEXT:
                AnswerListFragment answerListFragment = AnswerListFragment.newInstance(1);
                fragmentTransaction.add(R.id.all_answers_fragment_container, answerListFragment).addToBackStack(null).commit();
                displayedFragment = VIEW_ALL_ANSWERS_BUTTON_TEXT;
                numberOfDisplayedFragments = 1;

                break;
        }
    }

    @Override
    public void onListFragmentInteraction(Question item) {

        answerViewModel.setTempQuestion(item);
        closeFragment();
        clearMainActivityMembers();
        openFragment("View all Answers");
    }
}
