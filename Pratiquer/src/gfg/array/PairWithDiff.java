

package gfg.array;

/**
 * http://www.geeksforgeeks.org/find-a-pair-with-the-given-difference/
 * @author Udita
 */
public class PairWithDiff {
    
    public boolean findPairWithDifference(int[] input, int diff) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < input.length; i++) {
            max = Math.max(input[i], max);
        }
        // b - a = c
        // b = a + c
        // a = b - c
        int[] bin = new int[max+1];
        for (int i = 0; i < input.length; i++) {
            int ts = Math.abs(input[i] + diff);
            int td = Math.abs(input[i] - diff);
            
            if (ts < bin.length && bin[ts] == 1) {
                return true;
            } else if (td < bin.length && bin[td] == 1) {
                return true;
            }
            bin[input[i]] = 1;
            
        }
        return false;
    }

}
