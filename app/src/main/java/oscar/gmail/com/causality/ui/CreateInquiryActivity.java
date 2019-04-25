package oscar.gmail.com.causality.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

import oscar.gmail.com.causality.R;
import oscar.gmail.com.causality.effect.Effect;
import oscar.gmail.com.causality.effect.EffectViewModel;

public class CreateInquiryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private final String TAG = "app";
    public EffectViewModel mEffectViewModel;


    public static final String EXTRA_EFFECT = "effect";
    public static final String EXTRA_CAUSE = "cause";

    private String chosenEffect;
    private String chosenCause;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_inquiry);

        mEffectViewModel = ViewModelProviders.of(this).get(EffectViewModel.class);

        populateSpinner();
        createButtonListeners();
    }

    private void createButtonListeners() {
// Save-button
        final Button saveButton = findViewById(R.id.button_save_inquiry);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                finish();
            }
        });
// Cancel-button
        final Button cancelButton = findViewById(R.id.button_cancel_inquiry);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void populateSpinner() {
        Spinner allEffectsSpinner = (Spinner) findViewById(R.id.all_effects_spinner);
        Spinner allCausesSpinner = (Spinner) findViewById(R.id.all_causes_spinner);

        //todo: hämta alla Effect i databasen
        List<Effect> eff = mEffectViewModel.getAllEffects().getValue();
        try {
            Log.i(TAG, "Antal element i listan: " + eff.size());
        } catch (Exception e) {
            Log.i(TAG, "Listan var tom...");
        }

        Effect effect = new Effect("temp");
        Effect[] effects = {effect};

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, effects);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        allEffectsSpinner.setAdapter(adapter);
        allCausesSpinner.setAdapter(adapter);

        allEffectsSpinner.setOnItemSelectedListener(this);
        allCausesSpinner.setOnItemSelectedListener(this);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        chosenEffect = (String) parent.getItemAtPosition(position);
//        chosenCause = (String) parent.getItemAtPosition(position);

        Log.i(TAG, "Position is: " + position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
