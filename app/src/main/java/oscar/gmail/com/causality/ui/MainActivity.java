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

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import oscar.gmail.com.causality.R;
import oscar.gmail.com.causality.question.Question;
import oscar.gmail.com.causality.question.QuestionViewModel;
import oscar.gmail.com.causality.services.AlarmReceiver;
import oscar.gmail.com.causality.services.NotificationNotifier;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "causalityapp";

    //New Question
    public QuestionViewModel mQuestionViewModel;
    public static final String EXTRA_REPLY = "Question";
    public static final String EXTRA_INTERVAL = "Interval";
    private EditText createQuestionEditText;
    private String chosenQuestionInterval = "";

    //Create Inquiry
    private String chosenQuestionEffect = "";
    private List<Question> upToDateListofQuestions;

    //Create Notification
    NotificationNotifier notifier;

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

    }

    public void sendAlarm(View view) {
        Log.i(TAG, "MainActivity: Setting up AlarmManager");

        // Calendar
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") int minutes = Integer.parseInt(new SimpleDateFormat("mm").format(calendar.getTime()));
        @SuppressLint("SimpleDateFormat") int hours = Integer.parseInt(new SimpleDateFormat("HH").format(calendar.getTime()));
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.set(Calendar.MINUTE, minutes + 2);

        // intent
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        intent.setAction("MY_NOTIFICATION_MESSAGE");
        String dataint = Integer.toString(hours) + ":" + Integer.toString(minutes + 2);
        intent.putExtra("deliverytime", dataint);
        Log.i(TAG, "Main: Data in AlarmIntent = " + intent.getStringExtra("deliverytime"));

        // pendingIntent
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(this, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // AlarmManager
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        //todo: RTC_WAKEUP is for dev.purpose. Change to RTC at prod.
        am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        //am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }


    //todo: ska detta göras i annan tråd eller rent av från AnswerViewModel??
    public void sendNotification(View view) {
        notifier = new NotificationNotifier(this);
        notifier.sendNotification(view);
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

            Log.i(TAG, "not.time = " + notification_time);
            Question question = new Question(text,  null);
            mQuestionViewModel.insert(question);
            setContentView(R.layout.activity_main);
        }
    }

}
