
package practice;

/**
 *
 * @author papa2
 */
public class QuickSort {

    public int[] sort(int[] anArray) {
        quickSort(anArray, 0, anArray.length - 1);
        return anArray;
    }

    private void quickSort(int[] anArray, int lo, int hi) {
        if (lo < hi) {
            int part = partition(anArray, lo, hi);
            quickSort(anArray, lo, part);
            quickSort(anArray, part + 1, hi);
        }
    }

    private int partition(int[] anArray, int lo, int hi) {
        int pivot = anArray[hi];
        int tracker = lo - 1;
        for (int i = lo; i < hi - 1; i++) {
            if (anArray[i] < pivot) {
                tracker++;
                swap(anArray, i, tracker);
                        
            }
        }
        swap(anArray, tracker, hi);
        return tracker + 1;
    }

    private void swap(int[] anArray, int to, int from) {
        int temp = anArray[to];
        anArray[to] = anArray[from];
        anArray[from] = temp;
    }
}
