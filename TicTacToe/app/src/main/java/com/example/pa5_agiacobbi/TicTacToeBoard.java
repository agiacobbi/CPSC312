package com.example.pa5_agiacobbi;

/**
 * This program is the class file for a Tic Tac Toe game board.
 * It stores the board as an NxN array of Cells and has methods
 * to update the board according to player moves as well as
 * methods to check if a move is valid, if a player has won, and
 * if the board is full.
 * CPSC 224-01, Fall 2019
 * Programming Assignment #2
 * No sources to cite.
 *
 * @author Alex Giacobbi
 * @version v1.0 9/10/19
 */

public class TicTacToeBoard {
    private int dimensions;
    private Cell[][] grid;

    /**
     * Initializes empty Tic Tac Toe board with specified dimensions
     *
     * @param dimensions dimension for an square Tic Tac Toe board
     */
    public TicTacToeBoard(int dimensions) {
        this.dimensions = dimensions;
        this.grid = new Cell[dimensions][dimensions];

        for(int i = 0; i < dimensions; i++) {
            for(int j = 0; j < dimensions; j++) {
                Coordinates current = new Coordinates(i, j);
                this.grid[i][j] = new Cell(current, '-');
            }
        }
    }

    /**
     * Formats Tic Tac Toe board into reader-friendly string
     * Example empty 3x3 board:
     *   0 1 2
     * 0 - - -
     * 1 - - -
     * 2 - - -
     *
     * @return string formatted to show a visual representation of a Tic Tac Toe board
     */
    @Override
    public String toString() {
        String board = "  ";

        for (int i = 0; i < dimensions; i++) {
            board += i + " ";
        }
        board += '\n';

        for(int j = 0; j < dimensions; j++) {
            board += j + " ";
            for(int k = 0; k < dimensions; k++) {
                board += this.grid[j][k].toString() + " ";
            }
            board += '\n';
        }

        return board;
    }

    /**
     * Checks if set of coordinates is a valid move
     *
     * @param coordinates coordinates to check in grid
     * @return true if cell at coordinates is empty and within grid dimensions
     * false otherwise
     */
    public boolean isValidMove(Coordinates coordinates) {
        int x = coordinates.getRow();
        int y = coordinates.getCol();

        if (x >= dimensions || y >= dimensions) {
            return false;
        }

        if (grid[x][y].getSymbol() == '-') {
            return true;
        }

        return false;
    }

    /**
     * Sets symbol in an available cell for a valid set of coordinates
     *
     * @param coordinates coordinates of cell to be altered
     * @param playerSymbol character symbol of player to occupy cell
     */
    public void makeMove(Coordinates coordinates, char playerSymbol) {
        int x = coordinates.getRow();
        int y = coordinates.getCol();

        grid[x][y].setSymbol(playerSymbol);
    }

    /**
     * Checks if a player has won the game
     *
     * @param playerSymbol character symbol for player being checked
     * @return true if player has N in a row false otherwise
     */
    public boolean isWinner(char playerSymbol) {

        //check columns
        for (int i = 0; i < dimensions; i++) {
            int charsInARow = 0;
            for (int j = 0; j < dimensions; j++) {
                if (grid[i][j].getSymbol() == playerSymbol) {
                    charsInARow++;
                }
            }
            if (charsInARow == dimensions) {
                return true;
            }
        }

        //check rows
        for (int j = 0; j < dimensions; j++) {
            int charsInARow = 0;
            for (int i = 0; i < dimensions; i++) {
                if (grid[i][j].getSymbol() == playerSymbol) {
                    charsInARow++;
                }
            }
            if (charsInARow == dimensions) {
                return true;
            }
        }


        //check diagonal
        int diagonalChars = 0;
        for (int i = 0, j = 0; i < dimensions; i++, j++) {
            if (grid[i][j].getSymbol() == playerSymbol) {
                diagonalChars++;
            }
        }
        if (diagonalChars == dimensions) {
            return true;
        }

        //check diagonal
        diagonalChars = 0;
        for (int i = 0, j = dimensions - 1; i < dimensions; i++, j--) {
            if (grid[i][j].getSymbol() == playerSymbol) {
                diagonalChars++;
            }
        }
        return diagonalChars == dimensions;
    }

    /**
     * Checks if the grid is full; if so, no more moves can be made
     *
     * @return true if all cells have a symbol that is not the empty cell symbol '-'
     * false otherwise
     */
    public boolean isFull() {
        for (Cell[] column : grid) {
            for (Cell cell : column) {
                if (cell.getSymbol() == '-') {
                    return false;
                }
            }
        }

        return true;
    }
}
