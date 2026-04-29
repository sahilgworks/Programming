import java.util.*;
import java.lang.*;

class leetcode_862_shortest_subarray_k{

    public static void main(String[] args){
        SolutionV2 sol = new SolutionV2();
        int[] array = new int[]{1,2,3,6};
        int k = 6; 
        System.out.println("expected:  1 actual:"+sol.shortestSubarray(array, k));
        array = new int[]{-1,-2,-3,12};
        k= 10;
        System.out.println("expected:  1 actual:"+sol.shortestSubarray(array, k));
        array = new int[]{1};
        k= 1;
        System.out.println("expected:  1 actual:"+sol.shortestSubarray(array, k));
        array = new int[]{1,2};
        k= 4;
        System.out.println("expected:  -1  actual:"+sol.shortestSubarray(array, k));
        array = new int[]{2,-1,2};
        k= 3;
        System.out.println("expected:  3 actual:"+sol.shortestSubarray(array, k));
        array = new int[]{84, -37, 32, 40, 95};
        k= 167;
        System.out.println("expected:  3 actual:"+sol.shortestSubarray(array, k));
    }

    public static class SolutionV1{
        /**
         * what we need to do is to find shortest sliding window where sum is gte to k
         * we can use a shortest sliding window starting from first element.
         * if sum of this one element window is gte to k we can set result as 1
         * now extend this sliding windo - sw to 2 elements if size is gte to k compare
         * with result and if current sw length is less than result set result to current sw lengtho
         * now the question arises that if we keep on increasing the size of sw
         * then we would never reach the optimised solution because length is always increasing
         * one thing we can do since k >=1 that if we have the result with us
         * like 1,2,3,6 and k is 6 result would be 4 but expected answer is 1
         * what we can do is once we have a result we can shrink the window from left - there is no reason to shrink it from right
         * because we are scanning to right as a shrinked from right side only. 
         * so  lets say we keep on shrinking until the sum is lte to k, if we reach to equal to k value we compare with the result 
         * and if this new length is shorter than result we can set in result. also one thing to remember we can keep on comparing with 
         * result while shrinking the window from left.
         *
         * other use case is we have negative numbers in our array
         * let say -1,-2,-3,12 and k is 10
         * in this case if we apply above algorithm we will never get the result
         * because sum is always less than k
         * but if we stop scanning the array and skip the current value if it is less than 0
         * i think because k is gte 1 we can safely skip window which has a negative sum
         * or atleast we can shrink until we have a sum gte 0 or window is empty once we reach this state we can continue to scan further
         * because having neghative sum windows will never help us to to get a sum which is gte k
         *
         * ChatGPT: 
         *
         *A1: Negative sums are useless → discard
         *A2: Shrinking locally leads to optimal global answer
         *
         * both assumptions are wrong
         *
         * lets try to fix in SolutionV2
         *
         */
        public int shortestSubarray(int[] nums, int k) {
            int result = nums.length+1;
            int start = 0;
            int end = -1;
            int sum = 0;
            for(int i=0;i<nums.length;i++){
                if(start>end){
                    start = i;
                    end = i;
                }else{
                    end = i;
                }
                sum+=nums[i];
                if(sum>=k){
                    result = Math.min(result, end-start+1);
                    if(result == 1){
                        //can not be shorter than this
                        break;
                    }
                }
                //we will shrink the window from left
                //until the (window is empty or (windo sum is gte to 1 and window sum is lt k)
                while(start<=end && (sum<=0 || sum>=k)){
                    sum-=nums[start++];
                    if(sum>=k){
                        result = Math.min(result, end-start+1);
                        if(result == 1){
                            //can not be shorter than this
                            break;
                        }
                    }
                }
            }
            return (result==(nums.length+1))?-1:result;
        }
    }

    public static class SolutionV2{
        /**
         *In solutionv1 ChatGPT told us we have following two issues because test case: [84, -37, 32, 40, 95] , 167 is failing
         *
         *A1: Negative sums are useless → discard
         *A2: Shrinking locally leads to optimal global answer
         *
         * both assumptions are wrong
         *
         * lets try to fix in SolutionV2
         * 
         *
         *
         * i am trying to think how can we fix thid problems
         *
         * if we are shrinking elements how can we know that we need to stop now. 
         * like in the failing case we stopped at first index removal because now sum is less than k
         * but we can not eliminate all values considering we might need the last 1 or last few of sliding window. 
         *
         * i think we need to modify the algorithm. 
         *
         * i was not able to solve this problem and took help of ChatGPT
         *
         *
         * the essense of the solution is first calculate prefix sum for ech index
         *
         * once we calculate the prefix sum of an index, we need to compare the smallest prefix sums which satisfies the condition
         *
         * prefix[i] - prefix[j] >= k
         *
         * where i is current index
         * and j is front of deque. this deque stores the index of array such that front of the dequeu has minimum prefix sum and last of deque has maximum prefix sum
         * but one thing to remember we need to store indexed not values since we also need to calculte length of sub array
         *
         * for front index if it satisfird the above condition we can pop it from front since for future indices it is of no value
         * since the length will be longrer than current length since i will be larger than current i
         *
         *
         * we can keep on popping front of deque until front satisfies the above condition. but we would stop once font does not satisfies the above condition
         * because the deque is sorted in increasing order so future indices will have more value and no index will satisfy the above condition 
         * 
         * since ArrayDeque nor LinkedList supports sorted values we need to use PriorityQueue since we would always poll head of queue.
         *
         *
         */
        public int shortestSubarray(int[] nums, int k) {
            int result = nums.length+1;
            int start = 0;
            int end = -1;
            int sum = 0;
            int[] prefix = new int[nums.length];
            PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Comparator<Integer>(){
                public int compare(Integer left, Integer right){
                    return Integer.compare(prefix[left], prefix[right]);
                }

            });
            for(int i=0;i<nums.length;i++){
                sum+=nums[i];
                prefix[i] = sum;
                while(!pq.isEmpty() && prefix[i]-prefix[pq.peek()]>=k){
                    result = Math.min(result, i-pq.poll());
                    if(result == 1){
                        //can not be shorter than this
                        break;
                    }
                }
                pq.offer(i);
            }
            return (result==(nums.length+1))?-1:result;
        }
    }







}
