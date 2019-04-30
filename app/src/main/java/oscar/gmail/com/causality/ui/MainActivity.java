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

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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

    //impl tutorial https://www.techotopia.com/index.php/An_Android_Direct_Reply_Notification_Tutorial
    NotificationManager notificationManager;
    private String channelID = "oscar.gmail.com.causality";
    private static int notificationId = 101;
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



        //impl tutorial https://www.techotopia.com/index.php/An_Android_Direct_Reply_Notification_Tutorial
        notificationManager =
                (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel(channelID,
                "DirectReply News", "Example News Channel");
        handleIntent();
    }

    //impl tutorial https://www.techotopia.com/index.php/An_Android_Direct_Reply_Notification_Tutorial
    private void handleIntent() {
        Intent intent = this.getIntent();
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            String inputString = remoteInput.getCharSequence(
                    KEY_TEXT_REPLY).toString();
            Log.i(TAG, "Text in remoteInput = " + inputString);
        }
    }

    //impl tutorial https://www.techotopia.com/index.php/An_Android_Direct_Reply_Notification_Tutorial
    protected void createNotificationChannel(String id, String name, String description) {
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel =
                new NotificationChannel(id, name, importance);
        channel.setDescription(description);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{100, 200, 300, 400,
                500, 400, 300, 200, 400});
        notificationManager.createNotificationChannel(channel);
    }

    //impl tutorial https://www.techotopia.com/index.php/An_Android_Direct_Reply_Notification_Tutorial
    public void sendNotification(View view) {
        String replyLabel = "Enter your reply here";
        RemoteInput remoteInput =
                new RemoteInput.Builder(KEY_TEXT_REPLY)
                        .setLabel(replyLabel)
                        .build();
        Intent resultIntent = new Intent(this, MainActivity.class);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        final Icon icon =
                Icon.createWithResource(MainActivity.this,
                        android.R.drawable.ic_dialog_info);
        Notification.Action replyAction =
                new Notification.Action.Builder(
                        icon,
                        "Reply", resultPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        Notification newMessageNotification =
                new Notification.Builder(this, channelID)
                        .setColor(ContextCompat.getColor(this,
                                R.color.colorPrimary))
                        .setSmallIcon(
                                android.R.drawable.ic_dialog_info)
                        .setContentTitle("My Notification")
                        .setContentText("This is a test message")
                        .addAction(replyAction).build();
        NotificationManager notificationManager =
                (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId,
                newMessageNotification);
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
