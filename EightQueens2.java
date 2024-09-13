package eightqueens2;

import java.util.Random;

/**
 * This class implements a solution to the Eight Queens puzzle using
 * a hill-climbing algorithm. It randomly places queens on a chessboard 
 * and attempts to minimize conflicts through state changes and restarts.
 * 
 * @author John-Luke Deneen
 */
public class EightQueens2 {

    /** The current state of the chessboard represented as a 2D array. */
    public static int[][] currentState = new int[8][8];

    /** The next possible state of the chessboard represented as a 2D array. */
    public static int[][] nextState = new int[8][8];

    /** The number of state changes made during the algorithm execution. */
    public static int numStateChanges = 0;

    /** The number of restarts initiated during the algorithm execution. */
    public static int numRestarts = -1;

    /** The number of neighboring states with a lower heuristic than the current state. */
    public static int numNeighborLowerH = 0;

    /**
     * The main method that initializes the chessboard, runs the hill-climbing 
     * algorithm, and prints the solution once found.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {

        // Randomly populate queens
        populateRandState(currentState);

        // While there are conflicts, generate neighbor states and print the current state 
        while (calcHeuristic(currentState) != 0) {
            generateNeighborStates(currentState);
            printBoard(currentState);

            // If the nextState has fewer conflicts than the current state,
            // copy nextState to currentState and increment numStateChanges
            if (calcHeuristic(nextState) < calcHeuristic(currentState)) {
                copyArray(nextState, currentState);
                numStateChanges++;
            }

            // If solution is found, call finalPrint and exit
            if (calcHeuristic(currentState) == 0) {
                finalPrint(currentState);
                return;

            // Restart with a new random state if no neighbor with lower heuristic is found
            } else if (numNeighborLowerH == 0) {
                System.out.println("Restarting...");
                currentState = new int[8][8];
                populateRandState(currentState);
            }
        }
    }

    /**
     * Populates the chessboard with queens randomly placed in each column.
     * 
     * @param array the 2D array representing the chessboard
     */
    public static void populateRandState(int[][] array) {
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            int rowIndex = random.nextInt(8);
            array[rowIndex][i] = 1;
        }
        numRestarts++;
    }

    /**
     * Prints the current state of the chessboard along with the heuristic 
     * and the number of neighbors found with a lower heuristic.
     * 
     * @param array the 2D array representing the chessboard
     */
    public static void printBoard(int[][] array) {
        int currH = calcHeuristic(array);
        System.out.println("Current h: " + currH);
        System.out.println("Current state:");
        for (int[] row : array) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
        System.out.println("Neighbors found with lower h: " + numNeighborLowerH);
        System.out.println("Setting new current state");
        System.out.println();
    }

    /**
     * Prints the final state of the chessboard once the solution is found.
     * 
     * @param array the 2D array representing the chessboard
     */
    public static void finalPrint(int[][] array) {
        int currH = calcHeuristic(array);
        System.out.println("----------------");
        System.out.println("Solution Found!!");
        System.out.println("----------------");
        System.out.println("Current h: " + currH);
        System.out.println("Final state:");
        for (int[] row : array) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
        System.out.println("State changes: " + numStateChanges);
        System.out.println("Restarts: " + numRestarts);
    }

    /**
     * Calculates the heuristic value, which is the number of conflicts between queens 
     * on the chessboard. The goal is to minimize the heuristic to 0, which indicates no conflicts.
     * 
     * @param array the 2D array representing the chessboard
     * @return the heuristic value (number of conflicts)
     */
    public static int calcHeuristic(int[][] array) {
        int heuristic = 0;
        for (int col = 0; col < array.length; col++) {
            int row = -1;
            for (int i = 0; i < array.length; i++) {
                if (array[i][col] == 1) {
                    row = i;
                    break;
                }
            }
            if (row != -1) {
                for (int i = col + 1; i < array.length; i++) {
                    if (array[row][i] == 1) {
                        heuristic++;
                    }
                }
                for (int i = 1; row + i < array.length && col + i < array.length; i++) {
                    if (array[row + i][col + i] == 1) {
                        heuristic++;
                    }
                }
                for (int i = 1; row - i >= 0 && col + i < array.length; i++) {
                    if (array[row - i][col + i] == 1) {
                        heuristic++;
                    }
                }
            }
        }
        return heuristic;
    }

    /**
     * Generates neighboring states of the current chessboard and updates 
     * the state if a neighbor with a lower heuristic is found.
     * 
     * @param array the 2D array representing the current chessboard state
     */
    public static void generateNeighborStates(int[][] array) {
        int[][] tmp = deepCopy(array);
        nextState = deepCopy(array);
        int bestHeuristic = calcHeuristic(array);
        numNeighborLowerH = 0;

        for (int col = 0; col < array.length; col++) {
            copyArray(currentState, tmp);
            for (int row = 0; row < array[col].length; row++) {
                clearColumn(tmp, col);
                tmp[row][col] = 1;
                int currHeuristic = calcHeuristic(tmp);
                if (currHeuristic < bestHeuristic) {
                    bestHeuristic = currHeuristic;
                    copyArray(tmp, nextState);
                    numNeighborLowerH++;
                }
            }
        }
    }

    /**
     * Clears the specified column of the chessboard by setting all values in the column to 0.
     * 
     * @param array the 2D array representing the chessboard
     * @param col the column index to clear
     */
    public static void clearColumn(int[][] array, int col) {
        for (int j = 0; j < 8; j++) {
            array[j][col] = 0;
        }
    }

    /**
     * Copies the contents of the original array to the destination array.
     * 
     * @param original the original 2D array to copy
     * @param destination the destination 2D array
     */
    public static void copyArray(int[][] original, int[][] destination) {
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[i].length; j++) {
                destination[i][j] = original[i][j];
            }
        }
    }

    /**
     * Creates and returns a deep copy of the given 2D array.
     * 
     * @param original the original 2D array to copy
     * @return a deep copy of the original array
     */
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
