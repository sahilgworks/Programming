import java.util.*;
import java.lang.*;

public class leetcode_215_k_largest{


    public static void main(String[] args){
        new leetcode_215_k_largest().run();
    }


    private void run(){
        testcase(new int[]{3,2,1,5,6,4}, 2);
        testcase(new int[]{3,2,3,1,2,4,5,5,6}, 4);

    }

    private void testcase(int[] nums, int k){
        int result =  new Solution().findKthLargest(nums, k);
        System.out.println("solution: "+result);
    }

    class Solution {
        public int findKthLargest(int[] nums, int k) {
            PriorityQueue<Integer> pq = new PriorityQueue<Integer>(k);
            for(int num : nums){
                if(pq.size()<k){
                    pq.offer(num);
                }else if(pq.size()==k && pq.peek()<=num){
                    pq.poll();
                    pq.offer(num);
                }
            }
            return pq.peek();
        }
    }

}
