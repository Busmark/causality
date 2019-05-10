package oscar.gmail.com.causality.ui;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Fragment;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;


import java.util.List;

import oscar.gmail.com.causality.R;

import oscar.gmail.com.causality.question.Question;
import oscar.gmail.com.causality.question.QuestionViewModel;


public class MainActivity extends AppCompatActivity implements QuestionListFragment.OnListFragmentInteractionListener {
    private final String TAG = "causalityapp";

    private QuestionViewModel questionViewModel;
    private List<Question> upToDateListOfQuestions;

    private Button newQuestionButton;
    private Button viewAllQuestionsButton;
    private Button viewAllAnswersButton;

    private final String NEW_QUESTION_BUTTON_TEXT = "New Question";
    private final String VIEW_ALL_QUESTIONS_BUTTON_TEXT = "View all Questions";
    private final String VIEW_ALL_ANSWERS_BUTTON_TEXT = "View all Answers";

    //if a fragment is displayed numberOfDisplayedFragments>0
    private final String NUMBER_OF_DISPLAYED_FRAGMENTS = "numberOfDisplayedFragments";
    private int numberOfDisplayedFragments = 0;

    //if a fragment is displayed !displayedFragment = ""
    private final String DISPLAYED_FRAGMENT = "displayed_fragment";
    private String displayedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //todo. Varje gång jag tiltar telefonen skapas en ny activity

        newQuestionButton = findViewById(R.id.button_new_question);
        viewAllQuestionsButton = findViewById(R.id.button_view_questions);
        viewAllAnswersButton = findViewById(R.id.button_view_answers);

        if (savedInstanceState != null) {
            numberOfDisplayedFragments = savedInstanceState.getInt(NUMBER_OF_DISPLAYED_FRAGMENTS);
            displayedFragment = savedInstanceState.getString(DISPLAYED_FRAGMENT);

            // a fragment is displayed load it?
            if (numberOfDisplayedFragments > 0) {
                // do stuff
            }
        }


        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        questionViewModel.getQuestionList().observe(this, new Observer<List<Question>>() {

            @Override
            public void onChanged(List<Question> questions) {
                upToDateListOfQuestions = questions;
            }
        });


    }



    //Add the following method to MainActivity to save the state of the Fragment if the configuration changes:
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state of the fragment (true=open, false=closed).
        savedInstanceState.putInt(NUMBER_OF_DISPLAYED_FRAGMENTS, numberOfDisplayedFragments);
        savedInstanceState.putString(DISPLAYED_FRAGMENT, displayedFragment);
        super.onSaveInstanceState(savedInstanceState);
    }



    public QuestionViewModel getModel() {
        return questionViewModel;
    }


    public void mainButtonClicked(View view) {
        Button pressedButton = (Button) view;
        // numberOfDisplayedFragments 0 = no fragment displayed
        // numberOfDisplayedFragments 1 = a fragment is displayed
        switch (numberOfDisplayedFragments) {
            case 0:
                openFragment(pressedButton.getText().toString());
                break;
            case 1:
                closeFragment();
                // same buttonPressed as displayedFragment
                if (pressedButton.getText().toString().equals(displayedFragment)) {
                    clearFragmentMembers();
                }
                else {
                    clearFragmentMembers();
                    mainButtonClicked(view);
                }
                break;
            }
        }

    public void clearFragmentMembers() {
        numberOfDisplayedFragments = 0;
        displayedFragment = "";
    }

    public void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

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

            case VIEW_ALL_ANSWERS_BUTTON_TEXT:
                displayedFragment = VIEW_ALL_ANSWERS_BUTTON_TEXT;
                break;
        }
    }

    @Override
    public void onListFragmentInteraction(Question item) {
    }




//    //todo: för att se vilken fråga jag har vill ha svaret på behöver jag lista alla questions igen. Som jag gör på Order Notification.
//    public void printAllAnswers(View view) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        String[] texts = mQuestionViewModel.getQuestionTextArray();
//
//        builder.setTitle("Välj och klicka OK")
//                .setSingleChoiceItems(texts, checked, (dialog, which) -> {
//                    checked = which;
//                    buttonText = texts[checked];
//                })
//                .setPositiveButton("Ok", (dialog, id) -> {
//                    // när jag klickar ok vill jag ha alla answer som hör till den frågan.
//
////                    String pickedQuestionId = mQuestionViewModel.getQuestionId(checked);
//                    String pickedQuestionId = upToDateListOfQuestions.get(checked).getId();
////                    mAnswerViewModel.printAllAnswers(pickedQuestionId);
//
//                    upToDateListOfAnswers.forEach(answer -> {
//                        if (answer.getFkQuestionId().equals(pickedQuestionId)) {
//                            Log.i(TAG, answer.getAnswerText());
//                        }
//                    });
//
//                })
//                .setNegativeButton("Cancel", (dialog, id) -> {
//
//                });
//        AlertDialog temp = builder.create();
//
//        //todo: behövs denna?
//        temp.setOnDismissListener(dialog -> Log.i(TAG, "what?" + getButtonText()));
//        builder.show();
//
//    }



}
