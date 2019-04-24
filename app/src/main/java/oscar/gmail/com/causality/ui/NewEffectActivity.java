package oscar.gmail.com.causality.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import oscar.gmail.com.causality.R;

public class NewEffectActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private final String TAG = "qActivity";


    public static final String EXTRA_REPLY = "Effect";
    public static final String EXTRA_INTERVAL = "Interval";

    private EditText mEditQuestionView;

    private String chosenInterval;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_effect);
        mEditQuestionView = findViewById(R.id.edit_question);


        populateSpinner();

        createButtonListeners();

    }

    private void createButtonListeners() {

        // Save-button
        final Button saveButton = findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditQuestionView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String question = mEditQuestionView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, question);
                    replyIntent.putExtra(EXTRA_INTERVAL, chosenInterval);
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

    private void populateSpinner() {
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        chosenInterval = (String) parent.getItemAtPosition(position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
