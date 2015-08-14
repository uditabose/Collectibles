

package gfg.array;

/**
 *
 * @author Udita
 */
public class SmallestPositive {
    
    public int findSmallestPositive(int[] input) {
        int smallestWithoutPeer = input[0];
        int min = (input[0] < 0) ? Integer.MAX_VALUE : input[0];
        
        for (int i = 1; i < input.length; i++) {
            
            if (input[i] > 0) {
                min = Math.min(input[i], min);
                if (input[i] >  smallestWithoutPeer && input[i] == smallestWithoutPeer + 1) {
                    smallestWithoutPeer = input[i];
                }
            }
        }
        
        if (min == 2) {
            return 1;
        }
        
        return smallestWithoutPeer + 1;
    }

}
