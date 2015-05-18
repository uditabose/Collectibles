

package hackerrank.tries;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Udita
 */
public class SetIntersection {
    
    public static void main(String[] args) {
        Set<Integer> firstSet = new HashSet<>();
        firstSet.addAll(Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9}));
        Set<Integer> secondSet = new HashSet<>();
        secondSet.addAll(Arrays.asList(new Integer[]{3, 4, 5, 6, 7, 8, 9, 10, 11, 12}));
        
        firstSet.retainAll(secondSet);
        System.out.println(firstSet);
        System.out.println("----------------------------");
        System.out.println(secondSet);
    }

}
