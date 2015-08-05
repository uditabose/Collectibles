

package gfg.array;

/**
 *
 * @author Udita
 */
public class SepEvenOdd {
    
    public int[] separateEvenOdd(int[] input) {
        // {12, 34, 45, 9, 8, 90, 3}
        int left = 0, right = input.length - 1;
        
        while (left < right) {
            if (input[left] % 2 == 0) {
                left++;
            } else {
                if  (input[right] % 2 == 0) {
                    swap(input, left++, right--);
                } else {
                    right--;
                }
            }
        }
        
        return input;
    }

    private void swap(int[] input, int left, int right) {
        int temp = input[left];
        input[left] = input[right];
        input[right] = temp;
    }

}
