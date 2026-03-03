import static org.junit.Assert.assertEquals;
import java.util.*;
import java.lang.*;


public class leetcode_560_subarray_sum {

    public static void main(String[] args) {
        leetcode_560_subarray_sum.SolutionV3 solution = new leetcode_560_subarray_sum.SolutionV3();
        assertEquals(2, solution.subarraySum(new int[]{1, 1, 1}, 2));
        assertEquals(2, solution.subarraySum(new int[]{1, 2, 3}, 3));
        assertEquals(1, solution.subarraySum(new int[]{-1, -1, 1}, 1));
    }

    static class SolutionV3 {
        public int subarraySum(int[] nums, int k) {
            int result = 0;
            HashMap<Integer, Integer> freq = new HashMap<>();
            freq.put(0, 1);
            int prefix  = 0;
            Integer temp = 0;
            for (int i = 0; i < nums.length; i++) {
                prefix+= nums[i];
                temp = freq.get(prefix-k);
                if (temp != null) {
                    result+=temp;
                }
                freq.put(prefix, freq.getOrDefault(prefix, 0) + 1);
            }
            return result;
        }
    }

    /**
     * this solution works but is very slow, this is kind of brute force. takes o(n2) time.
     */
    static class SolutionV2 {
        public int subarraySum(int[] nums, int k) {
            int result = 0;
            for (int i = nums.length - 1; i >= 0; i--) {
                if (nums[i] == k) {
                    result++;
                }
                for (int j = i + 1; j < nums.length; j++) {
                    nums[j] += nums[i];
                    if (nums[j] == k) {
                        result++;
                    }
                }
            }
            return result;
        }
    }

    /**
     * this solution is incorrect
     */
    static class SolutionV1 {

        class Result {
            int value = 0;

            void inc() {
                value++;
            }
        }

        public int subarraySum(int[] nums, int k) {
            Result result = new Result();
            subarraySum(nums, k, nums.length, result, 0, 0);
            return result.value;
        }


        public void subarraySum(int[] nums, int k, int length, Result result, int prevSum, int index) {
            if (index >= length) {
                return;
            }
            int temp = 0;
            if (index != 0) {
                temp = prevSum + nums[index];
                if (temp == k) {
                    result.inc();
                } else {
                    subarraySum(nums, k, length, result, temp, index + 1);
                }
            }
            temp = nums[index];
            if (temp == k) {
                result.inc();
            } else {
                subarraySum(nums, k, length, result, temp, index + 1);
            }
            return;
        }
    }
}
