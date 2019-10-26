package com.example.pa5_agiacobbi;

/**
 * This program is the class file for a cell in a game board.
 * It stores the location of the cell as a pair of coordinates
 * and the symbol of the player occupying the cell or '-' if empty.
 * This class has methods to get the symbol and coordinates of a
 * cell and methods for updating the coordinates and symbol
 * CPSC 224-01, Fall 2019
 * Programming Assignment #2
 * No sources to cite.
 *
 * @author Alex Giacobbi
 * @version v1.0 9/10/19
 */

public class Cell {
    private Coordinates coordinates;
    private char symbol;

    /**
     * Default value constructor: initializes cell coordinates and symbol to default values
     */
    public Cell() {
        this.coordinates = new Coordinates();
        this.symbol = '-';
    }

    /**
     * Explicit value constructor: initializes cell coordinated and symbol to specified values
     *
     * @param coordinates coordinates to set cell coordinates
     * @param symbol character to set symbol to
     */
    public Cell(Coordinates coordinates, char symbol) {
        this.coordinates = coordinates;
        this.symbol = symbol;
    }

    /**
     * Gets cell coordinates for the user
     *
     * @return coordinates of cell
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Gets cell symbol for the user
     *
     * @return symbol stored in cell
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Sets cell coordinated to specified coordinates
     *
     * @param coordinates coordinate pair to set cell to
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Sets cell symbol to specified symbol
     *
     * @param symbol character to set cell symbol to
     */
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }


    /**
     * Formats cell into string containing cell symbol
     *
     * @return string of cell symbol
     */
    @Override
    public String toString() {
        return symbol + "";
    }
}
