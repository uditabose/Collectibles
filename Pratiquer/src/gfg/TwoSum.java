package gfg;

/**
 *
 * @author Udita
 */
public class TwoSum {

    public int[] twoSum(int[] nums, int target) {
        int minus = 0;
        for (int i = 0; i < nums.length; i++) {
            minus = target - nums[i];
            for (int j = 0; j < nums.length; j++) {
                if (i != j && nums[j] == minus) {
                    return new int[]{i + 1, j + 1};
                }
            }
        }
        
        return null;
        
    }

    private int binarySearch(int[] nums, int value, int lo, int hi) {
        if (hi == lo) {
            return -1;
        } else if (Math.abs(hi - lo) == 1) {
            if (value == nums[hi - 1]) {
                return (hi - 1);
            } else if (value == nums[lo]) {
                return lo;
            } else {
                return -1;
            }

        }

        int mid = (int) Math.floor(Math.abs(hi - lo) / 2) + lo;

        if (nums[mid] == value) {
            return mid;
        } else if (nums[mid] < value) {
            lo = mid;
        } else {
            hi = mid;
        }

        return binarySearch(nums, value, lo, hi);

    }
}
