package hackerrank;

/**
 *
 * @author Udita
 */
public class CrackedBit {

    static boolean getBit(int num, int i) {
        return ((num & (1 << i)) != 0);
    }

    static int setBit(int num, int i) {
        return (num | (1 << i));
    }

    static int clearBit(int num, int i) {
        int mask = ~(1 << i);
        System.out.println("mask : " + Integer.toBinaryString(mask));
        return (num & mask);
    }

    static int clearBitsMSBthroughI(int num, int i) {
        int mask = (1 << i) - 1;
        System.out.println("mask : " + Integer.toBinaryString(mask));
        return (num & mask);
    }

    static int clearBitsIthrough0(int num, int i) {
        int mask = ~((1 << (i + 1)) - 1);
        System.out.println("mask : " + Integer.toBinaryString(mask));
        return (num & mask);
    }

    static int updateBit(int num, int i, int v) {
        int mask = ~(1 << i);
        System.out.println("mask : " + Integer.toBinaryString((v << i)));
        return (num & mask) | (v << i);
    }

    // problem 5.1
    static int prob51(int num, int rep, int left, int right) {
        int lmove = (rep << right);
        while (left > right) {
            num = clearBit(num, left--);
        }

        return (num | lmove);
    }

    static void prob53(int num) {
        System.out.println("num : " + Integer.toBinaryString(num));
        int small = num | ~(1 << (num - 1));
        System.out.println("small : " + Integer.toBinaryString(small));
        System.out.println("small 2 : " + Integer.toBinaryString((1 ^ num + 2) | num));
        //int big 
    }

    static int prob55(int num, int rep) {
        System.out.println("num : " + Integer.toBinaryString(num));
        System.out.println("rep : " + Integer.toBinaryString(rep));
        int xored = (num ^ rep);
        System.out.println("xored : " + Integer.toBinaryString(xored));

        return countSetBits(xored);
    }

    static int countSetBits(int n) {
        int count = 0;
        while (n > 0) {
            n &= (n - 1);
            count++;
        }
        return count;
    }
    
    static int prob56(int x) {
        System.out.println("prob56 :: " + Integer.toBinaryString(x));
        return ( ((x & 0xaaaaaaaa) >> 1) | ((x & 0x55555555) << 1) ); 
    }

    public static void main(String[] args) {
        /*
         System.out.println("cleared : " + Integer.toBinaryString(clearBit(5, 2)));
         System.out.println("clearBitsMSBthroughI : " + Integer.toBinaryString(clearBitsMSBthroughI(5, 2)));
         System.out.println("clearBitsIthrough0 : " + Integer.toBinaryString(clearBitsIthrough0(5, 2)));
         System.out.println("updateBit : " + Integer.toBinaryString(updateBit(5, 2, 4)));
         */
        int num = 0b100000000001;
        int rep = 0b10011;
        int left = 6;
        int right = 2;
        // 10001001100
        // 10001001100
        // System.out.println("prob51 :: " + Integer.toBinaryString(prob51(num, rep, left, right))); 

        //prob53(11);
        int n = 90;
        int m = 14;
        //System.out.println(": ((n & (n-1)) == 0) >> " +  ((n & (n-1))));
        System.out.println("prob55 :: " + prob55(n, m));
        System.out.println("prob56 :: " + Integer.toBinaryString(prob56(n)));
    }

}
