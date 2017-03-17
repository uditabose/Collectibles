

package practice;

/**
 *
 * @author Udita
 */
public class MaximumSumIncreasingSubsequence {
    
    public int maxSum(int[] input) {
        int[] maxSums = new int[input.length];
        int maxSum = Integer.MIN_VALUE;
        
        maxSums[0] = input[0];
        for (int i = 1; i < input.length; i++) {
            for (int j = 0; j < i; j++) {
                if (input[i] > input[j]) {
                    maxSums[i] = Math.max(maxSums[i], maxSums[j] + input[i]);
                }  
            }
            maxSum = Math.max(maxSum, maxSums[i]);
        }
        
        return maxSum;
    }

}
