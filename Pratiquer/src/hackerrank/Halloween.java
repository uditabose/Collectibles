package hackerrank;

import java.math.BigInteger;
import java.util.Scanner;

/**
 *
 * @author Udita
 */
public class Halloween {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long inputNumber = -1l;
        long noOfTestCases = -1;
        int lineCount = 0;
        while (scanner.hasNext()) {
            inputNumber = scanner.nextLong();
            if (lineCount == 0) {
                noOfTestCases = inputNumber;
            } else {
                if (lineCount <= noOfTestCases) {
                    double left = Math.ceil(inputNumber / (double) 2);
                    double right = (long)Math.floor(inputNumber / (double) 2);
                    BigInteger max = BigInteger.valueOf((long)(left * right));
                    
                    System.out.println(String.valueOf(max));
                }

                if (lineCount == noOfTestCases) {
                    break;
                }
            }
            lineCount++;
        }
    }
}

/**
 * 10
6877550
9740081
2091018
5938116
1969577
4023883
3130903
1327048
339650
310570
 */

/**
 * 
 * 11825173500625
23717294471640
1093089069081
8815305407364
969808389732
4047908599422
2450638398852
440264098576
28840530625
24113431225 */