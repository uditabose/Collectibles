

package gfg.array;

import gfg.TwoSum;

/**
 *
 * @author Udita
 */
public class TripletSum {
    // {12, 3, 4, 1, 6, 9}
    // 24
    // {12, 21, 20, 23, 18, 15}
    public boolean tripletSum(int[] input, int theSum) {
        boolean isFound = false;
        
        int max = Integer.MIN_VALUE;
        int[] minus = new int[input.length];
        for (int i = 0; i < minus.length; i++) {
            minus[i] = theSum - input[i];
            max = Math.max(minus[i], max);
        }
        
        int[] bin = null;
        SumOfTwo sot = new SumOfTwo();
        for (int i = 0; i < minus.length; i++) {
            bin = sot.sumOfTwo(input, theSum - minus[i]);
            isFound = (bin[0] != -1 && bin[1] != -1);
            if (isFound) {
                break;
            }
        }
        
        return isFound;
    }

}
