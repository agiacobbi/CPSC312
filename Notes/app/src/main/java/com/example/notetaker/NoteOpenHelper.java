/**
 * This program is the database helper for a note taking app. It allows us to connect our data
 * generated in the app to persistent storage so the user can access it after closing the app.
 * This class is responsible for creating the necessary tables and executing SQL on the database
 * to add, update and delete notes
 * CPSC 312-01, Fall 2019
 * Programming Assignment #6
 * No sources to cite.
 *
 * @author Alex Giacobbi and Jalen Tacsiat
 * @version v1.0 11/17/19
 *
 * Alex contributions:
 * Created and documented this class
 */

package com.example.notetaker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * This is our database helper class for the notes app. This class extends the SQLiteOpenHelper
 * and will allow us to connect our app to a database so that our notes will persist in storage.
 * This class will create the table for our notes and handles all CRUD operation: retrieves all
 * notes or a note by its id, updates a note by its id, and deletes all notes or a single note
 * by its id.
 */
public class NoteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLiteFunTag";

    // database constants
    private static final String DATABASE_NAME = "noteDatabase";
    private static final int DATABASE_VERSION = 1;

    // table constants
    private static final String TABLE_NOTE = "Note";
    private static final String ID = "_id"; // _id is for use with adapters later
    private static final String TYPE = "note_type";
    private static final String CONTENT = "content";
    static final String TITLE = "title";


    /**
     * This is a constructor for our NoteOpenHelper it calls the parent constructor
     * with the context that is passed in
     *
     * @param context Context of where helper is located
     */
    public NoteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * Creates the tables for our database on first call to NoteOpenHelper
     *
     * @param sqLiteDatabase an SQLiteDatabase object to populate with tables
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCreate = "CREATE TABLE " + TABLE_NOTE +
                "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " TEXT, " +
                TYPE + " TEXT, " +
                CONTENT + " TEXT)";
        Log.d(TAG, "Create Table: " + sqlCreate);
        sqLiteDatabase.execSQL(sqlCreate);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    /**
     * Inserts a note into the notes table of the database
     *
     * @param note a Note to insert
     */
    public void insertNote(Note note) {
        String sqlInsert = "INSERT INTO " + TABLE_NOTE + " VALUES(null, '" +
                note.getTitle() + "', '" +
                note.getType() + "', '" +
                note.getContent() + "')";
        Log.d(TAG, "insertNote: " + sqlInsert);

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlInsert);
        db.close();
    }


    /**
     * Gets a note from the database by its id, returns the note
     *
     * @param id an int id to identify a note to retrieve
     * @return a Note object with values queried from db, null if note
     * does not exist
     */
    public Note getNoteById(int id) {
        Note note = new Note();
        String sqlSelect = "SELECT * FROM " + TABLE_NOTE +
                " WHERE _id = " + id;
        Log.d(TAG, "selectAll: " + sqlSelect);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelect, null);

        if (cursor.moveToNext()) {
            note.setId(cursor.getInt(0));
            note.setTitle(cursor.getString(1));
            note.setType(cursor.getString(2));
            note.setContent(cursor.getString(3));
            db.close();
            return note;
        }

        db.close();
        return null;
    }


    /**
     * Gets all notes from the notes table in the database
     *
     * @return a Cursor object that points to the beginning of the
     * result set
     */
    public Cursor getAllNotes() {
        String sqlSelect = "SELECT * FROM " + TABLE_NOTE;
        Log.d(TAG, "selectAll: " + sqlSelect);

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(sqlSelect, null);

        return cursor;
    }


    /**
     * Deletes all notes from the table
     */
    public void deleteAllNotes() {
        String sqlDelete = "DELETE FROM " + TABLE_NOTE;
        Log.d(TAG, "deleteAllNotes: " + sqlDelete);

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlDelete);
        db.close();
    }


    /**
     * Deletes a specific note from the table by its id
     * @param id an int id of a note to delete
     */
    public void deleteNote(int id) {
        String sqlDelete = "DELETE FROM " + TABLE_NOTE +
                " WHERE " + ID + " = " + id;
        Log.d(TAG, "deleteNote: " + sqlDelete);

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlDelete);
        db.close();
    }


    /**
     * Updates the values of a note given an id and new values
     *
     * @param id an int id of a note to update
     * @param note a Note containing new values to set
     */
    public void updateNote(int id, Note note) {
        String sqlUpdate = "UPDATE " + TABLE_NOTE +
                " SET " + TITLE + " = '" + note.getTitle() + "', " +
                TYPE  + " = '" + note.getType() + "', " +
                CONTENT + " = '" + note.getContent() +
                "' WHERE " + ID + " = " + id;
        Log.d(TAG, "updateNote: " + sqlUpdate);

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlUpdate);
        db.close();
    }
}
