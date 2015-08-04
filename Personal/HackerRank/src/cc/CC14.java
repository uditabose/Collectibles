
package cc;

/**
 * Write a method to replace all spaces in a string with'%20'. You may assume that
 * the string has sufficient space at the end of the string to hold the additional
 * characters, and that you are given the "true" length of the string. (Note: if implementing
 * in Java, please use a character array so that you can perform this operation in place.)
 * EXAMPLE
 * Input:
 * "Mr John Smith    "
 * Output: "Mr%20John%20Smith"
 * @author papa2
 */
public class CC14 {
    
    public String replaceBlank(char[] theString) {
        
        replaceBlanksFromEnd(theString, theString.length - 1);
        return new String(theString);  
    }

    private void replaceBlanksFromEnd(char[] theString, int lastIndex) {
        if (lastIndex <= 0) {
            return;
        }
        int spanStartIndex = lastIndex;
        
        for (int i = lastIndex; i >= 0; i--) {
            if (theString[i] != ' ') {
                spanStartIndex = i;
                break;
            }
        }
        
        lastIndex = swapRange(theString, lastIndex, spanStartIndex);
        replaceBlanksFromEnd(theString, lastIndex);
    }

    private int swapRange(char[] theString, int lastIndex, int spanStartIndex) {
        for (int i = spanStartIndex; i >= 0; i--) {
            if (theString[i] == ' ') {
                break;
            }
            if (lastIndex != i) {
                theString[lastIndex--] = theString[i];
                theString[i] = ' ';
            } else {
                lastIndex--;
            }
            
        }
        
        if (lastIndex <= 0) {
            return 0;
        }
        theString[lastIndex--] = '0';
        theString[lastIndex--] = '2';
        theString[lastIndex--] = '%';
        
        return lastIndex;
    }
    
}
