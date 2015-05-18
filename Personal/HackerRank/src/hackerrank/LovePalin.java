

package hackerrank;

import java.util.Scanner;

/**
 *
 * @author Udita
 */
public class LovePalin {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int noOfStrings = scanner.nextInt();
        
        while(0 < noOfStrings-- && scanner.hasNext()) {
            String testedString = scanner.next();
            
            int left = 0, right = testedString.length() - 1;
            int shifted = 0;
            while(right - left >= 1) {
                shifted += Math.abs((int)testedString.charAt(right) - (int)testedString.charAt(left));
                right--;
                left++;
            }
            System.out.println(shifted);
            
        }
    }

}
