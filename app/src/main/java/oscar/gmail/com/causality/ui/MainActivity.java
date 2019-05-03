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
    private List<Question> upToDateListofQuestions;

    public AnswerViewModel mAnswerViewModel;
    private List<Answer> upToDateListOfAnswers;

    int checked = -1;
    String buttonText;

    //New Question
    public static final String EXTRA_REPLY = "Question";
    public static final String EXTRA_INTERVAL = "Interval";
    private EditText createQuestionEditText;
    private String chosenQuestionInterval = "";

    //Create Notification
    NotificationNotifier notifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mQuestionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        mAnswerViewModel = ViewModelProviders.of(this).get(AnswerViewModel.class);

        //caches all the questions
        mQuestionViewModel.getAllQuestions()
                            .observe(this, questions -> upToDateListofQuestions = questions);

        mAnswerViewModel.getAllAnswers()
                            .observe(this, answers -> upToDateListOfAnswers = answers);
    }




    public void scheduleJob(View view) {

        int[] alarms = {1128, 1130};

        for (int alarm: alarms) {
            ComponentName componentName = new ComponentName( this, AlarmJobService.class);
            JobInfo info = new JobInfo.Builder(alarm, componentName)
                    .setRequiresCharging(true)
                    .setMinimumLatency(1)
                    .setOverrideDeadline(1)
                    .build();
            // för loggning
            JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            int resultCode = jobScheduler.schedule(info);
            if (resultCode == JobScheduler.RESULT_SUCCESS) {
                Log.i(TAG, "Main: Job Scheduled success");
            } else {
                Log.i(TAG, "Main: Job Scheduling failed");
            }
        }
    }




    //ger användaren en dialogruta så hen kan välja en fråga som därefter skapar upp en notifikation
    public void getListOfQuestionsAsAlertDialog(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //gör om listan med question till array med questionText
        String[] texts = new String[upToDateListofQuestions.size()];
        for(int i = 0; i < upToDateListofQuestions.size(); i++) {
            texts[i] =  upToDateListofQuestions.get(i).getQuestionText();
        }

        builder.setTitle("Välj och klicka OK")
                .setSingleChoiceItems(texts, checked, (dialog, which) -> {
                    checked = which;
                    buttonText = texts[checked];
                })
                .setPositiveButton("Ok", (dialog, id) -> {
                    Question q = upToDateListofQuestions.get(checked);
                    sendNotification(getButtonText(), q.getId());
                })
                .setNegativeButton("Cancel", (dialog, id) -> {

                });
        AlertDialog temp = builder.create();

        //todo: behövs denna?
        temp.setOnDismissListener(dialog -> Log.i(TAG, "what?" + getButtonText()));
        builder.show();
    }

    public String getButtonText() {
        return buttonText;
    }

    //todo: ska detta göras i annan tråd eller rent av från AnswerViewModel??
    public void sendNotification(String questionText, String questionId) {
        //todo: skicka med frågans text.
        notifier = new NotificationNotifier(this);
        notifier.sendNotification(questionText, questionId);
    }

    public void newQuestionBtnClicked(View v) {
        setContentView(R.layout.new_question);
    }



    public void cancelBtnClicked(View v) {
        setContentView(R.layout.activity_main);
    }



    public void saveQuestionBtnClicked(View v) {

        createQuestionEditText = findViewById(R.id.new_question_text);
        //todo: fånga upp notisTid
        if (TextUtils.isEmpty(createQuestionEditText.getText())) {
            setResult(RESULT_CANCELED);
        } else {
            String text = createQuestionEditText.getText().toString();
            String notification_hour = ((Spinner)findViewById(R.id.hour_spinner)).getSelectedItem().toString();
            String notification_mins = ((Spinner)findViewById(R.id.minute_spinner)).getSelectedItem().toString();
            String notification_time = notification_hour + ":" +notification_mins;
            String notification_reps = ((Spinner) findViewById(R.id.reps_spinner)).getSelectedItem().toString();

            Log.i(TAG, "not.time = " + notification_time);
            Log.i(TAG, "reps is = " + notification_reps);
            Question question = new Question(text,  notification_time);
            mQuestionViewModel.insert(question);
            setContentView(R.layout.activity_main);
        }

        // if question is in upToDateListofQuestions
        // skapa notifications * reps


    }

    public void printAllQuestions(View view) {
        upToDateListofQuestions.forEach(question -> Log.i(TAG, question.getQuestionText()));
    }

    //todo: för att se vilken fråga jag har vill ha svaret på behöver jag lista alla questions igen. Som jag gör på Order Notification.
    public void printAllAnswers(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //gör om listan med question till array med questionText
        String[] texts = new String[upToDateListofQuestions.size()];
        for(int i = 0; i < upToDateListofQuestions.size(); i++) {
            texts[i] =  upToDateListofQuestions.get(i).getQuestionText();
        }

        builder.setTitle("Välj och klicka OK")
                .setSingleChoiceItems(texts, checked, (dialog, which) -> {
                    checked = which;
                    buttonText = texts[checked];
                })
                .setPositiveButton("Ok", (dialog, id) -> {

                    // när jag klickar ok vill jag ha alla answer som hör till den frågan.

                    String pickedQuestionId = upToDateListofQuestions.get(checked).getId();
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
