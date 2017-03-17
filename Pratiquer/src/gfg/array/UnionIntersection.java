

package gfg.array;

/**
 * http://www.geeksforgeeks.org/union-and-intersection-of-two-sorted-arrays-2/
 * @author Udita
 */
public class UnionIntersection {
    
    public int[][] unionIntersection(int[] aArray, int[] bArray) {
        int[][] unionIntersection = new int[2][];
        unionIntersection[0] = new int[aArray.length + bArray.length];
        unionIntersection[1] = new int[Math.min(aArray.length, bArray.length)];
        
        int posA = 0, posB = 0, inPos = 0, unPos = 0;
        // arr1[] = {1, 3, 4, 5, 7}
        // arr2[] = {2, 3, 5, 6}
        // {1, 2, 3, 4, 5, 6, 7} 
        // {3, 5}
        while(posA < aArray.length || posB < bArray.length) {
            if (posA < aArray.length && posB < bArray.length ) {
                if (aArray[posA] == bArray[posB]) {
                    unionIntersection[0][unPos++] = aArray[posA];
                    unionIntersection[1][inPos++] = aArray[posA];
                    posA++; posB++;
                } else if (aArray[posA] < bArray[posB]) {
                    unionIntersection[0][unPos++] = aArray[posA];
                    posA++;
                } else {
                    unionIntersection[0][unPos++] = bArray[posB];
                    posB++;
                }
            } else if (posA < aArray.length) {
                unionIntersection[0][unPos++] = aArray[posA++];
            } else {
                unionIntersection[0][unPos++] = bArray[posB++];
            }
        }
        
        return unionIntersection;
    }

}
