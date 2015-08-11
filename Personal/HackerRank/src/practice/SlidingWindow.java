package practice;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 *
 * @author Udita
 */
public class SlidingWindow {

    public static void maxSlidingWindow(int A[], int w) {
        Deque<Integer> Q = new LinkedList<>();
        for (int i = 0; i < w; i++) {
            while (!Q.isEmpty() && A[i] >= A[Q.getLast()]) {
                Q.pollLast();
            }
            Q.addLast(i);
        }
        
        
        System.out.println(" Q :: " + Q);
        int n = A.length;
        int[] B = new int[n];
        for (int i = w; i < n; i++) {
            B[i - w] = A[Q.getFirst()];
            while (!Q.isEmpty() && A[i] >= A[Q.getLast()]) {
                Q.pollLast();
            }
            System.out.println(" Q :: " + Q);
            while (!Q.isEmpty() && Q.getFirst() <= i - w) {
                Q.pollFirst();
            }
            Q.addLast(i);
            System.out.println(" Q :: " + Q);
        }
        B[n - w] = A[Q.getLast()];
        System.out.println(Arrays.toString(B));
    }
    
    public static void main(String[] args) {
        maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3);
    }

}
