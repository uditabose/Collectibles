

package gfg.array;

/**
 *
 * @author Udita
 */
public class SubarrayWithSum {
    
    public int[] subWithSum(int[] input, int sum) {
        
        int maxSumAtI = 0, start = 0;
        for (int i = 0; i < input.length; i++) {
            maxSumAtI += input[i];
            if (maxSumAtI > sum) {
                while (maxSumAtI > sum && start < i - 1) {
                    maxSumAtI -= input[start++];
                }
            }
            
            if (maxSumAtI == sum) {
                return new int[]{start, i};
            }
            
        }
        
        return new int[]{-1, -1};
    }

}
