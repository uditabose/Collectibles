

package topcoder;


/**
 *
 * @author Udita
 */
public class ShortPalindromes {
    
    public static void main(String[] args) {
        String palinString = "TOPCODER";
        System.out.println(makePalindrome(palinString));
        //REDOCPOTOPCODER
        //REDTOCPCOTDER
    }

    
    private static String makePalindrome(String palinString) {
        
        String shortPalin = findShortestPalin(palinString, null, 0);
        return shortPalin;
    }

    private static String findShortestPalin(String palinString, String shortPalin, int shift) {
        if (shift >= palinString.length()/2) {
            return shortPalin;
        }
        if (palinString.charAt(shift) == palinString.charAt(palinString.length() - 1 - shift)) {
            return findShortestPalin(palinString, palinString, shift + 1);
        }
        
        String palin1 = doShortPalin(palinString, shift);
        String palin2 = doShortPalin(palinString, palinString.length() - 1 - shift);
        
        System.out.println(palin1 + " == " + palin2);
        
        if (shortPalin == null) {
            shortPalin = (palin1.compareTo(palin2) <= 0) ? palin1 : palin2;
        } else {
            String temp = (palin1.compareTo(palin2) <= 0) ? palin1 : palin2;
            shortPalin = (temp.compareTo(shortPalin) <= 0) ? temp : shortPalin; 
        }
        
        return findShortestPalin(palinString, shortPalin, shift + 1);
    }

    private static String doShortPalin(String palinString, int pivot) {
        char pivotChar = palinString.charAt(pivot);
        String left = palinString.substring(0, pivot), right = palinString.substring(pivot + 1);
        String leftRev = reverse(left), rightRev = reverse(right);
        
        String leftPalin = rightRev + left + pivotChar + leftRev + right;
        String rightPalin = left + rightRev + pivotChar + right + leftRev;
        System.out.println(leftPalin + " ** " + rightPalin);
        
        return (leftPalin.compareTo(rightPalin) <= 0) ? leftPalin : rightPalin;
    }

    private static String reverse(String toReverse) {
        char[] reversed = new char[toReverse.length()];
        for (int i = toReverse.length() - 1, j = 0; i >= 0; i--) {
            reversed[j++] = toReverse.charAt(i);
        }
        
        return new String(reversed);
    }

}
