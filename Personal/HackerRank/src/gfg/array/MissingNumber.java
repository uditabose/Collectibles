

package gfg.array;

/**
 *
 * @author Udita
 */
public class MissingNumber {
    
    public int missingNumber(int[] input) {
        int n = input.length + 1;
        int cumSum = n * (n + 1) / 2;
        for (int num : input) {
            cumSum -= num;
        }
        
        return cumSum;
    }
    
    public int missingNumberXOR(int[] input) {
        int missing = input[0] ^ 1;
        int cnt = 1;
        while (cnt < input.length) {
            missing = missing ^ (cnt + 1) ^ input[cnt];
            cnt++;
        }
        return (missing ^ (cnt + 1));
    }

}
