package com.example.pa5_agiacobbi;

/**
 * This program is the class file for a pair of coordinates.
 * The coordinates are stored as ordered pairs of integers indicating
 * row and column. This class has methods to format the row and
 * column values as a string, get the values for the row and column,
 * and set the row and column values.
 * CPSC 224-01, Fall 2019
 * Programming Assignment #2
 * No sources to cite.
 *
 * @author Alex Giacobbi
 * @version v1.0 9/10/19
 */

public class Coordinates {
    private int row;
    private int col;

    /**
     * Default value constructor for Coordinates class
     * Sets row and col to -1
     */
    public Coordinates() {
        this.row = -1;
        this.col = -1;
    }

    /**
     * Explicit value constructor for Coordinates class
     * Sets row and col to values specified in parameters
     *
     * @param row integer to set row field
     * @param col integer to set col field
     */
    public Coordinates(int row, int col) {
        this.row = row;
        this.col = col;
    }


    /**
     * Overrides Object's toString() to print coordinates
     *
     * @return string formatted to display coordinates as ordered pair in form (row, col)
     */
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

    /**
     * Gets row value for user
     *
     * @return integer value stored in row field
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets column value for user
     *
     * @return integer value stored in col field
     */
    public int getCol() {
        return col;
    }

    /**
     * Sets coordinate to pair of values
     *
     * @param row integer to set field this.row
     * @param col integer to set field this.row
     */
    public void setCoordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

}
