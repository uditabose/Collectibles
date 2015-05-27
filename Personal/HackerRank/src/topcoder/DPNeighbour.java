package topcoder;

import java.util.Arrays;

/**
 *
 * @author papa2
 */
public class DPNeighbour {

    public static void main(String[] args) {
        int[] input = new int[]{94, 40, 49, 65, 21, 21, 106, 80, 92, 81, 679, 4, 61,
            6, 237, 12, 72, 74, 29, 95, 265, 35, 47, 1, 61, 397,
            52, 72, 37, 51, 1, 81, 45, 435, 7, 36, 57, 86, 81, 72};
        //{11, 15};

        int[] sum = new int[input.length];

        int max = 0;
        for (int i = 0; i < input.length; i++) {
            sum[i] = input[i];
            if (sum[i] > max) {
                max = sum[i];
            }
        }

        int last = input.length - 1;
//        int[] select = new int[input.length];
//        for (int i = 0; i < select.length; i++) {
//            select[i] = -1;
//        }

        for (int i = 1; i < input.length; i++) {
            for (int j = 0; j < i; j++) {
                if (!isNeighbour(i, j, last)) {
                    if (sum[i] < sum[j] + input[i]) {
                        sum[i] = sum[j] + input[i];
                        if (max < sum[i]) {
                            max = sum[i];
                        }
                    }
                }

            }
        }

        System.out.println(Arrays.toString(sum));

        System.out.println(max);
    }

    private static boolean isNeighbour(int i, int j, int last) {
        if (i < 0 && j < 0) {
            return true;
        } else if (i == j) {
            return true;
        } else if (Math.abs(j - i) == 1) {
            return true;
        } else if (i == last && j == 0) {
            return true;
        }

        return false;
    }

}
