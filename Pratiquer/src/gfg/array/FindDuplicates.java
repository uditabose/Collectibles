

package gfg.array;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 *
 * http://www.geeksforgeeks.org/find-duplicates-in-on-time-and-constant-extra-space/
 * @author Udita
 */
public class FindDuplicates {
    
    public Integer[] findDuplicates(int[] input) {
        Set<Integer> duplicates = new HashSet<>();
        
        for (int i = 0; i < input.length; i++) {
            if (input[Math.abs(input[i])] > 0) {
                input[Math.abs(input[i])] = -input[Math.abs(input[i])];
            } else {
                duplicates.add(input[i]);
            }
            
        }
        
        return duplicates.toArray(new Integer[0]);
    }

}
