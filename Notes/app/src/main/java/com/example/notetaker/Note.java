/**
 * This program is the note object used in our note taking app. A note has a title, type, and
 * content, all of which are strings. We override the toString() method to return the title of
 * the note if there is one and "[empty title]" if not
 * CPSC 312-01, Fall 2019
 * Programming Assignment #6
 * No sources to cite.
 *
 * @author Alex Giacobbi and Jalen Tacsiat
 * @version v1.0 11/06/19
 *
 * Alex contributions:
 * made Note Serializable
 *
 * Jalen contributions:
 * created constructors, getters, setters, and toString
 */

package com.example.notetaker;

import java.io.Serializable;

import androidx.annotation.NonNull;

/**
 * Note class that contains fields for each note object
 */
public class Note implements Serializable {
    private int id;
    private String title;
    private String content;
    private String type;

    /**
     * DVC for note class
     * Gives fields blank values
     */
    public Note(){
        this.id = -1;
        this.title="";
        this.content="";
        this.type="";
    }

    /**
     * EVC for note class
     * @param title user input from title editText
     * @param content user input from content editText
     * @param type user selection from listView
     */
    public Note(String title, String content, String type){
        this.title = type;
        this.content = content;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return a string containing the type of note the user selected
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the field type to the user input
     * @param type user selection for type of note
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return a string containing the contents of the notes
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the field to the users input from the content editText
     * @param content string containing what the user entered in the content editText
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     *
     * @return string containing what the user entered in the title editText
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets the title field to the user input from the title editText
     * @param title string containing what the user enter in the title editText
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return the title of the note so it can be displayed in the listView
     */
    @NonNull
    @Override
    public String toString() {
        return title.length() > 0 ? this.title : "[empty title]";
    }
}
