

package util;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Udita
 */
public class SortUtil {
    
    public static void mergeSort(int[] input) {
        mergeSort0(input);
        
    }

    private static void mergeSort0(int[] input) {
        if (input.length <= 1) { return; }
        int mid = input.length >> 1;
        
        int[] left = Arrays.copyOfRange(input, 0, mid);
        int[] right = Arrays.copyOfRange(input, mid, input.length);
        mergeSort0(left);
        mergeSort0(right);
        merge(left, right, input);
    }

    private static void merge(int[] left, int[] right, int[] input) {
        int leftPos = 0, rightPos = 0;
        for (int i = 0; i < input.length; i++) {
            if (leftPos < left.length && rightPos < right.length) {
                if (left[leftPos] <= right[rightPos]) {
                    input[i] = left[leftPos++];
                } else {
                    input[i] = right[rightPos++];
                }
            } else {
                if (leftPos < left.length) {
                    input[i] = left[leftPos++];
                } else if (rightPos < right.length) {
                    input[i] = right[rightPos++];
                }
            }
        }
        
    }
    
    public static void quickSort(int[] input) {
        quickSort0(input, 0, input.length - 1);
    }

    private static void quickSort0(int[] input, int lo, int hi) {
        if (hi <= 1) return;
        int[] partitionKeys = partition(input, lo, hi);
        
        quickSort0(input, lo, partitionKeys[0] - lo + 1);
        quickSort0(input, partitionKeys[1], hi - (partitionKeys[1] - lo));
        
    }

    private static int[] partition(int[] input, int lo, int hi) {
        int partitionKey = lo + (new Random().nextInt(hi));
        int partitionValue = input[partitionKey];
        int leftTracker = lo - 1, current = lo, rightTracker = lo + hi;
        
        while(current < rightTracker) {
            if (input[current] < partitionValue) {
                swap(input, current++, ++leftTracker);
            } else if (input[current] > partitionValue) {
                swap(input, current, --rightTracker);
            } else {
                current++;
            }
        }
        
        return new int[]{leftTracker, rightTracker};
                
    }

    private static void swap(int[] input, int from, int to) {
        int temp = input[to];
        input[to] = input[from];
        input[from] = temp;
    }

}
