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

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import oscar.gmail.com.causality.R;
import oscar.gmail.com.causality.question.Effect;
import oscar.gmail.com.causality.question.EffectViewModel;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "mActivity";

    public static final int NEW_QUESTION_ACTIVITY_REQUEST_CODE = 1;

    public EffectViewModel mEffectViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get a new or existing ViewModel from the ViewModelProvider.
        mEffectViewModel = ViewModelProviders.of(this).get(EffectViewModel.class);


        //displayAllQuestions();
        displayButtons();
        //createNotification();
        //getAlertDialog();

        mEffectViewModel.getAllQuestions().observe(this, new Observer<List<Effect>>() {
            @Override
            public void onChanged(@Nullable final List<Effect> effects) {
                //String questionText = effects.iterator().next().getQuestionText();
                // Update the cached copy of the effects in the adapter.

                Log.i(TAG, "List size is: " + effects.size());

            }
        });

    }

    private void displayButtons() {
        Button newEffect = findViewById(R.id.button_new_effect);
        Button newCause = findViewById(R.id.button_new_cause);
        Button


        newEffect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewEffectActivity.class);
                startActivityForResult(intent, NEW_QUESTION_ACTIVITY_REQUEST_CODE);
            }
        });
    }









    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_QUESTION_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Effect effect = new Effect(data.getStringExtra(NewEffectActivity.EXTRA_REPLY));
            mEffectViewModel.insert(effect);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

//    public void displayAllQuestions() {
//
//        RecyclerView recyclerView = findViewById(R.id.recyclerview);
//        final EffectListAdapter adapter = new EffectListAdapter(this);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        mEffectViewModel.getAllQuestions().observe(this, new Observer<List<Effect>>() {
//            @Override
//            public void onChanged(@Nullable final List<Effect> effects) {
//                String questionText = effects.iterator().next().getQuestionText();
//                // Update the cached copy of the effects in the adapter.
//                adapter.setQuestions(effects);
//            }
//        });
//    }
//
//    public void createNotification() {
//
////      Skapa upp en Notification som startar en Activity
//        //todo: kan aktiviteten jag startar upp vara samma som används i NewEffectActivity, fast utan
//        //att köra aktiviteten i foreground?
//        Notification.createNotificationChannel(this);
//        Notification.createNotification(this);
//    }
//
//    public void getAlertDialog() {
//
////      skapar upp en AlertDialog som sparar ner svaret till databasen
//        final Notification qn = new Notification();
//        AlertDialog ad = qn.getAlertDialogWithMultipleList(this);
//        ad.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                Effect effect = new Effect("" + qn.getButtonText());
//                mEffectViewModel.insert(effect);
//            }
//        });
//        ad.show();
//    }

}
