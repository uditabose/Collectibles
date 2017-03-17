

package gfg.array;
import java.util.Arrays;
import static util.SearchUtil.*;

/**
 *
 * @author Udita
 */
public class SearchInPivot {
    
    public int searchInPivot(int[] input, int searchable) {
        //2, 3, 4, 5, 6, 7, 8, 1
        
        int pivot = findPivot(input);
        
        int pos = binarySearch(searchable, Arrays.copyOfRange(input, 0, pivot + 1));
        if (pos < 0) {
            pos = binarySearch(searchable, Arrays.copyOfRange(input, pivot, input.length));
            if (pos >= 0) {
                pos += pivot;
            }
        }
        
        return pos;
    }

    private int findPivot(int[] input) {
        return findPivot0(input, 0, input.length);
    }

    private int findPivot0(int[] input, int first, int last) {
        if (last - first <= 1) {
            return first;
        }
        int mid = first + (last - first)/2;
        if (input[mid] > input[mid + 1] && input[mid] > input[mid - 1]) {
            return mid;
        } else if (input[mid] > input[mid - 1]) {
            return findPivot0(input, mid, last);
        } else {
            return findPivot0(input, first, mid);
        }
    }
 
}
