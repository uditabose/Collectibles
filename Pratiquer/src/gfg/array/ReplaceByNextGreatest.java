

package gfg.array;

/**
 *
 * @author Udita
 */
public class ReplaceByNextGreatest {
    
    public int[] replaceByNextGreatest(int[] input) {
        
        int maxFromEnd = input[input.length - 1];
        input[input.length - 1] = -1;
        int temp = 0;
        for (int i = input.length - 2; i >= 0; i--) {
            temp = input[i];
            input[i] = maxFromEnd;
            maxFromEnd = Math.max(maxFromEnd, temp);
        }
        
        return input;
    }

}
