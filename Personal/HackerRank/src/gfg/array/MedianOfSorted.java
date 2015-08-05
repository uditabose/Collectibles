

package gfg.array;

/**
 * Assuming odd number of elements, and equal number of elements
 * 
 * @author Udita
 */
public class MedianOfSorted {
    
    public int medin(int[] aArray, int[] bArray) {
        // 10, 30, 50 - 30 => 30, 50
        // 20, 40, 60 - 40 => 20, 40
        // 10, 20, 30, 40, 50, 60
        
        return median0(aArray, 0, aArray.length - 1, bArray, 0, bArray.length - 1);
    }

    private int median0(int[] aArray, int aStart, int aEnd, int[] bArray, int bStart, int bEnd) {
        if (aEnd - aStart + 1 == 2) {
            return (Math.min(aArray[aEnd], bArray[bEnd]) 
                    + Math.max(aArray[aStart], bArray[bStart]))/ 2;
        }
        int aMid = aStart + (aEnd - aStart)/2;
        int bMid = bStart + (bEnd - bStart)/2;
        int aMedian = aArray[aMid];
        int bMedian = bArray[bMid];
        
        if (aMedian == bMedian) {
            return aMedian;
        } else if (aMedian < bMedian) {
            return median0(aArray, aMid, aEnd, bArray, bStart, bMid);
        } else {
            return median0(aArray, aStart, aMid, bArray, bStart, bMid);
        }
        
        
    }

}
