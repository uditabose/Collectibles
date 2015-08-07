

package gfg.array;

/**
 * http://www.geeksforgeeks.org/find-the-minimum-distance-between-two-numbers/
 * @author Udita
 */
public class MinDistance {
    
    public int findMinDistance(int[] input, int a, int b) {
        int minDiff = Integer.MAX_VALUE;
        for (int i = 0, j = 0; i < input.length && j < input.length; ) {
            if (input[i] == a && input[j] == b) {
                minDiff = Math.min(Math.abs(i - j), minDiff);
                i++; j++;
            } else if (input[i] == a && input[j] != b) {
                j++;
            } else if (input[i] != a && input[j] == b) {
                i++;
            } else {
                i++; j++;
            }
            
        }
        
        return minDiff;
    }

}
