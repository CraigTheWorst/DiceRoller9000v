package com.example.diceroller9000;

import android.os.Bundle;
import android.os.VibrationEffect;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import com.bumptech.glide.Glide;
import android.os.Vibrator;


import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Spinner diceSpinner;
    private EditText modifierInput;
    private ImageButton rollButton;
    private TextView resultText;
    private Spinner numberOfDiceSpinner;
    private ImageButton leftButton;
    private ImageButton rightButton;
    private int currentRollIndex = 0;
    private ArrayList<String> rollsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView gifView = findViewById(R.id.buttonCircle);
        gifView.setImageResource(R.drawable.gifcircle);



        diceSpinner = findViewById(R.id.dice_spinner);
        modifierInput = findViewById(R.id.modifier_input);
        rollButton = findViewById(R.id.roll_button);
        resultText = findViewById(R.id.result_text);
        numberOfDiceSpinner = findViewById(R.id.number_of_dice_spinner);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);
        resultText = findViewById(R.id.result_text);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dice_types, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diceSpinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> numberOfDiceAdapter = ArrayAdapter.createFromResource(this,
                R.array.number_of_dice, android.R.layout.simple_spinner_item);

        numberOfDiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberOfDiceSpinner.setAdapter(numberOfDiceAdapter);

        rollButton.setOnClickListener(view -> {
            int numberOfDice = Integer.parseInt(numberOfDiceSpinner.getSelectedItem().toString());
            int diceType = getDiceType();
            int modifier = 0;
            if(modifierInput.getText().length() > 0){
                modifier = Integer.parseInt(modifierInput.getText().toString());
            }
            int total = 0;
            String rollsString = "Rolls: ";
            for (int i = 0; i < numberOfDice; i++) {
                int roll = rollDice(diceType);
                rollsString += roll + " + ";
                total += roll;
            }
            rollsString = rollsString.substring(0, rollsString.length() - 3);
            rollsString += " + " + modifier + " = " + (total + modifier);
            resultText.setText(rollsString);

            rollsList.add(rollsString);
            currentRollIndex = rollsList.size() - 1;

        });

        TextView clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView resultText = findViewById(R.id.result_text);
                resultText.setText("");

                EditText modifierInput = findViewById(R.id.modifier_input);
                modifierInput.setText("");
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentRollIndex > 0) {
                    currentRollIndex--;
                    resultText.setText(rollsList.get(currentRollIndex));
                }
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentRollIndex < rollsList.size() - 1) {
                    currentRollIndex++;
                    resultText.setText(rollsList.get(currentRollIndex));
                }
            }
        });




        View view = findViewById(R.id.main_layout);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return false;
            }
        });





    }

    private void updateRollsList(String roll) {
        rollsList.add(roll);
        currentRollIndex = rollsList.size() - 1;
    }

    private int getDiceType() {
        String diceTypeString = diceSpinner.getSelectedItem().toString();
        return Integer.parseInt(diceTypeString.substring(1));
    }

    private int rollDice(int diceType) {
        Random random = new Random();
        return random.nextInt(diceType) + 1;
    }
}
