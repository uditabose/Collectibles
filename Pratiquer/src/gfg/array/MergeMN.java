

package gfg.array;

import java.util.Arrays;

/**
 *
 * @author Udita
 */
public class MergeMN {
    
    public int[] mergeMNInPlace(int[] mnArray, int[] nArray) {
        
        // 3, 4, *, *, 7, *, 9, 10, *, *, *
        // 8, 9, 12, 15, 20, 22
        // 3, 4, *, *, 7, *, 8, 9, *, *, *
        // 9, 10, 12, 15, 20, 22

        int mnPos = mnArray.length - 1;
        for (int i = mnArray.length - 1; i >= 0; i--) {
            if (mnArray[i] != -1) {
                mnArray[mnPos--] = mnArray[i];
            }
        }
        mnPos++;
        int nPos = 0, sPos = 0;
        for (; nPos < nArray.length; ) {
            
            if (nArray[nPos] > mnArray[mnPos]) {
                swap(mnArray, sPos++, mnPos++);
            } else {
                swap(mnArray, nArray, sPos++, nPos++);
            }
            
        }
        
        return mnArray;
    }
    
    private void swap(int[] aArray, int[] bArray, int aPos, int bPos) {
        //int temp = aArray[aPos];
        aArray[aPos] = bArray[bPos];
        //bArray[bPos] = temp;
    }
    
    private void swap(int[] aArray, int aPos, int bPos) {
        int temp = aArray[aPos];
        aArray[aPos] = aArray[bPos];
        aArray[bPos] = temp;
    }

}
