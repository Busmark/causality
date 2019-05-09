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

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;


import oscar.gmail.com.causality.R;

import oscar.gmail.com.causality.question.Question;
import oscar.gmail.com.causality.question.QuestionViewModel;


public class MainActivity extends AppCompatActivity implements QuestionListFragment.OnListFragmentInteractionListener {
    private final String TAG = "causalityapp";

    private QuestionViewModel questionViewModel;

    private Button newQuestionButton;
    private Button viewAllQuestions;
    private Button viewAllAnswers;

    private boolean isFragmentDisplayed = false;
    static final String STATE_FRAGMENT = "state_of_fragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);

        newQuestionButton = findViewById(R.id.button_new_question);
        viewAllQuestions = findViewById(R.id.button_view_questions);
        viewAllAnswers = findViewById(R.id.button_view_answers);

        if (savedInstanceState != null) {
            isFragmentDisplayed =
                    savedInstanceState.getBoolean(STATE_FRAGMENT);
            if (isFragmentDisplayed) {
                // do stuff
            }
        }

    }

    //Add the following method to MainActivity to save the state of the Fragment if the configuration changes:
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state of the fragment (true=open, false=closed).
        savedInstanceState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void setFragmentDisplayed(boolean fragmentDisplayed) {
        isFragmentDisplayed = fragmentDisplayed;
    }

    public void newQuestionButtonClicked(View view) {
        // Get the FragmentManager and begin transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        if (!isFragmentDisplayed) {
        NewQuestionFragment newQuestionFragment = NewQuestionFragment.newInstance();
        // Add the NewQuestionFragment.
        fragmentTransaction.add(R.id.new_question_fragment_container, newQuestionFragment).addToBackStack(null).commit();
        // Update the Button text.
        // Set boolean flag to indicate fragment is open.
        isFragmentDisplayed = true;

    } else {
        // Check to see if the fragment is already showing.
        NewQuestionFragment newQuestionFragment = (NewQuestionFragment) fragmentManager
                .findFragmentById(R.id.new_question_fragment_container);
        if (newQuestionFragment != null) {
            // Create and commit the transaction to remove the fragment.
            fragmentTransaction.remove(newQuestionFragment).commit();
            isFragmentDisplayed = false;
        }
    }
}

    public void viewAllQuestionButtonClicked(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        if (!isFragmentDisplayed) {
            QuestionListFragment questionListFragment = QuestionListFragment.newInstance(1);
            fragmentTransaction.add(R.id.all_questions_fragment_container, questionListFragment).addToBackStack(null).commit();
            isFragmentDisplayed = true;

        } else {
            // Check to see if the fragment is already showing.
            QuestionListFragment questionListFragment = (QuestionListFragment) fragmentManager
                    .findFragmentById(R.id.all_questions_fragment_container);
            if (questionListFragment != null) {
                // Create and commit the transaction to remove the fragment.
                fragmentTransaction.remove(questionListFragment).commit();
                isFragmentDisplayed = false;
            }
        }
    }

    @Override
    public void onListFragmentInteraction(Question item) {

    }

    public QuestionViewModel getQuestionViewModel() {
        return questionViewModel;
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
