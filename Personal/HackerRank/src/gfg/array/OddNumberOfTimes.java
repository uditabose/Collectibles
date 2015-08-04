

package gfg.array;

/**
 *
 * @author Udita
 */
public class OddNumberOfTimes {
    
    public int theOddOne(int[] input) {
        int theOdd = input[0];
        for (int i = 1; i < input.length; i++) {
            theOdd = theOdd ^ input[i];
            
        }
        
        return theOdd;
    }

}
