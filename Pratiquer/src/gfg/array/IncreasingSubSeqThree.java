

package gfg.array;

/**
 *
 * @author Udita
 */
public class IncreasingSubSeqThree {
    
    public int[] increasingSubSeq(int[] input) {
        int[] subSeq = new int[3];
        
        for (int i = 0, j = 1, k = 2; i < input.length - 2 
                && j < input.length - 1 
                && k < input.length; k++) {
            // {12, 11, 10, 5, 6, 2, 30};
            // {12, 1, 10, 5, 6, 2, 30};
            if (input[i] < input[j] && input[j] < input[k]) {
                subSeq = new int[]{i, j, k};
                break;
            } 
            if (input[i] > input[j]) {
                i++;
            }
            
            if (i == j) {
                j++;
                continue;
            }
            
            if (input[j] > input[k]) {
                j++;
            }

                     
        }
        
        return subSeq;
    }

}
