

package util;

/**
 *
 * @author Udita
 */
public class SearchUtil {
    
    public static int binarySearch(int aNum, int[] anArray) {
        return binarySearch0(aNum, anArray, 0, anArray.length);
    }

    private static int binarySearch0(int aNum, int[] anArray, int lo, int hi) {
        if (hi == lo) {
            return (anArray[hi] == aNum) ? hi : -1;
        } else if (Math.abs(hi - lo) == 1) {
            if (aNum == anArray[hi - 1]) {
                return hi - 1;
            } else if (aNum == anArray[lo]) {
                return lo;
            } else {
                return -1;
            }
        }
        
        int mid = (int)Math.floor(Math.abs(hi - lo)/2) + lo;
        
        if (anArray[mid] == aNum) {
            return mid;
        } else if (anArray[mid] < aNum){
            lo = mid;
        } else {
            hi = mid;
        }
        
        return binarySearch0(aNum, anArray, lo, hi);
    }
}
