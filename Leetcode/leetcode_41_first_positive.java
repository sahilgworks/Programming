import java.util.HashMap;
import java.lang.*;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class leetcode_41_first_positive {

    public static void main(String[] args) {
        leetcode_41_first_positive.SolutionV1 solution = new leetcode_41_first_positive.SolutionV1();
        assertEquals(3, solution.firstMissingPositive(new int[]{1, 2, 0}));
        System.out.println("test 1 passed");
        assertEquals(2, solution.firstMissingPositive(new int[]{3, 4, -1, 1}));
        System.out.println("test 2 passed");
        assertEquals(1, solution.firstMissingPositive(new int[]{7, 8, 9, 11, 12}));
        System.out.println("test 3 passed");
        assertEquals(2, solution.firstMissingPositive(new int[]{1}));
        System.out.println("test 4 passed");
    }

    static class SolutionV1 {


        void swap(int[] nums, int l, int r) {
            int g = nums[l];
            nums[l] = nums[r];
            nums[r] = g;
        }

        public int firstMissingPositive(int[] nums) {

            int len = nums.length;
            int value = 0;
            for (int index = 0; index < len; ) {
                value = nums[index];
                if (value > len || value < 1) {
                    //no op required
                    index++;
                } else {
                    if (nums[value - 1] != nums[index]) {
                        swap(nums, value - 1, index);
                    } else {
                        index++;
                    }
                }
            }
            for (int index = 0; index < len; index++) {
                if (nums[index] != (index + 1)) {
                    return index + 1;
                }
            }
            return len + 1;
        }

    }
}