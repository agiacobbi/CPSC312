/**
 * This program is the model for our grade calculator app
 * CPSC 312-02, Fall 2019
 * Programming Assignment #4
 * No sources to cite.
 *
 * @author Alex Giacobbi
 * @version v1.0 10/8/19
 */

package com.example.pa4_agiacobbi;

/**
 * This class stores values for the required grade, current grade, and
 * weight of final and calculates the grade necessary on a final exam to
 * earn the required grade. It has setters to adjust the fields as necessary
 * and a calculate method to return the required final exam grade.
 */
public class GradeCalculator {
    private double targetPercentage;
    private double currentPercentage;
    private double finalWeight;

    public GradeCalculator() {
        targetPercentage = 0;
        currentPercentage = 0;
        finalWeight = 0;
    }

    /**
     * Sets the target percentage to specified value
     *
     * @param targetPercentage a double representing the target grade in a class
     */
    public void setTargetPercentage(double targetPercentage) {
        this.targetPercentage = targetPercentage;
    }

    /**
     * Sets the current average to a specified value
     *
     * @param currentPercentage a double representing the current grade in aa class
     */
    public void setCurrentPercentage(double currentPercentage) {
        this.currentPercentage = currentPercentage;
    }

    /**
     * Sets the final weight to a specified value
     *
     * @param finalWeight a double representing the weight of the final exam
     */
    public void setFinalWeight(double finalWeight) {
        this.finalWeight = finalWeight;
    }

    /**
     * Calculates what grade is needed on the final exam to earn a given
     * target grade based on the weight of the final exam
     *
     * @return a double representing what a student must earn on the final
     */
    public double calculateFinalGrade() {
        double finalPercentage = finalWeight / 100;
        return ((targetPercentage - currentPercentage * (1 - finalPercentage)) / finalPercentage);
    }
}
