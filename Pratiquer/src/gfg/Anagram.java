

package gfg;

/**
 *
 * @author Udita
 */
public class Anagram {

    public boolean isAnagram(String first, String second) {
        if (isEmpty(first) || isEmpty(second)) {
            return false;
        }
        
        if (first.length() != second.length()) {
            return false;
        }
        
        int[] foundChars = new int[26];
        
        for (int i = 0; i < first.length(); i++) {
            foundChars[first.charAt(i) - 'A'] += 1;
        }
        
        for (int i = 0; i < second.length(); i++) {
            if (foundChars[second.charAt(i) - 'A'] <= 0) {
                return false;
            } else {
                foundChars[second.charAt(i) - 'A'] -= 1;
            }
        }
        
        return true;
    }
    
    public boolean isEmpty(String aString) {
        return (aString == null || aString.trim().length() == 0);
    }
}
