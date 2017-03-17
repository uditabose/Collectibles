package hackerrank;

import java.util.Scanner;

/**
 * The problem ----------------------------------------------------------- 
 * You have 100 small bottles arranged in a circle with a number on each bottle. You
 * drink bottle #1, skip 1, drink bottle #3, skip 2, drink bottle #6, skip 3,
 * drink bottle #10, and continue to skip one more bottle after each drink,
 * which bottle will the last one you drink?
 * -----------------------------------------------------------
 *
 * The solution ----------------------------------------------------------- 
 * If we arrange the positions then it forms a series of numbers, assume t(i) =
 * position of i'th bottle the series representing t(i) is t(i) = t(i-1) + i
 * solving the series further yields t(i) = i + (i -1) + (i - 2) + ... + 1
 * =>t(i) = sum of natural number series till i =>t(i) = (i * (i + 1)) / 2 --->
 * (EQN-1)
 *
 * Using this above equation we can formulate the solution various way.
 *
 * Solution 1 : Implemented in the procedural way by the method
 * theLastDrink(final int maxNumberOfBottles) In this solution we calculate
 * position of the bottle and check if the bottle is more than or equal to the
 * maximum number of bottles available
 *
 * Solution 2 : Implemented following OOPS principle with help of class
 * PopSugarSeries In this solution we solve for the quadratic equation of the
 * form a(x * x) + bx + c = 0 with the general solution of form x = [-b +
 * square_root(b * b - 4 * a * c)]/(2 * a) or [-b - square_root(b * b - 4 * a *
 * c)]/(2 * a)
 *
 * From these solutions we pick the non-negative non-imaginary solution and take
 * the integer value as the number of bottles we took a sip from. From this
 * number we calculate the last bottle position using EQN-1
 *
 * Run the program 
 * -----------------------------------------------------------
 * commend : java PopSugarSolution
 *
 * Sample Output : Which way I should solve this [P/p - procedural, O/o - OOPS]?
 * P How many bottles are in the circle ? 100
 *
 * Last bottle I took a sip is the 91th(st/nd) This was the 13th(st/nd) bottle I
 * sipped from
 *
 * Do you want to solve this again [Y/y - Yes, anything else - no] ? Y
 *
 * Which way I should solve this [P/p - procedural, O/o - OOPS]? o
 *
 * How many bottles are in the circle ? 10
 *
 * Last bottle I took a sip is the 10th(st/nd)
 *  This was the 4th(st/nd) bottle I sipped from 
 * -----------------------------------------------------------
 *
 * @author Udita <udita.bose@nyu.edu>
 */
public class PopSugarSolution {

    public static void main(String[] args) {

        // user command helper statements
        String stepOne = "Which way I should solve this [P/p - procedural, O/o - OOPS]?";
        String stepTwo = "How many bottles are in the circle ?";
        String stepThree = "Do you want to solve this again [Y/y - Yes, anything else - no] ?";
        String errorInput = "You have made a mistake in telling me what to do - please start over!";

        Scanner scanner = new Scanner(System.in);

        char runMode = 'o';
        int numberOfBottles = 0, userAtStep = 1; // user step 1 = what way
                                                 // step 2 = how many bottles
                                                 // step 3 = continue 

        // start by printing first helping statement
        printStatements(stepOne);
        
        String userInput = null;
        while (scanner.hasNext()) {
            userInput = scanner.nextLine().trim();

            if (userAtStep == 1) {
                runMode = Character.toLowerCase(userInput.charAt(0));
                // a wrong mode of solution 
                if (runMode != 'p' && runMode != 'o') {
                    printStatements(errorInput, stepOne);
                    continue;
                }
                printStatements(stepTwo);
                userAtStep = 2;
            } else if (userAtStep == 2) {
                try {
                    numberOfBottles = Integer.parseInt(userInput);

                    int[] theSolution = null;
                    // solve the issue either way
                    if (runMode == 'p') {
                        theSolution = theLastDrink(numberOfBottles);
                    } else {
                        PopSugarSeries series = new PopSugarSeries(numberOfBottles);
                        theSolution = series.getSolution();
                    }

                    // output
                    printFormattedOutput(theSolution);
                    printStatements(stepThree);

                    userAtStep = 3;
                } catch (NumberFormatException nfe) {
                    // not a number
                    printStatements(errorInput, stepOne);
                    userAtStep = 1;
                }

            } else if (userAtStep == 3) {
                // ask if user still want to continue
                if (userInput.equalsIgnoreCase("y")) {
                    printStatements(stepOne);
                    userAtStep = 1;
                } else {
                    break;
                }
            }
        }
    }
    
    // convenience method to print repeatable statements
    private static void printStatements(String... statements) {
        if (statements == null) {
            System.out.println();
        }
        for (int i = 0; i < statements.length; i++) {
            System.out.println(statements[i]);
            System.out.println();
        }
    }
    
    // convenience method to print final output statements
    private static void printFormattedOutput(int[] solution) {
        if (solution == null) {
            System.out.println();
        }
        String lastBottleFormat = "Last bottle I took a sip is the %dth(st/nd)%n";
        String noOfSipFormat = "This was the %dth(st/nd) bottle I sipped from%n";
        
        System.out.printf(lastBottleFormat, solution[1]);
        System.out.printf(noOfSipFormat, solution[0]);
        System.out.println();
    }

    /**
     * Procedural way of solving the problem in sort of brute-force way
     * @param maxNumberOfBottles - the number of bottles in the circle
     * @return an array of 2 numbers, first one is the number of sips taken,
     *                                second one is the position of last bottle
     */
    private static int[] theLastDrink(final int maxNumberOfBottles) {
        boolean isDone = false;
        int numberOfDrinks = 0, bottlePosition = 0, oldBottlePosition = 0;
        while (!isDone) {
            bottlePosition = oldBottlePosition + numberOfDrinks;
            if (bottlePosition >= maxNumberOfBottles) {
                isDone = true;
                if (bottlePosition > maxNumberOfBottles) {
                    bottlePosition = oldBottlePosition;
                    numberOfDrinks--;
                }
            } else {
                oldBottlePosition = bottlePosition;
                numberOfDrinks++;
            }
        }
        return new int[]{numberOfDrinks, bottlePosition};
    }

    /**
     * A private class, which encapsulates the solution
     */
    private static class PopSugarSeries {

        // the number of bottles in the circle
        private int maxNumberOfBottles;
        // the position of last bottle
        private int bottlePosition;
        // the number of sips taken
        private int numberOfDrinks;

        /**
         * constructor to initialize the PopSugarSeries
         * @param maxNumberOfBottles the number of bottles in the circle
         */
        public PopSugarSeries(int maxNumberOfBottles) {
            this.maxNumberOfBottles = maxNumberOfBottles;
        }

        private int getBottlePosition() {
            return bottlePosition;
        }

        private int getNumberOfDrinks() {
            return numberOfDrinks;
        }

        private void solveSeries() {
            // quadratic equation format -
            // a(x * x) + bx - (2 * maxNumberOfBottles) = 0
            int a = 1, b = 1, c = -(2 * maxNumberOfBottles);
            double delta = b * b - 4 * a * c;
            
            // if delta is negetive, then solutions are imaginary numbers
            if (delta < 0) {
                bottlePosition = -1;
                numberOfDrinks = -1;
                return;
            }
            delta = Math.sqrt(delta);
            double solution = ((-1) * b + delta) / (double) (2 * a);

            // we take the first non-negative solution
            if (solution < 0) {
                solution = ((-1) * b - delta) / (double) (2 * a);
            }
            
            // if not found then return invalid value
            if (solution < 0) {
                bottlePosition = -1;
                numberOfDrinks = -1;
                return;
            }

            // flooring the solution to get the maximum number of bottles, 
            // which must be an integer
            numberOfDrinks = (int) Math.floor(solution);
            // computing the position of bottles
            bottlePosition = (numberOfDrinks * (1 + numberOfDrinks)) / 2;
        }

        /**
         * computes the solution and returns it in array format
         * @return an array of 2 numbers, first one is the number of sips taken,
         *                                second one is the position of last bottle
         */
        public int[] getSolution() {
            solveSeries();
            return new int[]{getNumberOfDrinks(), getBottlePosition()};
        }
    }
}
