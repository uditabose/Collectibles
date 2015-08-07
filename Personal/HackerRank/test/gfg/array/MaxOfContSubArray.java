

package gfg.array;

import java.util.ArrayList;
import java.util.List;
import util.Heap;

/**
 * http://www.geeksforgeeks.org/maximum-of-all-subarrays-of-size-k/
 * @author Udita
 */
public class MaxOfContSubArray {

    public int[] maxOfContiguousSubArray(int[] input, int k) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < k; i++) {
            max = Math.max(input[i], max);
        }
        
        // 1, 2, 3, 4, 5, 6, 7
        // 9, 8, 6, 
        // 10, 60, 20, 30, 50, 70, 40
        
        List<Integer> maxArray = new ArrayList<>();
        return null;
    }
}
