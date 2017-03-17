

package gfg.array;

import java.util.Arrays;

/**
 *
 * @author Udita
 */
public class Rotate2DArray {
    
    public char[][] rotateBy90(char[][] image) {
        int x = 0, y = 0;
        
        System.out.println(Arrays.deepToString(image));
        rotateBy90Bylayer(image, 0, 0, image.length);
        System.out.println(Arrays.deepToString(image));
        
        return image;
        
    }

    private void rotateBy90Bylayer(char[][] image, int x, int y, int length) {
        if (length <= 1) {
            return;
        }
        
        for (int i = y; i < length; i++) {
            swapForLayer(image, x, i, length);
        }
        
        rotateBy90Bylayer(image, x + 1, y + 1, length - 2);
        
    }

    private void swapForLayer(char[][] image, int x, int y, int length) {
        int swapX = y, swapY = (length - 1) - x;
        char temp = image[swapX][swapY];
        image[swapX][swapY] = image[x][y]; // first change
        
        swapX = (length - 1) - x; swapY = (length - 1) - y;
        char temp1 = image[swapX][swapY];
        image[swapX][swapY] = temp; // 2nd change
        
        swapX = (length - 1) - y; swapY = x;
        temp = image[swapX][swapY];
        image[swapX][swapY] = temp1;
        
        image[x][y] = temp;
    }

}
