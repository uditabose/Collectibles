

package hackerrank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * The problem 
 * ------------------------------------------------------------------------
 * You have 100 small bottles arranged in a circle with a number on each bottle.
 * You drink bottle #1, skip 1, drink bottle #3, skip 2, drink bottle #6, skip 3,
 * drink bottle #10, and continue to skip one more bottle after each drink,
 * which bottle will the last one you drink?
 * ------------------------------------------------------------------------ 
 * The solution 
 * ------------------------------------------------------------------------
 * If we arrange the positions then it forms a series of numbers, assume 
 *    t(i) = position of i'th bottle the series representing 
 *    t(i) is t(i) = t(i-1) + i
 * solving the series further yields 
 *    t(i) = i + (i -1) + (i - 2) + ... + 1
 * => t(i) = sum of natural number series till i 
 * => t(i) = (i * (i + 1)) / 2 ---> (EQN-1)
 * 
 * This equation works for values less than the number of bottles. As the bottles 
 * are arranged in circle thus one can drink from the bottles in a multi-pass of 
 * the circles as long as each bottle is drank for only one time. So the actual
 * calculation for t(i) is as following
 *  t(i) = [(i * (i + 1)) / 2] modulo (n)
 * where n = total number of bottles
 * 
 * The equation basically calculates the remainder for each t(i), till the remainders
 * start to repeat.
 * ------------------------------------------------------------------------
 * Run the program 
 * ------------------------------------------------------------------------
 * commend : java hackerrank.PopSugarCircleSolution
 *
 * Sample Output : 
 * How many bottles are in the circle ? 
 * 
 * 100
 *
 * Last bottle I took a sip is the 28th(st/nd)
 * This was the 41th(st/nd) bottle I sipped from
 * 
 *
 * Do you want to solve this again [Y/y - Yes, anything else - no] ? 
 * Y
 *
 * 45
 * Last bottle I took a sip is the 33th(st/nd)
 * This was the 10th(st/nd) bottle I sipped from
 * ------------------------------------------------------------------------
 * 
 * @author Udita <udita.bose@nyu.edu>
 */
public class PopSugarCircleSolution {
    
    public static void main(String[] args) {
       
        // user command helper statements
        String stepOne = "How many bottles are in the circle ?";
        String stepTwo = "Do you want to solve this again [Y/y - Yes, anything else - no] ?";
        String errorInput = "You have made a mistake in telling me what to do - please start over!";
        int noOfBottlesinCircle = 0, userAtStep = 1; // user step 1 = how many bottles
                                                 // step 2 = continue or exit

        Scanner scanner = new Scanner(System.in);
        // start by printing first helping statement
        printStatements(stepOne);
        
        String userInput = null;
        while (scanner.hasNext()) {
            userInput = scanner.nextLine().trim();
            
            if (userAtStep == 1) {
                try {
                    noOfBottlesinCircle = Integer.parseInt(userInput);
                    int[]theSolution = theLastDrink(noOfBottlesinCircle);

                    // output
                    printFormattedOutput(theSolution);
                    printStatements(stepTwo);
                    
                    // move to step 2
                    userAtStep = 2;
                } catch (NumberFormatException nfe) {
                    // not a number
                    printStatements(errorInput, stepOne);
                    userAtStep = 1;
                }
            } else if (userAtStep == 2) {
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
     * Returns the result for last drink taken
     * @param noOfBottlesinCircle - the number of bottles in the circle
     * @return an array of 2 numbers, first one is the number of sips taken,
     *                                second one is the position of last bottle
     */
    private static int[] theLastDrink(final int noOfBottlesinCircle) {
        
        List<Integer> drankSequence = new ArrayList<>();
        
        int bottleNumber = 1;
        double drinkingBottle = 0d;
        while(true) {
            drinkingBottle = ((bottleNumber + Math.pow(bottleNumber, 2)) / 2) % noOfBottlesinCircle;
            // break out of loop in case the remainders start to repeat
            if (!drankSequence.isEmpty() && (int)drinkingBottle == drankSequence.get(0)) {
                break;
            }
            drankSequence.add((int)drinkingBottle);
            bottleNumber++;
        }
        Set<Integer> drankSet = new HashSet<>(drankSequence);
        // number of drinks taken, the last distinct bottle the drink was taken from
        return new int[]{drankSet.size(), drankSequence.get(drankSequence.size() - 1)};
    }
}
