package com.example.pa5_agiacobbi;

/**
 * This program is the java behind the game activity of our app. It connects the TicTacToeBoard
 * class, containing the game logic, to our android GUI. It handles user input and updates the
 * view accordingly
 * CPSC 312-01, Fall 2019
 * Programming Assignment #5
 * Icons made by https://flaticon.com/authors/freepik
 * from Flaticon.com
 *
 * @author Alex Giacobbi
 * @version v1.0 10/20/19
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * GameActivity class is our game controller for tic tac toe. It manages a
 * grid view containing a 'board' of image buttons and a button to play again and
 * quit. This class connects the logic of our tic tac toe game to this view and
 * updates the images and text as necessary as well as handles user inputs
 */
public class GameActivity extends AppCompatActivity implements View.OnClickListener{
    String playerOneName;
    String playerTwoName;
    GameStats player1Stats;
    GameStats player2Stats;
    ImageButton cell00;
    ImageButton cell01;
    ImageButton cell02;
    ImageButton cell10;
    ImageButton cell11;
    ImageButton cell12;
    ImageButton cell20;
    ImageButton cell21;
    ImageButton cell22;
    ImageView curPlayer;
    TextView gameStatusText;
    Button playAgain;
    Button quit;
    TicTacToeBoard board;
    boolean isWon;
    int index;
    char[] players;
    int winner;

    /**
     * This is our main click listener function for the board. When a user clicks on a cell to
     * take their turn, this function updates the icon in the image button and logs the turn in
     * the logic game model. It also checks if the player has won the game or if the game is a
     * scratch after each move the button is also disabled so the cell cannot be played twice
     * @param v the view element
     */
    @Override
    public void onClick(View v) {
        ImageButton button = (ImageButton) v;
        String buttonLocation = button.getTag().toString();
        int x = Integer.parseInt("" + buttonLocation.charAt(0));
        int y = Integer.parseInt("" + buttonLocation.charAt(1));
        Coordinates playerMove = new Coordinates(x, y);
        int imgID;

        if (index == 0) {
            imgID = R.drawable.player_one_icon;
        } else {
            imgID = R.drawable.player_two_icon;
        }

        board.makeMove(playerMove, players[index]);
        button.setImageResource(imgID);
        button.setEnabled(false);
        isWon = board.isWinner(players[index]);
        if (isWon) {
            winner = players[index];
            endGame();
        } else if (board.isFull()) {
            endGame();
        } else {
            index = (index + 1) % 2;
            setTurnMessage(index);
        }

    }

    /**
     * If a win or scratch is detected by the click listener for the image buttons,
     * the game is over and the message is updated appropriately. Game stats are updated
     * for each player and the play again button is made visible. All image buttons are
     * disabled so they cannot be changed after the end of a game
     */
    private void endGame() {
        if (winner == 'X'){
            String message = "Congrats, " + playerOneName + "! You won! Play again?";
            curPlayer.setImageResource(R.drawable.player_one_icon);
            gameStatusText.setText(message);
            player1Stats.addWin();
            player2Stats.addLoss();
        } else if (winner == 'O') {
            String message = "Congrats, " + playerTwoName + "! You won! Play again?";
            curPlayer.setImageResource(R.drawable.player_two_icon);
            gameStatusText.setText(message);
            player1Stats.addLoss();
            player2Stats.addWin();
        } else {
            String message = "Scratch game. Play again?";
            curPlayer.setImageResource(R.drawable.saturn);
            gameStatusText.setText(message);
            player1Stats.addScratch();
            player2Stats.addScratch();
        }
        playAgain.setVisibility(Button.VISIBLE);
        setButtonAccess(false);

    }

    /**
     * sets the button to enabled/disabled as specified by the parameter. Used disable all buttons
     * at the end of a game and enable all button at the beginning of a new game
     * @param access a boolean indicating whether to enable (true) or disable (false)
     */
    private void setButtonAccess(boolean access) {
        cell00.setEnabled(access);
        cell01.setEnabled(access);
        cell02.setEnabled(access);
        cell10.setEnabled(access);
        cell11.setEnabled(access);
        cell12.setEnabled(access);
        cell20.setEnabled(access);
        cell21.setEnabled(access);
        cell22.setEnabled(access);
    }

    /**
     * Runs on the creation of the activity. Initializes game stats for both players
     * and calls methods to register view elements, set up a new game, and add listeners
     * to all buttons
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        player1Stats = new GameStats('X');
        player2Stats = new GameStats('O');

        getViewElements();
        setupNewGame();
        setTurnMessage(index);
        setListeners();
    }

    /**
     * Sets click listeners for all buttons.
     */
    private void setListeners() {
        cell00.setOnClickListener(this);
        cell01.setOnClickListener(this);
        cell02.setOnClickListener(this);
        cell10.setOnClickListener(this);
        cell11.setOnClickListener(this);
        cell12.setOnClickListener(this);
        cell20.setOnClickListener(this);
        cell21.setOnClickListener(this);
        cell22.setOnClickListener(this);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupNewGame();
                setTurnMessage(index);
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.this.finish();
            }
        });
    }

    /**
     * Sets up a new game of tic tac toe. Resets board and isWon indicator. Randomly selects
     * first player, hides play again button, clears images from previous game and enables
     * all imagebuttons
     */
    private void setupNewGame() {
        board = new TicTacToeBoard(3);
        isWon = false;
        index = (int)(Math.random() * 2);         // Randomly selects which player will go first
        players = new char[]{'X', 'O'};
        winner = '-';
        playAgain.setVisibility(Button.INVISIBLE);
        clearCellImages();
        setButtonAccess(true);
    }

    /**
     * Clears the image from each image button to refresh the board for a new game
     */
    private void clearCellImages() {
        cell00.setImageResource(android.R.color.transparent);
        cell01.setImageResource(android.R.color.transparent);
        cell02.setImageResource(android.R.color.transparent);
        cell10.setImageResource(android.R.color.transparent);
        cell11.setImageResource(android.R.color.transparent);
        cell12.setImageResource(android.R.color.transparent);
        cell20.setImageResource(android.R.color.transparent);
        cell21.setImageResource(android.R.color.transparent);
        cell22.setImageResource(android.R.color.transparent);
    }

    /**
     * Registers all view elements by their xml id so they can be accessed and changed as
     * necessary
     */
    private void getViewElements() {
        Intent intent = getIntent();
        if (intent != null) {
            playerOneName = intent.getStringExtra("playerOneName");
            playerTwoName = intent.getStringExtra("playerTwoName");
        }

        cell00 = findViewById(R.id.cell00);
        cell01 = findViewById(R.id.cell01);
        cell02 = findViewById(R.id.cell02);
        cell10 = findViewById(R.id.cell10);
        cell11 = findViewById(R.id.cell11);
        cell12 = findViewById(R.id.cell12);
        cell20 = findViewById(R.id.cell20);
        cell21 = findViewById(R.id.cell21);
        cell22 = findViewById(R.id.cell22);
        curPlayer = findViewById(R.id.currentPlayer);
        gameStatusText = findViewById(R.id.statusMessage);
        playAgain = findViewById(R.id.playAgain);
        quit = findViewById(R.id.quit);
    }

    /**
     * Sets the turn message and image for the player depending on whose turn it is
     *
     * @param index 0 or 1 for player indicating whose turn it is
     */
    private void setTurnMessage(int index) {
        if (index == 0) {
            String message = playerOneName + "'s turn";
            curPlayer.setImageResource(R.drawable.player_one_icon);
            gameStatusText.setText(message);
        } else {
            String message = playerTwoName + "'s turn";
            curPlayer.setImageResource(R.drawable.player_two_icon);
            gameStatusText.setText(message);
        }
    }
}
