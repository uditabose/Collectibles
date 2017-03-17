

package gfg.array;

/**
 * http://www.geeksforgeeks.org/equilibrium-index-of-an-array/
 * @author Udita
 */
public class EquilibriumIndex {
    
    public int equilibriumIndex(int[] input) {
        // j = equilibrium index
        // sum[0-(j-1)] == sum[(j+1)-n]
        int[] leftSum = new int[input.length];
        int[] rightSum = new int[input.length];
        leftSum[0] = 0;
        rightSum[input.length - 1] = 0;
        
        for (int i = 1, j = input.length - 2; i < input.length && j >= 0; i++, j--) {
            leftSum[i] = leftSum[i-1] + input[i-1];
            rightSum[j] = rightSum[j+1] + input[j+1];
        }
        
        for (int i = 0; i < rightSum.length; i++) {
            if (rightSum[i] == leftSum[i]) {
                return i;
            }
            
        }
        
        return -1;
    }

}
