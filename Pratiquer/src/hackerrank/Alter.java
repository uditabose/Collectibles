

package hackerrank;

import java.util.Scanner;

/**
 *
 * @author Udita
 */
public class Alter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int noOfStrings = scanner.nextInt();
        
        while(0 < noOfStrings-- && scanner.hasNext()) {
            String testedString = scanner.next();
            char[] testedChars = testedString.toCharArray();
            char testChar = testedChars[0];
            int deleted = 0;
            
            for (int i = 1; i < testedChars.length; i++) {
                if (testChar == testedChars[i]) {
                    deleted++;
                } else {
                    testChar = testedChars[i];
                }
            }
            
            System.out.println(deleted);
            
        }
    }
}
