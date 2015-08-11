

package gfg.array;

import java.util.Arrays;

/**
 *
 * @author Udita
 */
public class CountSmallerOnRight {
    
    public int[] countSmallerOnRight(int[] input) {
        int[] smaller = new int[input.length];
        int[] higher = new int[input.length];
        smaller[smaller.length - 1] = 0;
        higher[0] = 0;
        
        for (int i = smaller.length - 2, j = 1; i >= 0 && j < higher.length; i--, j++) {
            if (input[i+1] < input[i]) {
                smaller[i] = smaller[i+1] + 1;
            } else {
                smaller[i] = smaller[i+1];
            }
            
            if (input[j] > input[j-1]) {
                higher[j] = higher[j-1] + 1;
            } else {
                higher[j] = higher[j-1];
            }
            
        }
        
        System.out.println(Arrays.toString(higher));
        System.out.println(Arrays.toString(smaller));
        
        for (int i = 0; i < higher.length; i++) {
            smaller[i] = Math.abs(smaller[i] - higher[i]);
            
        }
        
        System.out.println(Arrays.toString(smaller));
        
        return smaller;
    }
}
