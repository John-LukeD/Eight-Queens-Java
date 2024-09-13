/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package eightqueens2;

import java.util.Random;

/**
 * @author johnlukedeneen
 */
public class EightQueens2 {

    //Declare 8x8 2Darray chessBoards and initialize variables
    public static int[][] currentState = new int[8][8];
    public static int[][] nextState = new int[8][8];
    public static int numStateChanges = 0;
    public static int numRestarts = -1;
    public static int numNeighborLowerH = 0;

    public static void main(String[] args) {

        //randomly populate queens
        populateRandState(currentState);

        //While there are conflicts, generate neighbor states and print the current state 
        while (calcHeuristic(currentState) != 0) {
            generateNeighborStates(currentState);
            printBoard(currentState);

            //If the nextState has left conlficts then the current state,
            //coppy next stae to current state and increment numStateChanges
            if (calcHeuristic(nextState) < calcHeuristic(currentState)) {
                copyArray(nextState, currentState);
                numStateChanges++;
            }
            //if solution is found call final print
            if (calcHeuristic(currentState) == 0) {
                finalPrint(currentState);
                return;
                //else if solution not found and no solution,
                //restart with new random state
            } else if (numNeighborLowerH == 0) {
                System.out.println("Restarting...");
                currentState = new int[8][8];
                populateRandState(currentState);
            }
        }
    }

    //Method to randomly populate chessBoard
    public static void populateRandState(int[][] array) {
        Random random = new Random();
        //for loop to set 1 queen per column at a random row index
        for (int i = 0; i < 8; i++) {
            int rowIndex = random.nextInt(8);
            array[rowIndex][i] = 1;

        }
        numRestarts++;
    }

    //method to print out current state
    public static void printBoard(int[][] array) {
        int currH = calcHeuristic(array);
        System.out.println("Current h: " + currH);
        System.out.println("Current state:");
        //for loop to itterate through 2d array
        for (int[] array1 : array) {
            for (int j = 0; j < array1.length; j++) {
                System.out.print(array1[j] + " ");
            }
            //Move to a new line after each row is printed
            System.out.println();
        }
        System.out.println("Neighbors found with lower h: " + numNeighborLowerH);
        System.out.println("Setting new current state");
        System.out.println();
    }

    public static void finalPrint(int[][] array) {
        int currH = calcHeuristic(array);
        System.out.println("----------------");
        System.out.println("Solution Found!!");
        System.out.println("----------------");
        System.out.println("Current h: " + currH);
        System.out.println("Final state:");
        //for loop to itterate through 2d array
        for (int[] array1 : array) {
            for (int j = 0; j < array1.length; j++) {
                System.out.print(array1[j] + " ");
            }
            //Move to a new line after each row is printed
            System.out.println();
        }
        System.out.println("State changes: " + numStateChanges);
        System.out.println("Restarts: " + numRestarts);
    }

    //method to calculate current heuristic value
    public static int calcHeuristic(int[][] array) {
        //initialize heuristic to 0
        int heuristic = 0;
        //itterate through each column
        for (int col = 0; col < array.length; col++) {
            //set row to an invalid value, it will be changed to current
            //row of the queen in the current column after it is found
            int row = -1;
            //find the row of the queen in the current column
            for (int i = 0; i < array.length; i++) {
                if (array[i][col] == 1) {
                    //queen has been found, set row to correct value
                    row = i;
                    //break once queen has been found for given col
                    break;
                }
            }
            //check if queen was found in current column
            if (row != -1) {
                //check for conflicts in the same row, for each column starting
                //with second column (index 1 = col + 1)
                for (int i = col + 1; i < array.length; i++) {
                    if (array[row][i] == 1) {
                        //if conflict is found increment heuristic
                        heuristic++;
                    }
                }
                //check for conflicts in diagonal top left to bottom right
                for (int i = 1; row + i < array.length && col + i < array.length; i++) {
                    if (array[row + i][col + i] == 1) {
                        //if conflict is found increment heuristic
                        heuristic++;
                    }
                }
                //check for conflicts in diagonal top right to bottom left
                for (int i = 1; row - i >= 0 && col + i < array.length; i++) {
                    if (row - i >= 0 && array[row - i][col + i] == 1) {
                        //if conflict is found increment heuristic
                        heuristic++;
                    }
                }

            }
        }
        return heuristic;
    }

    public static void generateNeighborStates(int[][] array) {
        int[][] tmp = deepCopy(array);
        nextState = deepCopy(array);
        int bestHeuristic = calcHeuristic(array);
        numNeighborLowerH = 0;

        //itterate through each column
        for (int col = 0; col < array.length; col++) {
            //itterate through each row
            copyArray(currentState, tmp);
            for (int row = 0; row < array[col].length; row++) {
                //clear the current column
                clearColumn(tmp, col);
                //place queen at current index
                tmp[row][col] = 1;
                //variable to hold h value of current tmp state
                int currHeuristic = calcHeuristic(tmp);
                if (currHeuristic < bestHeuristic) {
                    bestHeuristic = currHeuristic;
                    //copy tmp to next state
                    copyArray(tmp, nextState);
                    numNeighborLowerH++;
                }
            }
        }
    }

    //Method to clear a specific column of a 2d array
    public static void clearColumn(int[][] array, int col) {
        for (int j = 0; j < 8; j++) {
            if (array[j][col] == 1) {
                array[j][col] = 0;
            }
        }
    }

    //copies original 2dArray to destination 
    public static void copyArray(int[][] original, int[][] destination) {
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[i].length; j++) {
                destination[i][j] = original[i][j];
            }
        }
    }

    //creates new 2d array copy the same as the origional array
    public static int[][] deepCopy(int[][] original) {
        int[][] copy = new int[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[i].length; j++) {
                copy[i][j] = original[i][j];
            }
        }
        return copy;
    }
}
