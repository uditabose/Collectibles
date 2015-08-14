

package quick;

/**
 *
 * @author Udita
 */
public class QuickCode {
    
    public static void main(String[] args) {
//        System.out.println( ('Z' - 'A'));
//        System.out.println(10 << 1);
//        System.out.println(10 >> 1);
//        System.out.println(20 ^ 20);
//        System.out.println(25 ^ 20);
//        System.out.println(20 ^ 25 ^ 20);
        
        int[] test = {12, 23, 34, 12, 12, 23, 12, 45};
        int xored = test[0];
        
        for (int i = 1; i < test.length; i++) {
            xored = xored ^ test[i];
            
        }
        
        System.out.println(xored);
    }

}
