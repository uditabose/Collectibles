

package practice;

/**
 *
 * @author Udita
 */
public class LongestIncreasingSubsequence {
    
    public int longestIncreasingSubseq(int[] input) {
        int[] longest = new int[input.length];
        int theLongest = Integer.MIN_VALUE;
        longest[0] = 1;
        for (int i = 1; i < input.length; i++) {
            for (int j = 0; j < i; j++) {
                if (input[j] < input[i]) {
                    longest[i] = Math.max(longest[i], longest[j] + 1);
                }
                
            }
            theLongest = Math.max(longest[i], theLongest);
            
        }
        
        return theLongest;
    }

}
