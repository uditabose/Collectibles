

package gfg.array;

import java.util.Arrays;

/**
 *
 * @author Udita
 */
public class NonAdjMaxSum {
    
    public int findNonAdjucentMaxSum(int[] input) {
        int[] sumAtI = new int[input.length];
        sumAtI[0] = input[0];
        sumAtI[1] = input[1];
        for (int i = 2; i < input.length; i++) {
            sumAtI[i] = Math.max(sumAtI[i - 1], sumAtI[i - 2] + input[i]);
        }
        System.out.println(Arrays.toString(sumAtI));
        return sumAtI[sumAtI.length - 1];
    }

}
