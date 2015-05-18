

package hackerrank;

import java.util.Scanner;

/**
 *
 * @author Udita
 */
public class FlipBit {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int noOfNumbers = scanner.nextInt();
        
        while(noOfNumbers > 0) {
            int x = scanner.nextInt();
            int mask = (1 << 31) - 1;
            //x = ~(x + 1) + 1;
            System.out.println(Integer.toBinaryString(x));
            System.out.println(Integer.toBinaryString((x ^ 0xFFFFFFFF)));
            System.out.println(Integer.toBinaryString((x ^ 0xFFFFFFFF) >> 1));
            
            
            noOfNumbers--;
        }
        
    }

}
