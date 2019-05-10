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

import android.util.Log;
import android.view.View;
import android.widget.Button;
import oscar.gmail.com.causality.R;

import oscar.gmail.com.causality.repository.Question;
import oscar.gmail.com.causality.models.QuestionViewModel;


public class MainActivity extends AppCompatActivity implements QuestionListFragment.OnListFragmentInteractionListener {
    private final String TAG = "causalityapp";

    private QuestionViewModel questionViewModel;

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
        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);

        if (savedInstanceState != null) {
            numberOfDisplayedFragments = savedInstanceState.getInt(NUMBER_OF_DISPLAYED_FRAGMENTS);
            displayedFragment = savedInstanceState.getString(DISPLAYED_FRAGMENT);
//            if (numberOfDisplayedFragments > 0) {
//                // do stuff
//            }
        }
    }

    //Add the following method to MainActivity to save the state of the Fragment if the configuration changes:
    public void onSaveInstanceState(Bundle savedInstanceState) {
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


    //todo: När denna kommer in har användaren klickat på en Question.
    // då ska denna question sparas ner till viewModellen för att sedan ligga till grund för
    // att hämta alla Answer som hör till Question. Dessa ska recyclas i en View så att de dyker upp under
    // ViewAllAnswers.
    //Då kan ju den knappen vara inaktiverad...
    @Override
    public void onListFragmentInteraction(Question item) {
        questionViewModel.setQuestionforAnswers(item);
    }







}
