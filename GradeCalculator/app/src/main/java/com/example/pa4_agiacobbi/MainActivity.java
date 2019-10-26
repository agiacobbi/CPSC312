/**
 * This program is the main activity view of our grade calculator app it uses
 * the grade calculator model to show the user what grade they need to earn a
 * specified score in a class given their current average and the weight of
 * the final
 * CPSC 312-02, Fall 2019
 * Programming Assignment #4
 * No sources to cite.
 *
 * @author Alex Giacobbi
 * @version v1.0 10/8/19
 */

package com.example.pa4_agiacobbi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Class MainActivity controls the elements in our app view. It uses the
 * grade calculator model to show the user what grade they need to earn a
 * specified score in a class given their current average and the weight of
 * the final. On creation, it adds an even listener to the calculate button
 * and displays results based on the user's input. If input is missing it
 * throws a Toast error message.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonCalculate = findViewById(R.id.button_calculate);
        final GradeCalculator calculator = new GradeCalculator();
        final EditText editTextTarget = findViewById(R.id.et_min_avg);
        final EditText editTextCurrent = findViewById(R.id.et_cur_avg);
        final EditText editTextWeight = findViewById(R.id.et_final_weight);
        final EditText editTextDesired = findViewById(R.id.et_desired_grade);
        final TextView textViewOutput = findViewById(R.id.tv_output);
        final TextView textViewLetter = findViewById(R.id.tv_letter_output);
        final DecimalFormat df = new DecimalFormat("#.00");

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double required;

                try {
                    calculator.setTargetPercentage(Double.parseDouble(editTextTarget.getText().toString()));
                    calculator.setCurrentPercentage(Double.parseDouble(editTextCurrent.getText().toString()));
                    calculator.setFinalWeight(Double.parseDouble(editTextWeight.getText().toString()));

                    required = calculator.calculateFinalGrade();

                    if (required > 0) {
                        String message = getString(R.string.you_need_score) + " " +
                                df.format(required) + " " + getString(R.string.on_the_final);
                        textViewOutput.setText(message);
                    } else {
                        textViewOutput.setText(R.string.do_not_take_final);
                    }
                    textViewLetter.setText(editTextDesired.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(MainActivity.this, getString(R.string.toast_message), Toast.LENGTH_LONG);
                    toast.show();
                    textViewOutput.setText("");
                    textViewLetter.setText("");
                }
            }
        });
    }
}

