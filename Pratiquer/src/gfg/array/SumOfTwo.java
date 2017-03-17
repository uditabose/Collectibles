

package gfg.array;

import static util.SearchUtil.*;
import static util.SortUtil.*;

/**
 * Given an array A[] and a number x, check for pair in A[] with sum as x
 * @author Udita
 */
public class SumOfTwo {
    
    /**
     * O(NlogN) approach
     * 
     * 
     * @param inputArray
     * @param sum
     * @return 
     */
    public int[] sumOfTwoOnSort(int[] inputArray, int sum) {
        int[] result = new int[]{-1, -1};
        mergeSort(inputArray); // O(logN)
        int[] minusArray = new int[inputArray.length];
        for (int i = 0; i < inputArray.length; i++) { // O(N)
            minusArray[i] = sum - inputArray[i]; // O(1)
        }
        
        mergeSort(minusArray); // O(logN)
        int first = -1, second = -1;
        for (int i = 0; i < minusArray.length; i++) { // O(N)
            first = binarySearch(minusArray[i], inputArray); // O(logN)
            if (first >= 0) {
                break;
            }
        }
        
        if (first >= 0) {
            second = binarySearch(sum - inputArray[first], inputArray); // O(logN)
        }
        
        if (second >= 0) {
            result[0] = inputArray[first];
            result[1] = inputArray[second];
        }

        return result;
    }
    
    /**
     * 
     * O(N) approach
     * 
     * @param input
     * @param sum
     * @return 
     */
    public int[] sumOfTwo(int[] input, int sum) {
        int max = Integer.MIN_VALUE;
        for (int num : input) { // O(N)
            if (max < num) {
                max = num;
            }
        }
        
        int[] binArr = new int[max + 1];
        int[] result = new int[]{-1, -1};
        for (int i = 0; i < input.length; i++) { // O(N)
            int minus = sum - input[i]; // O(1)
            if (minus < binArr.length && binArr[minus] == 1) { // O(1)
                result[0] = input[i]; // O(1)
                result[1] = minus; // O(1)
                break;
            }
            binArr[input[i]] = 1; // O(1)
            
        }
        
        return result;
    }

}
