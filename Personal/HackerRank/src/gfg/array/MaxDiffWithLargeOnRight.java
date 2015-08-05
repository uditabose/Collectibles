


package gfg.array;

/**
 * http://www.geeksforgeeks.org/maximum-difference-between-two-elements/
 * @author Udita
 */
public class MaxDiffWithLargeOnRight {
    
    public int getMaxDiff(int[] input) {
        int minOnLeft = input[0];
        //int[] diff = new int[input.length];
        // diff[0] = -1;
        int maxDiff = Integer.MIN_VALUE;
        
        for (int i = 1; i < input.length; i++) { // O(N)
            if (input[i] > minOnLeft) {
                maxDiff = Math.max(maxDiff, input[i] - minOnLeft);
            } else {
                minOnLeft = input[i];
            }
        }
        return maxDiff;
    }

}
