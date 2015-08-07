

package gfg.array;

/**
 *
 * @author Udita
 */
public class NextGreatest {
    
    public int[] nextGreatest(int[] input) {
        int[] greatest = new int[input.length];
        greatest[greatest.length - 1] = -1;
        //{4, 5, 2, 25}
        for (int i = input.length - 2; i >= 0; i--) {
            if (input[i+1] > input[i]) {
                greatest[i] = input[i+1];
                
            } else if (greatest[i+1] > input[i]) {
                greatest[i] = greatest[i+1];
            } else {
                greatest[i] = -1;
            }
            
        }
        return greatest;
    }

}
