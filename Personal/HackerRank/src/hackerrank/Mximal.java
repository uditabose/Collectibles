

package hackerrank;

import java.util.Scanner;

/**
 *
 * @author Udita
 */
public class Mximal {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int min = scanner.nextInt();
        int max = scanner.nextInt();
        
        int maximal = 0;
        for (int i = min; i <= max; i++) {
            for (int j = i; j <= max; j++) {
                int temp = (i ^ j);
                if (temp > maximal) {
                    maximal = temp;
                }
            }
        }
        
        System.out.println(maximal);
    }

}
