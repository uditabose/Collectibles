

package hackerrank;

import java.util.Arrays;
import java.util.Scanner;


/**
 *
 * @author Udita
 */
public class VerFair {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int noOfInputs = scanner.nextInt();
        int subsetLength = scanner.nextInt();

        Integer[] intArr = new Integer[noOfInputs];
        while(0 < noOfInputs && scanner.hasNextInt()) {
            intArr[--noOfInputs] = scanner.nextInt();
        }
        Arrays.sort(intArr);
        
        int startPos = 0, lastMinDiff = Integer.MAX_VALUE;
        while(startPos + subsetLength - 1 < intArr.length) {
            int thisDiff = intArr[startPos + subsetLength - 1] - intArr[startPos];
            if (thisDiff < lastMinDiff) {
                lastMinDiff = thisDiff;
            }
            startPos++;
        }
        System.out.println(lastMinDiff);
        
    }

}
