

package gfg.array;

/**
 * http://www.geeksforgeeks.org/find-the-maximum-element-in-an-array-which-is-first-increasing-and-then-decreasing/
 * @author Udita
 */
public class MxInIncreasDecreas {
    
    public int findMaxInIncrDecr(int[] input) {
        return findMaxInIncrDecr0(input, 0, input.length - 1);
    }

    private int findMaxInIncrDecr0(int[] input, int start, int end) {
        if (end - start == 0) {
            return input[start];
        } else if (end - start == 1) {
            return (input[start] > input[end]) ? input[start] : input[end];
        }
        
        int mid = start + (end - start)/2;
        if (input[mid - 1] <= input[mid] && input[mid + 1] <= input[mid]) {
            return input[mid];
        } else if (input[mid - 1] <= input[mid] && input[mid + 1] >= input[mid]) {
            return findMaxInIncrDecr0(input, mid, end);
        } else {
            return findMaxInIncrDecr0(input, start, mid);
        }
    }

}
