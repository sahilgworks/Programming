import java.util.*;
import java.lang.*;

class leetcode_239_sliding_window_max{

    public static void main(String[] args){
        Solution sol = new Solution();
        System.out.println("expected: {3,3,5,5,6,7} actual: "+Arrays.toString(sol.maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7},3)));
        System.out.println("expected: {1} actual: "+Arrays.toString(sol.maxSlidingWindow(new int[]{1},1)));

    }
    static class Solution{

        /**
         *
         *
         * one idea is we can use insertionsort on array of length k
         * which means each search takes logk time using binary search
         * each removal takes K time
         * each insertion takes K time
         * total number of window are len - k + 1 so outer loop runs for N times and inner loop runs for K times
         * total time complexity is N * K
         *
         * my other thought is can we make this faster may be N log K or N
         *
         *
         * if we use heap sort instead of insertion sort. removal and insertion takes logK time but finding element to remove takes O(K) time
         *
         * may be if we maintain a hashmap of index verssus object to remove and then we just use that object.
         * but this can lead to huge memory usage. what if k~N then memory usage jumps to O(N) instead of O(K) 
         * 
         * can we make this more memory efficient 
         * 
         * proboem is we know which element and which value need to be removed but the problem is we dont know where it lies for both insertion sort and heap
         *
         *
         * i took help of chat gpt since my answer was taking O(NlogK) but expected answer is O(n) . followin is from chat gpt
         *
         *
         *
         * SLIDING WINDOW MAX — MONOTONIC DEQUE (MENTAL TEMPLATE)
         *
         *    Goal:
         *   Maintain only "useful" candidates for maximum.
         *
         *   Core Idea:
         *   If a newer element is bigger, older smaller ones are useless forever.
         *
         *              Rules:
         *
         *                1. Expand (process nums[i]):
         *                  while deque not empty AND nums[back] < nums[i]:
         *                     remove back   ← eliminate dominated elements
         *                add i to back
         *
         *               2. Shrink (window size > k):
         *                 if front index is out of window:
         *                    remove front
         *
         *            3. Answer:
         *                  nums[front] = maximum
         *
         *               Key Invariant:
         *              Deque stores indices in decreasing order of values.
         *
         *               Why it works:
         *              - Back removals → eliminate future-useless elements
         *             - Front → always current maximum
         *            - Each element enters and leaves once → O(n)
         *
         *               One-line intuition:
         *              "A larger element kills all smaller elements before it."
         *              
         *
         *
         * What i will do it to implement this.
         *
         * i will create a deque to read from begining and remove from begining and add to end of queue
         * we will maintain just indexes not values
         * when we slide the window if firt index is not equal to slided index no operation is required else we will poll. 
         *
         * also now add new element in queue
         *
         * the first of queue is new maximum - but why              
         *  the reason is if we have a new value which is larger then queue end we will poll it until we find a larger value than current value in that case the exteeme left ones are the max value and values on right are possible maximum of future windows
         *
         **/
        public int[] maxSlidingWindow(int[] nums, int k) {
            Deque<Integer> deque = new ArrayDeque<Integer>();
            for(int i=0;i<k;i++){
                insert(nums, deque, i);
            }
            int[] result = new int[nums.length - k + 1];
            int j=0;
            result[j++] = nums[deque.peekFirst()];
            for(int i=k;i<nums.length;i++){
                if(deque.peekFirst() == i-k){
                    deque.pollFirst();
                }
                insert(nums, deque, i);
                result[j++] = nums[deque.peekFirst()];
            }
            return result;
        }
        void insert(int[] nums, Deque<Integer> deque, int i){
            while(!deque.isEmpty() && nums[deque.peekLast()] < nums[i]){
                deque.pollLast();
            } 
            deque.offerLast(i);
        }
    }   




}
