

package hackerrank;

import java.math.BigInteger;
import java.util.Scanner;

/**
 *
 * @author Udita
 */
public class MaxCourse {
    
    public static void main(String[] args) {
        //long startTime = System.currentTimeMillis();
        Scanner scanner = new Scanner(System.in);
        String[] metas = scanner.nextLine().split(" ");
        int noOfInputs = Integer.valueOf(metas[0]);
        
        String[] courseBits = new String[noOfInputs];
        while(0 < noOfInputs && scanner.hasNextLine()) {
            courseBits[--noOfInputs] = scanner.nextLine();
        }
        
        int teamWithMax = 0, lastMax = Integer.MIN_VALUE;
        for (int i = 0; i < courseBits.length - 1; i++) {
            BigInteger prv = new BigInteger(courseBits[i], 2);
            for (int j = i + 1; j < courseBits.length; j++) {
                BigInteger nxt = new BigInteger(courseBits[j], 2);
                int cmax = prv.or(nxt).bitCount();
                if (cmax == lastMax) {
                    teamWithMax++;
                } else if (cmax > lastMax) {
                    teamWithMax = 1;
                    lastMax = cmax;
                }
                        
            }
        }
        System.out.println(lastMax);
        System.out.println(teamWithMax);
        //System.out.println((System.currentTimeMillis() - startTime)/1000);
    }

}
