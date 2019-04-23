package oscar.gmail.com.causality;

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

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "mainA";

    public static final int NEW_QUESTION_ACTIVITY_REQUEST_CODE = 1;

    public QuestionViewModel mQuestionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final QuestionListAdapter adapter = new QuestionListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a new or existing ViewModel from the ViewModelProvider.
        mQuestionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedQuestions.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mQuestionViewModel.getAllQuestions().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(@Nullable final List<Question> questions) {
                // Update the cached copy of the questions in the adapter.
                adapter.setQuestions(questions);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewQuestionActivity.class);
                startActivityForResult(intent, NEW_QUESTION_ACTIVITY_REQUEST_CODE);
            }
        });

//      Skapa upp en Notification som startar en Activity
        //todo: kan aktiviteten jag startar upp vara samma som används i NewQuestionActivity, fast utan
        //att köra aktiviteten i foreground?
        QuestionNotification.createNotificationChannel(this);
        QuestionNotification.createNotification(this);

//      skapar upp en AlertDialog som sparar ner svaret till databasen
        final QuestionNotification qn = new QuestionNotification();
        AlertDialog ad = qn.getAlertDialogWithMultipleList(this);
        ad.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
               Question question = new Question("" + qn.getButtonText());
               mQuestionViewModel.insert(question);
            }
        });
        ad.show();

        Log.i(TAG, "end of onCreate");

    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_QUESTION_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Question question = new Question(data.getStringExtra(NewQuestionActivity.EXTRA_REPLY));
            mQuestionViewModel.insert(question);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

}
