
package practice;

import com.sun.org.apache.bcel.internal.Constants;
import java.util.Arrays;

/**
 *
 * @author papa2
 */
public class MergeSort {
    
    public int[] sort(int[] anArr) {
        
        mergeSort(anArr, 0, anArr.length - 1);
        return anArr;
    }

    private void mergeSort(int[] anArr, int lo, int hi) {
        if (lo < hi) {
            
            int mid = lo + (int)(hi - lo)/2;
            
            mergeSort(anArr, lo, mid);
            mergeSort(anArr, mid+1, hi);
            merge(anArr, lo, mid, hi);
        }
    }

    private void merge(int[] anArr, int lo, int mid, int hi) {
        
        int[] tempArr = new int[hi - lo + 1];
        int lIdx= lo, rIdx = mid + 1, tIdx = 0;

        while(lIdx <= mid && rIdx <= hi ) {
            if (anArr[lIdx] <= anArr[rIdx]) {
                tempArr[tIdx++] = anArr[lIdx++];
            } else {
                tempArr[tIdx++] = anArr[rIdx++];
            }
        }

        while(lIdx <= mid) {
            tempArr[tIdx++] = anArr[lIdx++];
        }
        
        // copy left
        while(rIdx <= hi) {
            tempArr[tIdx++] = anArr[rIdx++];
        }
        
        // Copy tmp back
        for(int i = 0; i < tempArr.length; i++) {
            anArr[lo + i] = tempArr[i];
        }
    }
    

}
