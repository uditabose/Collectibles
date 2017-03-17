

package hackerrank;

import java.util.Scanner;

/**
 *
 * @author Udita
 */
public class UtoHeight {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int noOfTestCases = scanner.nextInt();
        
        while(noOfTestCases > 0) {
            System.out.println((int)compute(scanner.nextInt()));
            noOfTestCases--;
        }
    }
    
    private static double compute(int cycleNumber) {
        if (cycleNumber == 0) {
            return 1;
        }
        int cycleCoeff = (cycleNumber + 1)/2;
        double coeffPow = Math.pow(2, cycleCoeff);
        double height = 2 * coeffPow  - 2;
        if (cycleNumber % 2 == 0) {
            height += 1;
        }
        
        return height;
    }
}
