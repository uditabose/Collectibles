
package topcoder;

import java.util.Arrays;

/**
 *
 * @author papa2
 */
public class DPZigZag {

    public static void main(String[] args) {
        int[] input = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        //{1, 7, 4, 9, 2, 5};
        
        /*{374, 40, 854, 203, 203, 156, 362, 279, 812, 955, 
                        600, 947, 978, 46, 100, 953, 670, 862, 568, 188, 
                        67, 669, 810, 704, 52, 861, 49, 640, 370, 908, 
                        477, 245, 413, 109, 659, 401, 483, 308, 609, 120, 
                        249, 22, 176, 279, 23, 22, 617, 462, 459, 244};
        */
        Integer[] subSeq = new Integer[input.length];
        
        for (int i = 0; i < subSeq.length; i++) {
            subSeq[i] = 1;
        }
        
        int maxL = 1;
        for (int i = 1; i < input.length; i++) {
            for (int j = 0; j < i; j++) {
                if (subSeq[j] + 1 > subSeq[i]) {
                    if (isZigZag(input, i)) {
                        subSeq[i] = subSeq[j] + 1;
                        if (maxL < subSeq[i]) {
                            maxL = subSeq[i];
                        }
                    }
                }
                
            }
            
        }
        System.out.println(Arrays.deepToString(subSeq));
        System.out.println(maxL);
    }

    private static boolean isZigZag(int[] input, int i) {
        if (input.length < i) {
            return false;
        }
        
        int f = i - 2, m = i - 1, l = i;
        if (f < 0 || m < 0) {
            return false;
        }
        
        if ((input[m] - input[f]) > 0 && (input[l] - input[m]) < 0) {
            return true;
        } else if ((input[m] - input[f]) < 0 && (input[l] - input[m]) > 0) {
            return true;
        } else {
            return false;
        }
    }
    
}
