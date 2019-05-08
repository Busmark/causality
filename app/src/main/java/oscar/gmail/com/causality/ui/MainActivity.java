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

import android.app.AlertDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import oscar.gmail.com.causality.R;
import oscar.gmail.com.causality.answer.Answer;
import oscar.gmail.com.causality.answer.AnswerViewModel;
import oscar.gmail.com.causality.question.Question;
import oscar.gmail.com.causality.question.QuestionViewModel;
import oscar.gmail.com.causality.services.AlarmJobService;
import oscar.gmail.com.causality.services.NotificationNotifier;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "causalityapp";

    public QuestionViewModel mQuestionViewModel;
    private List<Question> upToDateListOfQuestions;


    public AnswerViewModel mAnswerViewModel;
    private List<Answer> upToDateListOfAnswers;

    int checked = -1;
    String buttonText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mQuestionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        mAnswerViewModel = ViewModelProviders.of(this).get(AnswerViewModel.class);

        mAnswerViewModel.getAllAnswers()
                            .observe(this, answers -> upToDateListOfAnswers = answers);
        mQuestionViewModel.getAllQuestions()
                .observe(this, questions -> upToDateListOfQuestions = questions);

    }

    public String getButtonText() {
        return buttonText;
    }

    public void newQuestionBtnClicked(View v) {
        setContentView(R.layout.new_question);
    }

    public void cancelBtnClicked(View v) {
        setContentView(R.layout.activity_main);
    }



    public void saveQuestionBtnClicked(View view) {
        EditText createQuestionEditText = findViewById(R.id.new_question_text);
        if (TextUtils.isEmpty(createQuestionEditText.getText())) {
            setResult(RESULT_CANCELED);
        } else {
            String text = createQuestionEditText.getText().toString();
            String notification_hour = ((Spinner)findViewById(R.id.hour_spinner)).getSelectedItem().toString();
            String notification_mins = ((Spinner)findViewById(R.id.minute_spinner)).getSelectedItem().toString();
            String notification_time = notification_hour + notification_mins;
            String notification_reps = ((Spinner) findViewById(R.id.reps_spinner)).getSelectedItem().toString();

           mQuestionViewModel.saveQuestionBtnClicked(this, text, notification_hour, notification_mins, notification_time, notification_reps);
           setContentView(R.layout.activity_main);
        }
    }

    public void printAllQuestions(View view) {
        mQuestionViewModel.printAllQuestions(view);
    }

    //todo: för att se vilken fråga jag har vill ha svaret på behöver jag lista alla questions igen. Som jag gör på Order Notification.
    public void printAllAnswers(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String[] texts = mQuestionViewModel.getQuestionTextArray();

        builder.setTitle("Välj och klicka OK")
                .setSingleChoiceItems(texts, checked, (dialog, which) -> {
                    checked = which;
                    buttonText = texts[checked];
                })
                .setPositiveButton("Ok", (dialog, id) -> {
                    // när jag klickar ok vill jag ha alla answer som hör till den frågan.

//                    String pickedQuestionId = mQuestionViewModel.getQuestionId(checked);
                    String pickedQuestionId = upToDateListOfQuestions.get(checked).getId();
//                    mAnswerViewModel.printAllAnswers(pickedQuestionId);

                    upToDateListOfAnswers.forEach(answer -> {
                        if (answer.getFkQuestionId().equals(pickedQuestionId)) {
                            Log.i(TAG, answer.getAnswerText());
                        }
                    });

                })
                .setNegativeButton("Cancel", (dialog, id) -> {

                });
        AlertDialog temp = builder.create();

        //todo: behövs denna?
        temp.setOnDismissListener(dialog -> Log.i(TAG, "what?" + getButtonText()));
        builder.show();

    }



}
