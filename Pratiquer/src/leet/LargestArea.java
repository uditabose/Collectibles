

package leet;

import java.util.Arrays;



/**
 *
 * @author Udita
 */
public class LargestArea {
    // 2,1,5,6,2,3
    public int largestRectangleAreaBrute(int[] height) {
        int n = height.length; // length of height array
        int maxArea[][] = new int[n][n]; // max area with rectangles from i-j
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n-1; i++) {
            for (int j = 1; j < n; j++) {
                maxArea[i][j] = Math.max(maxArea[i][j-1], (j - i + 1) * findMinCommonHeight(height, i, j));
                max = Math.max(max, maxArea[i][j]);
            }
        }
        
//        for (int[] area : maxArea) {
//            System.out.println(Arrays.toString(area));
//        }
        
        return max;
    }
    
    public int largestRectangleArea(int[] height) {
        int n = height.length; // length of height array
        int max = Integer.MIN_VALUE; // max area
        
        int[][] maxArea = new int[n][3];
        maxArea[0] = new int[]{0, 0, height[0]};
        
        for (int i = 1; i < n; i++) {
            int[] lastOne = maxArea[i-1];
            int tempArea = findMinCommonHeight(height, lastOne[0], i) * (i - lastOne[0] + 1);
            if (tempArea >= lastOne[2]) {
                maxArea[i] = new int[]{lastOne[0], i, tempArea};
            } else {
                maxArea[i] = new int[]{lastOne[0], lastOne[1], lastOne[2]};
            }
            
            if (max < maxArea[i][2]) {
                max = maxArea[i][2];
            }
            
            //System.out.println(Arrays.toString(maxArea[i]));
        }
        
        return max;
    
    }

    private int findMinCommonHeight(int[] height, int startIdx, int endIdx) {
        int min = Integer.MAX_VALUE;
        for (int i = startIdx; i <= endIdx; i++) {
            min = Math.min(min, height[i]); 
        }
        return min;
    }
    
    /*
    
    int maxArea = Integer.MIN_VALUE; // cumulative max area till i'th
        
        // trivial case, if length of height array is 1
        maxArea = height[0];
        
        if (height.length == 1) {
            return maxArea;
        }
        int lastStartIdx = 0;
        for (int i = 1; i < height.length; i++) {
            int minCommonHeight = findMinCommonHeight(height, lastStartIdx, i);
            maxArea = Math.max(maxArea, i)
        }
        // say we try to include i'th,
        // there is a max common height available, 
        // now we calculate the minimum of the height till i'th element
        // if this min is higher than or equals to the last min til (i-1), add the area of i'th
        // if not, then c
        
        
        return maxArea;
    
    */
    
}
