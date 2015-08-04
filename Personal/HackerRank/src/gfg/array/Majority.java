

package gfg.array;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Udita
 */
public class Majority {
    
    public int getMajorityViaHashMap(int[] input) {
        Map<Integer, Integer> majorityMap = new HashMap<>();
        for (int num : input) {
            if (majorityMap.containsKey(num)) {
                int occur = majorityMap.get(num) + 1;
                if (occur > input.length/2) {
                    return num;
                }
                majorityMap.put(num, occur);
            } else {
                majorityMap.put(num, 1);
            }
        }
        
        return -1;
    }
    
    public int getMajorityInplace(int[] input) {
        
        int majIdx = 0;
        int inCnt = 1;
        int occur = 0;
        
        while(inCnt < input.length) {
            if (input[majIdx] == input[inCnt]) {
                occur++;
            } else {
                occur--;
            }
            if (occur == 0) {
                majIdx = inCnt;
                occur = 1;
            }
            inCnt++;
        }
        
        occur = 0;
        for (int num : input) {
            if (num == input[majIdx]) {
                occur++;
            }
            
            if (occur > input.length/2) {
                return input[majIdx];
            }
        }
        
        return -1;
    }

}
