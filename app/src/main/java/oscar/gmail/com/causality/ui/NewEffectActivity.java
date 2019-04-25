package oscar.gmail.com.causality.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import oscar.gmail.com.causality.R;
import oscar.gmail.com.causality.effect.Effect;
import oscar.gmail.com.causality.effect.EffectViewModel;

public class NewEffectActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private final String TAG = "app";
    public EffectViewModel mEffectViewModel;


    public static final String EXTRA_REPLY = "Effect";
    public static final String EXTRA_INTERVAL = "Interval";

    private EditText mEditEffectView;
    private String chosenInterval;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_effect);
        mEditEffectView = findViewById(R.id.edit_question);

        mEffectViewModel = ViewModelProviders.of(this).get(EffectViewModel.class);
//        mEffectViewModel.getAllEffects().observe(this, new Observer<List<Effect>>() {
//            @Override
//            public void onChanged(@Nullable final List<Effect> effects) {
//                String questionText = effects.iterator().next().getText();
//            }
//        });

        populateEffectSpinner();
        createEffectButtonListeners();
    }

    private void populateEffectSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.answer_interval_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
    }


    private void createEffectButtonListeners() {

        // Save-button
        final Button effectSaveButton = findViewById(R.id.button_save);
        effectSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditEffectView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String text = mEditEffectView.getText().toString();

                    Effect effect = new Effect(text);
                    Log.i(TAG, "Effects in viewmodel.getalleffects: " + mEffectViewModel.getAllEffects());

                    mEffectViewModel.insert(effect);


//                    replyIntent.putExtra(EXTRA_REPLY, effect);
//                    replyIntent.putExtra(EXTRA_INTERVAL, chosenInterval);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        // Cancel-button
        final Button cancelButton = findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        chosenInterval = (String) parent.getItemAtPosition(position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
