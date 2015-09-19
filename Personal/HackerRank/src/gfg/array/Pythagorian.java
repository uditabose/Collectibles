

package gfg.array;

import java.util.Arrays;
//import gfg.TwoSum;

/**
 * http://www.geeksforgeeks.org/find-pythagorean-triplet-in-an-unsorted-array/
 *
 * https://ideone.com/q2XhrR
 * @author Udita
 */


public class Pythagorian {

    // O(n2) with sum of two approach
    public boolean hasPytha(int[] input) {
        for (int i = 0; i < input.length; i++) {
            int t = input[i];
            input[i] = t * t;
        }
        
        int[] bin = null;
        boolean isFound = false;
        for (int i = 0; i < input.length; i++) {
            bin = sumOfTwo(input, input[i]);
            isFound = (bin[0] != -1 && bin[1] != -1);
            if (isFound) {
                break;
            }
        }
        
        return isFound;
        
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
            int minus =  Math.abs(sum - input[i]); // O(1)
            if (minus < binArr.length && binArr[minus] == 1) { // O(1)
                result[0] = input[i]; // O(1)
                result[1] = minus; // O(1)
                break;
            }
            binArr[input[i]] = 1; // O(1)
            
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        int[] input = {3, 1, 4, 6, 5};
        Pythagorian pytha = new Pythagorian();
        boolean hasIt = pytha.hasPytha(input);
        
        if (hasIt) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

}