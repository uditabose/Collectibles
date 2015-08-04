

package practice;

/**
 *
 * @author Udita
 */
public class MergeArray {
    
    public int[] merge(int[] array1, int[] array2) {
        int[] finArray = new int[array1.length + array2.length];
        int position_1 = 0;
        int position_2 = 0;
        int finPos = 0;
        while (position_1 < array1.length || position_2 < array2.length) {
            if (position_1 < array1.length && position_2 < array2.length) {
                if (array1[position_1] <= array2[position_2]) {
                    finArray[finPos++] = array1[position_1];
                    position_1++;
                } else {
                    finArray[finPos++] = array2[position_2];
                    position_2++;
                }
            } else if (position_1 < array1.length && position_2 >= array2.length) {
                finArray[finPos++] = array1[position_1++];
            } else if (position_2 < array2.length && position_1 >= array1.length) {
                finArray[finPos++] = array2[position_2++];
            }
        }
        
        return finArray;
        
    }

}
