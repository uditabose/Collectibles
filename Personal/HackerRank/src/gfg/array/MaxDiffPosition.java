

package gfg.array;

import java.util.Arrays;

/**
 * http://www.geeksforgeeks.org/given-an-array-arr-find-the-maximum-j-i-such-that-arrj-arri/
 * @author Udita
 */
public class MaxDiffPosition {
    
    public int findMaxDiffPosition(int[] input) {
        // {34, 8, 10, 3, 2, 80, 30, 33, 1};
        //int left = input.length/2, right = input.length/2 + 1;
        int[] leftMin = new int[input.length];
        leftMin[0] = Integer.MAX_VALUE;
        int[] rightMax = new int[input.length];
        rightMax[input.length - 1] = Integer.MIN_VALUE;
        for (int left = 1, right = input.length - 2; left < input.length && right >= 0; left++, right--) {
            leftMin[left] = Math.min(input[left], leftMin[left - 1]);
            rightMax[right] = Math.max(input[right], rightMax[right + 1]);
        }
        System.out.println(Arrays.toString(leftMin));
        System.out.println(Arrays.toString(rightMax));
        // -, 8, 8, 3, 2, 2, 2, 1
        // 80, 80, 80, 33, 33, -
        int maxDiff = Integer.MIN_VALUE;
        for (int left = 0, right = 0; left < leftMin.length && right < rightMax.length;) {
            if (leftMin[left] < rightMax[right]) {
                maxDiff = Math.max(right - left, maxDiff);
                right++;
            } else {
                left++;
            }
        }
        
        return maxDiff;
    }

}
