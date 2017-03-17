
package gfg.array;

/**
 * http://www.geeksforgeeks.org/merge-two-sorted-arrays-o1-extra-space/
 * 
 * Input: ar1[] = {1, 5, 9, 10, 15, 20};
 *        ar2[] = {2, 3, 8, 13};
 * Output: ar1[] = {1, 2, 3, 5, 8, 9}
 *         ar2[] = {10, 13, 15, 20}  
 * 
 * @author uditabose
 */
public class MergeInSeparate {
    public int[][] mergeInSeparate(int[] array1, int[] array2) {
        for (int i = array2.length - 1; i >= 0; i--) {
            int last = array1[array1.length - 1];
            
            int j = - 1;
            for (j = array1.length - 1; j >= 0 && array1[j] > array2[i]; j--) {
                array1[j] = array1[j-1];
            }
            
            array1[j+1] = array2[i];
            array2[i] = last;            
        }
        
        int[][] retArray = new int[2][];
        retArray[0] = array1;
        retArray[1] = array2;
        return retArray;
    }
}
