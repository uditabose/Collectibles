

package gfg.array;

/**
 * http://www.geeksforgeeks.org/find-a-repeating-and-a-missing-number/
 * @author Udita
 */
public class MissingAndRecurring {
    //a + b + c + d + b = n(n+1)/2
    //a + b + b + d = n(n+1)/2 + b - c
    //0+0+c+0
    // abcd/abbd = c/b = x
    //c - b = y
    // (b + y)/b = x
    // 1 + y/b = x
    // b = y/(x-1)
    
    public int[] findMissingAndRecurring(int[] input) {
        int n = input.length;
        int idealSum = (n + 1) * n / 2;
        int actualSum = 0;
        int[] counter = new int[n+1];
        int recur = 0;
        for (int i = 0; i < n; i++) {
            if (counter[input[i]] == 1) {
                recur = input[i];
            } else {
                counter[input[i]] = 1;
            }
            actualSum += input[i];
        }
        int missing = idealSum + recur - actualSum;
        return new int[]{missing, recur};
        
    }
}
