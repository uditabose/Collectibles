

package cs6253.project.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class which handles MD5 hashing, and sharding
 * based on MD5 hash
 * 
 * @author Udita
 */
public class ShardingUtil {
    
    private static MessageDigest _messageDigest;
    
    // initialize the message digest for MD5 algorithm
    static {
        try {
            _messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            _messageDigest = null;
        } 
    }
    
    /**
     * Returns the least significant bit of the word
     * @param aString word to be hashed
     * @return 0 or 1, the least significant bit
     */
    public static int getLeastSignificantBit(String aString) {
        return leastSignificantBit(aString);
    }
    
    private static int leastSignificantBit(String aString) {
        int leastSignificantBit = -1;
        
        if (aString == null || "".equals(aString.trim()) || _messageDigest == null) {
            return leastSignificantBit;
        }
        
        // per the dictionary, except for the first character all other characters
        // should be in lower-case
        aString = aString.charAt(0) + aString.substring(1).toLowerCase();
        
        byte[] digested = _messageDigest.digest(aString.getBytes());
        leastSignificantBit = (int)(Math.abs(digested[digested.length - 1]) % 2);
        
        return leastSignificantBit;
    }
    
    /**
     * Creates 2 shard list from the wordlist supplied, based on 
     * least significant bit of the word
     * 
     * @param wordList list to be sharded
     * @return an array of 2 shard lists
     */
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
