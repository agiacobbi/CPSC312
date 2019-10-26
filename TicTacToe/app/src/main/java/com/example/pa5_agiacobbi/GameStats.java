package com.example.pa5_agiacobbi;

/**
 * This programs is the class file that handles Tic Tac Toe game
 * stats for a player. Its methods store and update number of wins,
 * losses, and scratch games and formats them for readable display
 * CPSC 224-01, Fall 2019
 * Programming Assignment #2
 * No sources to cite.
 *
 * @author Alex Giacobbi
 * @version v1.0 9/10/19
 */

public class GameStats {
    private int wins;
    private int losses;
    private int scratch;
    private char playerSymbol;

    /**
     * Explicit value constructor: initializes game stats to zero
     * and sets player symbol to specified character
     *
     * @param playerSymbol character represeting a player
     */
    public GameStats(char playerSymbol) {
        this.playerSymbol = playerSymbol;
        this.wins = 0;
        this.losses = 0;
        this.scratch = 0;
    }

    /**
     * Increments number of wins
     */
    public void addWin() {
        wins++;
    }

    /**
     * Increments number of losses
     */
    public void addLoss() {
        losses++;
    }

    /**
     * Increments number of scratches
     */
    public void addScratch() {
        scratch++;
    }

    /**
     * Formats stats into a reader-friendly string in form:
     * Player X game stats
     * -------------------
     * Win to loss ratio: 1:4
     * Win percentage: 0.25
     * Number of scratch games: 2
     *
     * @return string containing game statistics in the above format
     */
    @Override
    public String toString() {
        double w = wins, t = (losses + wins + scratch);
        double percentage = w / t;

        return "Player " + playerSymbol + " game stats\n" +
                "-------------------\n" +
                "Win to loss ratio: " + wins + ":" + losses + '\n' +
                "Win percentage: " + percentage + '\n' +
                "Number of scratch games: " + scratch + '\n';
    }
}
