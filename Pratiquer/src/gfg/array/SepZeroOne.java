

package gfg.array;

/**
 * http://www.geeksforgeeks.org/segregate-0s-and-1s-in-an-array-by-traversing-array-once/
 * @author Udita
 */
public class SepZeroOne {
    
    // push all the ones to right
    // 1, 0, 1, 1, 0, 0, 0, 1
    public int[] separateByPush(int[] input) {
        int posEnd = input.length - 1;
        for (int i = input.length - 1; i >= 0; i--) { // O(N)
            if (input[i] == 1) {
                input[posEnd] = 1;
                if (i != posEnd) {
                    input[i] = 0;
                }
                posEnd--;
            }
        }
        return input;
    }
    
    public int[] separateByCounting(int[] input) {
        int countZero = 0;
        for (int i = 0; i < input.length; i++) {
            if (input[i] == 0) {
                countZero++;
            } 
        }
        
        for (int i = 0; i < countZero; i++) {
            input[i] = 0;
        }
        
        for (int i = countZero; i < input.length; i++) {
            input[i] = 1;
        }
        return input;
    }
    //0 1 0 1 0 0 1 1 1 0

}
