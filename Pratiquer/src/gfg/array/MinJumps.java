

package gfg.array;

/**
 *
 *  http://www.geeksforgeeks.org/minimum-number-of-jumps-to-reach-end-of-a-given-array/
 * 
 * @author Udita
 */
public class MinJumps {
    
    public int minJumps(int[] input) {
        int[] jumps = new int[input.length];
       
        jumps[0] = 0;
        for (int i = 1; i < jumps.length; i++) {
            jumps[i] = Integer.MAX_VALUE;
            for (int j = 0; j < i; j++) {
                if (i <= input[j] + j && jumps[j] != Integer.MAX_VALUE) {
                    jumps[i] = Math.min(jumps[i], jumps[j] + 1);
                }                
            }
        }

        
        return jumps[jumps.length - 1];
    }
}
