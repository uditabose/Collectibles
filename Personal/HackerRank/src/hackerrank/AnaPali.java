

package hackerrank;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Udita
 */
public class AnaPali {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String mixedString = scanner.nextLine();
        Set<Character> pairSet = new HashSet<Character>();
        
        for (char testChar  : mixedString.toCharArray()) {
            if (pairSet.contains(testChar)) {
                pairSet.remove(testChar);
            } else {
                pairSet.add(testChar);
            }
        }
        
        if (pairSet.size() > 1) {
            System.out.println("NO");
        } else {
            System.out.println("YES");
        }
    }

}
