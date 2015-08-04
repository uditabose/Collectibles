

package gfg.array;

/**
 *
 * @author Udita
 */
public class MaxSumSubArray {
    public int maxSum(int[] input) {
        int maxTillEnd = input[0];
        int maxTillCurrent = input[0];
        
        for (int i = 1; i < input.length; i++) {
            //if (maxTillCurrent + input[i] > maxTillCurrent) {
            maxTillCurrent = Math.max(0, maxTillCurrent + input[i]);
            //}
            
            if (maxTillEnd < maxTillCurrent) {
                maxTillEnd = maxTillCurrent;
            }
            
        }
        return maxTillEnd;
    }

}
