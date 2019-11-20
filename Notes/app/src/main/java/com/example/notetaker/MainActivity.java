/**
 * This program is the main activity of a note taking app. It displays notes to a user and
 * allows the user to select a note to edit, create a new note, or select a note to delete
 * CPSC 312-01, Fall 2019
 * Programming Assignment #6
 * Sources to Site
 * Icons Used
 * School Icon used:
 * <div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
 * Work Icon used:
 * <div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
 * Personal Icon used:
 * <div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
 * Other icon used:
 * <div>Icons made by <a href="https://www.flaticon.com/authors/ddara" title="dDara">dDara</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
 *
 * @author Alex Giacobbi and Jalen Tacsiat
 * @version v2.0 11/06/19
 *
 * Alex contributions:
 * Designed activity listeners
 * Formed Intents that are passed between activities
 * Created data structure to store notes in
 *
 * Connected the list of notes to the database
 * Refactored logic for all operations:
 *   create note
 *   edit note
 *   delete note
 *   delete all
 *
 * Jalen contributions:
 * Initially connected MainActivity class with NoteActivityClass
 * Created note Class
 * Created NoteLayout class for the noteActivity
 *
 * Created menu add and delete options
 * Added icons to spinner
 * Added contextual action mode for long clicking a note
 */
package com.example.notetaker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * This class is the main activity of our notes app. Here, users will be able
 * to create a new note, view a list of their existing notes, select a note to
 * edit and delete a note by long pressing. When a user is finished creating or
 * editing a note, they will return to this screen and see their note added to
 * the list.
 */
public class MainActivity extends AppCompatActivity {
    static final int LOGIN_REQUEST_CODE = 1;
    static final String TAG = "inMainActivity";

    private SimpleCursorAdapter cursorAdapter;


    /**
     * When user finishes creating note, gets index and note from intent extras, inserts
     * or updates the note and refreshes the view
     *
     * @param requestCode a request code
     * @param resultCode activity result code
     * @param data intent received as result
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOGIN_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if (data != null) {
                Note newNote = (Note) data.getSerializableExtra("note");
                int id = data.getIntExtra("id", -1);
                NoteOpenHelper helper = new NoteOpenHelper(this);

                Log.d(TAG, "id: " + id);

                if (id == -1) {
                    helper.insertNote(newNote);
                    Cursor cursor = helper.getAllNotes();
                    cursorAdapter.changeCursor(cursor);
                } else {
                    helper.updateNote(id, newNote);
                    Cursor cursor = helper.getAllNotes();
                    cursorAdapter.changeCursor(cursor);
                }
            }
        }
    }


    /**
     * Runs when app's main activity is created. Sets up click listeners for the new note
     * button and the adapter and listeners for the listview containing the notes. Connects
     * listview to the database with a cursor adapter
     *
     * @param savedInstanceState bundle containing saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainLayout layout = new MainLayout(this);
        setContentView(layout);

        final ListView notes = findViewById(R.id.notesListView);
        final NoteOpenHelper helper = new NoteOpenHelper(this);
        Cursor cursor = helper.getAllNotes();

        /*cursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_activated_1,
                cursor,
                new String[] {NoteOpenHelper.TITLE},
                new int[] {android.R.id.text1},
                0 // leave default
        );*/
        setupCustomAdapter();
        notes.setAdapter(cursorAdapter);

        notes.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        notes.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            int amountSelected;
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                amountSelected++;
                //Log.d("inItemCheckedStateChanged");
            }


            /**
             * Inflates the CAM menu
             *
             * @param actionMode the action mode
             * @param menu the menu for this context
             * @return true if menu is inflated successfully
             */
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }


            /**
             * Handles an action selection by a user in CAM. Only action for this menu is to delete
             * selected notes
             *
             * @param actionMode the action mode
             * @param menuItem the selected menu item
             * @return true if request is handled, false otherwise
             */
            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.deleteMenuItem:
                        SparseBooleanArray checked = notes.getCheckedItemPositions();
                        NoteOpenHelper helper = new NoteOpenHelper(MainActivity.this);
                        Log.d(TAG, "onActionItemClicked: " + checked.toString());
                        for (int i = 0; i < checked.size(); i++) {
                            if (checked.valueAt(i)) {
                            int id = (int) cursorAdapter.getItemId(checked.keyAt(i));
                            helper.deleteNote(id);
                            Log.d(TAG, "onActionItemClicked: " + id + ", " + i);
                        }
                    }
                    Cursor cursor = helper.getAllNotes();
                    cursorAdapter.changeCursor(cursor);
                    actionMode.finish(); // exit CAM
                    return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });

        notes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Click listener for the array adapter. Creates an intent to pass the selected note
             * to NoteActivity and the _id of the note in the database.
             *
             * @param adapterView adapter view for our note list
             * @param view the listview that user is selecting from
             * @param i index selected
             * @param l the _id of the note in the database
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                int id = (int) cursorAdapter.getItemId(i);
                Log.d(TAG, "onItemClick POSITION: " + i);
                Note note = helper.getNoteById(id);

                intent.putExtra("note", note);
                intent.putExtra("id", id);
                Log.d(TAG, "NoteID: " + id);
                startActivityForResult(intent, LOGIN_REQUEST_CODE);
            }
        });
    }

    private void setupCustomAdapter() {
        NoteOpenHelper helper = new NoteOpenHelper(this);
        Cursor cursor = helper.getAllNotes();
        cursorAdapter = new SimpleCursorAdapter(MainActivity.this,
                android.R.layout.simple_list_item_activated_1,
                cursor,
                new String[] {NoteOpenHelper.TITLE},
                new int[] {android.R.id.text1},
                0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(context);
                return inflater.inflate(R.layout.custom_list_item, null);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                super.bindView(view, context, cursor);

                TextView noteTitle = view.findViewById(R.id.title);
                ImageView icon = view.findViewById(R.id.image);
                String type = cursor.getString(2);
                Log.d(TAG, "bindView: " + type);

                noteTitle.setText(cursor.getString(1));

                switch (type) {
                    case "Work":
                        icon.setImageResource(R.drawable.projectmanagement);
                        break;
                    case "School":
                        icon.setImageResource(R.drawable.classroom);
                        break;
                    case "Other":
                        icon.setImageResource(R.drawable.multipleuserssilhouette);
                        break;
                    default:
                        icon.setImageResource(R.drawable.responsive);
                }

            }
        };
    }


    /**
     * Inflates the app bar menu
     *
     * @param menu the menu for this activity
     * @return true if inflated successfully
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * Listener for when a menu item is selected. Performs action indicated by the
     * selected menu item
     *
     * @param item the selected item
     * @return true if action is handled, false otherwise
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        String TAG = "itemSelectedTag";
        switch (id) {
            case R.id.addMenuItem:
                //add a note
                Log.d(TAG, "in add");
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("note", new Note());
                intent.putExtra("index", -1);
                startActivityForResult(intent, LOGIN_REQUEST_CODE);
                //Toast.makeText(this, "add item", Toast.LENGTH_SHORT);
                return true;
            case R.id.deleteMenuItem:
                //delete items
                Log.d(TAG, "in delete");
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setTitle("Delete all Notes")
                        .setMessage("Are you sure you want to delete all notes?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NoteOpenHelper helper = new NoteOpenHelper(MainActivity.this);
                                helper.deleteAllNotes();
                                Cursor cursor = helper.getAllNotes();
                                cursorAdapter.changeCursor(cursor);
                            }
                        })
                        .setNegativeButton("NO", null)
                        .show();

                //Toast.makeText(this, "delete item", Toast.LENGTH_SHORT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
