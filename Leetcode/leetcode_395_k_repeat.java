import java.util.*;
import java.lang.*;

import static org.junit.Assert.assertEquals;

public class leetcode_395_k_repeat {

    public static void main(String[] args) {
        SolutionV3 solution = new SolutionV3();
        assertEquals(3, solution.longestSubstring("aaabb", 3));
        assertEquals(5, solution.longestSubstring("ababbc", 2));
        assertEquals(0, solution.longestSubstring("ababacb", 3));
        assertEquals(3, solution.longestSubstring("bbaaacbd", 3));
        assertEquals(12, solution.longestSubstring("aabcccbdddde", 1));
        assertEquals(11, solution.longestSubstring("aabcccbdddde", 2));
        assertEquals(4, solution.longestSubstring("aabcccbdddde", 3));
        assertEquals(4, solution.longestSubstring("aabcccbdddde", 4));
    }

    /**
     * Optimized Divide & Conquer solution for:
     * LeetCode 395: Longest Substring with At Least K Repeating Characters
     *
     * Logic:
     * - Count character frequencies in the current substring range.
     * - Find any character that appears >0 but <k times → such a character can NEVER be part of a valid substring.
     * - Use that character as a split-point to divide the string into subproblems.
     * - Recursively compute the longest valid substring in each partition.
     * - If all characters appear >= k times → the entire substring is valid.
     *
     * Time Complexity: O(26 * N) = O(N)
     * Space Complexity: O(1) — since character count array is constant (26)
     */
    static class SolutionV3 {

        /**
         * Public API — Finds longest substring of 's' where each character appears at least 'k' times.
         *
         * @param s input string consisting of lowercase English letters
         * @param k minimum number of repetitions per character
         * @return length of the longest valid substring
         */
        public int longestSubstring(String s, int k) {
            return longestSubstring(s.toCharArray(), 0, s.length() - 1, k);
        }

        /**
         * Recursive helper using divide & conquer on substring s[start..end].
         *
         * @param s character array for fast access
         * @param start starting index in substring
         * @param end ending index in substring
         * @param k repetition threshold
         * @return longest valid length within substring
         */
        private int longestSubstring(char[] s, int start, int end, int k) {

            // If length < k, no valid substring possible
            if (end < (start + k - 1)) {
                return 0;
            }

            // Count frequencies of characters in the current range
            int[] count = new int[26];
            Arrays.fill(count, 0);
            for (int i = start; i <= end; i++) {
                count[s[i] - 'a']++;
            }

            // Identify a character that occurs >0 but <k times → split here
            for (int i = 0; i < 26; i++) {
                if (count[i] > 0 && count[i] < k) {
                    char split = (char) ('a' + i);

                    int max = 0;
                    int left = start;

                    // Partition around every occurrence of the split character
                    for (int right = left; right <= end; right++) {
                        if (s[right] == split) {
                            // Recursively solve the left partition
                            max = Math.max(max, longestSubstring(s, left, right - 1, k));
                            left = right + 1; // Move to segment after split
                        }
                    }

                    // Solve the last partition segment after final split point
                    max = Math.max(max, longestSubstring(s, left, end, k));
                    return max;
                }
            }

            // All characters appear >= k times → entire substring is valid
            return end - start + 1;
        }
    }


    /**
     * Uses Divide & Conquer, this works but is very slow because we are splitting at each occurrenc eof character less than k
     * and unsable to resuse count array
     */
    static class SolutionV2 {

        int[] count = new int[26];
        char ch;
        int i;

        public int longestSubstring(String s, int k) {
            return longestSubstring(s.toCharArray(), 0, s.length() - 1, k);
        }

        private int longestSubstring(char[] s, int start, int end, int k) {
            if (end < (start + k - 1)) {
                return 0;
            }
            Arrays.fill(count, 0);
            for (i = start; i <= end; i++) {
                count[s[i] - 'a']++;
            }
            for (i = start; i <= end; i++) {
                ch = s[i];
                if (count[ch - 'a'] < k) {
                    return Math.max(
                            longestSubstring(s, start, i - 1, k),
                            longestSubstring(s, i + 1, end, k));
                }
            }
            return end - start + 1;
        }
    }

    /**
     * Not working
     */
    static class Solution {
        public int longestSubstring(String s, int k) {
            int[] used = new int[26];
            int[] avail = new int[26];
            Arrays.fill(used, 0);
            Arrays.fill(avail, 0);
            int result = 0;
            int length = 0;
            int start = -1;
            char[] chars = s.toCharArray();
            for (char ch : chars) {
                avail[ch - 'a']++;
            }
            for (int index = 0; index <= chars.length; index++) {
                int i = chars[index] - 'a';
                if ((used[i] + avail[i]) < k) {
                    if (length > result) {
                        result = length;
                    }
                    for (int j = start; j < index; j++) {
                        if (used[j] >= k) {

                        }
                    }
                    start = -1;
                    length = 0;
                    if (i < chars.length) {
                        avail[i]--;
                    }
                } else {
                    length++;
                    if (start == -1) {
                        start = index;
                    }
                    used[i]++;
                    avail[i]--;
                }
                index++;
            }
            if (length > result && getMin(used) >= k) {
                result = length;
            }
            return result;
        }

        private int getMin(int[] array) {
            int min = Integer.MAX_VALUE;
            for (int i : array) {
                if (i != 0 && i < min) {
                    min = i;
                }
            }
            return min;
        }
    }
}
