

package gfg.array;

/**
 *
 * @author Udita
 */
public class ReverseArray {
    
    public int[] reverse(int[] toReverse) {
        for (int i = 0, j = toReverse.length - 1; j - i > 0; i++, j--) {
            swap(toReverse, i, j);
        }
        return toReverse;
    }

    private void swap(int[] toReverse, int i, int j) {
        int temp = toReverse[i];
        toReverse[i] = toReverse[j];
        toReverse[j] = temp;
    }

}
