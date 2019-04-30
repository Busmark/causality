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

import android.app.NotificationManager;
import android.app.RemoteInput;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import oscar.gmail.com.causality.NotificationNotifier;
import oscar.gmail.com.causality.R;
import oscar.gmail.com.causality.question.Question;
import oscar.gmail.com.causality.question.QuestionType;
import oscar.gmail.com.causality.question.QuestionViewModel;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "app";

    public static final int NEW_QUESTION_ACTIVITY_REQUEST_CODE = 1;
    public static final int CREATE_INQUIRY_ACTIVITY_REQUEST_CODE = 2;

    //New Question
    public QuestionViewModel mQuestionViewModel;
    public static final String EXTRA_REPLY = "Question";
    public static final String EXTRA_INTERVAL = "Interval";
    private EditText createQuestionEditText;
    private String chosenQuestionInterval = "";
    private String[] notificationDays= new String[6];
    private String notificationTime;

    //Create Inquiry
    private String chosenQuestionEffect = "";
    private List<Question> upToDateListofQuestions;

    //Create Notification
    //impl tutorial https://www.techotopia.com/index.php/An_Android_Direct_Reply_Notification_Tutorial
    NotificationManager notificationManager;
    NotificationNotifier notifier;
    private static String KEY_TEXT_REPLY = "key_text_reply";


    /*
        test med att starta upp en recieverservice från mainactivity när jag beställer en Notification.
        Receiverservicen bör då ha möjlighet att ta emot datan och spara ner den till db´n.
        ...även om MainActivity är avstängd.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mQuestionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);

        //caches all the questions
        mQuestionViewModel.getAllQuestions()
                            .observe(this, questions -> upToDateListofQuestions = questions);
        handleTutorialIntent();
    }

    //todo: ska detta göras i annan tråd?
    public void sendTutorialNotification(View view) {
        notifier = new NotificationNotifier(this);
        notifier.sendTutorialNotification(view);

    }
    //impl tutorial https://www.techotopia.com/index.php/An_Android_Direct_Reply_Notification_Tutorial
    private void handleTutorialIntent() {
        Intent intent = this.getIntent();
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            String inputString = remoteInput.getCharSequence(
                    KEY_TEXT_REPLY).toString();
            Log.i(TAG, "Text in notifierActivity = " + inputString);
        }
    }










    public void newQuestionBtnClicked(View v) {
        setContentView(R.layout.new_question);
    }

    public void createInquiryBtnClicked(View v) {
        setContentView(R.layout.create_inquiry);
        populateCreateInquirySpinners();
    }

    private void populateCreateInquirySpinners() {
        //Todo: hämta en spinner via xml eller factory i stället?
        //se hur jag gjorde på saveQuestionButton...
        Spinner allEffectQuestions = findViewById(R.id.all_effects_spinner);
        Spinner allCauseQuestions = findViewById(R.id.all_causes_spinner);

        ArrayAdapter<CharSequence> effectQuestionsForSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> causeQuestionsForSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);

        upToDateListofQuestions.forEach(question -> {
            if (question.getQuestionType().matches(QuestionType.EFFECT.toString()))
                effectQuestionsForSpinner.add(question.getText());
            if (question.getQuestionType().matches(QuestionType.CAUSE.toString()))
                causeQuestionsForSpinner.add(question.getText());
                }
        );
        effectQuestionsForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        causeQuestionsForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        allEffectQuestions.setAdapter(effectQuestionsForSpinner);
        allCauseQuestions.setAdapter(causeQuestionsForSpinner);
    }

    public void saveInquiryBtnClicked(View v) {

        String effect = ((Spinner)findViewById(R.id.all_effects_spinner)).getSelectedItem().toString();
//        String cause = ((Spinner)findViewById(R.id.all_causes_spinner)).getSelectedItem().toString();

        setContentView(R.layout.activity_main);
    }

    public void cancelBtnClicked(View v) {
        setContentView(R.layout.activity_main);
    }



    public void saveQuestionBtnClicked(View v) {

        createQuestionEditText = findViewById(R.id.new_question_text);
        String spinnerText = ((Spinner)findViewById(R.id.question_type_spinner)).getSelectedItem().toString();

        if (TextUtils.isEmpty(createQuestionEditText.getText())) {
            setResult(RESULT_CANCELED);
        } else {
            String text = createQuestionEditText.getText().toString();
            Question question = new Question(true, spinnerText,  text);
            mQuestionViewModel.insert(question);
            setContentView(R.layout.activity_main);
        }
    }

    public void onWeekdayCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.monday:
                Log.i(TAG, "Monday clicked");
                Log.i(TAG, "" + view.getId());
                break;
            case R.id.tuesday:
                Log.i(TAG, "tuesday clicked");
                Log.i(TAG, "" + view.getId());
                break;
            case R.id.wednesday:
                Log.i(TAG, "wednesday clicked");
                Log.i(TAG, "" + view.getId());
                break;
            case R.id.thursday:
                Log.i(TAG, "thursday clicked");
                Log.i(TAG, "" + view.getId());
                break;
            case R.id.friday:
                Log.i(TAG, "friday clicked");
                Log.i(TAG, "" + view.getId());
                break;
            case R.id.saturday:
                Log.i(TAG, "saturday clicked");
                Log.i(TAG, "" + view.getId());
                break;
            case R.id.sunday:
                Log.i(TAG, "sunday clicked");
                Log.i(TAG, "" + view.getId());
                break;
        }
    }
}
