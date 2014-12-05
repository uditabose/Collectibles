

package cs6253.project.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Udita
 */
public class ShardingUtil {
    
    private static MessageDigest _messageDigest;
    
    static {
        try {
            _messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            _messageDigest = null;
        } 
    }
    
    public static int getLeastSignificantBit(String aString) {
        return leastSignificantBit(aString);
    }
    
    private static int leastSignificantBit(String aString) {
        int leastSignificantBit = -1;
        
        if (aString == null || "".equals(aString.trim()) || _messageDigest == null) {
            return leastSignificantBit;
        }
        
        aString = aString.charAt(0) + aString.substring(1).toLowerCase();
        
        byte[] digested = _messageDigest.digest(aString.getBytes());
        leastSignificantBit = (int)(Math.abs(digested[digested.length - 1]) % 2);
        
        //System.out.printf(" String : %s :: leastSignificantBit : %d\n", aString, leastSignificantBit);
        
        return leastSignificantBit;
    }
    
    public static List[] populateWordLists(List<String> wordList) {
        List<String> shard0List = new ArrayList<>(); 
        List<String> shard1List = new ArrayList<>();
        for (String word : wordList) {
            int leastSignificantBit = leastSignificantBit(word);
            if (leastSignificantBit == 0) {
                shard0List.add(word);
            } else if (leastSignificantBit == 1) {
                shard1List.add(word);
            }
        }
        return new List[]{shard0List, shard1List};
    }

}
