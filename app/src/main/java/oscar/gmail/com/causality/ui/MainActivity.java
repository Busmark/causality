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

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import oscar.gmail.com.causality.R;
import oscar.gmail.com.causality.effect.Effect;
import oscar.gmail.com.causality.effect.EffectViewModel;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "app";

    public static final int NEW_EFFECT_ACTIVITY_REQUEST_CODE = 1;
    public static final int CREATE_INQUIRY_ACTIVITY_REQUEST_CODE = 2;

    public EffectViewModel mEffectViewModel;


    public static final String EXTRA_REPLY = "Effect";
    public static final String EXTRA_INTERVAL = "Interval";

    private EditText mEditEffectView;
    private String chosenInterval;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //displayAllQuestions();
//        displayButtons();
        //createNotification();
        //getAlertDialog();
        mEffectViewModel = ViewModelProviders.of(this).get(EffectViewModel.class);

    }

    public void newEffectBtnClicked(View v) {
        setContentView(R.layout.activity_new_effect);
    }

    public void cancelBtnClicked(View v) {
        setContentView(R.layout.activity_main);
    }
    public void saveEffectBtnClicked(View v) {
        mEditEffectView = findViewById(R.id.edit_question);

        if (TextUtils.isEmpty(mEditEffectView.getText())) {
            setResult(RESULT_CANCELED);
        } else {
            String text = mEditEffectView.getText().toString();
            Effect effect = new Effect(text);
            Log.i(TAG, effect.getText());
//            mEffectViewModel.insert(effect);
            setContentView(R.layout.activity_main);
        }
    }




//    public void newCauseBtnClicked(View v) {
//        setContentView(R.layout.activity_new_effect);
//    }

//    public void createInquiryBtnClicked(View v) {
//        setContentView(R.layout.activity_create_inquiry);
//    }
//
//    public void investigatInquiryBtnClicked(View v) {
//        setContentView(R.layout.)
//    }





    //todo: kan jag lägga clicklyssnarna i xml´en istället alt i EffectViewModel?
    private void displayButtons() {
        Button newEffect = findViewById(R.id.button_new_effect);
//        Button newCause = findViewById(R.id.button_new_cause);
        Button createInquiry = findViewById(R.id.button_create_inquiry);
//        Button investigateInquiry = findViewById(R.id.button_investigate_inquiry);

        newEffect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_new_effect);
                mEditEffectView = findViewById(R.id.edit_question);
//                Intent intent = new Intent(MainActivity.this, NewEffectActivity.class);
//                startActivityForResult(intent, NEW_EFFECT_ACTIVITY_REQUEST_CODE);
            }
        });
        createInquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, CreateInquiryActivity.class);
//                startActivityForResult(intent, CREATE_INQUIRY_ACTIVITY_REQUEST_CODE);
            }
        });

    }


    //    /**
//     * Kallas på när man går tillbaka till MainActivity
//     * @param requestCode
//     * @param resultCode
//     * @param data
//     */
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        Log.i(TAG, "onActivityResult.requestCode: " + requestCode);
//        Log.i(TAG, "onActivityResult.resultCode: " + resultCode);
//        Log.i(TAG, "onActivityResult.data: " + data);
//
//
//        if (requestCode == CREATE_INQUIRY_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
//            Log.i(TAG, "Return from create Inquiry");
//        }
//        //todo: hantera if-else-satsen här
//        if (requestCode == NEW_EFFECT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
//            Effect effect = new Effect(data.getStringExtra(NewEffectActivity.EXTRA_REPLY));
//            mEffectViewModel.insert(effect);
//        } else {
//            Toast.makeText(
//                    getApplicationContext(),
//                    R.string.empty_not_saved,
//                    Toast.LENGTH_LONG).show();
//        }
//    }

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
//                String questionText = effects.iterator().next().getText();
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
