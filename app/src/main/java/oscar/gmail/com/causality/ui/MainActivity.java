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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import oscar.gmail.com.causality.R;
import oscar.gmail.com.causality.effect.Effect;
import oscar.gmail.com.causality.effect.EffectViewModel;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "app";

    public static final int NEW_EFFECT_ACTIVITY_REQUEST_CODE = 1;
    public static final int CREATE_INQUIRY_ACTIVITY_REQUEST_CODE = 2;

    //New Effect
    public EffectViewModel mEffectViewModel;
    public static final String EXTRA_REPLY = "Effect";
    public static final String EXTRA_INTERVAL = "Interval";
    private EditText mEditEffectView;
    private String chosenEffectInterval = "";

    //Create Inquiry
    private String chosenInquiryEffect = "";

    private List<Effect> upToDateListofEffects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEffectViewModel = ViewModelProviders.of(this).get(EffectViewModel.class);
        mEffectViewModel.getAllEffects().observe(this, words -> {
            // Update the cached copy of the words in the adapter.
            upToDateListofEffects = words;
        });

    }

    public void newEffectBtnClicked(View v) {
        setContentView(R.layout.new_effect);
        populateEffectSpinner();
    }

    public void createInquiryBtnClicked(View v) {
    setContentView(R.layout.create_inquiry);
    populateInquirySpinners();
    }

    private void populateInquirySpinners() {
        //Todo: hämta en spinner via factory i stället?
        Spinner allEffects = findViewById(R.id.all_effects_spinner);


        ArrayAdapter<CharSequence> effectsForSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        upToDateListofEffects.forEach(effect -> {
            effectsForSpinner.add(effect.getText());
        });

        effectsForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        allEffects.setAdapter(effectsForSpinner);

        allEffects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenInquiryEffect = (String) parent.getItemAtPosition(position);

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //todo: spinner för Cause
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
            mEffectViewModel.insert(effect);
            setContentView(R.layout.activity_main);
        }
    }

    private void populateEffectSpinner() {
        Spinner spinner = findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.answer_interval_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenEffectInterval = (String) parent.getItemAtPosition(position);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }






//    public void newCauseBtnClicked(View v) {
//        setContentView(R.layout.new_effect);
//    }


//
//    public void investigatInquiryBtnClicked(View v) {
//        setContentView(R.layout.)
//    }






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
