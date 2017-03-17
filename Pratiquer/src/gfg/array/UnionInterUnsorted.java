package gfg.array;

/**
* @author Udita Bose
* http://www.geeksforgeeks.org/find-union-and-intersection-of-two-unsorted-arrays/
*/
import java.util.Arrays;

public class UnionInterUnsorted {
    
    public int[] union(int[] input1, int[] input2) {
        int[] binArr = new int[findMax(input1, input2) + 1];
        int[] result = new int[input1.length + input2.length];
        
        for (int n : input1) {
            binArr[n] = 1;
        }
        
        for (int n : input2) {
            binArr[n] = 1;
        }
    
        int rcnt = 0;
        for (int i = 0; i < binArr.length; i++) {
            if (binArr[i] == 1) {
                result[rcnt++] = i;
            }
        }
        return result;
    }
    
    public int[] intersection(int[] input1, int[] input2) {
        int[] binArr = new int[findMax(input1, input2) + 1];
        int[] result = new int[Math.min(input1.length, input2.length)];
        
        for (int n : input1) {
            binArr[n] += 1;
        }
        
        for (int n : input2) {
            binArr[n] += 1;
        }
    
        int rcnt = 0;
        for (int i = 0; i < binArr.length; i++) {
            if (binArr[i] > 1) {
                result[rcnt++] = i;
            }
        }
        return result;
    }
    
    public int findMax(int[] input1, int[] input2) {
        int max = Integer.MIN_VALUE;
        for (int n : input1) {
            max = Math.max(max, n);
        }
        
        for (int n : input2) {
            max = Math.max(max, n);
        }
        
        return max;
    }
    
    public static void main(String[] args) {
        UnionInterUnsorted uius = new UnionInterUnsorted();
        
        int[] input1 = {7, 1, 5, 2, 3, 6};
        int[] input2 = {3, 8, 6, 20, 7};
        
        int[] union = uius.union(input1, input2);
        System.out.println(Arrays.toString(union));
        
        int[] inter = uius.intersection(input1, input2);
        System.out.println(Arrays.toString(inter));
        
    }
}