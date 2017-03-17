

package gfg.array;

import java.util.Arrays;

/**
 *
 * @author Udita
 */
public class AllLeaders {
    
    public int[] findLeaders(int[] input) {
        int[] leaders = new int[input.length];
        
        for (int i = 0; i < leaders.length; i++) {
            leaders[i] = Integer.MIN_VALUE;
        }
        
        
        leaders[leaders.length - 1] = input[input.length - 1];
        for (int i = leaders.length - 2; i >= 0 ; i--) {
            leaders[i] = Math.max(input[i], leaders[i+1]);
        }
        System.out.println(Arrays.toString(leaders));
        return leaders;
    }

}
