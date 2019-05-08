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

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;


import oscar.gmail.com.causality.R;

import oscar.gmail.com.causality.question.NewQuestionFragment;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "causalityapp";

//    public AnswerViewModel mAnswerViewModel;

    private Button newQuestionButton;
    private Button viewAllQuestions;
    private Button viewAllAnswers;

    private boolean isFragmentDisplayed = false;
    static final String STATE_FRAGMENT = "state_of_fragment";



//    private List<Answer> upToDateListOfAnswers;
//
//    int checked = -1;
//    String buttonText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        mAnswerViewModel = ViewModelProviders.of(this).get(AnswerViewModel.class);

//        mAnswerViewModel.getAllAnswers()
//                            .observe(this, answers -> upToDateListOfAnswers = answers);


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
    if (!isFragmentDisplayed) {
        NewQuestionFragment newQuestionFragment = NewQuestionFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Add the SimpleFragment.
        fragmentTransaction.add(R.id.fragment_container, newQuestionFragment).addToBackStack(null).commit();
        // Update the Button text.
        // Set boolean flag to indicate fragment is open.
        isFragmentDisplayed = true;


    } else {
        // Get the FragmentManager.
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Check to see if the fragment is already showing.
        NewQuestionFragment simpleFragment = (NewQuestionFragment) fragmentManager
                .findFragmentById(R.id.fragment_container);
        if (simpleFragment != null) {
            // Create and commit the transaction to remove the fragment.
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
            isFragmentDisplayed = false;
        }
    }



}





























//    public String getButtonText() {
//        return buttonText;
//    }



//    public void printAllQuestions(View view) {
//        mQuestionViewModel.printAllQuestions(view);
//    }
//
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
