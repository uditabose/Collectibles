
package learning.wip;

import java.util.Arrays;

/**
 *
 * @author papa2
 */
public class BasicSort {
    private static int[] bubbleSort(int[] input) {
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length - 1; j++) {
                if (input[j] > input[j + 1]) {
                    int temp = input[j];
                    input[j] = input[j + 1];
                    input[j + 1] = temp;
                }
            }
            
        }
        
        return input;
    }
    
    private static int[] insertionSort(int[] input) {
        for (int i = 0; i < input.length; i++) {
            for (int j = i; j < input.length; j++) {
                if (input[i] > input[j]) {
                    int temp = input[i];
                    input[i] = input[j];
                    input[j] = temp;
                }
                
            }
            
        }
        return input;
    }
    
    private static int[] mergeSort(int[] input) {
        if (input.length == 1) {
            return input;
        }
        int mid = (input.length - 1)/2;
        int[] left = subArray(input, 0, mid);
        int[] right = subArray(input, mid + 1, input.length - 1);
        left = mergeSort(left);
        right = mergeSort(right);
        input = merge(left, right);
        return input;
    }
    
    private static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        
        int lPos =  0, rPos = 0, cPos = 0;
        while (cPos < result.length) {
            if (lPos < left.length && rPos < right.length) {
                if (left[lPos] < right[rPos]) {
                    result[cPos++] = left[lPos++];
                } else {
                    result[cPos++] = right[rPos++];
                }
            } else if (rPos >= right.length) {
                result[cPos++] = left[lPos++];
            } else {
                result[cPos++] = right[rPos++];
            }
        }

        return result;
    }
    
    private static int[] subArray(int[] input, int start, int end) {
        int[] output = new int[end - start + 1];
        System.arraycopy(input, start, output, 0, (end - start + 1));
        return output;
    }
    
    
    private  static int[] copyArray(int[] input) {
        int[] output = new int[input.length];
        System.arraycopy(input, 0, output, 0, input.length);
        return output;
    }
    
    private static void printArray(int[] intArray) {
        System.out.println(Arrays.toString(intArray));
    }
    
    public static void main(String[] args) {
        int[] input = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        //{12, 8, 123, 7, 0, 17, 98, 43, 81};

        printArray(bubbleSort(copyArray(input)));
        printArray(insertionSort(copyArray(input)));
        printArray(mergeSort(copyArray(input)));
        printArray(input);
        
    }

    

    

    
}
