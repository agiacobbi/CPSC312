/**
 * The layout for the main activity is defined programmatically here. This layout extends
 * grid layout and contains a Button to create a new note and a ListView to display notes
 * CPSC 312-01, Fall 2019
 * Programming Assignment #6
 * No sources to cite.
 *
 * @author Alex Giacobbi and Jalen Tacsiat
 * @version v1.0 11/06/19
 *
 * Alex contributions:
 * Created elements
 *
 * Jalen Contributions:
 * Added final touches on MainLayout to match it with example
 * Setup layout parameters
 */
package com.example.notetaker;

import android.content.Context;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;

/**
 * This is class is used to create the layout for the MainActivity
 */
public class MainLayout extends GridLayout {
    public MainLayout(Context context) {
        super(context);

        ListView notesListView;
        Button newNoteButton;

        setColumnCount(1);
        //sets up layout params for the newNoteButton
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.rowSpec = GridLayout.spec(0, 1,1/2);
        layoutParams.columnSpec = GridLayout.spec(0, 1, 1);

        //creates newNoteButton and adds it to the screen
        /*newNoteButton = new Button(context);
        newNoteButton.setId(R.id.newNoteButton);
        newNoteButton.setText(R.string.add_note);
        newNoteButton.setLayoutParams(layoutParams);
        this.addView(newNoteButton);*/

        //creates layout parameters for the listView
        GridLayout.LayoutParams notesListLayout = new GridLayout.LayoutParams();
        notesListLayout.rowSpec = GridLayout.spec(1, 1, 1);
        notesListLayout.columnSpec = GridLayout.spec(0, 1, 1);

        //creates a listView that is used contain a list of notes and adds it to the screen
        notesListView = new ListView(context);
        notesListView.setId(R.id.notesListView);
        notesListLayout.setGravity(Gravity.TOP);
        notesListView.setLayoutParams(notesListLayout);
        this.addView(notesListView);
    }
}
