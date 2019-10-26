/**
 * This program is the java behind the main activity of our app. Tha activity displays
 * game instructions to the user and takes two names to play tic tac tow with
 * CPSC 312-01, Fall 2019
 * Programming Assignment #5
 * No sources to cite.
 *
 * @author Alex Giacobbi
 * @version v1.0 10/20/19
 */
package com.example.pa5_agiacobbi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * MainActivity class is the welcome screen of our tic tac toe game. It displays
 * directions for play and allows users to enter two names to play with. PLAY! button
 * allows users to start a game once both have entered a unique name
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Sets up the activity and registers all elements that will need validation
     * adds a click listener to the button and ensures two unique names are entered before
     * creating an intent to start a game
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText player1 = findViewById(R.id.playerOneName);
        final EditText player2 = findViewById(R.id.playerTwoName);
        Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p1Name = player1.getText().toString();
                String p2Name = player2.getText().toString();

                if (isValid(p1Name, p2Name)) {
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    intent.putExtra("playerOneName", p1Name);
                    intent.putExtra("playerTwoName", p2Name);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Must enter two unique names", Toast.LENGTH_SHORT).show();
                }
            }

            private boolean isValid(String p1Name, String p2Name) {
                if (p1Name.length() > 0 && p2Name.length() > 0) {
                    if (!p1Name.equals(p2Name)) {
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
