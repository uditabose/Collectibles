
package practice;

/**
 *
 * @author papa2
 */
public class BinarySerach {

    public boolean search(int aNum, int[] anArray) {
        return search(aNum, anArray, 0, anArray.length);
    }

    private boolean search(int aNum, int[] anArray, int lo, int hi) {
        if (hi == lo) {
            return (anArray[hi] == aNum);
        } else if (Math.abs(hi - lo) == 1) {
            return (aNum == anArray[hi - 1] || aNum == anArray[lo]);
        }
        
        int mid = (int)Math.floor(Math.abs(hi - lo)/2) + lo;
        
        if (anArray[mid] == aNum) {
            return true;
        } else if (anArray[mid] < aNum){
            lo = mid;
        } else {
            hi = mid;
        }
        
        return search(aNum, anArray, lo, hi);
    }
    
}
