/**
 * This program is the note edit activity of a note taking app. Here, the user can set a
 * title for the note, fill the note with content, and choose a category for the note using
 * a spinner
 * CPSC 312-01, Fall 2019
 * Programming Assignment #6
 * No sources to cite.
 *
 * @author Alex Giacobbi and Jalen Tacsiat
 * @version v2.0 11/06/19
 *
 * Alex contributions:
 * mangaged loading in note from intent
 * formed intents to pass back to MainActivity
 * set spinner to type specified in note
 *
 * Minor changes to the intent structure to reflect
 *  use of database ids
 * Added a back button to return users to the MainActivity
 *   case they don't want to save their changes
 *
 * Jalen contributions:
 * Created listeners for buttons
 */

package com.example.notetaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * This class is the activity for users to edit a note. Users can change the title,
 * content, type and save the note when they are finished.
 */
public class NoteActivity extends AppCompatActivity {
    static final String TAG = "inNoteActivity";
    static final int PERSONAL = 0;
    static final int SCHOOL = 1;
    static final int WORK = 2;
    static final int OTHER = 3;

    private Note note;
    private int id;

    /**
     * When activity is initialized, note is gathered from intent and view is updated
     * to reflect current note contents. Sets up click listener for done button and
     * connects all view elements from layout
     *
     * @param savedInstanceState bundle saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NoteLayout noteLayout = new NoteLayout(this);
        setContentView(noteLayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText noteTitle = findViewById(R.id.noteTitle);
        final EditText content = findViewById(R.id.noteContent);
        final Spinner noteType = findViewById(R.id.noteType);
        id = -1;

        Intent intent = getIntent();
        if(intent != null){
            note = (Note)intent.getSerializableExtra("note");
            id = intent.getIntExtra("id", -1);
            Log.d(TAG, "onCreate id: " + id);
            noteTitle.setText(note.getTitle());
            content.setText(note.getContent());
        }

        noteTitle.setText(note.getTitle());
        content.setText(note.getContent());
        noteType.setSelection(getAdapterIndex());

        noteType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Sets the note type when a new type is selected from the spinner
             *
             * @param adapterView adapter view for type selection spinner
             * @param view spinner element
             * @param i index of spinner selection
             * @param l _id of an item
             */
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = adapterView.getItemAtPosition(i).toString();
                note.setType(selection);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button doneButton = findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            /**
             * When user clicks 'Done', gets all changes from the edittexts and updates the
             * note object then passes the note object and index in an intent back to main
             * activity. Finishes NoteActivity
             * @param view done button
             */
            @Override
            public void onClick(View view) {
                if(noteTitle.getText().toString().equals("")){
                    Toast toast = Toast.makeText(NoteActivity.this, "Title is Empty, please add a title", Toast.LENGTH_LONG);
                    toast.show();
                }else {
                    note.setTitle(noteTitle.getText().toString());
                    note.setContent(content.getText().toString());
                    Log.d(TAG, "Title: " + note.getTitle() + " Content: " + note.getContent() + " Type: " + note.getType());

                    Intent intent = new Intent(NoteActivity.this, MainActivity.class);
                    intent.putExtra("note", note);
                    intent.putExtra("id", id);
                    Log.d(TAG, "NoteID: " + id);
                    setResult(RESULT_OK, intent);
                    NoteActivity.this.finish();
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Gets the index in the adapter of a string type
     *
     * @return int indicating index in spinner of a certain type
     */
    private int getAdapterIndex() {
        switch (note.getType()) {
            case "Other":
                return OTHER;
            case "School":
                return SCHOOL;
            case "Work":
                return WORK;
            default:
                return PERSONAL;
        }
    }
}
