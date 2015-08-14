

package gfg.array;

/**
 *
 * @author Udita
 */
public class NextGreatest {
    
    public int[] nextGreatest(int[] input) {
        int maxOnRight = input[input.length - 1];
        int[] greatest = new int[input.length];
        greatest[greatest.length - 1] = -1;
        //{4, 5, 2, 25}
        for (int i = input.length - 2; i >= 0; i--) {
            maxOnRight = Math.max(input[i + 1], maxOnRight);
            if (input[i+1] > input[i]) {
                greatest[i] = input[i+1];
                
            } else if (greatest[i+1] > input[i]) {
                greatest[i] = greatest[i+1];
            } else if (maxOnRight > input[i]) {
                greatest[i] = maxOnRight;
            } else {
                greatest[i] = -1;
            }
            
        }
        return greatest;
    }

}
